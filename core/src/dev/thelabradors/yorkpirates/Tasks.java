package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;


public class Tasks{

    YorkPiratesGame game;
    static String task;

    /**
     * Used so for displaying which tasks the player needs to complete.
     * @return string of the current task that the player must complete.
     */
    public static String getNewTask() {
        if(collect5Coins()==false){
            task = "Collect 5 coins";
        } else if (GameScreen.goodricke.health > 0) {
            task = "Shoot Goodricke " + GameScreen.goodricke.health + " times";
        } else if (GameScreen.constantine.health > 0) {
            task = "Shoot constantine " + GameScreen.constantine.health + " times";
        } else if (GameScreen.james.health > 0) {
            task = "Shoot James " + GameScreen.james.health + " times";
        } else {
            task = "Collect remaining coins";
        }
        return task;
    }

    public static boolean collect5Coins(){
        //Need a counter of coins to see if 5 coins has been collected.
        if(GameScreen.numOfCoins >= 5){
            return true;
        }else{
            return false;
        }
    }
}