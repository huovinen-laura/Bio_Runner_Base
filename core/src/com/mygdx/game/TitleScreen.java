package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TitleScreen extends ScreenAdapter {
    BioRunnerGame game;

    public TitleScreen(BioRunnerGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.font = new BitmapFont();
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
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Bio Runner", Gdx.graphics.getWidth() * .25f,
                Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Press space to play!", Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
