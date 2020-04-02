package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
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
import com.mygdx.game.gamestate.LifeCounter;

public class ShopScreen extends ScreenAdapter {
    public BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private Button upperButton;
    private Button lowerButton;
    private OrthographicCamera camera;

    public ShopScreen(BioRunnerGame game) {
        this.game = game;
        this.lowerButton = new Button(1f,1f,1f,1f, game.textureAssets.getButtonBlue());
        this.font = game.getFont();
        this.upperButton = new Button(1f,3f,1f,1f, game.textureAssets.getButtonBlue());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        this.font.draw(game.batch, "Double points but only one life", Gdx.graphics.getWidth() * 0.20f,
                Gdx.graphics.getHeight() * 0.2f);
        this.font.draw(game.batch, "Extra life",
                Gdx.graphics.getWidth() * 0.2f,Gdx.graphics.getHeight() * 0.75f);
        game.batch.end();

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.upperButton.draw(texturesBatch);
        this.lowerButton.draw(texturesBatch);
        this.texturesBatch.end();
    }

    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        this.isPossibleToLeave = true;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WORLD_WIDTH,game.WORLD_HEIGHT);
        game.collectedStuffList.clear();
        game.allObstaclesCollection.clear();

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));

                if( upperButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    if(game.lifeCounter.getLivesAmount() < 3) {
                        game.lifeCounter.gainLife();
                        game.setGameScreen();
                    } else if(game.lifeCounter.getLivesAmount() == 3) {
                        game.setGameScreen();
                    }

                } else if( lowerButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    game.lifeCounter.setLives(1);
                    game.setGameScreen();
                }

                return true;
            }
        });

    }

    @Override
    public void hide() {
        super.hide();
    }
}
