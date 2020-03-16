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
    RecycleScreen recycle;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        title = new TitleScreen(this);
        game = new BallGame(this);
        recycle = new RecycleScreen(this);
        end = new EndScreen(this);
        setScreen(this.title);
    }

    public void setEndScreen() {
        setScreen(this.end);
    }

    public void setGameScreen() {
        setScreen(this.game);
    }

    public void setTitleScreen() {
        setScreen(this.title);
    }
    public void setRecycleScreen() {setScreen(this.recycle);}

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
