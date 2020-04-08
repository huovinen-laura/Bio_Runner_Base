package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.gamestate.LifeCounter;
import com.mygdx.game.screens.*;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class BioRunnerGame extends Game {
    public final float WORLD_WIDTH = 8;
    public final float WORLD_HEIGHT = 4;
    public SpriteBatch batch;
    public BitmapFont font;
    SettingsScreen settings;
    SkinShopScreen skin;
    public ShitCollection collectedStuffList;
    public ObstacleCollection allObstaclesCollection;
    public float worldSpeed;
    public LifeCounter lifeCounter;
    public int playerScore;
    private TitleScreen title;
    private BallGame game;
    private EndScreen end;
    private RecycleScreen recycle;
    private ShopScreen shop;
    public TextureAssets textureAssets;
    private Vector3 projected;
    private OrthographicCamera textureCamera;

    private int levelNumber;
    private String skinName;
    private Preferences prefs;
    private Music backgroundMusic;
    private Vector2 lastCollectable;
    private float initialSpeed = -2;

    private PowerUpCollection powerUps;
    private int pointsPerCollectable;
    private GameAction currentPowerUp;

    public Texture getCurrentAnimation() {
        for(int i = 0; i < this.textureAssets.getSkinAssets().getAnimations().size(); i++) {
            if (this.skinName.contentEquals(this.textureAssets.getSkinAssets().getNames().get(i))) {
                return(this.textureAssets.getSkinAssets().getAnimations().get(i));
            }
        }

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
        };
        levelNumber = 1;
        this.skinName = "korona";
        this.pointsPerCollectable = 1;
        batch = new SpriteBatch();
        textureAssets = new TextureAssets();
        textureCamera = new OrthographicCamera();
        textureCamera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.lastCollectable = new Vector2(0f,0f);

        playerScore = 0;

        this.worldSpeed = this.initialSpeed;

        this.lifeCounter = new LifeCounter(this.textureAssets.getPlayerChonky(),this);

        collectedStuffList = new ShitCollection(this);
        allObstaclesCollection = new ObstacleCollection(this);

        font = new BitmapFont(Gdx.files.internal("font.txt"));
        font.getData().setScale(0.5f, 0.5f);
        game = new BallGame(this);
        this.title = new TitleScreen(this);
        recycle = new RecycleScreen(this);
        end = new EndScreen(this);
        shop = new ShopScreen(this);
        settings = new SettingsScreen(this);
        skin = new SkinShopScreen(this);
        this.setScreen(new TitleScreen(this));
        powerUps = new PowerUpCollection(this);

        // Tallentaminen
        prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.flush();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        backgroundMusic.setVolume(0.20f);
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

    public BitmapFont getFont() {return (this.font); };

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
        return(this.game.world);
    }

    @Override
    public void resume() {
        super.resume();
    }

    public void clearScore() {
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

    public Preferences getPrefs() {
        return prefs;
    }

    public Music getMusic() {
        return backgroundMusic;
    }

    public void setSkinName(String skinName) {
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
}
