package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite{
    public Vector2 velocity = new Vector2();
    public float speed = 250;
    public Texture texture;
    public TiledMapTileLayer collisionLayer;
    float w, h;
    public float angle;
    //float x, y;
    public Vector2 direction = (new Vector2(0.0f, 1.0f)).rotateDeg(angle);
    public Vector2 position;


    //public float delta = Gdx.graphics.getDeltaTime();


    public Player(Sprite sprite /*, TiledMapTileLayer collisionLayer */){
        super(sprite);
        this.w = 117; //117
        this.h = 200; //200
        this.position = (new Vector2(100, 100));
        this.setSize(w/2, h/2);
        //setOrigin(0.5f, 0.5f);
        this.setX(position.x);
        this.setY(position.y);
        this.flip(false, false);
        this.setRotation(angle);
        //this.collisionLayer = collisionLayer;
    }
    /*
    public void render(SpriteBatch batch){
        batch.draw(texture, 
        position.x, position.y, 
        w,h,
        0, 0, 
        texture.getWidth(), texture.getHeight(),
        false, false);
    } */
    public void update(float delta){
        float oldX = getX(), oldY = getY();
        //setX(getX() + velocity.x * delta);
        setX(position.x);
        setY(position.y);
        //Vector2 direction = (new Vector2(0.0f, 1.0f)).rotateDeg(angle);
        /*
        if (velocity.x < 0){
            collisionX = collidesLeft();
        }
        else if (velocity.x > 0){
            collisionX = collides
        }
        */
        //setY(getY() + velocity.y * delta);
        setRotation(angle);
        direction.rotateDeg(angle);
    }

    public void draw(SpriteBatch spriteBatch){
        //update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch); //spriteBatch.draw(Player)
    }
    public Vector2 getPosition(){
        return new Vector2 (getX(), getY());
    }

    public void turnRight(float delta) {
        angle += delta * 90.0f;
    }

    public void turnLeft(float delta) {
        angle -= delta * 90.0f;
    }

    public void moveForward(float delta) {
        position.add(direction.x * delta * 2.0f, direction.y * delta * 2.0f);
    }

    public void moveBackwards(float delta) {
        position.sub(direction.x * delta * 2.0f, direction.y * delta * 2.0f);
    }

//    public void moveRight(){
//        this.velocity.x = speed;
//    }
//    public void moveLeft(){
//        this.velocity.x = -speed;
//    }
//    public void moveUp(){
//        this.velocity.y = speed;
//    }
//    public void moveDown(){
//        this.velocity.y = -speed;
//    }
    public void stopHorizontal(){
        this.velocity.x = 0;
    }
    public void stopVertical(){
        this.velocity.y = 0;
    }
    public void getCoords(){
        System.out.println("x coord: " + getX() + " y coord: " + getY());
    }
}
