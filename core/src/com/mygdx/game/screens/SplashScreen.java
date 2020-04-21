package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.ObstacleCollection;
import com.mygdx.game.ShitCollection;

public class SplashScreen extends ScreenAdapter {
    private Texture tiko = new Texture("tiko_valk.png");
    private Texture tuni = new Texture("tuni.png");
    private Texture oras = new Texture("oras.png");
    private OrthographicCamera camera;
    private BioRunnerGame game;
    private SpriteBatch batch;
    private float timeLeft;

    public SplashScreen(BioRunnerGame game) {
        super();
        this.game = game;
        this.timeLeft = 2f;


    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(this.game.textureAssets.update()) {
            Gdx.app.log("splash","" + game.textureAssets.getLoadedAssets());
            game.afterLoadConstructor();
            game.setTitleScreen();
            game.lifeCounter.setTexture(game.textureAssets.getLives());
            game.collectedStuffList = new ShitCollection(game);
            game.allObstaclesCollection = new ObstacleCollection(game);
        }

        this.batch.begin();
        this.batch.draw(tiko,2f,1f,2f,2f*tiko.getHeight()/tiko.getWidth());
        this.batch.draw(oras,3f,2f,2f,2f*oras.getHeight()/oras.getWidth());
        this.batch.draw(tuni,4f,3f,2f,2f*tuni.getHeight()/tuni.getWidth());

        this.batch.end();

    }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WORLD_WIDTH,game.WORLD_HEIGHT);
        batch = new SpriteBatch();
        this.batch.setProjectionMatrix(camera.combined);


    }

    @Override
    public void dispose() {
        super.dispose();
        tiko.dispose();
        tuni.dispose();
        oras.dispose();
    }
}
