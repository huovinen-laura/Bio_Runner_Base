package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
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
    private AssetManager textures;

    public SplashScreen(BioRunnerGame game) {
        super();
        textures = new AssetManager();
        textures.load("tiko_valk.png", Texture.class);
        textures.load("tuni.png", Texture.class);
        textures.load("oras.png", Texture.class);
        textures.finishLoading();
        this.game = game;
        this.timeLeft = 2f;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(this.game.getTextureAssets().update()) {
            Gdx.app.log("splash","" + game.getTextureAssets().getLoadedAssets());
            game.afterLoadConstructor();
            game.setTitleScreen();
            game.getLifeCounter().setTexture(game.getTextureAssets().getLives());
            game.setCollectedStuffList(new ShitCollection(game));
            game.setAllObstaclesCollection(new ObstacleCollection(game));
        }
        this.batch.begin();

        this.batch.draw(textures.get("tiko_valk.png",Texture.class),3f,1f,2f,
                2f*(textures.get("tiko_valk.png",Texture.class).getHeight())/
                        (textures.get("tiko_valk.png",Texture.class)).getWidth());

        this.batch.draw(textures.get("tuni.png",Texture.class),2f,0f,2f,
                2f*(textures.get("tuni.png",Texture.class).getHeight())/
                        (textures.get("tuni.png",Texture.class)).getWidth());

        this.batch.draw(textures.get("oras.png",Texture.class),4f,2f,2f,
                2f*(textures.get("oras.png",Texture.class).getHeight())/
                        (textures.get("oras.png",Texture.class)).getWidth());

        this.batch.end();

    }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWORLD_WIDTH(), game.getWORLD_HEIGHT());
        batch = new SpriteBatch();
        this.batch.setProjectionMatrix(camera.combined);


    }

    @Override
    public void dispose() {
        super.dispose();
        this.textures.dispose();
    }
}
