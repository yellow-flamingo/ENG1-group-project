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

public class TileMap2 extends ApplicationAdapter{

    Vector2 position;
    Vector2 barrelOffset;

    ArrayList<Bullet> bullets;

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
    float angle;

    ArrayList<Building> enemys;

//    @Override
//    public void create() {
//        float aspectRatio = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
//        camera = new OrthographicCamera(10, 10 / aspectRatio);
//        camera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
//        TiledMap tiledMap = new TmxMapLoader().load("Test1.tmx");
//        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
//        spriteBatch = new SpriteBatch();
//        shapeRenderer = new ShapeRenderer();
//        texture = new Texture("Boat2.png"); // From https://opengameart.org/content/topdown-shooter (Kenney)
//
//        position = new Vector2();
//        barrelOffset = new Vector2(0.0f, 1.0f).scl(0.5f); // Relative coordinates to where the barrel is in the sprite
//
//        bullets = new ArrayList<>();
//    }

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
        //Gdx.input.setInputProcessor(this);
        position = new Vector2();
        barrelOffset = new Vector2(0.0f, 1.0f).scl(0.5f); // Relative coordinates to where the barrel is in the sprite
        texture = new Texture("Boat2.png");
        player = new Player(new Sprite(texture));

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

        float delta = Gdx.graphics.getDeltaTime();
        player.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            player.turnLeft(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            player.turnRight(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            player.moveForward(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            player.moveBackwards(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.U))
			System.out.println(bullets.size());
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
			for(Bullet b : bullets){
                b.coordOut();
            }
        }

        player.draw(spriteBatch);

        spriteBatch.end();
    }


//    @Override
//    public boolean keyDown(int keycode) {
//        if (keycode == Input.Keys.A){
//            player.turnLeft();
//        }
//        if (keycode == Input.Keys.D){
//            player.turnRight();
//        }
//        if (keycode == Input.Keys.W){
//            //position.add(direction.x * delta * 2.0f, direction.y * delta * 2.0f);
//
//            player.moveForward();
//        }
//        if (keycode == Input.Keys.S){
//            player.moveBackwards();
//        }
//        if (keycode == Input.Keys.SPACE){
//            bullets.add(new Bullet(new Texture("badlogic.jpg"), player.getPosition(), new Vector2(0,0)));
//        }
//        if (keycode == Input.Keys.P){
//            System.out.println(camera.position);
//        }
//        if (keycode == Input.Keys.U){
//            player.getCoords();
//        }
//        return true;
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        if (keycode == Input.Keys.A){
//            player.stopHorizontal();
//        }
//        if (keycode == Input.Keys.D){
//            player.stopHorizontal();
//        }
//        if (keycode == Input.Keys.W){
//            player.stopVertical();
//        }
//        if (keycode == Input.Keys.S){
//            player.stopVertical();
//        }
//        return false;
//    }



}