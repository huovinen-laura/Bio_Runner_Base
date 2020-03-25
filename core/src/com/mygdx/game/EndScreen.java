package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndScreen extends ScreenAdapter {
    BioRunnerGame game;
    BitmapFont font;

    public EndScreen(BioRunnerGame game) {
        this.game = game;
        this.font = game.font;
    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setTitleScreen();
                    BallGame.clearScore();
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                game.setTitleScreen();
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100/255f, 197/255f, 165/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        String score = Integer.toString(BallGame.getPlayerScore());

        game.batch.begin();
        font.draw(game.batch, "You lost!", Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .75f);
        font.draw(game.batch, "Your score was: " + score, Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .50f);
        font.draw(game.batch, "Press space or tap to continue", Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
