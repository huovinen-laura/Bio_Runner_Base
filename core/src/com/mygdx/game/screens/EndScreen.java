package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.Player;
import com.mygdx.game.WasteDisplayRecycle;
import com.mygdx.game.gamestate.LifeCounter;

import java.util.Locale;

public class EndScreen extends ScreenAdapter {
    BioRunnerGame game;
    BitmapFont font;
    private boolean isAllowedToLeave;
    private SpriteBatch textureBatch;
    private WasteDisplayRecycle wrongWasteDisplay;
    private OrthographicCamera textureCamera;
    private String score;
    private Texture sadGuy;

    Locale localeFI;
    Locale localeEN;
    I18NBundle myBundleFI;
    I18NBundle myBundleEN;
    String lost, yourScore, whatHit;

    public EndScreen(BioRunnerGame game) {
        this.game = game;
        this.font = game.getFont();
        this.sadGuy = game.textureAssets.getSadGirl();

        localeFI = new Locale("", "");
        localeEN = new Locale("en", "UK");
        myBundleFI = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), localeFI);
        myBundleEN = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), localeEN);
    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();
        this.textureBatch = new SpriteBatch();
        this.isAllowedToLeave = false;
        textureCamera = new OrthographicCamera();
        textureCamera.setToOrtho(false,game.WORLD_WIDTH,game.WORLD_HEIGHT);

        textureBatch.setProjectionMatrix(textureCamera.combined);
        this.wrongWasteDisplay = new WasteDisplayRecycle(game.allObstaclesCollection.getAllObstacles(),
                4.5f,0.50f,2f,60);
        this.score = Integer.toString(game.playerScore);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setTitleScreen();
                    game.clearScore();
                    game.collectedStuffList.clear();
                    game.allObstaclesCollection.clear();
                    game.playerScore = 0;
                    game.setPointsPerCollectable(1);
                    game.worldSpeed = game.getInitialSpeed();
                    game.setLevelNumber(1);
                    game.lifeCounter.setLives(3);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(isAllowedToLeave) {
                    game.setTitleScreen();
                    game.clearScore();
                    game.collectedStuffList.clear();
                    game.allObstaclesCollection.clear();
                    game.playerScore = 0;
                    game.setPointsPerCollectable(1);
                    game.worldSpeed = game.getInitialSpeed();
                    game.setLevelNumber(1);
                    game.lifeCounter.setLives(3);
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100/255f, 197/255f, 165/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getPrefs().getBoolean("fiOrNot")) {
            lost = myBundleFI.get("lost");
            yourScore = myBundleFI.get("score");
            whatHit = myBundleFI.get("whatHitMe");
        } else if (!game.getPrefs().getBoolean("fiOrNot")) {
            lost = myBundleEN.get("lost");
            yourScore = myBundleEN.get("score");
            whatHit = myBundleEN.get("whatHitMe");
        }

        game.batch.begin();
        font.draw(game.batch, lost, Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .75f);
        font.draw(game.batch, yourScore + score, Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .50f);
        font.draw(game.batch, whatHit,Gdx.graphics.getWidth() * 0.25f,Gdx.graphics.getHeight() * .25f);


        game.batch.end();
        this.textureBatch.begin();
        this.textureBatch.draw(this.sadGuy,0f,0f,2f,3f);
        if(this.wrongWasteDisplay.draw(this.textureBatch) ) {
            this.isAllowedToLeave = true;
        }

        this.textureBatch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
