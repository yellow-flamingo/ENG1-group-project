package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Enemy extends Sprite implements Poolable{
    public float x, y;
    public int health;
    public boolean remove;
    public float angle;
    /**
     * Enemy super class for all other enemies
     * @param tex       Texture of the enemy
     * @param x         initial x location of the enemy
     * @param y         initial y location of the enemy
     * @param health    enemy's health
     * @param angle     allows for angle transformation of the img
     */
    public Enemy(Texture tex, float x, float y, int health, float angle){
        super(tex);
        setX(x);
        setY(y);
        this.health = health;
        this.remove = false;
        this.setRotation(angle);
    }
    /**
     * Draw enemy on the screen
     * @param spriteBatch   passes through the spritebatch,
     *      allows for the libGDX
     */
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
    }
    /**
     * update the enemy every frame
     */
    public void update(){
        healthUpdate();
        /*No movement for assessment 1*/
    }
    /** Check whether the enemy should be dead, if so, remove it */
    public void healthUpdate(){
        if (this.health <= 0){
            this.remove = true;
        }
    }
    /** decrement health by one */
    public void hit(){
        this.health -= 1;
    }
    /** Getter for remove */
    public boolean getRemove(){
        return this.remove;
    }
    /** logs health */
    public void getHealth(){
        System.out.println(this.health);
    }
    /** helpful to reset the enemy from pool with different attributes */
    public void resetHelper(float x, float y, int health, float angle){
        setX(x);
        setY(y);
        this.health = health;
        this.setRotation(angle);
    }
    /**
     * Used for pooling
     * reset the remove attribute.
     */
    @Override
    public void reset() {
        // TODO Auto-generated method stub
        this.remove = false;
    }
}