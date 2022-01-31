package dev.thelabradors.yorkpirates;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Pool;

public class YorkPiratesGame extends Game {

    SpriteBatch batch;
    BitmapFont font;
    ShapeRenderer sr;
    String playerCollege;
    AssetManager manager = new AssetManager();
    public BulletPool bulletPool;
    public CoinPool coinPool;
    public EnemyPool enemyPool;

    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Enemy> enemys = new ArrayList<>();
    public ArrayList<Coin> coins = new ArrayList<>();
    
    public HashMap<String, String> imgMap = new HashMap<>();
    

    @Override
    public void create() {
        font = new BitmapFont();
        batch = new SpriteBatch();
        sr = new ShapeRenderer();

        imgMap.put("bullet", "bullet.png");
        imgMap.put("player", "Boat2.png");
        imgMap.put("enemy-ship", "enemy-ship.png");
        imgMap.put("coin", "coin.png");
        imgMap.put("map", "Test1.tmx");

        manager.load(imgMap.get("bullet"), Texture.class);
        manager.load(imgMap.get("player"), Texture.class);
        manager.load(imgMap.get("enemy-ship"), Texture.class);
        manager.load(imgMap.get("coin"), Texture.class);
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("Test1.tmx", TiledMap.class);
        manager.finishLoading();

        bulletPool = new BulletPool();
        coinPool = new CoinPool();
        enemyPool = new EnemyPool();
        setScreen(new ChooseCollegeScreen(this));
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
        manager.dispose();
        sr.dispose();
    }
    class BulletPool extends Pool<Bullet>{
        @Override
        protected Bullet newObject() {
            return new Bullet(manager.get(imgMap.get("bullet"), Texture.class), 0,0, 0);
        }
    }
    class CoinPool extends Pool<Coin>{
        @Override
        protected Coin newObject() {
            // TODO Auto-generated method stub
            return new Coin(manager.get(imgMap.get("coin"), Texture.class), 0,0,1,1);
        }
    }
    class EnemyPool extends Pool<Enemy>{
        @Override
        protected Enemy newObject() {
            // TODO Auto-generated method stub
            return new Enemy(manager.get(imgMap.get("enemy-ship"), Texture.class), 0,0,5,0);
        }
    }
}
