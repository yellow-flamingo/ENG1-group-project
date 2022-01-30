package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class YorkPiratesGame extends Game {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    //Texture img;
    BitmapFont font;

    @Override
    public void create () {
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new TileMap2());
        batch = new SpriteBatch();
        //img = new Texture("badlogic.jpg");
    }

/*	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}*/

    @Override
    public void dispose () {
        font.dispose();
        batch.dispose();
        //img.dispose();
    }
}
