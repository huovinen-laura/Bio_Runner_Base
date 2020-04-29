package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.bioRunnerGame.gamestate.LifeCounter;
import com.mygdx.bioRunnerGame.screens.*;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;
import java.util.Locale;

/**
 * Stores all relevant data for the game,
 */
public class BioRunnerGame extends Game {
    private final float WORLD_WIDTH = 8;
    private final float WORLD_HEIGHT = 4;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont bubbleFont;
    private String playerName = "testPlayer";
    private World world;
    private HighScoreScreen highScoreScreen;
    private List<HighScoreEntry> highScores;
    private Screen splashScreen;
    private boolean tutorialOk;

    /**
     * Gives lowest high score
     *
     * @return lowest high score
     */
    public int getLowestHighScore() {

        return(highScores.get(9).getScore());
    }

    /**
     * Adds flower points to player
     *
     * @param amount amount to be added
     */
    public void addFlowerPoints(int amount) {
        this.flowerPoints += amount;
        Preferences prefs = Gdx.app.getPreferences("points");
        prefs.putInteger("flowerPoints",this.flowerPoints);
        prefs.flush();
    }
    private int flowerPoints;
    private ShitCollection collectedStuffList;
    private ObstacleCollection allObstaclesCollection;
    private float worldSpeed;
    private LifeCounter lifeCounter;
    private int playerScore;
    private TitleScreen title;
    private BallGame game;
    private SettingsScreen settings;
    private SkinShopScreen skin;
    private EndScreen end;
    private RecycleScreen recycle;
    private ShopScreen shop;
    private CreditsScreen credits;
    private TutorialScreen tutorial;
    private TextureAssets textureAssets;
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



    /**
     * Gives the animation texture of current skin animation
     *
     * @return animation texture of currently equipped skin
     */
    public Texture getCurrentAnimation() {
        for(int i = 0; i < this.getTextureAssets().getSkinAssets().getAnimationTextures().size(); i++) {
            if (this.skinName.contentEquals(this.getTextureAssets().getSkinAssets().getNames().get(i))) {
                return(this.getTextureAssets().getSkinAssets().getAnimationTextures().get(i));
            }
        }

        return null;
    }

    /**
     * Gives the camera for textures
     *
     * @return camera
     */
    public OrthographicCamera getTextureCamera() {
        return textureCamera;
    }


