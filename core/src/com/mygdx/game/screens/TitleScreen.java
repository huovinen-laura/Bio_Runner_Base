package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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

public class TitleScreen extends ScreenAdapter {
    BioRunnerGame game;
    Button startButton;
    Button settingsButton;
    Button skinShopButton;
    SpriteBatch titleBatch;
    OrthographicCamera camera = new OrthographicCamera();
    OrthographicCamera fontCamera = new OrthographicCamera();
    BitmapFont font;
    float width;
    float height;
    private Vector3 projected;
    Texture tausta;

    Locale locale;
    I18NBundle myBundle;
    String play, settings, shop;

    public TitleScreen(BioRunnerGame game) {
        camera.setToOrtho(false, BallGame.WORLD_WIDTH, BallGame.WORLD_HEIGHT);
        projected = camera.project(new Vector3(game.WORLD_WIDTH,game.WORLD_HEIGHT,0f));
        this.game = game;
        this.font = game.getFont();
        tausta = game.textureAssets.getMenu();
        width = game.WORLD_WIDTH;
        height = game.WORLD_HEIGHT;

        // Kieli. Default hakee järjestelmän kielen, new Localessa kielen voi päättää itse.
        locale = Locale.getDefault();
        //locale = new Locale("en", "UK");
        myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
        play = myBundle.get("play");
        settings = myBundle.get("settings");
        shop = myBundle.get("shop");
    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();
        this.titleBatch = new SpriteBatch();
        this.titleBatch.setProjectionMatrix(game.getTextureCamera().combined);

        this.startButton = new Button(2f,1.8f,1f,1f, this.game.textureAssets.getButtonBlue());
        this.settingsButton = new Button(2f, 1f, 1f, 1f, this.game.textureAssets.getButtonBlue());
        this.skinShopButton = new Button(2f, 0.2f, 1f, 1f, this.game.textureAssets.getButtonBlue());


        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.SPACE) {
                    game.setGameScreen();
                }

                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));

                if (startButton.isInsideButton(worldCoords.x,worldCoords.y)) {
                    game.setGameScreen();
                } else if (settingsButton.isInsideButton(worldCoords.x, worldCoords.y)) {
                    game.setSettingsScreen();
                } else if (skinShopButton.isInsideButton(worldCoords.x, worldCoords.y)) {
                    game.setSkinShopScreen();
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100/255f, 197/255f, 165/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(game.getPrefs().getBoolean("musicOn")) {
            game.getMusic().play();
        }

        // Draws background
        this.titleBatch.setProjectionMatrix(camera.combined);
        this.titleBatch.begin();
        this.titleBatch.draw(tausta, 0, 0, width, height);
        this.titleBatch.end();

        // Draws fonts
        game.batch.begin();
        font.draw(game.batch, play, projected.x * 0.40f,
                projected.y * .60f);
        font.draw(game.batch, settings, projected.x * 0.40f, projected.y * 0.40f);
        font.draw(game.batch, shop, projected.x * 0.40f, projected.y * 0.20f);
        game.batch.end();

        // Draws textures
        this.titleBatch.setProjectionMatrix(camera.combined);
        this.titleBatch.begin();
        this.startButton.draw(this.titleBatch);
        this.settingsButton.draw(this.titleBatch);
        this.skinShopButton.draw(this.titleBatch);
        this.titleBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.fontCamera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
