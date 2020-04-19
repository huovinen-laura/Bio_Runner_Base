package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.Button;
import com.mygdx.game.GameAction;
import com.mygdx.game.TextBubble;
import com.mygdx.game.gamestate.LifeCounter;

import java.util.Locale;

public class ShopScreen extends ScreenAdapter {
    public BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private Button upperButton;
    private Button lowerButton;
    private OrthographicCamera camera;
    private GameAction[] powerUps;
    private String firstPowerUp,secondPowerUp;
    private Texture tausta;
    private float width, height;





    public ShopScreen(BioRunnerGame game) {
        this.game = game;
        this.font = game.getFont();
        tausta= game.textureAssets.getCommon();
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;



    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.texturesBatch.draw(tausta, 0, 0, width, height);
        this.texturesBatch.end();

        game.batch.begin();


        this.font.draw(game.batch, firstPowerUp, game.getProjected().x * 0.30f,
                game.getProjected().y * 0.4f);

        this.font.draw(game.batch, secondPowerUp,

                game.getProjected().x * 0.3f,game.getProjected().y * 0.75f);
        game.batch.end();

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();

        this.upperButton.draw(texturesBatch);
        this.lowerButton.draw(texturesBatch);
        this.texturesBatch.end();
    }

    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        this.isPossibleToLeave = true;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WORLD_WIDTH,game.WORLD_HEIGHT);
        game.collectedStuffList.clear();
        game.allObstaclesCollection.clear();
        this.powerUps = game.getPowerUps().getTwoRandomPowers();
        firstPowerUp = game.getText(powerUps[1].getName());
        secondPowerUp = game.getText(powerUps[0].getName());
        this.lowerButton = new Button(1f,1f,1f,1f, powerUps[1].getButtonTexture());
        this.upperButton = new Button(1f,2.3f,1f,1f, powerUps[0].getButtonTexture());

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));

                if( upperButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    game.getCurrentPowerUp().undoAction();
                    powerUps[0].doAction();
                    game.setCurrentPowerUp(powerUps[0]);
                    game.setGameScreen();

                } else if( lowerButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    game.getCurrentPowerUp().undoAction();
                    powerUps[1].doAction();
                    game.setCurrentPowerUp(powerUps[1]);
                    game.setGameScreen();
                }

                return true;
            }
        });

    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
}
