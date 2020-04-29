package com.mygdx.bioRunnerGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
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
 * Displays options
 */
public class SettingsScreen extends ScreenAdapter {
    public BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private Button backButton;
    private Button musicOnButton, musicOffButton;
    private Button soundOnButton, soundOffButton;
    private Button creditsButton;
    private Button finnishButton;
    private Button englishButton;
    private Button tutorialButton;
    private OrthographicCamera camera;
    private Texture tausta;
    private float width, height;


    String settings, music, sounds;

    public SettingsScreen(BioRunnerGame game) {
        this.game = game;
        this.backButton = new Button(6.5f,3f,1f,1f, game.getTextureAssets().getCloseButton());
        this.musicOnButton = new Button(1.4f, 2.35f, 0.6f, 0.6f, game.getTextureAssets().getSpeakerOn());
        this.musicOffButton = new Button(1.4f, 2.35f, 0.6f, 0.6f, game.getTextureAssets().getSpeakerOff());
        this.soundOnButton = new Button(1.4f, 1.65f, 0.6f, 0.6f, game.getTextureAssets().getSpeakerOn());
        this.soundOffButton = new Button(1.4f, 1.65f, 0.6f, 0.6f, game.getTextureAssets().getSpeakerOff());
        this.creditsButton = new Button(1.4f, 0.95f, 0.6f, 0.6f, game.getTextureAssets().getInfoButton());
        this.finnishButton = new Button(5f, 2.2f, 0.7f, 1f, game.getTextureAssets().getFinnishButton());
        this.englishButton = new Button(5f, 1.2f, 0.7f, 1f, game.getTextureAssets().getEnglishButton());
        this.tutorialButton = new Button(3f, 0.2f, 0.5f, 1.8f, game.getTextureAssets().getGeneralButton());
        this.font = game.getFont();
        tausta = game.getTextureAssets().getCommon();
        width = game.getWORLD_WIDTH();
        height = game.getWORLD_HEIGHT();


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

        game.getBatch().begin();
        this.font.draw(game.getBatch(), game.getText("settings"), game.getProjected().x * 0.4f,
                game.getProjected().y * 0.85f);
        this.font.draw(game.getBatch(), game.getText("music"), game.getProjected().x * 0.300f,
                game.getProjected().y * 0.70f);
        this.font.draw(game.getBatch(), game.getText("sounds"), game.getProjected().x * 0.30f,
                game.getProjected().y * 0.53f);
        this.font.draw(game.getBatch(), game.getText("credits"), game.getProjected().x * 0.3f,
                game.getProjected().y * 0.36f);
        game.getBatch().end();

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();

        if(game.getPrefs().getBoolean("musicOn")) {
            this.musicOnButton.draw(texturesBatch);
        } else {
            this.musicOffButton.draw(texturesBatch);
        }

        if(game.getPrefs().getBoolean("soundOn")) {
            this.soundOnButton.draw(texturesBatch);
        } else {
            this.soundOffButton.draw(texturesBatch);
        }

        this.backButton.draw(texturesBatch);
        this.creditsButton.draw(texturesBatch);
        this.finnishButton.draw(texturesBatch);
        this.englishButton.draw(texturesBatch);
        this.tutorialButton.draw(texturesBatch);
        this.texturesBatch.end();

        game.getBatch().begin();
        this.font.draw(game.getBatch(), game.getText("tutorial"), game.getProjected().x * 0.4f,
                game.getProjected().y * 0.15f);
        game.getBatch().end();
    }

    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        this.isPossibleToLeave = true;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWORLD_WIDTH(),game.getWORLD_HEIGHT());

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));
                Preferences prefs = game.getPrefs();
                if( backButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    game.setTitleScreen();
                } else if (creditsButton.isInsideButton(worldCoords.x, worldCoords.y)) {
                    game.setCreditsScreen();
                } else if (musicOnButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && prefs.getBoolean("musicOn")) {
                    prefs.putBoolean("musicOn", false);
                    prefs.flush();
                    game.getMusic().stop();

                } else if (musicOnButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && !prefs.getBoolean("musicOn")){
                    prefs.putBoolean("musicOn", true);
                    prefs.flush();
                    game.getMusic().play();
                    Gdx.app.log("", "Music on");
                } else if (soundOnButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && prefs.getBoolean("soundOn")){
                    prefs.putBoolean("soundOn", false);
                    prefs.flush();

                } else if (soundOnButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && !prefs.getBoolean("soundOn")) {
                    prefs.putBoolean("soundOn", true);
                    prefs.flush();

                } else if (englishButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && prefs.getBoolean("fiOrNot")) {
                    prefs.putBoolean("fiOrNot", false);
                    prefs.flush();

                } else if (finnishButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && !prefs.getBoolean("fiOrNot")) {
                    prefs.putBoolean("fiOrNot", true);
                    prefs.flush();

                } else if (tutorialButton.isInsideButton(worldCoords.x, worldCoords.y)) {
                    game.setTutorialScreen();
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
