package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;


public class Tasks extends ApplicationAdapter{

    YorkPiratesGame game;

    private SpriteBatch batch;
    private BitmapFont font;
    static String task;

    @Override
    public void create(){
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        task = getNewTask();
    }

    public static String getNewTask() {
        if(Collect5Coins()==false){
            task = "Collect 5 coins";
        } else if (shoot7Goodricke()==false) {
            task = "Shoot Goodricke 7 times";
        } else if (shoot7Constantine() == false) {
            task = "Shoot constantine 7 times";
        } else if (shoot7James() == false) {
            task = "Shoot James 7 times";
        } else if (shoot7Derwent() == false) {
            task = "Shoot Derwent 7 times";
        } else {
            task = "Collect remaining coins";
        }
        return task;
    }

    public static boolean Collect5Coins(){
        //Need a counter of coins to see if 5 coins has been collected.
        if(Coin.getNumCoins() >= 5){
            return true;
        }else{
            return false;
        }
    }

    public static boolean shoot7Goodricke() {
        if (GameScreen.goodricke.health == 0) {
            return true;
        }
        return false;
    }

    public static boolean shoot7Constantine() {
        if (GameScreen.constantine.health == 0) {
            return true;
        }
        return false;
    }

    public static boolean shoot7James() {
        if (GameScreen.james.health == 0) {
            return true;
        }
        return false;
    }

    public static boolean shoot7Derwent() {
        if (GameScreen.derwent.health == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render(){
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.setColor(Color.WHITE);
        font.draw(batch, task, 10, 10);
        batch.end();
    }

}