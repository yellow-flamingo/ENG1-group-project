package dev.thelabradors.yorkpirates;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.awt.*;

import static java.awt.Color.*;

public class TileMap2 extends ApplicationAdapter implements InputProcessor, Screen {

    YorkPiratesGame game;

    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 600;
    public static float screenWidth = Gdx.graphics.getWidth();
    public static float screenHeight = Gdx.graphics.getHeight();
    public float cameraX;
    public float cameraY;
    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    TiledMapRenderer tiledMapRenderer;
    BitmapFont font;
    Player player;
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

    AssetManager manager;

    Vector2 position;
    Vector2 barrelOffset;
    float angle;

    ArrayList<Bullet> bullets;
    ArrayList<Enemy> enemys;
    ArrayList<Coin> coins;

    //@Override
    public TileMap2(YorkPiratesGame game) {
        this.game = game;
        //float aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        //System.out.println("The width: " + Gdx.graphics.getWidth());
        //System.out.println("The height: " + Gdx.graphics.getHeight());
        //System.out.println("The height: " + Toolkit.getDefaultToolkit().getScreenSize());
        //camera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight() * 2);

        //camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        //camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        viewport = new FitViewport(camera.viewportWidth/2, camera.viewportHeight/2, camera);
        TiledMap tiledMap = new TmxMapLoader().load("Test1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        manager = new AssetManager();
        manager.load("bullet.png", Texture.class);
        manager.load("Boat2.png", Texture.class);
        manager.load("enemy-ship.png", Texture.class);
        manager.load("coin.png", Texture.class);
        manager.finishLoading();

        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        position = new Vector2();
        barrelOffset = new Vector2(1.0f, -0.5f).scl(0.5f); // Relative coordinates to where the barrel is in the sprite
        player = new Player((Texture) (manager.get("Boat2.png", Texture.class)), (TiledMapTileLayer) tiledMap.getLayers().get(1));
        bullets = new ArrayList<>();
        enemys = new ArrayList<>();
        coins = new ArrayList<>();
        constantine = new Building((Texture) (manager.get("enemy-ship.png", Texture.class)), 1000, 600, 180.0f);
        goodricke = new Building((Texture) (manager.get("enemy-ship.png", Texture.class)), 700, 2200, 40.0f);
        james = new Building((Texture) (manager.get("enemy-ship.png", Texture.class)), 2600, 2500, -20.0f);
        derwent = new Building((Texture) (manager.get("enemy-ship.png", Texture.class)), 2700, 600, -150.0f);
        enemys.add(constantine);
        enemys.add(goodricke);
        enemys.add(james);
        enemys.add(derwent);
        coin1 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 700, 850, 22, 22);
        coins.add(coin1);
        coin2 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 250, 250, 22, 22);
        coins.add(coin2);
        coin3 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 300, 500, 22, 22);
        coins.add(coin3);
        coin4 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 500, 350, 22, 22);
        coins.add(coin4);
        coin5 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 2000, 800, 22, 22);
        coins.add(coin5);
        coin6 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 850, 200, 22, 22);
        coins.add(coin6);
        coin7 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 325, 1500, 22, 22);
        coins.add(coin7);
        coin8 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 500, 1300, 22, 22);
        coins.add(coin8);
        coin9 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 700, 1700, 22, 22);
        coins.add(coin9);
        coin10 = new Coin((Texture) (manager.get("coin.png", Texture.class)), 1500, 1200, 22, 22);
        coins.add(coin10);
    }

