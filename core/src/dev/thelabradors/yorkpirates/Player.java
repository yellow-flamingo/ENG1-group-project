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
    // Player's current speed.
    public float speed = 0;
    // The total vector of the player.
    public static final float TOTALVELOCITY = 250;
    // Angles start at +ve x axis, but the player starts facing +ve y axis.
    // Therefore some calculations need to be corrected.
    public static final float IMAGE_CORRECTION_ANGLE = 90;
    // used for collision.
    public static String blockedKey = "blocked";
    public TiledMapTileLayer collisionLayer;
    float w, h;
    // The current angle of the player
    public float angle;
    // Change in angle that is required for the current frame.
    public float angleChange;
    public Vector2 velocity = new Vector2();
    Rectangle playerRec;
    /**
     * The class for the player.
     * @param tex               Texture/img of the player
     * @param collisionLayer    layer in the map that the player can collide with.
     * @param game              passes through the YorkPiratesGame class
     */
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
        // Used for collision
        this.playerRec = this.getBoundingRectangle();
    }
    /**
     * Called every frame
     * change the player's information as needed.
     * @param delta     used as a control variable,
     *                  so speed doesn't change at different framerates.
     */
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
    /**
     * draw the player on the screen.
     * @param spriteBatch required for libGDX to draw on the screen.
     */
    public void draw(SpriteBatch spriteBatch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }
    /**Getter for x,y, returns a "Vector2" */
    public Vector2 getPosition(){
        return new Vector2 (getX(), getY());
    }

    //Rotating movement
    /** Rotate player right */
    public void turnRight() {
        angleChange = -90.0f;
    }
    /** rotate the player left */
    public void turnLeft() {
        angleChange = 90.0f;
    }
    /** player moves forward */
    public void moveForward() {
        speed = TOTALVELOCITY;
    }
    /** player moves backwards */
    public void moveBackwards() {
        speed = -TOTALVELOCITY;
    }
    /** stop turning when key is released */
    public void stopTurn(){
        angleChange = 0;
    }
    /** Stop moving when key is released. */
    public void stopMove(){
        speed = 0;
    }

    //Collision Code
    /**
     * Checks whether the coordinates corrispond to a tile in the map
     * where the proporties in the tile contain "blocked."
     * @param x x coordinate that the player is trying to reach.
     * @param y y coordinate that the player is trying to reach.
     * @return  true if the coordinate is blocked, false otherwise.
     */
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}
    /**
     * Check for collision on the right
     * if the player collides with a tile on the right, return true
     * if the player is at the right most edge, return true.
     * @return true if collision occurs, false otherwise
     */
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
    /**
     * Check for collision on the left
     * if the player collides with a tile on the left, return true
     * if the player is at the left most edge, return true.
     * @return true if collision occurs, false otherwise
     */
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
    /**
     * Check for collision on the top
     * if the player collides with a tile on the top, return true
     * if the player is at the top most edge, return true.
     * @return true if collision occurs, false otherwise
     */
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
    /**
     * Check for collision on the bottom
     * if the player collides with a tile on the bottom, return true
     * if the player is at the bottom most edge, return true.
     * @return true if collision occurs, false otherwise
     */
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
    /** logs the coordintes of the player */
    public void getCoords(){
        System.out.println("x coord: " + getX() + " y coord: " + getY());
    }
    /** getter for angle */
    public float getAngle(){
        return this.angle;
    }
    /** getter for angle + IMAGE_CORRECTION_ANGLE
     * useful for various calculations
     */
    public float getCorrectedAngle(){
        return this.angle + IMAGE_CORRECTION_ANGLE;
    }
    /** getter for the location at the center of the player */
    public Vector2 getCenter() {
        return new Vector2(getX() + getWidth()/2, getY() + getHeight()/2);
    }
}
