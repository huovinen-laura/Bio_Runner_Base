package com.mygdx.bioRunnerGame.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bioRunnerGame.BioRunnerGame;
import com.mygdx.bioRunnerGame.Button;
import com.mygdx.bioRunnerGame.HighScoreServer;

import java.util.List;

/**
 * Displays high score data
 */
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
        HighScoreServer.readConfig("highscore.config");
        HighScoreServer.fetchHighScores(this);
        this.game = game;
        this.backButton = new Button(6.5f,3f,1f,1f, game.getTextureAssets().getCloseButton());
        tausta = game.getTextureAssets().getCommon();
        font = game.getFont();
        scoresFirstColumn = "\n\n" + game.getText("loading");
        scoresSecondColumn = scoresFirstColumn;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.texturesBatch.draw(tausta, 0, 0, game.getWORLD_WIDTH(), game.getWORLD_HEIGHT());
        this.backButton.draw(this.texturesBatch);
        this.texturesBatch.end();

        game.getBatch().begin();
        this.font.draw(game.getBatch(), game.getText("highScores"), game.getProjected().x * 0.2f,
                game.getProjected().y * 0.90f);
        this.smallFont.draw(game.getBatch(),this.scoresFirstColumn,
                game.getProjected().x*0.17f,game.getProjected().y*0.91f);
        this.smallFont.draw(game.getBatch(),this.scoresSecondColumn,
                game.getProjected().x*0.6f,game.getProjected().y*0.91f);
        game.getBatch().end();

    }

    /**
     * Readies the screen for rendering and gets all necessary
     *
     *
     * */
    @Override
    public void show() {
        super.show();
        HighScoreServer.fetchHighScores(this);
        this.smallFont = game.getBubbleFont();
        this.smallFont.getData().setScale(0.3f * Gdx.graphics.getWidth()/800,0.3f
                * Gdx.graphics.getHeight()/400);
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWORLD_WIDTH(),game.getWORLD_HEIGHT());

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

    /**
     * Sets font back to normal
     */
    @Override
    public void hide() {
        super.hide();
        game.getBubbleFont().getData().setScale(1f* Gdx.graphics.getWidth()/800);
    }


    /**
     * Reads the high scores from server and converts them for rendering
     *
     * @param highScores
     */
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

    /**
     * Posts new highscore to the server
     *
     * @param highScoreEntry
     */
    public void postNewHighScore(HighScoreEntry highScoreEntry) {
        HighScoreServer.sendNewHighScore(highScoreEntry,this);
    }
}
