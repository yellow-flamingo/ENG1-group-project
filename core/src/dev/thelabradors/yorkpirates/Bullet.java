package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Bullet extends Sprite implements Poolable{

    private Vector2 startingPosition = new Vector2();
    private boolean remove;
    // Speed of the bullet
    public static final float SPEED = 500f;
    // Number of pixels that the bullet can travel before disapearing.
    public static final float RANGE = 1000f;
    private float dx, dy;
    Texture texture;
    /**
     * The projectile class for the player
     * @param texture   the Texture that the bullet uses(image)
     * @param x         the initial x coord of the bullet
     * @param y         the initial y coord of the bullet
     * @param angle     the angle that the bullet fires at
     */
    public Bullet(Texture texture, float x, float y, float angle){
        super(texture);
        this.setSize(getWidth()/24, getHeight()/24);
        setX(x - this.getWidth()/4);
        setY(y- this.getHeight()/4);
        this.startingPosition.set(getX(), getY());
        this.remove = false;

        // X and Y vectors of the bullet, simple vector maths.
        this.dx = SPEED * MathUtils.cosDeg(angle);
        this.dy = SPEED * MathUtils.sinDeg(angle);
    }
    /**
     * called every frame, update the bullet accordingly.
     * @param delta used so that movement doesn't change due to framerate.
     */
    public void update(float delta){
        // Change the position of the bullet
        setX(getX() + dx*delta);
        setY(getY() + dy*delta);
        // Remove after "RANGE" number of pixels.
        if (startingPosition.dst(getX(), getY()) > RANGE){
            this.remove = true;
        }
    }
    /**
     * Code for drawing the building
     * @param batch spritebatch from the game, allows the code to draw the building.
     */
    public void draw (SpriteBatch batch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }
    /**
     * Check for collision with some other object (likely to be an enemy)
     * @param r Rectangle of r, check whether the bullet rectangle and r overlap
     * @return  returns true, if collided, false otherwise.
     */
    public boolean collisionCheck(Rectangle r){
        if (this.getBoundingRectangle().overlaps(r)){
            this.remove = true;
            return true;
        }
        return false;
    }
    /** Getter for the remove attribute */
    public boolean getRemove(){
        return this.remove;
    }
    /**
     * Used for pooling, remove is reset to false;
     */
    @Override
    public void reset() {
        this.remove = false;
        
    }
    /** Setter for x and y vectors */
    public void setdxdy(float angle){
        this.dx = SPEED * MathUtils.cosDeg(angle);
        this.dy = SPEED * MathUtils.sinDeg(angle);
    }
    /** setter for the x and y coodinates + starting coords */
    public void setPos(float x, float y){
        this.startingPosition.set(x, y);
        setX(x - this.getWidth()/2);
        setY(y - this.getHeight()/2);
    }
}