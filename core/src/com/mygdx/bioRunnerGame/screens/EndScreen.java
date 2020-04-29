package com.mygdx.bioRunnerGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.bioRunnerGame.BioRunnerGame;
import com.mygdx.bioRunnerGame.WasteDisplayRecycle;

/**
 * Game over screen
 *
 */
public class EndScreen extends ScreenAdapter implements Input.TextInputListener {
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
    private String PlayerName;

    public EndScreen(BioRunnerGame game) {
        this.game = game;
        this.font = game.getFont();
        this.sadGuy = game.getTextureAssets().getSadGirl();
        tausta = game.getTextureAssets().getEnd();
        width = game.getWORLD_WIDTH();
        height = game.getWORLD_HEIGHT();

    }

    @Override
    public void show() {
        game.setBatch(new SpriteBatch());
        this.flowerPoints = game.getPlayerScore() /10;
        this.textureBatch = new SpriteBatch();
        this.isAllowedToLeave = false;
        textureCamera = new OrthographicCamera();
        textureCamera.setToOrtho(false, game.getWORLD_WIDTH(), game.getWORLD_HEIGHT());

        textureBatch.setProjectionMatrix(textureCamera.combined);
        this.wrongWasteDisplay = new WasteDisplayRecycle(game.getAllObstaclesCollection().getAllObstacles(),
                2.8f,1.1f,2f,60);
        this.score = Integer.toString(game.getPlayerScore());

        if(game.getLowestHighScore() < game.getPlayerScore()) {
            this.newHighScore();

        }
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.addFlowerPoints(flowerPoints);
                    game.setTitleScreen();
                    game.clearScore();
                    game.getCollectedStuffList().clear();
                    game.getAllObstaclesCollection().clear();
                    game.setPlayerScore(0);
                    game.setPointsPerCollectable(1);
                    game.setWorldSpeed(game.getInitialSpeed());
                    game.setLevelNumber(1);
                    game.getLifeCounter().setLives(3);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                if(isAllowedToLeave) {
                    game.addFlowerPoints(flowerPoints);
                    game.setTitleScreen();
                    game.clearScore();
                    game.getCollectedStuffList().clear();
                    game.getAllObstaclesCollection().clear();
                    game.setPlayerScore(0);
                    game.setPointsPerCollectable(1);
                    game.setWorldSpeed(game.getInitialSpeed());
                    game.setLevelNumber(1);
                    game.getLifeCounter().setLives(3);
                }
                return true;
            }
        });
    }

    private void newHighScore() {

        Gdx.input.getTextInput(this,game.getText("newName"),"",game.getText("newNameHint"));


    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100/255f, 197/255f, 165/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.textureBatch.begin();
        this.textureBatch.draw(tausta, 0, 0, width, height);
        this.textureBatch.end();

        game.getBatch().begin();
        font.draw(game.getBatch(), game.getText("gameover"), game.getProjected().x * 0.08f,
                game.getProjected().y * .88f);
        font.draw(game.getBatch(), game.getText("lost"), game.getProjected().x * 0.08f,
                game.getProjected().y * .78f);
        font.draw(game.getBatch(), game.getText("score") + score, game.getProjected().x * 0.08f,
                game.getProjected().y * .68f);
        font.draw(game.getBatch(), game.getText("newFlowerPoints1") + this.flowerPoints + " " + game.getText("newFlowerPoints2"),
                game.getProjected().x * 0.08f,game.getProjected().y * .58f);
        font.draw(game.getBatch(), game.getText("whatHitMe"),game.getProjected().x * 0.08f,game.getProjected().y * .45f);
        font.draw(game.getBatch(), game.getText("tap"), game.getProjected().x * 0.065f, game.getProjected().y * 0.205f);
        game.getBatch().end();

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

    @Override
    public void input(String text) {

        if(text.length() < 5) {
            this.game.setPlayerName(text);
        } else {
            this.game.setPlayerName(text.substring(0,5));
        }
        try {
            game.postNewHighScore(game.getPlayerScore(), game.getPlayerName());
        }
        catch (Exception e) {
            // asdfaskdjfa
        }

        }


    @Override
    public void canceled() {
    }
}
