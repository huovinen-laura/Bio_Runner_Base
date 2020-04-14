package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.Button;

import java.util.Locale;

public class SkinShopScreen extends ScreenAdapter {
    public BioRunnerGame game;
    private SpriteBatch texturesBatch;
    private SpriteBatch fontBatch;
    private BitmapFont font;
    private boolean isPossibleToLeave;
    private Button backButton;
    private OrthographicCamera camera;
    private Texture tausta;
    private float width, height;
    private Button velhoSkin;
    private Button koronaSkin;
    private Button vakioSkin;
    private Button jarviSkin;
    private String flowerPointText;
    private int velhoSkinCost;


    public SkinShopScreen(BioRunnerGame game) {
        this.game = game;
        this.velhoSkinCost = 1000;
        this.backButton = new Button(6.5f,3f,1f,1f, game.textureAssets.getCloseButton());
        this.velhoSkin = new Button(2f,2f,1f,1f, game.textureAssets.getButtonBlue());
        this.koronaSkin = new Button(3f,2f,1f,1f, game.textureAssets.getButtonBlue());
        this.vakioSkin = new Button(4f,2f,1f,1f, game.textureAssets.getButtonBlue());
        this.jarviSkin = new Button(5f,2f,1f,1f, game.textureAssets.getButtonBlue());
        this.font = game.getFont();
        tausta = game.textureAssets.getCommon();
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        TextureRegion currentSkinFrame = game.textureAssets.getSkinAssets()
                .getAnimationFrame(game.getCurrentAnimation());
        currentSkinFrame.flip(false,true);



        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.texturesBatch.draw(tausta, 0, 0, width, height);
        this.texturesBatch.end();

        game.batch.begin();
        this.font.draw(game.batch, game.getText("shop"), Gdx.graphics.getWidth() * 0.2f,
                Gdx.graphics.getHeight() * 0.80f);
        this.font.draw(game.batch,
                this.flowerPointText + game.getFlowerPoints(), Gdx.graphics.getWidth() * 0.2f,
                Gdx.graphics.getHeight() * 0.1f);
        this.font.draw(game.batch,game.getText("costPoints") + this.velhoSkinCost,
                Gdx.graphics.getWidth() * 0.25f,
                Gdx.graphics.getHeight() * 0.25f);
        game.batch.end();

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.backButton.draw(texturesBatch);
        this.vakioSkin.draw(texturesBatch,
                game.textureAssets.getSkinAssets()
                        .getAnimationFrame(game.textureAssets.getPlayerChonkyAnimation()));
        this.koronaSkin.draw(texturesBatch,
                game.textureAssets.getSkinAssets()
                        .getAnimationFrame(game.textureAssets.getSkinAssets().getKoronaAnimaatio()));
        this.velhoSkin.draw(texturesBatch,
                game.textureAssets.getSkinAssets()
                        .getAnimationFrame(game.textureAssets.getSkinAssets().getVelhoAnimaatio()));
        this.jarviSkin.draw(texturesBatch,
                game.textureAssets.getSkinAssets()
                        .getAnimationFrame(game.textureAssets.getSkinAssets().getJarviAnimaatio()));
        this.texturesBatch.draw(game.textureAssets.getSkinAssets().getAnimationFrame(game.getCurrentAnimation()),
                1f,
                0.52f,
                0,
                0,
                1f,
                currentSkinFrame.getRegionHeight()/currentSkinFrame.getRegionWidth(),
                1f,
                1f,
                0f
                );
        this.texturesBatch.end();
    }

    @Override
    public void show() {
        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        this.isPossibleToLeave = true;
        this.flowerPointText = game.getText("flowerPoints");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BallGame.WORLD_WIDTH,BallGame.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));

                if( backButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    game.setTitleScreen();
                } else if(vakioSkin.isInsideButton(worldCoords.x,worldCoords.y)) {
                    game.setSkinName("vakio");
                    game.setTitleScreen();
                } else if(jarviSkin.isInsideButton(worldCoords.x,worldCoords.y)) {
                    game.setSkinName("jarviChan");
                    game.setTitleScreen();
                } else if(koronaSkin.isInsideButton(worldCoords.x,worldCoords.y)) {
                    game.setSkinName("korona");
                    game.setTitleScreen();
                } else if(velhoSkin.isInsideButton(worldCoords.x,worldCoords.y)) {
                    game.setSkinName("velho");
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
}
