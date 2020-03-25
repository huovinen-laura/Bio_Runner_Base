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
    BitmapFont font;
    float width;
    float height;
    Texture img;

    public TitleScreen(BioRunnerGame game) {
        camera.setToOrtho(false, BallGame.WORLD_WIDTH, BallGame.WORLD_HEIGHT);
        this.game = game;
        this.font = game.getFont();
        img = new Texture("tausta.png");
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;
    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();
        this.titleBatch = new SpriteBatch();
        this.startButton = new Button(1f,1f,1f,1f);

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
        font.draw(game.batch, "Bio Runner", Gdx.graphics.getWidth() * .25f,
                Gdx.graphics.getHeight() * .75f);
        font.draw(game.batch, "Press space or the button to play!", Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .25f);
        game.batch.end();

        // Draws textures
        this.titleBatch.setProjectionMatrix(camera.combined);
        this.titleBatch.begin();
        this.startButton.draw(this.titleBatch);
        this.titleBatch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
