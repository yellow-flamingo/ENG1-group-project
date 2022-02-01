package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Coin extends Sprite implements Poolable{
    public float x, y;
    public boolean remove;
    public boolean check;
    public int value;
    //Original width and height
    private float w, h;
    /**
     * Coins are collectable items that the player may collide
     * and my spend at the shop during assessment 2
     * @param tex       The Texture (image) of the coin
     * @param x         x location of the coin
     * @param y         y location of the coin
     * @param scaleX    allows for the change in size of the coin on the map
     * @param scaleY    same as above, except for y scaling
     */
    public Coin(Texture tex, float x, float y, float scaleX, float scaleY){
        super(tex);
        setX(x);
        setY(y);
        this.remove = false;
        this.w = this.getTexture().getWidth();
        this.h = this.getTexture().getHeight();
        
        this.setSize(w/scaleX, h/scaleY);

        this.value = 1;
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
    /**
     * checks for collision with the player
     * @param r     pass in the player's BoundingRectangle
     * @return      if coin and the player overlap, return true
     *                  otherwise false.
     */
    public boolean collisionCheck(Rectangle r){
        if (this.getBoundingRectangle().overlaps(r)){
            this.remove = true;
            return true;
        }
        return false;
    }
    /**
     * Used for pooling
     * reset the value of the coin
     * reset the remove attribute
     */
    @Override
    public void reset() {
        this.value = 1;
        this.remove = false;
    }
    /**
     * Useful for when new coins are needed, but have different sizes
     * or locations to the ones in the pool
     * @param x         x location of the coin
     * @param y         y location of the coin
     * @param scaleX    scaling of the img in the x direction
     * @param scaleY    scaling of the img in the y direction.
     */
    public void resetHelper(float x, float y, float scaleX, float scaleY){
        setX(x);
        setY(y);
        this.setSize(w/scaleX, h/scaleY);
    }
    /** Getter for value */
    public int getValue(){
        return this.value;
    }
    /** Setter for value */
    public void setValue(int newVal){
        this.value = newVal;
    }
}
