package dev.thelabradors.yorkpirates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.ApplicationListener;



public class Tasks extends ApplicationAdapter{
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
        }
        else{
            if(shoot7Goodricke()==false){
                task = "Shoot Goodricke 7 times";
            }
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