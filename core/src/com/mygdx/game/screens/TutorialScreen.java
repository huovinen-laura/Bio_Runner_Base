package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.BioRunnerGame;

public class TutorialScreen extends ScreenAdapter {
    public BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private OrthographicCamera camera;
    private Texture tutorial1, tutorial2, tutorial3;
    private float width, height;
    private boolean isAllowedToLeave = true;
    private boolean first, second, third;

    public TutorialScreen(BioRunnerGame game) {
        this.game = game;
        this.font = game.getFont();

        first = true;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        if (first) {
            this.texturesBatch.draw(tutorial1, 0, 0, width, height);
        } else if(second) {
            this.texturesBatch.draw(tutorial2, 0, 0, width, height);
        } else if(third) {
            this.texturesBatch.draw(tutorial3, 0, 0, width, height);
        }
        this.texturesBatch.end();

        game.batch.begin();
        if (first) {
            this.font.draw(game.batch, game.getText("tutorial1"), game.getProjected().x * 0.05f,
                    game.getProjected().y * 0.85f);
        } else if(second) {
            this.font.draw(game.batch, game.getText("tutorial2"), game.getProjected().x * 0.05f,
                    game.getProjected().y * 0.25f);
        } else if(third) {
            this.font.draw(game.batch, game.getText("tutorial3"), game.getProjected().x * 0.05f,
                    game.getProjected().y * 0.85f);
        }

        game.batch.end();

    }

    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;
        tutorial1= game.textureAssets.getTutorial1();
        tutorial2 = game.textureAssets.getTutorial2();
        tutorial3 = game.textureAssets.getTutorial3();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.WORLD_WIDTH,game.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(first) {
                    second = true;
                    first = false;
                } else if (second) {
                    third = true;
                    second = false;
                } else if (third) {
                    game.setGameScreen();
                    third = false;
                    first = true;
                }
                return true;
            }
        });

    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
}
