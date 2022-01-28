package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Building extends Sprite{
    public float x, y;
    public Sprite sprite;
    public int health;
    public boolean remove;
    public Building(Sprite sprite, float x, float y){
        super(sprite);
        setX(x);
        setY(y);
        this.health = 5;
        this.remove = false;
    }
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
    }
    public void update(){
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
}
