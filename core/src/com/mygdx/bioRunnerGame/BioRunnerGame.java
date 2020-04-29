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
 * Stores all relevant data for the game.
 */
public class BioRunnerGame extends Game {

    /**
     * Determines the width of the game screen.
     */
    private final float WORLD_WIDTH = 8;

    /**
     * Determines the height of the game screen.
     */
    private final float WORLD_HEIGHT = 4;

    /**
     * The batch for the game.
     */
    private SpriteBatch batch;

    /**
     * The font for the game.
     */
    private BitmapFont font;

    /**
     * Font used in recycle screen and in high score list.
     */
    private BitmapFont bubbleFont;

    /**
     * Sets a default name for the player.
     */
    private String playerName = "testPlayer";

    /**
     * Handles box2d related things.
     */
    private World world;

    /**
     * Screen that shows high scores.
     */
    private HighScoreScreen highScoreScreen;

    /**
     * List that holds high scores.
     */
    private List<HighScoreEntry> highScores;

    /**
     * First screen of the game which shows logos.
     */
    private Screen splashScreen;

    /**
     * Gives lowest high score.
     */
    public int getLowestHighScore() {

        return(highScores.get(9).getScore());
    }

    /**
     * Adds flower points to player.
     *
     * @param amount amount to be added.
     */
    public void addFlowerPoints(int amount) {
        this.flowerPoints += amount;
        Preferences prefs = Gdx.app.getPreferences("points");
        prefs.putInteger("flowerPoints",this.flowerPoints);
        prefs.flush();
    }

    /**
     * The flower points player has.
     */
    private int flowerPoints;

    /**
     * List that holds info about collected biowaste.
     */
    private ShitCollection collectedStuffList;

    /**
     * List that holds info about collected obstacles.
     */
    private ObstacleCollection allObstaclesCollection;

    /**
     * The speed of the player.
     */
    private float worldSpeed;

    /**
     * The player's life counter.
     */
    private LifeCounter lifeCounter;

    /**
     * The player's score for one round.
     */
    private int playerScore;

    /**
     * The title screen.
     */
    private TitleScreen title;

    /**
     * The game screen.
     */
    private BallGame game;

    /**
     * The settings screen.
     */
    private SettingsScreen settings;

    /**
     * The skin shop screen.
     */
    private SkinShopScreen skin;

    /**
     * The "game over" screen.
     */
    private EndScreen end;

    /**
     * The waypoint screen.
     */
    private RecycleScreen recycle;

    /**
     * The power up screen.
     */
    private ShopScreen shop;

    /**
     * The credits screen.
     */
    private CreditsScreen credits;

    /**
     * The tutorial screen.
     */
    private TutorialScreen tutorial;

    /**
     * The class that holds all the textures of the game.
     */
    private TextureAssets textureAssets;

    /**
     * The real pixel coordinates.
     */
    private Vector3 projected;

    /**
     * Camera for drawing the textures.
     */
    private OrthographicCamera textureCamera;

    /**
     * Camera for drawing fonts.
     */
    private OrthographicCamera fontCamera;

    /**
     * Tells the number of the level.
     */
    private int levelNumber;

    /**
     * The name of the skin.
     */
    private String skinName;

    /**
     * Game's preferences.
     */
    private Preferences prefs;

    /**
     * The background music for the game.
     */
    private Music backgroundMusic;

    /**
     * The latest spawned collectable.
     */
    private Vector2 lastCollectable;

    /**
     * The speed of the game at the start.
     */
    private float initialSpeed = -2;

    /**
     * The game's power ups.
     */
    private PowerUpCollection powerUps;

    /**
     * How many points player gains from collectables.
     */
    private int pointsPerCollectable;

    /**
     * The current power up.
     */
    private GameAction currentPowerUp;

    /**
     * Finnish locale.
     */
    private Locale localeFI;

    /**
     * English locale.
     */
    private Locale localeEN;

    /**
     * Finnish texts.
     */
    private I18NBundle myBundleFI;

    /**
     * English texts.
     */
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

