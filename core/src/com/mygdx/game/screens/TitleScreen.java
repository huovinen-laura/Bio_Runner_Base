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
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.Button;

public class TitleScreen extends ScreenAdapter {
    BioRunnerGame game;
    private Button startButton;
    private Button settingsButton;
    private Button skinShopButton;
    private SpriteBatch titleBatch;
    private OrthographicCamera camera = new OrthographicCamera();
    private OrthographicCamera fontCamera = new OrthographicCamera();
    private BitmapFont font;
    private float width;
    private float height;
    private Vector3 projected;
    private Texture tausta;

    public TitleScreen(BioRunnerGame game) {

        this.game = game;
        this.font = game.getFont();
        tausta = game.textureAssets.getMenu();
        width = game.WORLD_WIDTH;
        height = game.WORLD_HEIGHT;
    }

    @Override
    public void show() {
        camera.setToOrtho(false, game.WORLD_WIDTH, game.WORLD_HEIGHT);
        projected = camera.project(new Vector3(game.WORLD_WIDTH,game.WORLD_HEIGHT,0f));
        Gdx.app.log("TitleProject",""+projected);
        Gdx.app.log("GameProject","" + game.getProjected());
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
        //String totalScore = Integer.toString(game.getPrefs().getInteger("totalScore"));
        game.batch.begin();
        //font.draw(game.batch, totalScore, projected.x * 0.7f, projected.y * .6f);
        font.draw(game.batch, game.getText("play"), projected.x * 0.40f,
                projected.y * .60f);
        font.draw(game.batch, game.getText("settings"), projected.x * 0.40f, projected.y * 0.40f);
        font.draw(game.batch, game.getText("shop"), projected.x * 0.40f, projected.y * 0.20f);
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
