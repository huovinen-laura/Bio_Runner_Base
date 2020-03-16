package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BioRunnerGame extends Game {
    SpriteBatch batch;
    BitmapFont font;
    TitleScreen title;
    BallGame game;
    EndScreen end;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        game = new BallGame(this);
        end = new EndScreen(this);
        this.setScreen(new TitleScreen(this));
    }

    public void setEndScreen() {
        setScreen(new EndScreen(this));
    }

    public void setGameScreen() {
        setScreen(this.game);
    }

    public void setTitleScreen() {
        setScreen(new TitleScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
