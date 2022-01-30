package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy extends Sprite{
    public float x, y;
    public int health;
    public boolean remove;
    public Enemy(Texture tex, float x, float y, int health){
        super(tex);
        setX(x);
        setY(y);
        this.health = health;
        this.remove = false;
    }
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
    }
    public void update(){
        healthUpdate();
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
}