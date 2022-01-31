package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Coin extends Sprite{
    public float x, y;
    public boolean remove;
    static int numCoins;
    public Coin(Texture tex, float x, float y, float scaleX, float scaleY){
        super(tex);
        setX(x);
        setY(y);
        this.remove = false;
        this.setSize(getWidth()/scaleX, getHeight()/scaleY);
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
    public boolean collisionCheck(Rectangle r){
        if (this.getBoundingRectangle().overlaps(r)){
            numCoins +=1;
            System.out.println("Coins: "+ Coin.getNumCoins());
            this.remove = true;
            return true;
        }
        return false;
    }

    public static int getNumCoins() {
        return numCoins;
    }
}
