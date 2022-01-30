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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.awt.*;

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
    Building building1;
    Building building2;
    Building building3;
    Building building4;
    InputProcessor inputProcessor;

    AssetManager manager;

    Vector2 position;
    Vector2 barrelOffset;
    float angle;

    ArrayList<Bullet> bullets;
    ArrayList<Enemy> enemys;

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
        //viewport = new FitViewport(camera.viewportWidth/2, camera.viewportHeight/2, camera);
        TiledMap tiledMap = new TmxMapLoader().load("Test1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        manager = new AssetManager();
        manager.load("bullet.png", Texture.class);
        manager.load("Boat2.png", Texture.class);
        manager.load("enemy-ship.png", Texture.class);
        manager.finishLoading();

        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        position = new Vector2();
        barrelOffset = new Vector2(1.0f, -0.5f).scl(0.5f); // Relative coordinates to where the barrel is in the sprite
        player = new Player((Texture) (manager.get("Boat2.png", Texture.class)), (TiledMapTileLayer) tiledMap.getLayers().get(1));
        bullets = new ArrayList<>();
        enemys = new ArrayList<>();
        building1 = new Building((Texture) (manager.get("enemy-ship.png", Texture.class)), 1000, 600, 180.0f);
        building2 = new Building((Texture) (manager.get("enemy-ship.png", Texture.class)), 700, 2200, 40.0f);
        building3 = new Building((Texture) (manager.get("enemy-ship.png", Texture.class)), 2600, 2500, -20.0f);
        building4 = new Building((Texture) (manager.get("enemy-ship.png", Texture.class)), 2700, 600, -150.0f);
        enemys.add(building1);
        enemys.add(building2);
        enemys.add(building3);
        enemys.add(building4);
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

        if (!manager.update()){
            float progress = manager.getProgress();
            System.out.println("Loading... " + progress * 100 + "%");
        }

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
            }
        }
        bullets.removeAll(bulletsToRemove);
        enemys.removeAll(enemiesToRemove);

        player.draw(spriteBatch);
        font.getData().setScale(5f);
        font.draw(spriteBatch, "UP: FORWARDS", camera.position.x - - (int) (screenWidth/2.5), camera.position.y - screenHeight/2);
        font.draw(spriteBatch, "LEFT: TURN LEFT", camera.position.x - - (int) (screenWidth/2.5), camera.position.y - (int) (screenHeight/1.6));
        font.draw(spriteBatch, "DOWN: BACKWARDS", camera.position.x - - (int) (screenWidth/2.5), camera.position.y - (int) (screenHeight/1.3));
        font.draw(spriteBatch, "RIGHT: TURN RIGHT", camera.position.x - - (int) (screenWidth/2.5), camera.position.y - (int) (screenHeight/1.1));
        spriteBatch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        shapeRenderer.end();

        if (enemys.isEmpty()) {
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