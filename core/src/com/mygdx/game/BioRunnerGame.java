package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.gamestate.LifeCounter;
import com.mygdx.game.screens.*;
import com.badlogic.gdx.physics.box2d.World;

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

    public OrthographicCamera getTextureCamera() {
        return textureCamera;
    }

    public Vector3 getTextureCameraProjection() {
        return(textureCamera.project(new Vector3(this.WORLD_WIDTH,this.WORLD_HEIGHT,0f)));
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        textureAssets = new TextureAssets();
        textureCamera = new OrthographicCamera();
        textureCamera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        playerScore = 0;

        this.worldSpeed = -1f;

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
}
