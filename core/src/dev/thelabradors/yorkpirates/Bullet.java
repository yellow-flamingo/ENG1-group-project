package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

import org.w3c.dom.Text;

public class Bullet extends Sprite implements Poolable{

    private Vector2 startingPosition = new Vector2();
    private boolean remove;
    public static final float SPEED = 500f;
    public static final float RANGE = 1000f;
    private float dx, dy;
    Texture texture;

    public Bullet(Texture texture, float x, float y, float angle){
        super(texture);
        this.setSize(getWidth()/24, getHeight()/24);
        setX(x - this.getWidth()/4);
        setY(y- this.getHeight()/4);
        this.startingPosition.set(getX(), getY());
        this.remove = false;

        this.dx = SPEED * MathUtils.cosDeg(angle);
        this.dy = SPEED * MathUtils.sinDeg(angle);
    }

    public void update(float delta){
        setX(getX() + dx*delta);
        setY(getY() + dy*delta);
        if (startingPosition.dst(getX(), getY()) > RANGE){
            this.remove = true;
        }
    }

    public void draw (SpriteBatch batch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void coordOut() {
    }

    public boolean collisionCheck(Rectangle r){
        if (this.getBoundingRectangle().overlaps(r)){
            this.remove = true;
            return true;
        }
        return false;
    }

    public boolean getRemove(){
        return this.remove;
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        this.remove = false;
        //this.setSize(200, 200);
        
    }
    public void setdxdy(float angle){
        this.dx = SPEED * MathUtils.cosDeg(angle);
        this.dy = SPEED * MathUtils.sinDeg(angle);
    }
    public void setPos(float x, float y){
        this.startingPosition.set(x, y);
        setX(x - this.getWidth()/2);
        setY(y - this.getHeight()/2);
    }
}