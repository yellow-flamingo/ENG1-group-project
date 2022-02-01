package dev.thelabradors.yorkpirates;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ApplicationAdapter implements InputProcessor, Screen {

    YorkPiratesGame game;

    //V_WIDTH, V_HEIGHT - viewport width and height, 
    //      The amount the user can see at any given time.
    public static final int V_WIDTH = 1600;
    public static final int V_HEIGHT = 900;
    public float cameraX;
    public float cameraY;
    OrthographicCamera camera;
    Viewport viewport;
    TiledMapRenderer tiledMapRenderer;
    static Building constantine;
    static Building goodricke;
    static Building james;
    Player player;
    MapProperties mapProp;

    int mapWidth;
    int mapHeight;
    int tilePixelWidth;
    int tilePixelHeight;
    public static int mapPixelWidth;
    public static int mapPixelHeight;
    int mapBorderRight;
    int mapBorderTop;
    long startTime;

    AssetManager manager;

    boolean showControls;

    boolean constantineCapture;
    boolean goodrickeCapture;
    boolean jamesCapture;
    public static int numOfCoins;
    public static int nonTimerPoints;


    Vector2 emptyVector = new Vector2();
    // Information a
    int[] coinsData = {
        700, 850, 22, 22,
        250, 250, 22, 22,
        300, 500, 22, 22,
        500, 350, 22, 22,
        2000, 800, 22, 22,
        850, 200, 22, 22,
        325, 1500, 22, 22,
        500, 1300, 22, 22,
        700, 1700, 22, 22,
        1500, 1200, 22, 22};

    //@Override
    /**
     * Instantiates the GameScreen class.
     * @param game passes the YorkPiratesGame class, 
     *      which contains many of the shared varibles
     */
    public GameScreen(YorkPiratesGame game) {
        this.game = game;
    }

    /**
     * Called when screen changes to GameScreen
     */
    @Override
    public void show() {
        // Creation of camera allows for the camera to be manipulated and moved
        camera = game.camera;
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        // Creating viewport allows for the screen to be scaled and retain aspect ratio.
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        
        manager = game.manager;
        // tiledmap allows for the used of "Tiled" application to create game maps.
        TiledMap tiledMap = manager.get(game.imgMap.get("map"));
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        // Calculating properties around the map.
        mapProp = tiledMap.getProperties();

        mapWidth = mapProp.get("width", Integer.class);
        mapHeight = mapProp.get("height", Integer.class);
        tilePixelWidth = mapProp.get("tilewidth", Integer.class);
        tilePixelHeight = mapProp.get("tileheight", Integer.class);
        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        //System.out.println("mapW: " + mapPixelWidth + " mapH: " + mapPixelHeight);

        // Borders of the rightmost and topmost where the camera no longer follows the player
        // But stays static.
        mapBorderRight = mapPixelWidth - V_WIDTH/2;
        mapBorderTop = mapPixelHeight - V_HEIGHT/2;

        Gdx.input.setInputProcessor(this);

        player = new Player(manager.get(game.imgMap.get("player"), Texture.class), (TiledMapTileLayer) tiledMap.getLayers().get(1), game);

        // Clear the contents of the Arrays.
        game.bullets.clear();
        game.enemys.clear();
        game.coins.clear();

        // Creation of the enemys and the coins
        constantine = new Building(manager.get(game.imgMap.get("enemy-ship"), Texture.class), 1000, 600, 7, 180.0f);
        goodricke = new Building(manager.get(game.imgMap.get("enemy-ship"), Texture.class), 700, 2200, 4, 40.0f);
        james = new Building(manager.get(game.imgMap.get("enemy-ship"), Texture.class), 2600, 2500, 3, -20.0f);

        game.enemys.add(constantine);
        game.enemys.add(goodricke);
        game.enemys.add(james);

        // Adding all the current coins to the game.
        for (int i = 0; i < coinsData.length; i+= 4){
                Coin c = game.coinPool.obtain();
                c.resetHelper(coinsData[i], coinsData[i+1],
                    coinsData[i+2],coinsData[i+3]);
                game.coins.add(c);
        }
        // Starting game variables, added here so that they are reset given a restart condition
        // restart conditions:
        //      enter is pressed
        //      all enemies are killed + all coins collected.
        constantineCapture = false;
        goodrickeCapture = false;
        jamesCapture = false;
        numOfCoins = 0;
        nonTimerPoints = 0;
        showControls = true;

        startTime = TimeUtils.millis();
    }

    
    /** 
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    
    /** 
     * @param delta
     */
    @Override
    public void render(float delta) {

        if (!manager.update()){
            float progress = manager.getProgress();
            System.out.println("Loading... " + progress * 100 + "%");
        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cameraX = player.getX() + player.getWidth()/2;
        if (cameraX < V_WIDTH/2){
            cameraX = V_WIDTH/2;
        }else if (cameraX  > mapBorderRight){
            cameraX = mapBorderRight;
        }
        cameraY = player.getY() + player.getHeight()/2;
        if (cameraY < V_HEIGHT/2){
            cameraY = V_HEIGHT/2;
        } else if (cameraY > mapBorderTop){
            cameraY = mapBorderTop;
        }
        camera.position.set(cameraX, cameraY, 0);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        // If an item needs to be removed, add to _ToRemove ArrayList, 
        // then remove at the end of for loop
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        ArrayList<Coin> coinsToRemove = new ArrayList<>();

        // Drawing and collision of sprites
        for (Bullet bullet:game.bullets){
            bullet.draw(game.batch);
            for (Enemy enemy : game.enemys){
                if (bullet.collisionCheck(enemy.getBoundingRectangle())){
                    enemy.hit();
                    enemy.healthUpdate();
                }
            }
            if (bullet.getRemove()){
                bulletsToRemove.add(bullet);
                game.bulletPool.free(bullet);
            }
        }

        for (Enemy enemy : game.enemys){
            enemy.draw(game.batch);
            if (enemy.getRemove()){
                enemiesToRemove.add(enemy);
                //If a college is removed, then we want to place a coin down.
                if (enemy == constantine) {
                    constantineCapture = true;
                    Coin c = game.coinPool.obtain();
                    c.resetHelper(enemy.getX(), enemy.getY()+250, 10 ,10);
                    c.setValue(5);
                    game.coins.add(c);
                    nonTimerPoints += 100;
                } else if (enemy == goodricke) {
                    goodrickeCapture = true;
                    Coin c = game.coinPool.obtain();
                    c.resetHelper(enemy.getX()+200, enemy.getY()-120, 10, 10);
                    c.setValue(5);
                    game.coins.add(c);
                    nonTimerPoints += 100;
                } else if (enemy == james) {
                    jamesCapture = true;
                    Coin c = game.coinPool.obtain();
                    c.resetHelper(enemy.getX()-200, enemy.getY()-120, 10, 10);
                    c.setValue(5);
                    game.coins.add(c);
                    nonTimerPoints += 100;
                }

            }
        }
        game.bullets.removeAll(bulletsToRemove);
        game.enemys.removeAll(enemiesToRemove);

        for (Coin coin : game.coins){
            coin.draw(game.batch);
            coin.collisionCheck(player.getBoundingRectangle());
            if (coin.getRemove()){
                coinsToRemove.add(coin);
                numOfCoins += coin.getValue();
                game.coinPool.free(coin);
            }
        }
        game.coins.removeAll(coinsToRemove);

        player.draw(game.batch);
        // Drawing the various text on the screen
        game.font.getData().setScale(4.5f);
        game.font.setColor(1,1,1,1);
        if (showControls == true){
            game.font.draw(game.batch, "ENTER: restart",        (float) (camera.position.x + 50),    (float) (camera.position.y - 30));
            game.font.draw(game.batch, "SPACE: SHOOT",          (float) (camera.position.x + 50),    (float) (camera.position.y - 90));
            game.font.draw(game.batch, "UP/W: FORWARDS",        (float) (camera.position.x + 50),    (float) (camera.position.y - 150));
            game.font.draw(game.batch, "LEFT/A: TURN LEFT",     (float) (camera.position.x + 50),    (float) (camera.position.y - 210));
            game.font.draw(game.batch, "DOWN/S: BACKWARDS",     (float) (camera.position.x + 50),    (float) (camera.position.y - 270));
            game.font.draw(game.batch, "RIGHT/D: TURN RIGHT",   (float) (camera.position.x + 50),    (float) (camera.position.y - 330));
            game.font.draw(game.batch, "H: toggle help",        (float) (camera.position.x + 50),    (float) (camera.position.y - 390));
        }
        game.font.getData().setScale(6f);
        game.font.draw(game.batch, Tasks.getNewTask(), camera.position.x - V_WIDTH/2, camera.position.y + V_HEIGHT/2);
        game.font.draw(game.batch, "Coins: " + numOfCoins, camera.position.x - V_WIDTH/2, camera.position.y + V_HEIGHT/2 - 100);
        game.font.draw(game.batch, "Points: " + ((int) (TimeUtils.timeSinceMillis(startTime) / 500) + nonTimerPoints), camera.position.x - V_WIDTH/2, camera.position.y + V_HEIGHT/2 - 200);

        game.font.getData().setScale(4f);
        game.font.setColor(1,0,0,1);

        if (constantineCapture) {
            game.font.setColor(0,1,0,1);
        }
        game.font.draw(game.batch, "Constantine", 935, 825);
        game.font.setColor(1,0,0,1);

        if (goodrickeCapture) {
            game.font.setColor(0,1,0,1);
        }
        game.font.draw(game.batch, "Goodricke", 400, 2500);
        game.font.setColor(1,0,0,1);

        if (jamesCapture) {
            game.font.setColor(0,1,0,1);
        }
        game.font.draw(game.batch, "James", 2400, 2800);
        game.font.setColor(1,0,0,1);

        game.font.getData().setScale(3f);
        game.font.draw(game.batch, game.playerCollege, player.getX() - player.getWidth()/2, player.getY());

        game.batch.end();

        // ShapeRenderer, used if any shapes are to be drawn.
        game.sr.setProjectionMatrix(camera.combined);
        //game.sr.begin(ShapeType.Line);
        //game.sr.end();
        
        // The user has won if they kill all enemies and there are no coins on the screen.
        if (game.enemys.isEmpty() && game.coins.isEmpty()) {
            game.setScreen(game.gameWonScreen);
        }
    }
    /** Used to hide certain objects */
    @Override
    public void hide() {
    }
    /**
     * Dispose of any assets which no longer need to be used.
     */
    @Override
    public void dispose(){
        super.dispose();
        manager.dispose();
    }
    /**
     * Allows the program to check for keyboard buttons being pressed.
     * @param   keycode can be retried from libGDX
     * @return  boolean when the method is used.
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A){
            player.turnLeft();
        }
        if (keycode == Input.Keys.D){
            player.turnRight();
        }
        if (keycode == Input.Keys.W){
            player.moveForward();
        }
        if (keycode == Input.Keys.S){
            player.moveBackwards();
        }
        //Creates a bullet (retrieves by pooling for memory management)
        if (keycode == Input.Keys.SPACE){
            Bullet b = game.bulletPool.obtain();
            b.setdxdy(player.getCorrectedAngle());
            b.setPos(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2);
            game.bullets.add(b);
        }
        // if (keycode == Input.Keys.P){
        //     System.out.println(camera.position);
        // }
        // if (keycode == Input.Keys.U){
        //     player.getCoords();
        // }
        if (keycode == Input.Keys.UP){
            player.moveForward();
        }
        if (keycode == Input.Keys.DOWN){
            player.moveBackwards();
        }
        if (keycode == Input.Keys.LEFT){
            player.turnLeft();
        }
        if (keycode == Input.Keys.RIGHT){
            player.turnRight();
        }
        if (keycode == Input.Keys.ENTER){
            game.setScreen(game.gameScreen);
        }
        if (keycode == Input.Keys.H){
            showControls = !showControls;
        }
        return true;
    }
    /**
     * Allows for the program to check for keyboard buttons being released.
     * @param   keycode keycode is an integer, which can be retrieved from libGDX
     * @return  boolean when function is used for keys being released
     */
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A){
            player.stopTurn();
        }
        if (keycode == Input.Keys.D){
            player.stopTurn();
        }
        if (keycode == Input.Keys.W){
            player.stopMove();
        }
        if (keycode == Input.Keys.S){
            player.stopMove();
        }
        if (keycode == Input.Keys.UP){
            player.stopMove();
        }
        if (keycode == Input.Keys.DOWN){
            player.stopMove();
        }
        if (keycode == Input.Keys.LEFT){
            player.stopTurn();
        }
        if (keycode == Input.Keys.RIGHT){
            player.stopTurn();
        }
        return true;
    }
    
    /** 
     * @param character
     * @return boolean
     */
    //For Keys that are typed
    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /** 
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return boolean
     */
    //active when a mouse button is pressed down
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /** 
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return boolean
     */
    //active when a mouse button is released
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /** 
     * @param screenX
     * @param screenY
     * @param pointer
     * @return boolean
     */
    //Active when mouse button is down and pointers moves
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /** 
     * @param screenX
     * @param screenY
     * @return boolean
     */
    //active when pointer moves
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /** 
     * @param amountX
     * @param amountY
     * @return boolean
     */
    //active when scroll wheel is moved.
    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        return false;
    }
}