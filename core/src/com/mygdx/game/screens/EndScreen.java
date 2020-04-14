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
    private BitmapFont font;
    private boolean isAllowedToLeave;
    private SpriteBatch textureBatch;
    private WasteDisplayRecycle wrongWasteDisplay;
    private OrthographicCamera textureCamera;
    private String score;
    private Texture sadGuy;
    private Texture tausta;
    private int flowerPoints;
    private float width, height;

    public EndScreen(BioRunnerGame game) {
        this.game = game;
        this.font = game.getFont();
        this.sadGuy = game.textureAssets.getSadGirl();
        tausta = game.textureAssets.getEnd();
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;

    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();
        this.flowerPoints = game.playerScore/10;
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
                    game.addFlowerPoints(flowerPoints);
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
                    game.addFlowerPoints(flowerPoints);
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

        this.textureBatch.begin();
        this.textureBatch.draw(tausta, 0, 0, width, height);
        this.textureBatch.end();

        game.batch.begin();
        font.draw(game.batch, game.getText("lost"), Gdx.graphics.getWidth() * 0.08f,
                Gdx.graphics.getHeight() * .85f);
        font.draw(game.batch, game.getText("score") + score, Gdx.graphics.getWidth() * 0.08f,
                Gdx.graphics.getHeight() * .75f);
        font.draw(game.batch, game.getText("whatHitMe"),Gdx.graphics.getWidth() * 0.08f,Gdx.graphics.getHeight() * .65f);
        font.draw(game.batch, "Added flowerpoints: " + this.flowerPoints,
                Gdx.graphics.getWidth() * 0.08f,Gdx.graphics.getHeight() * .40f);
        font.draw(game.batch, "Flowerpoints total: " + (game.getFlowerPoints() + this.flowerPoints),
                Gdx.graphics.getWidth() * 0.08f,Gdx.graphics.getHeight() * .30f);

        game.batch.end();

        this.textureBatch.begin();
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