    @Override
    public void create() {
        this.world = new World(new Vector2(0, -5f), true);

        this.setTextureAssets(new TextureAssets());
        this.splashScreen = new SplashScreen(this);

        setScreen(this.splashScreen);



        this.flowerPoints = Gdx.app.getPreferences("points").getInteger("flowerPoints",0);
        localeFI = new Locale("", "");
        localeEN = new Locale("en", "UK");
        myBundleFI = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), localeFI);
        myBundleEN = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), localeEN);
        this.setPlayerName(this.getNameFromPrefs());

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

            @Override
            public Texture getButtonTexture() {
                return null;
            }
        };








    }

    /**
     * All the constructor operations that depend on textureAssets
     *
     */
    public void afterLoadConstructor() {
        levelNumber = 1;
        this.skinName = Gdx.app.getPreferences("skinPrefs").getString("skinName","vakio");
        this.pointsPerCollectable = 1;

        setBatch(new SpriteBatch());
        textureCamera = new OrthographicCamera();
        textureCamera.setToOrtho(false,this.WORLD_WIDTH, this.WORLD_HEIGHT);
        projected = textureCamera.project(new Vector3(this.WORLD_WIDTH,this.WORLD_HEIGHT,0f));

        this.lastCollectable = new Vector2(0f,0f);

        setPlayerScore(0);

        this.setWorldSpeed(this.initialSpeed);

        this.setLifeCounter(new LifeCounter(this));
        setFont(new BitmapFont(Gdx.files.internal("font.txt")));
        getFont().getData().setScale(0.4f*Gdx.graphics.getWidth()/800, 0.4f*Gdx.graphics.getHeight()/400);
        setBubbleFont(new BitmapFont(Gdx.files.internal("font.txt")));
        getBubbleFont().getData().setScale(0.3f*Gdx.graphics.getWidth()/800,
                0.3f*Gdx.graphics.getHeight()/400);
        game = new BallGame(this);
        this.title = new TitleScreen(this);
        recycle = new RecycleScreen(this);
        end = new EndScreen(this);
        shop = new ShopScreen(this);
        settings = new SettingsScreen(this);
        skin = new SkinShopScreen(this);
        credits = new CreditsScreen(this);
        tutorial = new TutorialScreen(this);

        powerUps = new PowerUpCollection(this);

        // Tallentaminen
        prefs = Gdx.app.getPreferences("MyPreferences");
        prefs.flush();

        // Taustamusiikki
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        backgroundMusic.setVolume(0.60f);
        backgroundMusic.setLooping(true);
        this.highScoreScreen = new HighScoreScreen(this);
    }

    /**
     * Reads from memory the name of player
     *
     * @return players name
     */
    private String getNameFromPrefs() {
        Preferences preferences = Gdx.app.getPreferences("MyPreferences");
        return(preferences.getString("name","User"));
    }
    public void setShopScreen() { setScreen((this.shop));}

    public void setHighScoreScreen() {
        setScreen(this.highScoreScreen);
    }

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

    public void setTutorialScreen() {
        setScreen(this.tutorial);
    }

    public BitmapFont getFont() {return (this.font); }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        this.splashScreen.dispose();
        backgroundMusic.dispose();
        this.game.dispose();

        this.bubbleFont.dispose();
        this.font.dispose();

        this.splashScreen.dispose();
        this.settings.dispose();
        this.recycle.dispose();
        this.shop.dispose();


        getTextureAssets().dispose();
        getBatch().dispose();
        getFont().dispose();




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
        this.playerScore = 0;
    }

    /**
     * Returns text according to bundle key in current language
     *
     * @param key key for language bundle
     * @return text in correct language
     */
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

    /**
     * Writes new state of skin unlock in preferences
     *
     * @param skinName name of the skin to unlock
     * @param cost cost in flowerpoints
     * @return true if skin bought, false if nothing happened
     */
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
        TextureRegion current = this.getTextureAssets().getSkinAssets().getAnimationFrame(this.skinName);
        Texture toBeReturned = current.getTexture();
        return(toBeReturned);
    }

    public BitmapFont getBubbleFont() {
        return(this.bubbleFont);
    }

    public void setHighScores(List<HighScoreEntry> highScores) {
        this.highScores = highScores;
    }

    public void postNewHighScore(int playerScore, String playerName) {
        this.highScoreScreen.postNewHighScore(new HighScoreEntry(playerName,playerScore));
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        Preferences preferences = Gdx.app.getPreferences("MyPreferences");
        preferences.putString("name",playerName);
        preferences.flush();
    }

    public String getName() {
        return this.getPlayerName();
    }

    public float getWORLD_HEIGHT() {
        return WORLD_HEIGHT;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public void setBubbleFont(BitmapFont bubbleFont) {
        this.bubbleFont = bubbleFont;
    }

    public String getPlayerName() {
        return playerName;
    }

    public ShitCollection getCollectedStuffList() {
        return collectedStuffList;
    }

    public void setCollectedStuffList(ShitCollection collectedStuffList) {
        this.collectedStuffList = collectedStuffList;
    }

    public ObstacleCollection getAllObstaclesCollection() {
        return allObstaclesCollection;
    }

    public void setAllObstaclesCollection(ObstacleCollection allObstaclesCollection) {
        this.allObstaclesCollection = allObstaclesCollection;
    }

    public float getWorldSpeed() {
        return worldSpeed;
    }

    public void setWorldSpeed(float worldSpeed) {
        this.worldSpeed = worldSpeed;
    }

    public LifeCounter getLifeCounter() {
        return lifeCounter;
    }

    public void setLifeCounter(LifeCounter lifeCounter) {
        this.lifeCounter = lifeCounter;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public TextureAssets getTextureAssets() {
        return textureAssets;
    }

    public void setTextureAssets(TextureAssets textureAssets) {
        this.textureAssets = textureAssets;
    }

    public void setTutorialOk() {
        this.tutorialOk = true;
    }
}
