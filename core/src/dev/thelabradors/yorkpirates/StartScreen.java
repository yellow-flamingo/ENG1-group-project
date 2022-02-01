package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Align;

public class StartScreen implements Screen {

    YorkPiratesGame game;
    /**
     * Class for the start 
     * allows user to press space to proceed onto the main game.
     * * @param game passes through the main YorkPiratesGame class
     */
    public StartScreen(YorkPiratesGame game) {
        this.game = game;
    }
    /**
     * Called when the screen changes to StartScreen

     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(game.gameScreen);
                }
                return true;
            }
        });
    }
    /**
     * Drawing some text to tell the player how to proceed.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "You are playing as " + game.playerCollege + "!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .65f);
        game.font.draw(game.batch, "Press space to play", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .4f);
        game.batch.end();
    }
    /** Empty */
    @Override
    public void resize(int width, int height) {
    }
    /** Empty */
    @Override
    public void pause() {
    }
    /** Empty */
    @Override
    public void resume() {
    }
    /** Empty */
    @Override
    public void hide() {
    }
    /** Empty */
    @Override
    public void dispose() {
    }
}