    /**
     * Creates everything needed for the game to function.
     */
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
            /**
             * Executes the power up.
             */
            @Override
            public void doAction() {

            }

            /**
             * Gets the name of the power up.
             *
             * @return Gets the name of the power up
             */
            @Override
            public String getName() {
                return null;
            }

            /**
             * Undos the power up.
             */
            @Override
            public void undoAction() {

            }

            /**
             * Describes the power up.
             *
             * @return Returns the description of the power up.
             */
            @Override
            public String getDescription() {
                return null;
            }

            /**
             * Returns the button for the power up.
             *
             * @return Returns the button for the power up.
             */
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

    /**
     * Changes screen to power up screen.
     */
    public void setShopScreen() { setScreen((this.shop));}

    /**
     * Changes screen to high score screen.
     */
    public void setHighScoreScreen() {
        setScreen(this.highScoreScreen);
    }

    /**
     * Changes screen to end screen.
     */
    public void setEndScreen() {
        setScreen(this.end);
    }

    /**
     * Changes screen to game screen.
     */
    public void setGameScreen() {
        setScreen(this.game);
    }

    /**
     * Changes screen to title screen.
     */
    public void setTitleScreen() {
        setScreen(this.title);
    }

    /**
     * Changes screen to waypoint screen.
     */
    public void setRecycleScreen() {
        setScreen(this.recycle);
    }

    /**
     * Changes screen to settings screen.
     */
    public void setSettingsScreen() {
        setScreen(this.settings);
    }

    /**
     * Changes screen to skin shop screen.
     */
    public void setSkinShopScreen() {
        setScreen(this.skin);
    }

    /**
     * Changes screen to credits screen.
     */
    public void setCreditsScreen() {
        setScreen(this.credits);
    }

    /**
     * Changes screen to tutorial screen.
     */
    public void setTutorialScreen() {
        setScreen(this.tutorial);
    }

    /**
     * Returns the game's font.
     *
     * @return Returns the game's font.
     */
    public BitmapFont getFont() {return (this.font); }

