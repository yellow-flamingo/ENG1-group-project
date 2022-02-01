package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.graphics.Texture;

public class Building extends Enemy{
    /**
     * Building - child class of Enemy, 
     * these are the non-moving enemies that the player kills
     * 
     * @param tex       Texture of the building
     * @param x         x location of the enemy
     * @param y         y location of the enemy
     * @param health    health of the enemy
     * @param angle     transform the image of the building, by the "angle"
     */
    public Building(Texture tex, float x, float y, int health, float angle){
        super(tex, x, y, health, angle);
    }
}