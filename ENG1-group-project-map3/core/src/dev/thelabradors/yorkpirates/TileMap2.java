package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TileMap2 extends ApplicationAdapter{

    public static final int V_WIDTH = 1000;
    public static final int V_HEIGHT = 600;
    OrthographicCamera camera;
    Viewport viewport;
    SpriteBatch spriteBatch;
    TiledMapRenderer tiledMapRenderer;
    Texture texture;
    Player player;

    @Override
    public void create() {
        camera = new OrthographicCamera(V_WIDTH, V_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        TiledMap tiledMap = new TmxMapLoader().load("Test1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        spriteBatch = new SpriteBatch();
        texture = new Texture("Boat2.png");
        player = new Player(new Sprite(texture));
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        System.out.println("Player x position: " + player.position.x);
        System.out.println("Player y position: " + player.position.y);
        System.out.println("Player direction: " + player.direction);
        System.out.println("Player x position getX(): " + player.getX());
        System.out.println("Player y position getY(): " + player.getY());

        //camera.position.set(position, 0);
        camera.position.set(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, 0);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            player.turnLeft(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            player.turnRight(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            player.moveForward(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            player.moveBackwards(delta);

        player.update();
        player.draw(spriteBatch);

        spriteBatch.end();
    }
}