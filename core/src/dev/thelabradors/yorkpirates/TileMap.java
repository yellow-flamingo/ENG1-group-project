package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TileMap extends ApplicationAdapter implements InputProcessor {
    TiledMap tiledMap;
    OrthographicCamera camera;
    Viewport viewport;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Player player;
    
    @Override
    public void create () {
        float w = 100; //640
        float h = 100; //480

        camera = new OrthographicCamera(w,h);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight/2, 0);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        System.out.println("Width: " + camera.viewportWidth);
        System.out.println("height: " + camera.viewportHeight);
        tiledMap = new TmxMapLoader().load("Test1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Texture player_texture = new Texture("Boat2.png");
        player = new Player(new Sprite(player_texture));
        System.out.println("x: " + player.getX() + 
            " y: " + player.getY() + 
            " height: " + player.getHeight() + 
            " width: " + player.getWidth());
        Gdx.input.setInputProcessor(this);
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
    }

    @Override
    public void resize (int width, int height){
        viewport.update(width, height);
        //camera.position.set(player.getX() + player.getWidth()/2, player.getY() + player.getHeight()/2, 0);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Vector2 position = player.getPosition();
        camera.position.set(position, 0);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
		batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.circle(player.getX(),player.getY(),15);
        shapeRenderer.rect(50f, 50f, camera.viewportWidth - 100, camera.viewportHeight - 100);
        shapeRenderer.end();
    }
    @Override
    public void dispose(){
        shapeRenderer.dispose();
        batch.dispose();
        tiledMap.dispose();
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
        if(keycode == Input.Keys.LEFT)
            camera.translate(-32,0);

        if(keycode == Input.Keys.RIGHT)
            camera.translate(32,0);

        if(keycode == Input.Keys.UP)
           camera.translate(0,-32);

        if(keycode == Input.Keys.DOWN)
            camera.translate(0,32);

        if(keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());

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

    
    public boolean scrolled(float amount, float amount2) {
        return false;
    }
}
