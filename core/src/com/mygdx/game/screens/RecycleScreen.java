package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.Button;
import com.mygdx.game.TextBubble;
import com.mygdx.game.WasteDisplayRecycle;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

public class RecycleScreen extends ScreenAdapter {
    BioRunnerGame game;
    private Texture tausta;
    private WasteDisplayRecycle wasteTextures;
    private SpriteBatch texturesBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private Button leaveButton;
    private OrthographicCamera camera;
    private WasteDisplayRecycle obstacleTextures;
    private boolean praise;
    private Texture happyGuy ;
    private Texture sadGuy;
    private boolean sad;
    private float width, height;
    private TextBubble information;
    private Vector3 projected;

    public RecycleScreen(BioRunnerGame game) {
        this.game = game;
        this.font = game.getFont();
        this.happyGuy = game.textureAssets.getHappyGirl();
        this.sadGuy = game.textureAssets.getSadGirl();
        tausta = game.textureAssets.getRecycle();
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;


        camera = new OrthographicCamera();
        camera.setToOrtho(false, this.game.WORLD_WIDTH, this.game.WORLD_HEIGHT);
        projected = camera.project(new Vector3(game.WORLD_WIDTH,game.WORLD_HEIGHT,0f));

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(100/255f, 197/255f, 165/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        this.texturesBatch.begin();
        this.texturesBatch.draw(tausta, 0, 0, width, height);
        this.texturesBatch.draw(laitos, 4.2f, -0.25f, width * 0.7f, height * 0.7f);
        this.information.DrawBubble(this.texturesBatch)
        /*
        if(sad) {
            this.texturesBatch.draw(this.sadGuy,2.75f,-0.75f,2.5f,2.5f);
        } else {
            this.texturesBatch.draw(this.happyGuy, 2.75f, -0.75f, 2.5f, 2.5f);
        }
         */

        if(this.wasteTextures.draw(this.texturesBatch)) {



            if(this.obstacleTextures.draw(this.texturesBatch)) {
                this.leaveButton.draw(texturesBatch);



                this.isPossibleToLeave = true;
            }
        }
        this.texturesBatch.end();

        game.batch.begin();
        //this.font.draw(game.batch, "You collected all this, good job!", Gdx.graphics.getWidth() * 0.15f,
        //Gdx.graphics.getHeight() * .25f);
        this.information.DrawFont(game.batch,projected);


        game.batch.end();

    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();
        this.praise = true;
        this.texturesBatch = new SpriteBatch();
        this.texturesBatch.setProjectionMatrix(game.getTextureCamera().combined);

        this.leaveButton = new Button(1f,1f,1f,1f,game.textureAssets.getButtonBlue());

        this.information = new TextBubble("this is not a game", new Vector2(1f,0f),
                new Vector2(3f,4f), game);

        this.isPossibleToLeave = false;
        font.getData().setScale(0.5f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,8,4);
        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.wasteTextures = new WasteDisplayRecycle(game.collectedStuffList.getAllShit(),
                3f,3f,4f,180);
        this.obstacleTextures = new WasteDisplayRecycle(game.allObstaclesCollection.getAllObstacles(),
                3f,2f,2f,60);

        if (this.obstacleTextures.isEmpty()) {
            this.sad = false;
        } else {
            this.sad = true;
        }


        Gdx.input.setInputProcessor(new InputAdapter() {


            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));

                if (isPossibleToLeave) {

                    if (leaveButton.isInsideButton(worldCoords.x, worldCoords.y)) {

                        if(game.worldSpeed >= -5f) {

                            game.worldSpeed -= 0.5f;
                            Gdx.app.log("RecycleScreen", "speeding: " + game.worldSpeed);
                        }

                        game.setLevelNumber((game.getLevelNumber()+1));
                        game.setShopScreen();
                    }
                }



                return true;
            }
    });

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
