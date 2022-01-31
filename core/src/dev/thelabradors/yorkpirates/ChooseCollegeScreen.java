package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class ChooseCollegeScreen implements Screen {

    YorkPiratesGame game;

    public ChooseCollegeScreen(YorkPiratesGame game) {
        this.game = game;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.A) {
                    game.playerCollege = "Alcuin";
                    game.setScreen(new StartScreen(game));
                } else if (keyCode == Input.Keys.L) {
                    game.playerCollege = "Langwith";
                    game.setScreen(new StartScreen(game));
                } else if (keyCode == Input.Keys.H) {
                    game.playerCollege = "Halifax";
                    game.setScreen(new StartScreen(game));
                }
                return true;
            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.getData().setScale(3f);
        game.font.draw(game.batch, "Choose your college! - type the corresponding letter", Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "A - Alcuin", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .55f);
        game.font.draw(game.batch, "L - Langwith", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .4f);
        game.font.draw(game.batch, "H - Halifax", Gdx.graphics.getWidth() * .3f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
