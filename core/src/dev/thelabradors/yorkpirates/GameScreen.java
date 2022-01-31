package dev.thelabradors.yorkpirates;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ApplicationAdapter implements InputProcessor, Screen {

    YorkPiratesGame game;

    public static final int V_WIDTH = 1600;
    public static final int V_HEIGHT = 900;
    public float cameraX;
    public float cameraY;
    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    TiledMapRenderer tiledMapRenderer;
    BitmapFont font;
    static Building constantine;
    static Building goodricke;
    static Building james;
    static Building derwent;
    Coin coin1;
    Coin coin2;
    Coin coin3;
    Coin coin4;
    Coin coin5;
    Coin coin6;
    Coin coin7;
    Coin coin8;
    Coin coin9;
    Coin coin10;
    Coin coin11;
    InputProcessor inputProcessor;
    BitmapFont font12;
    Player player;
    Building building1;
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

    boolean showControls = true;

    ArrayList<Bullet> bullets;
    private BulletPool bulletPool;
    static ArrayList<Enemy> enemys;
    ArrayList<Coin> coins;
    private CoinPool coinPool;
    Vector2 emptyVector = new Vector2();

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
    public GameScreen(YorkPiratesGame game) {
        this.game = game;
    }


    @Override
    public void show() {
        camera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        TiledMap tiledMap = new TmxMapLoader().load("Test1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        System.out.println("width: " + camera.viewportWidth + " height: " + camera.viewportHeight);
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        manager = new AssetManager();
        manager.load("bullet.png", Texture.class);
        manager.load("Boat2.png", Texture.class);
        manager.load("enemy-ship.png", Texture.class);
        manager.load("coin.png", Texture.class);
        manager.finishLoading();

        mapProp = tiledMap.getProperties();

        mapWidth = mapProp.get("width", Integer.class);
        mapHeight = mapProp.get("height", Integer.class);
        tilePixelWidth = mapProp.get("tilewidth", Integer.class);
        tilePixelHeight = mapProp.get("tileheight", Integer.class);
        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        System.out.println("mapW: " + mapPixelWidth + " mapH: " + mapPixelHeight);

        mapBorderRight = mapPixelWidth - V_WIDTH/2;
        mapBorderTop = mapPixelHeight - V_HEIGHT/2;

        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        player = new Player(manager.get("Boat2.png", Texture.class), (TiledMapTileLayer) tiledMap.getLayers().get(1));

        bullets = new ArrayList<>();
        bulletPool = new BulletPool();

        enemys = new ArrayList<>();

        coins = new ArrayList<>();
        coinPool = new CoinPool();
        
        constantine = new Building(manager.get("enemy-ship.png", Texture.class), 1000, 600, 180.0f);
        goodricke = new Building(manager.get("enemy-ship.png", Texture.class), 700, 2200, 40.0f);
        james = new Building(manager.get("enemy-ship.png", Texture.class), 2600, 2500, -20.0f);
        derwent = new Building(manager.get("enemy-ship.png", Texture.class), 2700, 600, -150.0f);
        enemys.add(constantine);
        enemys.add(goodricke);
        enemys.add(james);
        enemys.add(derwent);
        //Adding all the current coins to the game.
        for (int i = 0; i < coinsData.length; i+= 4){
                Coin c = coinPool.obtain();
                c.resetHelper(coinsData[i], coinsData[i+1],
                    coinsData[i+2],coinsData[i+3]);
                coins.add(c);
        }
        System.out.println(coins.size());
        startTime = TimeUtils.millis();
    }
    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }
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
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        ArrayList<Coin> coinsToRemove = new ArrayList<>();

        for (Bullet bullet:bullets){
            bullet.draw(spriteBatch);
            for (Enemy enemy : enemys){
                if (bullet.collisionCheck(enemy.getBoundingRectangle())){
                    enemy.hit();
                    enemy.healthUpdate();
                }
            }
            if (bullet.getRemove()){
                bulletsToRemove.add(bullet);
                bulletPool.free(bullet);
            }
        }

        for (Enemy enemy : enemys){
            enemy.draw(spriteBatch);
            if (enemy.getRemove()){
                enemiesToRemove.add(enemy);
                if (enemy == constantine) {
                    Coin c = coinPool.obtain();
                    c.resetHelper(enemy.getX(), enemy.getY()+250, 10 ,10);
                    coins.add(c);
                } else if (enemy == goodricke) {
                    coins.add(new Coin((Texture) (manager.get("coin.png", Texture.class)), enemy.getX()+200, enemy.getY()-120, 10, 10));
                } else if (enemy == james) {
                    coins.add(new Coin((Texture) (manager.get("coin.png", Texture.class)), enemy.getX()-200, enemy.getY()-120, 10, 10));
                }

            }
        }
        bullets.removeAll(bulletsToRemove);
        enemys.removeAll(enemiesToRemove);

        for (Coin coin : coins){
            coin.draw(spriteBatch);
            coin.collisionCheck(player.getBoundingRectangle());
            if (coin.getRemove()){
                coinsToRemove.add(coin);
                coinPool.free(coin);
            }
        }
        coins.removeAll(coinsToRemove);

        player.draw(spriteBatch);
        font.getData().setScale(4.5f);
        font.setColor(1,1,1,1);
        if (showControls == true){
            font.draw(spriteBatch, "SPACE: SHOOT",      (float) (camera.position.x + 50),    (float) (camera.position.y - 90));
            font.draw(spriteBatch, "UP/W: FORWARDS",      (float) (camera.position.x + 50),    (float) (camera.position.y - 150));
            font.draw(spriteBatch, "LEFT/A: TURN LEFT",   (float) (camera.position.x + 50),    (float) (camera.position.y - 210));
            font.draw(spriteBatch, "DOWN/S: BACKWARDS",   (float) (camera.position.x + 50),    (float) (camera.position.y - 270));
            font.draw(spriteBatch, "RIGHT/D: TURN RIGHT", (float) (camera.position.x + 50),    (float) (camera.position.y - 330));
            font.draw(spriteBatch, "H: toggle help",    (float) (camera.position.x + 50),    (float) (camera.position.y - 390));
        }
        font.getData().setScale(6f);
        font.draw(spriteBatch, Tasks.getNewTask(), camera.position.x - V_WIDTH/2, camera.position.y + V_HEIGHT/2);
        font.draw(spriteBatch, "Coins: " + Coin.getNumCoins(), camera.position.x - V_WIDTH/2, camera.position.y + V_HEIGHT/2 - 100);
        font.draw(spriteBatch, "Points: " + (int) (TimeUtils.timeSinceMillis(startTime) / 500), camera.position.x - V_WIDTH/2, camera.position.y + V_HEIGHT/2 - 200);

        font.getData().setScale(4f);
        font.setColor(1,0,0,1);
        font.draw(spriteBatch, "Constantine", 935, 825);
        font.draw(spriteBatch, "Goodricke", 400, 2500);
        font.draw(spriteBatch, "James", 2400, 2800);
        font.draw(spriteBatch, "Derwent", 2500, 825);

        font.getData().setScale(3f);
        font.draw(spriteBatch, game.playerCollege, player.getX() - player.getWidth()/2, player.getY());

        spriteBatch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        shapeRenderer.rect(V_WIDTH/2, V_HEIGHT/2, mapBorderRight - V_WIDTH/2, mapBorderTop - V_HEIGHT/2);
        shapeRenderer.end();

        if (enemys.isEmpty() && coins.isEmpty()) {
            Coin.numCoins = 0;
            game.setScreen(new GameWonScreen(game));
        }
    }


    @Override
    public void hide() {
    }

    @Override
    public void dispose(){
        super.dispose();
        manager.dispose();
        font.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
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
        if (keycode == Input.Keys.SPACE){
            Bullet b = bulletPool.obtain();
            b.setdxdy(player.getCorrectedAngle());
            b.setPos(player.getPosition());
            bullets.add(b);
        }
        if (keycode == Input.Keys.P){
            System.out.println(camera.position);
        }
        if (keycode == Input.Keys.U){
            player.getCoords();
        }
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
            game.setScreen(new GameWonScreen(game));
        }
        if (keycode == Input.Keys.H){
            showControls = !showControls;
        }
        return true;
    }

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
    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        return false;
    }
    private class BulletPool extends Pool<Bullet>{

        @Override
        protected Bullet newObject() {
            return new Bullet(manager.get("bullet.png", Texture.class), player.getCenter(), player.getCorrectedAngle());
        }
    }
    private class CoinPool extends Pool<Coin>{
        @Override
        protected Coin newObject() {
            // TODO Auto-generated method stub
            return new Coin(manager.get("coin.png", Texture.class), 0,0,1,1);
        }
    }
}