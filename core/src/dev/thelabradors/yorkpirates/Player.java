package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite{
    public Vector2 velocity = new Vector2();
    public static float speed = 250;
    public static String blockedKey = "blocked";
    public Texture texture;
    public TiledMapTileLayer collisionLayer;
    float w, h;
    public Player(Texture tex , TiledMapTileLayer collisionLayer){
        super(tex);
        this.w = 117; //117
        this.h = 200; //200
        setSize(w/2, h/2);
        this.setX(100);
        this.setY(100);
        this.collisionLayer = collisionLayer;
    }
    public void update(float delta){
        float oldX = getX(), oldY = getY();
        setX(getX() + velocity.x * delta);
        boolean collisionX = false, collisionY = false;
        if (velocity.x < 0){
            collisionX = collidesLeft();
        }
        else if (velocity.x > 0){
            collisionX = collidesRight();
        }
        if (collisionX){
            setX(oldX);
        }
        setY(getY() + velocity.y * delta);
        if (velocity.y > 0){
            collisionY = collidesTop();
        }else if (velocity.y < 0){
            collisionY = collidesBottom();
        }
        if (collisionY){
            setY(oldY);
        }
    }
    public void draw(SpriteBatch spriteBatch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }
    //Getter for x,y, returns a "Vector2"
    public Vector2 getPosition(){
        return new Vector2 (getX(), getY());
    }
    //Movement Code
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

    //Collision Code
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}
    public boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }
    public boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }
    public boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;

    }
    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
        }
    //getter for x,y coords
    public void getCoords(){
        System.out.println("x coord: " + getX() + " y coord: " + getY());
    }
}
