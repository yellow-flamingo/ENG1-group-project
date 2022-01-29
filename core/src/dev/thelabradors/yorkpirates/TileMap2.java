package dev.thelabradors.yorkpirates;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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

public class TileMap2 extends ApplicationAdapter implements InputProcessor{
    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 600;
    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    TiledMapRenderer tiledMapRenderer;
    BitmapFont font;
    Player player;
    Building building1;

    AssetManager manager;

    Vector2 position;
    Vector2 barrelOffset;
    float angle;

    ArrayList<Bullet> bullets;
    ArrayList<Enemy> enemys;
    @Override
    public void create() {
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
        manager.load("badlogic.jpg", Texture.class);
        manager.load("Boat2.png", Texture.class);
        manager.load("building.png", Texture.class);
        manager.finishLoading();

        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        position = new Vector2();
        barrelOffset = new Vector2(1.0f, -0.5f).scl(0.5f); // Relative coordinates to where the barrel is in the sprite
        player = new Player((Texture) (manager.get("Boat2.png", Texture.class)), (TiledMapTileLayer) tiledMap.getLayers().get(1));
        bullets = new ArrayList<>();
        enemys = new ArrayList<>();
        building1 = new Building((Texture) (manager.get("building.png", Texture.class)), 500, 500);
        enemys.add(building1);
    }
//    @Override
//    public void resize(int width, int height){
//        viewport.update(width, height);
//    }

    @Override
    public void resize(int width, int height){
    }

    @Override
    public void render() {
        if (!manager.update()){
            float progress = manager.getProgress();
            System.out.println("Loading... " + progress * 100 + "%");
        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //camera.position.set(position, 0);
        //camera.position.set((float) (player.getX()+(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1.4) + player.getWidth()), (float) (player.getY()+Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.5) + player.getHeight()/2, 0);
        if (player.getX() >= 100 && player.getX() <= 696) {
            if (player.getY() >= 100 && player.getY() <= 1688) {
                camera.position.set((float) (player.getX()+(Gdx.graphics.getWidth()/1.1) + player.getWidth()), (float) (player.getY()+Gdx.graphics.getHeight()/1.2) + player.getHeight()/2, 0);
            } else {
                camera.position.set((float) (player.getX()+(Gdx.graphics.getWidth()/1.1) + player.getWidth()), (float) (player.getY()+Gdx.graphics.getHeight()/1.2) + player.getHeight()/2, 0);
                //camera.position.set((float) (player.getX()+(Gdx.graphics.getWidth()/1.1) + player.getWidth()), player.oldY, 0);
            }
        } else if (player.getY() >= 100 && player.getY() <= 1688) {
            camera.position.set((float) (player.getX()+(Gdx.graphics.getWidth()/1.1) + player.getWidth()), (float) (player.getY()+Gdx.graphics.getHeight()/1.2) + player.getHeight()/2, 0);
            //camera.position.set(player.oldX, (float) (player.getY()+Gdx.graphics.getHeight()/1.2) + player.getHeight()/2, 0);
        } else {
            camera.position.set((float) (player.getX()+(Gdx.graphics.getWidth()/1.1) + player.getWidth()), (float) (player.getY()+Gdx.graphics.getHeight()/1.2) + player.getHeight()/2, 0);
            //camera.position.set(player.oldX, player.oldY, 0);
        }

        //camera.position.set((float) (player.getX()+(Gdx.graphics.getWidth()/1.1) + player.getWidth()), (float) (player.getY()+Gdx.graphics.getHeight()/1.2) + player.getHeight()/2, 0);
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
        font.getData().setScale(15f);
        font.draw(spriteBatch, "Hello :)", 100, 100);
        spriteBatch.end();
        //System.out.println(player.angle);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        shapeRenderer.end();
    }

    @Override
    public void dispose(){
        super.dispose();
        manager.dispose();
        font.dispose();
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A){
            player.moveLeft();
        }
        if (keycode == Input.Keys.D){
            player.moveRight();
        }
        if (keycode == Input.Keys.W){
            player.moveUp();
        }
        if (keycode == Input.Keys.S){
            player.moveDown();
        }
        if (keycode == Input.Keys.SPACE){
            bullets.add(new Bullet(manager.get("badlogic.jpg", Texture.class), player.getCenter(), player.getCorrectedAngle()));
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
        return true;
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
}
