package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Coin extends Sprite{
    public float x, y;
    public boolean remove;
    public Coin(Texture tex, float x, float y){
        super(tex);
        setX(x);
        setY(y);
        this.remove = false;
    }
    public Coin(Texture texture, int i, int j) {
    }
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
    }
    public void hit(){
        getRemove();
    }
    public boolean getRemove(){
        return this.remove;
    }
}
