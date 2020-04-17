package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.gamestate.LifeCounter;
import com.mygdx.game.screens.*;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Locale;

public class BioRunnerGame extends Game {
    public final float WORLD_WIDTH = 8;
    public final float WORLD_HEIGHT = 4;
    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont bubbleFont;
    private World world;

    public void addFlowerPoints(int amount) {
        this.flowerPoints += amount;
        Preferences prefs = Gdx.app.getPreferences("points");
        prefs.putInteger("flowerPoints",this.flowerPoints);
        prefs.flush();
    }

    private int flowerPoints;
    public ShitCollection collectedStuffList;
    public ObstacleCollection allObstaclesCollection;
    public float worldSpeed;
    public LifeCounter lifeCounter;
    public int playerScore;
    private TitleScreen title;
    private BallGame game;
    private SettingsScreen settings;
    private SkinShopScreen skin;
    private EndScreen end;
    private RecycleScreen recycle;
    private ShopScreen shop;
    private CreditsScreen credits;
    public TextureAssets textureAssets;
    private Vector3 projected;
    private OrthographicCamera textureCamera;
    private OrthographicCamera fontCamera;

    private int levelNumber;
    private String skinName;
    private Preferences prefs;
    private Music backgroundMusic;
    private Vector2 lastCollectable;
    private float initialSpeed = -2;

    private PowerUpCollection powerUps;
    private int pointsPerCollectable;
    private GameAction currentPowerUp;

    private Locale localeFI;
    private Locale localeEN;
    private I18NBundle myBundleFI;
    private I18NBundle myBundleEN;



    public Texture getCurrentAnimation() {
        for(int i = 0; i < this.textureAssets.getSkinAssets().getAnimationTextures().size(); i++) {
            if (this.skinName.contentEquals(this.textureAssets.getSkinAssets().getNames().get(i))) {
                return(this.textureAssets.getSkinAssets().getAnimationTextures().get(i));
            }
        }

        Gdx.app.log("TextureCurrent",  "null");
        return null;
    }

    public OrthographicCamera getTextureCamera() {
        return textureCamera;
    }

    public Vector3 getTextureCameraProjection() {
        return(textureCamera.project(new Vector3(this.WORLD_WIDTH,this.WORLD_HEIGHT,0f)));
    }

