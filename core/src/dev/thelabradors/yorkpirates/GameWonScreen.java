package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameWonScreen implements Screen {

    YorkPiratesGame game;
    /**
     * Screen shown after the player has won the game.
     * Player can press space to restart the game.
     * @param game passes through the main YorkPiratesGame class.
     */
    public GameWonScreen(YorkPiratesGame game) {
        this.game = game;
    }
    /**
     * Called when the screen changes to GameWonScreen.
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
     * Renders some text to the screen.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.getData().setScale(3f);
        game.font.draw(game.batch, "You Won!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Pres space to play", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
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
