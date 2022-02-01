package dev.thelabradors.yorkpirates;

import javax.swing.plaf.TextUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Building extends Enemy{
    public Building(Texture tex, float x, float y, int health, float angle){
        super(tex, x, y, health, angle);
    }
}