    @Override
    public void create() {
        this.world = new World(new Vector2(0, -5f), true);

        this.flowerPoints = Gdx.app.getPreferences("points").getInteger("flowerPoints",0);
        localeFI = new Locale("", "");
        localeEN = new Locale("en", "UK");
        myBundleFI = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), localeFI);
        myBundleEN = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), localeEN);

        this.currentPowerUp = new GameAction() {
            @Override
            public void doAction() {

            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public void undoAction() {

            }

            @Override
            public String getDescription() {
                return null;
            }
        };

        levelNumber = 1;
        this.skinName = Gdx.app.getPreferences("skinPrefs").getString("skinName","vakio");
        this.pointsPerCollectable = 1;

        batch = new SpriteBatch();
        textureAssets = new TextureAssets();
        textureCamera = new OrthographicCamera();
        textureCamera.setToOrtho(false,game.WORLD_WIDTH, game.WORLD_HEIGHT);
        projected = textureCamera.project(new Vector3(game.WORLD_WIDTH,game.WORLD_HEIGHT,0f));

        this.lastCollectable = new Vector2(0f,0f);

        playerScore = 0;

        this.worldSpeed = this.initialSpeed;

        this.lifeCounter = new LifeCounter(this.textureAssets.getPlayerChonky(),this);

        collectedStuffList = new ShitCollection(this);
        allObstaclesCollection = new ObstacleCollection(this);

        font = new BitmapFont(Gdx.files.internal("font.txt"));
        font.getData().setScale(0.4f*Gdx.graphics.getWidth()/800, 0.4f*Gdx.graphics.getHeight()/400);
        bubbleFont = new BitmapFont(Gdx.files.internal("font.txt"));
        bubbleFont.getData().setScale(0.3f*Gdx.graphics.getWidth()/800,
                0.3f*Gdx.graphics.getHeight()/400);
        game = new BallGame(this);
        this.title = new TitleScreen(this);
        recycle = new RecycleScreen(this);
        end = new EndScreen(this);
        shop = new ShopScreen(this);
        settings = new SettingsScreen(this);
        skin = new SkinShopScreen(this);
        credits = new CreditsScreen(this);
        this.setScreen(new TitleScreen(this));
        powerUps = new PowerUpCollection(this);

        // Tallentaminen
        prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.flush();

        // Taustamusiikki
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        backgroundMusic.setVolume(0.40f);
        backgroundMusic.setLooping(true);

    }

    public void setShopScreen() { setScreen((this.shop));}


    public void setEndScreen() {
        setScreen(this.end);
    }

    public void setGameScreen() {
        setScreen(this.game);
    }

    public void setTitleScreen() {
        setScreen(this.title);
    }

    public void setRecycleScreen() {
        setScreen(this.recycle);
    }

    public void setSettingsScreen() {
        setScreen(this.settings);
    }

    public void setSkinShopScreen() {
        setScreen(this.skin);
    }

    public void setCreditsScreen() {
        setScreen(this.credits);
    }

    public BitmapFont getFont() {return (this.font); }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        Gdx.app.log("Game", "dispose");
        textureAssets.dispose();
        batch.dispose();
        font.dispose();
        backgroundMusic.dispose();


        // asset manager
    }

    @Override
    public void pause() {
        super.pause();
    }

    public World getWorld() {
        return(this.world);
    }

    @Override
    public void resume() {
        super.resume();
    }

    public void clearScore() {
    }

    public String getText(String key) {

        if(this.getPrefs().getBoolean("fiOrNot",true) ) {
            return(this.getMyBundleFI().get(key));
        } else {
            return(this.getMyBundleEN().get(key));
        }
    }

    public Vector3 getProjected() {
        return projected;
    }

    public void setProjected(Vector3 projected) {
        this.projected = projected;
    }

    public int getPointsPerCollectable() {
        return pointsPerCollectable;
    }

    public void setPointsPerCollectable(int pointsPerCollectable) {
        this.pointsPerCollectable = pointsPerCollectable;
    }

    public String getSkinName() {
        return skinName;
    }

    public boolean unlockSkin(String skinName, int cost) {
        if(cost <= flowerPoints) {
            addFlowerPoints(-cost);
            Preferences preferences = Gdx.app.getPreferences("skinPrefs");
            preferences.putBoolean(skinName,true);
            preferences.flush();
            return true;
        }

        return false;

    }

    public boolean isUnlocked(String skinName) {
        Preferences preferences = Gdx.app.getPreferences("skinPrefs");
        return(preferences.getBoolean(skinName,false));

    }

    public Preferences getPrefs() {
        return(Gdx.app.getPreferences("Settings"));

    }

    public Music getMusic() {
        return backgroundMusic;
    }

    public void setSkinName(String skinName) {
        Preferences preferences = Gdx.app.getPreferences("skinPrefs");
        preferences.putString("skinName",skinName);
        preferences.flush();
        this.skinName = skinName;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public Vector2 getLastCollectable() {
        return lastCollectable;
    }

    public void setLastCollectable(Vector2 lastCollectable) {
        this.lastCollectable = lastCollectable;
    }

    public float getInitialSpeed() {
        return initialSpeed;
    }

    public void setInitialSpeed(float initialSpeed) {
        this.initialSpeed = initialSpeed;
    }


    public PowerUpCollection getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(PowerUpCollection powerUps) {
        this.powerUps = powerUps;
    }

    public GameAction getCurrentPowerUp() {
        return currentPowerUp;
    }

    public void setCurrentPowerUp(GameAction currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }

    public float getWORLD_WIDTH() {
        return WORLD_WIDTH;
    }

    public Locale getLocaleFI() {
        return localeFI;
    }

    public void setLocaleFI(Locale localeFI) {
        this.localeFI = localeFI;
    }

    public Locale getLocaleEN() {
        return localeEN;
    }

    public void setLocaleEN(Locale localeEN) {
        this.localeEN = localeEN;
    }

    public I18NBundle getMyBundleFI() {
        return myBundleFI;
    }

    public void setMyBundleFI(I18NBundle myBundleFI) {
        this.myBundleFI = myBundleFI;
    }

    public I18NBundle getMyBundleEN() {
        return myBundleEN;
    }

    public void setMyBundleEN(I18NBundle myBundleEN) {
        this.myBundleEN = myBundleEN;
    }

    public int getFlowerPoints() {
        return flowerPoints;
    }

    public void setFlowerPoints(int flowerPoints) {
        this.flowerPoints = flowerPoints;
    }

    public Texture getCurrentSkinFrame() {
        TextureRegion current = this.textureAssets.getSkinAssets().getAnimationFrame(this.getCurrentAnimation());
        Texture toBeReturned = current.getTexture();
        return(toBeReturned);
    }

    public BitmapFont getBubbleFont() {
        return(this.bubbleFont);
    }
}
