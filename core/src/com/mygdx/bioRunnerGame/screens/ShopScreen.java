package com.mygdx.bioRunnerGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bioRunnerGame.BioRunnerGame;
import com.mygdx.bioRunnerGame.Button;
import com.mygdx.bioRunnerGame.GameAction;

/**
 * Displays the choice of power up
 */
public class ShopScreen extends ScreenAdapter {
    BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private Button upperButton;
    private Button lowerButton;
    private OrthographicCamera camera;
    private GameAction[] powerUps;
    private String firstPowerUp,secondPowerUp;
    private String firstPowerUpInfo, secondPowerUpInfo;
    private Texture tausta;
    private float width, height;





    /**
     * Initializes the screen
     *
     */
    public ShopScreen(BioRunnerGame game) {
        this.game = game;
        this.font = game.getFont();
        tausta= game.getTextureAssets().getCommon();
        width =game.getWORLD_WIDTH();
        height = game.getWORLD_HEIGHT();



    }

    /**
     * Renders the screen
     *
     * @param delta time from last call to render
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.texturesBatch.draw(tausta, 0, 0, width, height);
        this.texturesBatch.end();

        game.getBatch().begin();

        this.font.draw(game.getBatch(), game.getText("choose"), game.getProjected().x * 0.40f,
                game.getProjected().y * 0.90f);
        //Left button
        this.font.draw(game.getBatch(), firstPowerUp, game.getProjected().x * 0.2f,
                game.getProjected().y * 0.55f);
        this.font.draw(game.getBatch(), firstPowerUpInfo, game.getProjected().x * 0.13f,
                game.getProjected().y * 0.45f);
        //Right button
        this.font.draw(game.getBatch(), secondPowerUp, game.getProjected().x * 0.6f,
                game.getProjected().y * 0.55f);
        this.font.draw(game.getBatch(), secondPowerUpInfo, game.getProjected().x * 0.53f,
                game.getProjected().y * 0.45f);
        game.getBatch().end();

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();

        this.upperButton.draw(texturesBatch);
        this.lowerButton.draw(texturesBatch);
        this.texturesBatch.end();
    }

    /**
     * Readies this for rendering
     *
     */
    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        this.isPossibleToLeave = true;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWORLD_WIDTH(), game.getWORLD_HEIGHT());
        game.getCollectedStuffList().clear();
        game.getAllObstaclesCollection().clear();
        this.powerUps = game.getPowerUps().getTwoRandomPowers();
        firstPowerUp = game.getText(powerUps[0].getName());
        secondPowerUp = game.getText(powerUps[1].getName());
        firstPowerUpInfo = powerUps[0].getDescription();
        secondPowerUpInfo = powerUps[1].getDescription();
        this.upperButton = new Button(2f,2.3f,0.8f,0.8f, powerUps[0].getButtonTexture());
        this.lowerButton = new Button(5f,2.3f,0.8f,0.8f, powerUps[1].getButtonTexture());

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

    /**
     * Disconnect inputprocessor when exiting screen
     */
    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
}
