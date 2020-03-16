package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RecycleScreen extends ScreenAdapter {
    BioRunnerGame game;

    public RecycleScreen(BioRunnerGame game) {
        this.game = game;


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Recycle screen, press space to continue", Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();
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
