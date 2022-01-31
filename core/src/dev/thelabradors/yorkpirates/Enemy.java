package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Enemy extends Sprite implements Poolable{
    public float x, y;
    public int health;
    public boolean remove;
    public float angle;
    public Enemy(Texture tex, float x, float y, int health, float angle){
        super(tex);
        setX(x);
        setY(y);
        this.health = health;
        this.remove = false;
        this.setRotation(angle);
    }
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
    }
    public void update(){
        healthUpdate();
        /*No movement for assessment 1*/
    }
    public void healthUpdate(){
        if (this.health <= 0){
            this.remove = true;
        }
    }
    public void hit(){
        this.health -= 1;
    }
    public boolean getRemove(){
        return this.remove;
    }
    public void getHealth(){
        System.out.println(this.health);
    }
    public void resetHelper(float x, float y, int health, float angle){
        setX(x);
        setY(y);
        this.health = health;
        this.setRotation(angle);
    }
    @Override
    public void reset() {
        // TODO Auto-generated method stub
        this.remove = false;
    }
}