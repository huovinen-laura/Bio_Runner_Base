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
    private Button buyButton;
    private Texture lifeTexture;
    private Button leaveButton;
    private OrthographicCamera camera;

    public ShopScreen(BioRunnerGame game) {
        this.game = game;
        this.leaveButton = new Button(1f,1f,1f,1f);
        this.lifeTexture = new Texture("badlogic.jpg");
        this.font = game.getFont();
        this.buyButton = new Button(1f,3f,1f,1f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        this.font.draw(game.batch, "Continue", Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * .2f);
        this.font.draw(game.batch, "A life 50pts",
                Gdx.graphics.getWidth() * 0.2f,Gdx.graphics.getHeight() * 0.75f);
        game.batch.end();

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.buyButton.draw(texturesBatch);
        this.leaveButton.draw(texturesBatch);
        this.texturesBatch.end();
    }

    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        this.isPossibleToLeave = true;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BallGame.WORLD_WIDTH,BallGame.WORLD_HEIGHT);
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));

                if(buyButton.isInsideButton(worldCoords.x,worldCoords.y)) {
                    LifeCounter.gainLife();
                } else if(leaveButton.isInsideButton(worldCoords.x,worldCoords.y)) {
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
