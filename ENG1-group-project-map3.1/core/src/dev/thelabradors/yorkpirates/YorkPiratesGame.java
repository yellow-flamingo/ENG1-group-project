package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class YorkPiratesGame extends Game {

    SpriteBatch batch;
    BitmapFont font;

    @Override
    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        setScreen(new TileMap2(this));
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}
