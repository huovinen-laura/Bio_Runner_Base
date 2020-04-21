package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.Button;
import com.mygdx.game.TextBubble;
import com.mygdx.game.WasteDisplayRecycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private ArrayList<String> facts;
    private ArrayList<String> factsBattery;
    private ArrayList<String> factsPoop;

    public RecycleScreen(BioRunnerGame game) {
        this.game = game;
        this.font = game.getFont();
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;
        this.projected = game.getProjected();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(100/255f, 197/255f, 165/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        this.texturesBatch.begin();
        this.texturesBatch.draw(tausta, 0, 0, width, height);
        /*
        if(sad) {
            this.texturesBatch.draw(this.sadGuy,2.75f,-0.75f,2.5f,2.5f);
        } else {
            this.texturesBatch.draw(this.happyGuy, 2.75f, -0.75f, 2.5f, 2.5f);
        }
         */

        if(this.wasteTextures.draw(this.texturesBatch)) {



            if(this.obstacleTextures.draw(this.texturesBatch)) {




                this.isPossibleToLeave = true;
            }
        }
        this.texturesBatch.end();

        game.batch.begin();
        this.information.DrawFont(game.batch,projected);
        if(this.isPossibleToLeave) {
            font.draw(game.batch, game.getText("tap"), projected.x * 0.065f, projected.y * 0.205f);
        }


        game.batch.end();

    }

    @Override
    public void show() {
        this.happyGuy = game.textureAssets.getHappyGirl();
        this.sadGuy = game.textureAssets.getSadGirl();
        tausta = game.textureAssets.getRecycle();
        game.batch = new SpriteBatch();
        this.praise = true;
        this.texturesBatch = new SpriteBatch();
        this.texturesBatch.setProjectionMatrix(game.getTextureCamera().combined);
        // Adding facts to ArrayList
        List<String> factList = Arrays.asList(game.getText("info1"), game.getText("info2"),
                game.getText("info3"), game.getText("info4"), game.getText("info5"),
                game.getText("info6"), game.getText("info7"), game.getText("info8"),
                game.getText("info9"), game.getText("info10"), game.getText("info11"),
                game.getText("info12"));
        facts = new ArrayList<>();
        facts.addAll(factList);

        factsBattery = new ArrayList<>();
        factsBattery.add(game.getText("infoBattery1"));
        factsBattery.add(game.getText("infoBattery2"));

        factsPoop = new ArrayList<>();
        factsPoop.add(game.getText("infoPoop1"));
        factsPoop.add(game.getText("infoPoop2"));

        this.leaveButton = new Button(0.4f,0.4f,0.5f,3f,game.textureAssets.getGeneralButton());

        /*
        this.information = new TextBubble("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus" +
                " a tincidunt neque. Ut id tempor sapien, in tincidunt odio. Donec dignissim purus eros.", new Vector2(0.5f,3.5f),
                new Vector2(3.2f,4f), game);

         */
        this.information = new TextBubble(getFact(), new Vector2(0.5f, 3.5f),
                new Vector2(3.2f, 4), game);

        this.isPossibleToLeave = false;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,8,4);
        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.wasteTextures = new WasteDisplayRecycle(game.collectedStuffList.getAllShit(),
                3.7f,3f,3.3f,180);
        this.obstacleTextures = new WasteDisplayRecycle(game.allObstaclesCollection.getAllObstacles(),
                3.7f,2f,2f,60);

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



                        if(game.worldSpeed >= -5f) {

                            game.worldSpeed -= 0.5f;
                            Gdx.app.log("RecycleScreen", "speeding: " + game.worldSpeed);
                        }

                        game.setLevelNumber((game.getLevelNumber()+1));
                        game.setShopScreen();

                }



                return true;
            }
    });

    }

    public String getFact() {
        return facts.get(MathUtils.random(0, 11));
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
