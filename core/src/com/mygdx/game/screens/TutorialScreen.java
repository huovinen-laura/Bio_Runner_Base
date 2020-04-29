package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.BioRunnerGame;

/**
 * Displays the tutorial
 */
public class TutorialScreen extends ScreenAdapter {
    public BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private OrthographicCamera camera;
    private Texture tutorial1, tutorial2, tutorial3, tutorial4;
    private float width, height;
    private boolean isAllowedToLeave = true;
    private boolean first, second, third, fourth;

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
        } else if(fourth) {
            this.texturesBatch.draw(tutorial4, 0, 0, width, height);
        }
        this.texturesBatch.end();

        game.getBatch().begin();
        if (first) {
            this.font.draw(game.getBatch(), game.getText("tutorial1"), game.getProjected().x * 0.05f,
                    game.getProjected().y * 0.85f);
        } else if(second) {
            this.font.draw(game.getBatch(), game.getText("tutorial2"), game.getProjected().x * 0.05f,
                    game.getProjected().y * 0.25f);
        } else if(third) {
            this.font.draw(game.getBatch(), game.getText("tutorial3"), game.getProjected().x * 0.05f,
                    game.getProjected().y * 0.85f);
        } else if(fourth) {
            this.font.draw(game.getBatch(), game.getText("tutorial4a"), game.getProjected().x * 0.05f,
                    game.getProjected().y * 0.73f);
            this.font.draw(game.getBatch(), game.getText("tutorial4b"), game.getProjected().x * 0.05f,
                    game.getProjected().y * 0.42f);
            this.font.draw(game.getBatch(), game.getText("tapb"), game.getProjected().x * 0.05f,
                    game.getProjected().y * 0.2f);
        }

        game.getBatch().end();

    }

    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        width = game.getWORLD_WIDTH();
        height = game.getWORLD_HEIGHT();
        tutorial1= game.getTextureAssets().getTutorial1();
        tutorial2 = game.getTextureAssets().getTutorial2();
        tutorial3 = game.getTextureAssets().getTutorial3();
        tutorial4 = game.getTextureAssets().getTutorial4();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWORLD_WIDTH(), game.getWORLD_HEIGHT());

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
                    fourth = true;
                    third = false;
                } else if (fourth) {
                    game.setTutorialOk();
                    game.setGameScreen();
                    fourth = false;
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