//    @Override
//    public void resize(int width, int height){
//        viewport.update(width, height);
//    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        System.out.println(camera.position.x);
        System.out.println(player.getX());

        if (!manager.update()){
            float progress = manager.getProgress();
            System.out.println("Loading... " + progress * 100 + "%");
        }

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector3 vec=new Vector3(player.getX(),player.getY(),0);
        camera.project(vec);

        cameraX = (float) (player.getX() + Gdx.graphics.getWidth()/1.1 + player.getWidth());
        cameraY = (float) (player.getY() + Gdx.graphics.getHeight()/1.2 + player.getHeight());

        //camera.position.set(position, 0);

        if (player.getX() < 100) {
            cameraX = 100 + (float) (Gdx.graphics.getWidth()/1.1 + player.getWidth());
        }
        if (player.getY() < 100) {
            cameraY = 100 + (float) (Gdx.graphics.getHeight()/1.2 + player.getHeight());
        }

        if (player.getX() > 700) {
            cameraX = 700 + (float) (Gdx.graphics.getWidth()/1.1 + player.getWidth());
        }
        if (player.getY() > 1674) {
            cameraY = 1674 + (float) (Gdx.graphics.getHeight()/1.2 + player.getHeight());
        }
        //camera.position.set(position, 0);

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
            }
        }
        for (Enemy enemy : enemys){
            enemy.draw(spriteBatch);
            if (enemy.getRemove()){
                enemiesToRemove.add(enemy);
                if (enemy == constantine) {
                    coins.add(new Coin((Texture) (manager.get("coin.png", Texture.class)), enemy.getX(), enemy.getY()+250, 10, 10));
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
            if(coin.collisionCheck(player.getBoundingRectangle())){
                coin.getRemove();
            }
            if (coin.getRemove()){
                coinsToRemove.add(coin);
            }
        }
        coins.removeAll(coinsToRemove);

        player.draw(spriteBatch);
        font.getData().setScale(5f);
        font.setColor(1,1,1,1);

        font.draw(spriteBatch, "SPACE: SHOOT", camera.position.x - - (int) (screenWidth/2.5), camera.position.y - (int) (screenHeight/2.7));
        font.draw(spriteBatch, "UP: FORWARDS", camera.position.x - - (int) (screenWidth/2.5), camera.position.y - screenHeight/2);
        font.draw(spriteBatch, "LEFT: TURN LEFT", camera.position.x - - (int) (screenWidth/2.5), camera.position.y - (int) (screenHeight/1.6));
        font.draw(spriteBatch, "DOWN: BACKWARDS", camera.position.x - - (int) (screenWidth/2.5), camera.position.y - (int) (screenHeight/1.3));
        font.draw(spriteBatch, "RIGHT: TURN RIGHT", camera.position.x - - (int) (screenWidth/2.5), camera.position.y - (int) (screenHeight/1.1));
        font.getData().setScale(8f);
        font.draw(spriteBatch, Tasks.getNewTask(), camera.position.x - V_WIDTH/2-700, camera.position.y + V_HEIGHT/2+450);
        font.draw(spriteBatch, "Coins: " + Coin.getNumCoins(), camera.position.x - V_WIDTH/2-700, camera.position.y + V_HEIGHT/2-900);
        font.getData().setScale(4f);
        font.setColor(1,0,0,1);
        font.draw(spriteBatch, "Constantine", 935, 825);
        font.draw(spriteBatch, "Goodricke", 400, 2500);
        font.draw(spriteBatch, "James", 2400, 2800);
        font.draw(spriteBatch, "Derwent", 2500, 825);
        spriteBatch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        shapeRenderer.end();

        if (enemys.isEmpty() && coins.isEmpty()) {
            Coin.numCoins = 0;
            game.setScreen(new GameWonScreen(game));
        }
    }

    @Override
    public void resize(int width, int height){
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose(){
        super.dispose();
        manager.dispose();
        font.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE){
            bullets.add(new Bullet(manager.get("bullet.png", Texture.class), player.getCenter(), player.getCorrectedAngle()));
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
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A){
            player.stopHorizontal();
        }
        if (keycode == Input.Keys.D){
            player.stopHorizontal();
        }
        if (keycode == Input.Keys.W){
            player.stopVertical();
        }
        if (keycode == Input.Keys.S){
            player.stopVertical();
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

}