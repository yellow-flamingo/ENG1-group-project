package dev.thelabradors.yorkpirates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    public OrthographicCamera camera = new OrthographicCamera();

    Player player;
    Building constantine;
    Building goodricke;
    Building james;

    public BulletPool bulletPool;
    public CoinPool coinPool;
    public EnemyPool enemyPool;

    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Enemy> enemys = new ArrayList<>();
    public ArrayList<Coin> coins = new ArrayList<>();
    
    public HashMap<String, String> imgMap = new HashMap<>();
    
    // Objects for each screen, so new objects don't need to be made.
    public GameScreen gameScreen = new GameScreen(this);
    public GameWonScreen gameWonScreen = new GameWonScreen(this);
    public ChooseCollegeScreen chooseCollegeScreen = new ChooseCollegeScreen(this);
    public StartScreen startScreen = new StartScreen(this);
    /**
     * Called at the start of the program
     * instantiates some of the starting variables and holders
     */
    @Override
    public void create() {
        // font for drawing text
        font = new BitmapFont();
        // for drawing sprites
        batch = new SpriteBatch();
        // for drawing shapes.
        sr = new ShapeRenderer();

        // HashMap used to map a string to the img file name
        imgMap.put("bullet", "bullet.png");
        imgMap.put("player", "Boat2.png");
        imgMap.put("enemy-ship", "enemy-ship.png");
        imgMap.put("coin", "coin.png");
        imgMap.put("map", "Test1.tmx");

        // Manager used so that assets may be reused, 
        // Instead of calling for new Textures everytime.
        manager.load(imgMap.get("bullet"), Texture.class);
        manager.load(imgMap.get("player"), Texture.class);
        manager.load(imgMap.get("enemy-ship"), Texture.class);
        manager.load(imgMap.get("coin"), Texture.class);
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		manager.load("Test1.tmx", TiledMap.class);
        manager.finishLoading();

        // Pools for memory management
        bulletPool = new BulletPool();
        coinPool = new CoinPool();
        enemyPool = new EnemyPool();
        // Start the game.
        setScreen(chooseCollegeScreen);
    }
    /**
     * Dispose of assets that are no longer used.
     */
    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
        manager.dispose();
        sr.dispose();
    }
    /**
     * Pools are used for "Pooling"
     * allows for objects to be reused, so garbage collector isn't filled up
     * Memory management requirement.
     */
    class BulletPool extends Pool<Bullet>{
        @Override
        protected Bullet newObject() {
            return new Bullet(manager.get(imgMap.get("bullet"), Texture.class), 0,0, 0);
        }
    }
    /** Same as above */
    class CoinPool extends Pool<Coin>{
        @Override
        protected Coin newObject() {
            // TODO Auto-generated method stub
            return new Coin(manager.get(imgMap.get("coin"), Texture.class), 0,0,1,1);
        }
    }
    /** Same as above 
     * Not used for assessment one, since there were only 4 enemies.
    */
    class EnemyPool extends Pool<Enemy>{
        @Override
        protected Enemy newObject() {
            // TODO Auto-generated method stub
            return new Enemy(manager.get(imgMap.get("enemy-ship"), Texture.class), 0,0,5,0);
        }
    }
}
