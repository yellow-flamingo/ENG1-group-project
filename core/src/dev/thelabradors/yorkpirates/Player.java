package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.*;

public class Player extends Sprite{
    YorkPiratesGame game;
    public float speed = 0;
    public static final float TOTALVELOCITY = 250;
    public static final float IMAGE_CORRECTION_ANGLE = 90;
    public static String blockedKey = "blocked";
    public TiledMapTileLayer collisionLayer;
    float w, h;
    public float angle;
    public float angleChange;
    public Vector2 velocity = new Vector2();
    Rectangle playerRec;
    public Player(Texture tex , TiledMapTileLayer collisionLayer, YorkPiratesGame game){
        super(tex);
        this.game = game;
        this.w = 117; //117
        this.h = 200; //200
        setSize(w/2, h/2);
        this.setX(100);
        this.setY(100);
        this.flip(false, false);
        //Used to rotate around the center of the image, otherwise, if the image is scaled
        //then the image will rotate around the original size center
        this.setOriginCenter();
        this.setRotation(angle);
        this.collisionLayer = collisionLayer;
        this.playerRec = this.getBoundingRectangle();
    }

    public void update(float delta){
        //Change the Angle
        angle += angleChange*delta;
        setRotation(angle);
        //Angle changed for velocity, since the default 0 degrees is the "positive x on a 2D graph"
        // Some vector maths.
        velocity.x = speed*MathUtils.cosDeg(getCorrectedAngle())*delta;
        velocity.y = speed*MathUtils.sinDeg(getCorrectedAngle())*delta;

        //Checking if any collision has occured, if so, move the player back to the old coords
        float oldX = getX(), oldY = getY();
        setX(getX() + velocity.x);
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
        setY(getY() + velocity.y);
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

    //Rotating movement
    public void turnRight() {
        angleChange = -90.0f;
    }

    public void turnLeft() {
        angleChange = 90.0f;
    }

    public void moveForward() {
        speed = TOTALVELOCITY;
    }

    public void moveBackwards() {
        speed = -TOTALVELOCITY;
    }
    public void stopTurn(){
        angleChange = 0;
    }
    public void stopMove(){
        speed = 0;
    }

    //Collision Code
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}
    public boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX() + getWidth(), getY() + step) || 
            getX() > GameScreen.mapPixelWidth - this.getWidth())
                return true;
        for (Enemy enemy:game.enemys) {
            Rectangle enemyRec = enemy.getBoundingRectangle();
            Rectangle intersection = new Rectangle();
            Intersector.intersectRectangles(enemyRec, playerRec, intersection);
            if(intersection.x> playerRec.x)
                return true;
        }
        return false;
    }
    public boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX(), getY() + step) || getX() < 0)
                return true;
        for (Enemy enemy:game.enemys) {
            Rectangle enemyRec = enemy.getBoundingRectangle();
            Rectangle intersection = new Rectangle();
            Intersector.intersectRectangles(enemyRec, playerRec, intersection);
            if (intersection.x > enemyRec.x)
                return true;
        }
        return false;
    }
    public boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY() + getHeight()) || 
            getY() > GameScreen.mapPixelHeight - this.getHeight())
                return true;
            for (Enemy enemy:game.enemys) {
                Rectangle enemyRec = enemy.getBoundingRectangle();
                Rectangle intersection = new Rectangle();
                Intersector.intersectRectangles(enemyRec, playerRec, intersection);
                if(intersection.y > playerRec.y)
                    return true;
            }
        return false;

    }
    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY()) || getY() < 0)
                return true;
        for (Enemy enemy:game.enemys) {
            Rectangle enemyRec = enemy.getBoundingRectangle();
            Rectangle intersection = new Rectangle();
            Intersector.intersectRectangles(enemyRec, playerRec, intersection);
            if(intersection.y > enemyRec.y)
                return true;
        }
        return false;
        }
    //getter for x,y coords
    public void getCoords(){
        System.out.println("x coord: " + getX() + " y coord: " + getY());
    }
    public float getAngle(){
        return this.angle;
    }
    public float getCorrectedAngle(){
        return this.angle + IMAGE_CORRECTION_ANGLE;
    }
    public Vector2 getCenter() {
        return new Vector2(getX() + getWidth()/2, getY() + getHeight()/2);
    }
}
