package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.Button;

import java.util.ArrayList;
import java.util.List;

public class HighScoreScreen extends ScreenAdapter implements HighScoreListener {
    private final Button backButton;
    private final Texture tausta;
    List<HighScoreEntry> highScores;
    BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private OrthographicCamera camera;
    private BitmapFont font;
    private BitmapFont smallFont;
    private String scoresFirstColumn;
    private String scoresSecondColumn;

    public HighScoreScreen(BioRunnerGame game) {
        super();

        this.game = game;
        HighScoreServer.readConfig("highscore.config");
        HighScoreServer.fetchHighScores(this);
        this.backButton = new Button(6.5f,3f,1f,1f, game.textureAssets.getCloseButton());
        tausta = game.textureAssets.getCommon();
        font = game.getFont();
        scoresFirstColumn = game.getText("loading");
        scoresSecondColumn = scoresFirstColumn;
        

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.texturesBatch.draw(tausta, 0, 0, game.WORLD_WIDTH, game.WORLD_HEIGHT);
        this.backButton.draw(this.texturesBatch);
        this.texturesBatch.end();

        game.batch.begin();
        this.font.draw(game.batch, game.getText("highScores"), game.getProjected().x * 0.2f,
                game.getProjected().y * 0.90f);
        this.smallFont.draw(game.batch,this.scoresFirstColumn,
                game.getProjected().x*0.17f,game.getProjected().y*0.91f);
        this.smallFont.draw(game.batch,this.scoresSecondColumn,
                game.getProjected().x*0.6f,game.getProjected().y*0.91f);
        game.batch.end();

    }

    @Override
    public void show() {
        super.show();
        this.smallFont = game.getBubbleFont();
        this.smallFont.getData().setScale(0.3f);
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BallGame.WORLD_WIDTH,BallGame.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));

                if( backButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    game.setTitleScreen();
                }

                return true;
            }
        });
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void receiveHighScore(List<HighScoreEntry> highScores) {
        game.setHighScores(highScores);
        this.scoresFirstColumn = "";
        this.scoresSecondColumn ="";
        for(int i = 0; i < highScores.size(); i++) {
            if(i < 5) {
                this.scoresFirstColumn = this.scoresFirstColumn + "\n\n" + (i + 1) + ". " +
                        highScores.get(i).getName() + "    " + highScores.get(i).getScore();
            } else {
                this.scoresSecondColumn = this.scoresSecondColumn + "\n\n" + (i + 1) + ". " +
                        highScores.get(i).getName() + "    " + highScores.get(i).getScore();
            }
        }



        this.highScores = highScores;
    }

    @Override
    public void failedToRetrieveHighScores(Throwable t) {
        Gdx.app.log("server","failed to load scores");
    }

    @Override
    public void receiveSendReply(Net.HttpResponse httpResponse) {
        Gdx.app.log("server","" + httpResponse);
    }

    @Override
    public void failedToSendHighScore(Throwable t) {
        Gdx.app.log("server","failed to send");
    }

    public void postNewHighScore(HighScoreEntry highScoreEntry) {
        HighScoreServer.sendNewHighScore(highScoreEntry,this);
    }
}
