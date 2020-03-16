package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class RecycleScreen extends ScreenAdapter {
    BioRunnerGame game;

    public RecycleScreen(BioRunnerGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "This is recycle screen", Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void show() {
        super.show();
        Gdx.app.log("RecycleScreen","show");

    }

    @Override
    public void hide() {
        super.hide();
    }
}
