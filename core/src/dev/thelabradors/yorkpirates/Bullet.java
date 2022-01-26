package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Text;

public class Bullet extends Sprite{
    public Vector2 position = new Vector2();
    public Vector2 direction = new Vector2();
    public Vector2 startingPosition = new Vector2();
    public boolean remove;
    public float speed;
    Texture texture;
    public Bullet(Texture texture, Vector2 position, Vector2 direction){
        super(texture);
        setX(position.x);
        setY(position.y);
        this.startingPosition.set(position);
        this.direction.set(direction);
        this.remove = false;
        this.speed = 60f;
        this.setSize(getWidth()/4, getHeight()/4);
    }
    public void update(float delta){
        setX(getX() + speed*delta);
        if (startingPosition.dst(getX(), getY()) > 100){
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
    
}