    /**
     * Renders the game.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Disposes all the textures, screens, fonts, etc.
     */
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
    }

    /**
     * Pauses the game.
     */
    @Override
    public void pause() {
        super.pause();
    }

    /**
     * Returns the world.
     *
     * @return Returns the world.
     */
    public World getWorld() {
        return(this.world);
    }

    /**
     * Continues the game.
     */
    @Override
    public void resume() {
        super.resume();
    }

    /**
     * Clears player's score after the game ends.
     */
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

    /**
     * Returns Vector3 projected.
     *
     * @return Returns Vector3 projected.
     */
    public Vector3 getProjected() {
        return projected;
    }

    /**
     * Sets the Vector3 projected.
     *
     * @param projected New Vector3 projected.
     */
    public void setProjected(Vector3 projected) {
        this.projected = projected;
    }

    /**
     * Return the value of one collectable.
     *
     * @return Returns the value of one collectable.
     */
    public int getPointsPerCollectable() {
        return pointsPerCollectable;
    }

    /**
     * Sets how many points player gains from one collectable.
     *
     * @param pointsPerCollectable New points per collectable.
     */
    public void setPointsPerCollectable(int pointsPerCollectable) {
        this.pointsPerCollectable = pointsPerCollectable;
    }

    /**
     * Returns the skin's name.
     *
     * @return Returns the skin's name.
     */
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

    /**
     * Returns the game's preferences.
     *
     * @return Returns the game's preferences.
     */
    public Preferences getPrefs() {

        return(Gdx.app.getPreferences("Settings"));
    }

    /**
     * Returns the background music.
     *
     * @return Returns the background music.
     */
    public Music getMusic() {
        return backgroundMusic;
    }

    /**
     * Sets a name for the skin.
     *
     */
    public void setSkinName(String skinName) {
        Preferences preferences = Gdx.app.getPreferences("skinPrefs");
        preferences.putString("skinName",skinName);
        preferences.flush();
        this.skinName = skinName;
    }

    /**
     * Returns the level number.
     *
     * @return Returns the level number.
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Sets the level number.
     *
     * @param levelNumber New level number.
     */
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    /**
     * Returns the latest spawned collectable.
     *
     * @return Returns the latest spawned collectable.
     */
    public Vector2 getLastCollectable() {
        return lastCollectable;
    }

    /**
     * Sets the latests collectable.
     *
     * @param lastCollectable The latests collectable.
     */
    public void setLastCollectable(Vector2 lastCollectable) {
        this.lastCollectable = lastCollectable;
    }

    /**
     * Returns the initial speed.
     *
     * @return Returns the initial speed.
     */
    public float getInitialSpeed() {
        return initialSpeed;
    }

    /**
     * Sets a new initial speed.
     *
     * @param initialSpeed New initial speed.
     */
    public void setInitialSpeed(float initialSpeed) {
        this.initialSpeed = initialSpeed;
    }

    /**
     * Returns power ups.
     *
     * @return Returns power ups.
     */
    public PowerUpCollection getPowerUps() {
        return powerUps;
    }

    /**
     * Sets power ups.
     *
     * @param powerUps New power ups.
     */
    public void setPowerUps(PowerUpCollection powerUps) {
        this.powerUps = powerUps;
    }

    /**
     * Returns the current power up.
     *
     * @return Returns the current power up.
     */
    public GameAction getCurrentPowerUp() {
        return currentPowerUp;
    }

    /**
     * Sets the current power up.
     *
     * @param currentPowerUp Sets the current power up.
     */
    public void setCurrentPowerUp(GameAction currentPowerUp) {
        this.currentPowerUp = currentPowerUp;
    }

    /**
     * Returns the world's width.
     *
     * @return Returns the world's width.
     */
    public float getWORLD_WIDTH() {
        return WORLD_WIDTH;
    }

    /**
     * Returns the finnish locale.
     *
     * @return Returns the finnish locale.
     */
    public Locale getLocaleFI() {
        return localeFI;
    }

    /**
     * Sets a new finnish locale.
     *
     * @param localeFI New finnish locale.
     */
    public void setLocaleFI(Locale localeFI) {
        this.localeFI = localeFI;
    }

    /**
     * Returns the english locale.
     *
     * @return Returns the english locale.
     */
    public Locale getLocaleEN() {
        return localeEN;
    }

    /**
     * Sets a new english locale.
     *
     * @param localeEN New english locale.
     */
    public void setLocaleEN(Locale localeEN) {
        this.localeEN = localeEN;
    }

    /**
     * Returns the finnish bundle.
     *
     * @return Returns the finnish bundle.
     */
    public I18NBundle getMyBundleFI() {
        return myBundleFI;
    }

    /**
     * Sets a new finnish bundle.
     *
     * @param myBundleFI New finnish bundle.
     */
    public void setMyBundleFI(I18NBundle myBundleFI) {
        this.myBundleFI = myBundleFI;
    }

    /**
     * Returns the english bundle.
     *
     * @return Returns the english bundle.
     */
    public I18NBundle getMyBundleEN() {
        return myBundleEN;
    }

    /**
     * Sets a new english bundle.
     *
     * @param myBundleEN New english bundle.
     */
    public void setMyBundleEN(I18NBundle myBundleEN) {
        this.myBundleEN = myBundleEN;
    }

    /**
     * Returns the player's flower points.
     *
     * @return Returns player's flower point.
     */
    public int getFlowerPoints() {
        return flowerPoints;
    }

    /**
     * Sets the player's flower points.
     *
     * @param flowerPoints New flower points.
     */
    public void setFlowerPoints(int flowerPoints) {
        this.flowerPoints = flowerPoints;
    }

    /**
     * Returns the current skin frame.
     *
     * @return Returns the current skin frame.
     */
    public Texture getCurrentSkinFrame() {
        TextureRegion current = this.getTextureAssets().getSkinAssets().getAnimationFrame(this.skinName);
        Texture toBeReturned = current.getTexture();
        return(toBeReturned);
    }

    /**
     * Returns the bubbleFont.
     *
     * @return Returns the bubbleFont.
     */
    public BitmapFont getBubbleFont() {
        return(this.bubbleFont);
    }

    /**
     * Sets the high scores.
     *
     * @param highScores New high scores.
     */
    public void setHighScores(List<HighScoreEntry> highScores) {
        this.highScores = highScores;
    }

    /**
     * Posts a new score to the high score list.
     *
     * @param playerScore Player's score.
     * @param playerName Player's name.
     */
    public void postNewHighScore(int playerScore, String playerName) {
        this.highScoreScreen.postNewHighScore(new HighScoreEntry(playerName,playerScore));
    }

    /**
     * Sets player's name.
     *
     * @param playerName Player's name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        Preferences preferences = Gdx.app.getPreferences("MyPreferences");
        preferences.putString("name",playerName);
        preferences.flush();
    }

    /**
     * Returns the player's name.
     *
     * @return Returns the player's name.
     */
    public String getName() {
        return this.getPlayerName();
    }

    /**
     * Returns the world's height.
     *
     * @return Returns the world's height.
     */
    public float getWORLD_HEIGHT() {
        return WORLD_HEIGHT;
    }

    /**
     * Returns the batch.
     *
     * @return Returns the batch.
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * Sets the batch.
     *
     * @param batch New batch.
     */
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    /**
     * Sets the font.
     *
     * @param font New font.
     */
    public void setFont(BitmapFont font) {
        this.font = font;
    }

    /**
     * Sets a new bubbleFont.
     *
     * @param bubbleFont new BubbleFont.
     */
    public void setBubbleFont(BitmapFont bubbleFont) {
        this.bubbleFont = bubbleFont;
    }

    /**
     * Returns player's name.
     *
     * @return Return player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the list of collected biowaste.
     *
     * @return Returns the list of collected biowaste.
     */
    public ShitCollection getCollectedStuffList() {
        return collectedStuffList;
    }

    /**
     * Sets a new list for collectables.
     *
     * @param collectedStuffList New list for collectables.
     */
    public void setCollectedStuffList(ShitCollection collectedStuffList) {
        this.collectedStuffList = collectedStuffList;
    }

    /**
     * Returns the list of collected obstacles.
     *
     * @return Returns the list of collected obstacles.
     */
    public ObstacleCollection getAllObstaclesCollection() {
        return allObstaclesCollection;
    }

    /**
     * Sets a new list for obstacles.
     *
     * @param allObstaclesCollection New list for obstacles.
     */
    public void setAllObstaclesCollection(ObstacleCollection allObstaclesCollection) {
        this.allObstaclesCollection = allObstaclesCollection;
    }

    /**
     * Returns the worldSpeed.
     *
     * @return Returns the worldSpeed.
     */
    public float getWorldSpeed() {
        return worldSpeed;
    }

    /**
     * Sets a new worldSpeed.
     *
     * @param worldSpeed New worldSpeed.
     */
    public void setWorldSpeed(float worldSpeed) {
        this.worldSpeed = worldSpeed;
    }

    /**
     * Returns the life counter.
     *
     * @return Returns the life counter.
     */
    public LifeCounter getLifeCounter() {
        return lifeCounter;
    }

    /**
     * Sets a new life counter.
     *
     * @param lifeCounter New life counter.
     */
    public void setLifeCounter(LifeCounter lifeCounter) {
        this.lifeCounter = lifeCounter;
    }

    /**
     * Returns the player's score.
     *
     * @return Returns the player's score.
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * Sets a new score for the player.
     *
     * @param playerScore New score for player.
     */
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    /**
     * Returns textures.
     *
     * @return Returns textures.
     */
    public TextureAssets getTextureAssets() {
        return textureAssets;
    }

    /**
     * Sets new textureAssets.
     *
     * @param textureAssets New textureAssets.
     */
    public void setTextureAssets(TextureAssets textureAssets) {
        this.textureAssets = textureAssets;
    }
}
