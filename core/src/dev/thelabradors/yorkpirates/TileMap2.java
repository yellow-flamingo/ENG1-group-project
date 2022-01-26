package dev.thelabradors.yorkpirates;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TileMap2 extends ApplicationAdapter implements InputProcessor{
    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 600;
    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch spriteBatch;
    ShapeRenderer shapeRenderer;
    TiledMapRenderer tiledMapRenderer;
    Texture texture;
    Player player;
    Building building1;

    Vector2 position;
    Vector2 barrelOffset;
    float angle;

    ArrayList<Bullet> bullets;
    ArrayList<Building> enemys;
    @Override
    public void create() {
        float aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        System.out.println("The width: " + Gdx.graphics.getWidth());
        System.out.println("The height: " + Gdx.graphics.getHeight());
        camera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
        //camera = new OrthographicCamera((float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
        
        // viewport = new FitViewport(V_WIDTH, V_HEIGHT, camera);
        // viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        TiledMap tiledMap = new TmxMapLoader().load("Test1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        position = new Vector2();
        barrelOffset = new Vector2(1.0f, -0.5f).scl(0.5f); // Relative coordinates to where the barrel is in the sprite
        player = new Player(new Sprite(new Texture("Boat2.png")));

        bullets = new ArrayList<>();
        enemys = new ArrayList<>();
        building1 = new Building(new Sprite(new Texture("building.png")), 500, 500);
        enemys.add(building1);
    }
    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(position, 0);
        camera.position.set(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, 0);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        
        /*
        float delta = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            player.moveLeft();
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            player.moveRight();
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            player.moveUp();
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            player.moveDown();
		if (Gdx.input.isKeyPressed(Input.Keys.U))
			System.out.println(bullets.size());
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
			for(Bullet b : bullets){
                b.coordOut();
            }
        }
        */
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet:bullets){
            bullet.draw(spriteBatch);
            if (bullet.remove){
                bulletsToRemove.add(bullet);
            }
            for (Building enemy : enemys){
                if (bullet.collisionCheck(enemy.getBoundingRectangle())){
                    enemy.hit();
                }
            }
        }
        bullets.removeAll(bulletsToRemove);

        ArrayList<Object> enemiesToRemove = new ArrayList<>();
        for (Object enemy:enemys){
            ((Building) enemy).draw(spriteBatch);
            ((Building) enemy).update();
            if (((Building) enemy).getRemove()){
                enemiesToRemove.add(enemy);
            }
        }
        enemys.removeAll(enemiesToRemove);
        player.draw(spriteBatch);
        
        spriteBatch.end();
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
            bullets.add(new Bullet(new Texture("badlogic.jpg"), player.getPosition(), new Vector2(0,0)));
        }
        if (keycode == Input.Keys.P){
            System.out.println(camera.position);
        }
        if (keycode == Input.Keys.U){
            player.getCoords();
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
        return false;
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