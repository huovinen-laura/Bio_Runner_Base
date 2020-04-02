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
    Button startButton;
    SpriteBatch titleBatch;
    OrthographicCamera camera = new OrthographicCamera();
    OrthographicCamera fontCamera = new OrthographicCamera();
    BitmapFont font;
    float width;
    float height;
    private Vector3 projected;
    private Texture img;

    public TitleScreen(BioRunnerGame game) {
        camera.setToOrtho(false, BallGame.WORLD_WIDTH, BallGame.WORLD_HEIGHT);
        fontCamera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.game = game;
        this.font = game.getFont();
        img = game.textureAssets.getSky();
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;
    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();
        this.titleBatch = new SpriteBatch();
        this.titleBatch.setProjectionMatrix(fontCamera.combined);
        this.startButton = new Button(1f,1f,1f,1f,this.game.textureAssets.getButtonBlue());
        projected = camera.project(new Vector3(BallGame.WORLD_WIDTH,BallGame.WORLD_HEIGHT,0f));

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
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100/255f, 197/255f, 165/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws background
        this.titleBatch.setProjectionMatrix(camera.combined);
        this.titleBatch.begin();
        this.titleBatch.draw(img, 0, 0, width, height);
        this.titleBatch.end();

        // Draws fonts
        game.batch.begin();
        font.draw(game.batch, "Bio Runner", projected.x * .25f,
                projected.y * .75f);
        font.draw(game.batch, "Press space or the button to play!", projected.x * 0.25f,
                projected.y * .25f);
        game.batch.end();

        // Draws textures
        this.titleBatch.setProjectionMatrix(camera.combined);
        this.titleBatch.begin();
        this.startButton.draw(this.titleBatch);
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
