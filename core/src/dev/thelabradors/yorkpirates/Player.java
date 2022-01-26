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
    public Player(Sprite sprite /*, TiledMapTileLayer collisionLayer */){
        super(sprite);
        this.w = 117; //117
        this.h = 200; //200
        setSize(w/2, h/2);
        this.setX(100);
        this.setY(100);
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
        setX(getX() + velocity.x * delta);
        /*
        if (velocity.x < 0){
            collisionX = collidesLeft();
        }
        else if (velocity.x > 0){
            collisionX = collides
        }
        */
        setY(getY() + velocity.y * delta);
    }
    public void draw(SpriteBatch spriteBatch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }
    public Vector2 getPosition(){
        return new Vector2 (getX(), getY());
    }

    public void moveRight(){
        this.velocity.x = speed;
    }
    public void moveLeft(){
        this.velocity.x = -speed;
    }
    public void moveUp(){
        this.velocity.y = speed;
    }
    public void moveDown(){
        this.velocity.y = -speed;
    }
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
