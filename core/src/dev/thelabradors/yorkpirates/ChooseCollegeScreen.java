package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ChooseCollegeScreen implements Screen {

    YorkPiratesGame game;
    /**
     * Allows the user to choose which college they would like to join
     * @param game passes though the main game class.
     */
    public ChooseCollegeScreen(YorkPiratesGame game) {
        this.game = game;
    }
    /**
     * Called at the start of the game.
     * Player presses first letter of the college to join the college
     * college is shown under the boat during the game.
     */
    @Override
    public void show() {

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.A) {
                    game.playerCollege = "Alcuin";
                    game.setScreen(game.startScreen);
                } else if (keyCode == Input.Keys.L) {
                    game.playerCollege = "Langwith";
                    game.setScreen(game.startScreen);
                } else if (keyCode == Input.Keys.H) {
                    game.playerCollege = "Halifax";
                    game.setScreen(game.startScreen);
                }
                return true;
            }
        });
    }
    /** Render text on the screen */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.getData().setScale(Gdx.graphics.getWidth() / 385);
        game.font.draw(game.batch, "Choose your college! - type the corresponding letter", Gdx.graphics.getWidth() * .1f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "A - Alcuin", Gdx.graphics.getWidth() * .2f, Gdx.graphics.getHeight() * .55f);
        game.font.draw(game.batch, "L - Langwith", Gdx.graphics.getWidth() * .2f, Gdx.graphics.getHeight() * .4f);
        game.font.draw(game.batch, "H - Halifax", Gdx.graphics.getWidth() * .2f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();

    }
    /** Not used for this class */
    @Override
    public void resize(int width, int height) {
    }
    /** Not used for this class */
    @Override
    public void pause() {
    }
    /** Not used for this class */
    @Override
    public void resume() {
    }
    /** Not used for this class */
    @Override
    public void hide() {
    }
    /** Not used for this class */
    @Override
    public void dispose() {
    }
}
