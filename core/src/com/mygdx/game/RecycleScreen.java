package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RecycleScreen extends ScreenAdapter {
    BioRunnerGame game;
    WasteDisplayRecycle wasteTextures;
    SpriteBatch texturesBatch;

    public RecycleScreen(BioRunnerGame game) {
        this.game = game;


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(100/255f, 197/255f, 165/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Recycle screen, press space to continue", Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .25f);
        game.batch.end();

        this.texturesBatch.begin();
        this.wasteTextures.draw(this.texturesBatch);
        this.texturesBatch.end();

    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false,8,4);
        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.wasteTextures = new WasteDisplayRecycle();
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
                game.setGameScreen();
                BallGame.worldSpeed -= 0.1f;
                return true;
            }
        });

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
