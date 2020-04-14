package com.mygdx.game.screens;

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
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.Button;

import java.util.Locale;

public class SettingsScreen extends ScreenAdapter {
    public BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private Button backButton;
    private Button musicButton;
    private Button soundButton;
    private Button finnishButton;
    private Button englishButton;
    private OrthographicCamera camera;
    private Texture tausta;
    private float width, height;


    String settings, music, sounds;

    public SettingsScreen(BioRunnerGame game) {
        this.game = game;
        this.backButton = new Button(6.5f,3f,1f,1f,game.textureAssets.getCloseButton());
        this.musicButton = new Button(1f, 2f, 1f, 1f, game.textureAssets.getButtonBlue());
        this.soundButton = new Button(1f, 1f, 1f, 1f, game.textureAssets.getButtonBlue());
        this.finnishButton = new Button(5f, 2f, 0.7f, 1f, game.textureAssets.getFinnishButton());
        this.englishButton = new Button(5f, 1f, 0.7f, 1f, game.textureAssets.getEnglishButton());
        this.font = game.getFont();
        tausta = game.textureAssets.getCommon();
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getPrefs().getBoolean("fiOrNot")) {
            music = game.getMyBundleFI().get("music");
            settings = game.getMyBundleFI().get("settings");
            sounds = game.getMyBundleFI().get("sounds");
        } else {
            music = game.getMyBundleEN().get("music");
            settings = game.getMyBundleEN().get("settings");
            sounds = game.getMyBundleEN().get("sounds");
        }

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.texturesBatch.draw(tausta, 0, 0, width, height);
        this.texturesBatch.end();

        game.batch.begin();
        this.font.draw(game.batch, settings, Gdx.graphics.getWidth() * 0.4f,
                Gdx.graphics.getHeight() * 0.80f);
        this.font.draw(game.batch, music, Gdx.graphics.getWidth() * 0.300f,
                Gdx.graphics.getHeight() * 0.65f);
        this.font.draw(game.batch, sounds, Gdx.graphics.getWidth() * 0.30f,
                Gdx.graphics.getHeight() * 0.4f);
        game.batch.end();

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.backButton.draw(texturesBatch);
        this.musicButton.draw(texturesBatch);
        this.soundButton.draw(texturesBatch);
        this.finnishButton.draw(texturesBatch);
        this.englishButton.draw(texturesBatch);
        this.texturesBatch.end();
    }

    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        this.isPossibleToLeave = true;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BallGame.WORLD_WIDTH,BallGame.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));
                Preferences prefs = game.getPrefs();
                if( backButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    game.setTitleScreen();
                } else if (musicButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && prefs.getBoolean("musicOn")) {
                    prefs.putBoolean("musicOn", false);
                    prefs.flush();
                    game.getMusic().stop();
                    Gdx.app.log("", "Music off");
                } else if (musicButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && !prefs.getBoolean("musicOn")){
                    prefs.putBoolean("musicOn", true);
                    prefs.flush();
                    game.getMusic().play();
                    Gdx.app.log("", "Music on");
                } else if (soundButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && prefs.getBoolean("soundOn")){
                    prefs.putBoolean("soundOn", false);
                    prefs.flush();
                    Gdx.app.log("", "Sound off");
                } else if (soundButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && !prefs.getBoolean("soundOn")) {
                    prefs.putBoolean("soundOn", true);
                    prefs.flush();
                    Gdx.app.log("","Sound on");
                } else if (englishButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && prefs.getBoolean("fiOrNot")) {
                    prefs.putBoolean("fiOrNot", false);
                    prefs.flush();
                    Gdx.app.log("", "English");
                } else if (finnishButton.isInsideButton(worldCoords.x, worldCoords.y)
                        && !prefs.getBoolean("fiOrNot")) {
                    prefs.putBoolean("fiOrNot", true);
                    prefs.flush();
                    Gdx.app.log("", "Finnish");
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
