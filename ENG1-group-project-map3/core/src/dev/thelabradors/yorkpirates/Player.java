package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite{
    float w, h;
    public float angle;
    public Vector2 direction = (new Vector2(0.0f, 1.0f)).rotateDeg(angle);
    public Vector2 position;

    public Player(Sprite sprite){
        super(sprite);
        this.w = 117; //117
        this.h = 200; //200
        this.position = (new Vector2(100, 100));
        this.setSize(w/2, h/2);
        this.setX(position.x);
        this.setY(position.y);
        this.flip(false, false);
        this.setRotation(angle);
    }

    public void update(){
        setX(position.x);
        setY(position.y);
        setRotation(angle);
        direction.x = 0;
        direction.y = 1;
        direction.rotateDeg(angle);
    }

    public void draw(SpriteBatch spriteBatch){
        //update();
        super.draw(spriteBatch); //spriteBatch.draw(Player)
    }

    public Vector2 getPosition(){
        return new Vector2 (getX(), getY());
    }

    public void turnRight(float delta) {
        angle -= delta * 90.0f;
    }

    public void turnLeft(float delta) {
        angle += delta * 90.0f;
    }

    public void moveForward(float delta) {
        position.add(direction.x * delta * 50.0f, direction.y * delta * 50.0f);
    }

    public void moveBackwards(float delta) {
        position.sub(direction.x * delta * 50.0f, direction.y * delta * 50.0f);
    }
}
