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

/**
 * Screen for showing credits
 *
 */
public class CreditsScreen extends ScreenAdapter {

    public BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private Button backButton;
    private OrthographicCamera camera;
    private Texture tausta;
    private float width, height;

    /**
     * Creates credits with default configuration
     *
     * @param game BioRunnerGame object to put this on
     */
    public CreditsScreen(BioRunnerGame game) {
        this.game = game;
        this.backButton = new Button(6.5f,3f,1f,1f, game.getTextureAssets().getCloseButton());
        this.font = game.getFont();
        tausta = game.getTextureAssets().getCommon();
        width =game.getWORLD_WIDTH();
        height = game.getWORLD_HEIGHT();


    }

    /**
     * Renders the credits screen
     *
     * @param delta delta time
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
        this.font.draw(game.getBatch(), game.getText("credits"), Gdx.graphics.getWidth() * 0.4f,
                Gdx.graphics.getHeight() * 0.85f);
        this.font.draw(game.getBatch(), game.getText("jere"), Gdx.graphics.getWidth() * 0.15f,
                Gdx.graphics.getHeight() * 0.75f);
        this.font.draw(game.getBatch(), game.getText("aaro"), Gdx.graphics.getWidth() * 0.15f,
                Gdx.graphics.getHeight() * 0.65f);
        this.font.draw(game.getBatch(), game.getText("laura"), Gdx.graphics.getWidth() * 0.15f,
                Gdx.graphics.getHeight() * 0.55f);
        this.font.draw(game.getBatch(), game.getText("milla"), Gdx.graphics.getWidth() * 0.15f,
                Gdx.graphics.getHeight() * 0.45f);
        this.font.draw(game.getBatch(), game.getText("zapsplat"), Gdx.graphics.getWidth() * 0.15f,
                Gdx.graphics.getHeight() * 0.35f);
        game.getBatch().end();

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.backButton.draw(texturesBatch);
        this.texturesBatch.end();
    }

    /**
     * Sets this ready to be rendered
     *
     */
    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        this.isPossibleToLeave = true;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width,height);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));

                if( backButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    game.setSettingsScreen();
                }

                return true;
            }
        });
    }

    @Override
    public void hide() {
        super.hide();
    }
}
