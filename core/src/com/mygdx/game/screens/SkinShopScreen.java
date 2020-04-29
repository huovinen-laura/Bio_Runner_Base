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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.Button;

/**
 * Displays the skin shop
 */
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
    private Button velhoLocked;
    private Button koronaLocked;
    private Button jarviLocked;
    private Button farmerSkin;
    private Button farmerLocked;
    private Button tikoSkin;
    private Button tikoLocked;
    private Button bunnySkin;
    private Button bunnyLocked;
    private Button banaaniSkin;
    private Button banaaniLocked;
    private String flowerPointText;

    private boolean[] isSkinUnlocked;
    private String[] skinNames;


    public SkinShopScreen(BioRunnerGame game) {
        this.game = game;
        this.backButton = new Button(6.5f,3f,1f,1f, game.getTextureAssets().getCloseButton());

        this.vakioSkin = new Button(2.5f,2f,1f,1f, game.getTextureAssets().getStoreBG());
        this.farmerSkin = new Button(3.5f, 2f, 1f, 1f, game.getTextureAssets().getStoreBG());
        this.tikoSkin = new Button(4.5f, 2f, 1f, 1f, game.getTextureAssets().getStoreBG());
        this.koronaSkin = new Button(5.5f,2f,1f,1f, game.getTextureAssets().getStoreBG());

        this.bunnySkin = new Button(2.5f, 1f, 1f, 1f, game.getTextureAssets().getStoreBG());
        this.velhoSkin = new Button(3.5f,1f,1f,1f, game.getTextureAssets().getStoreBG());
        this.banaaniSkin = new Button(4.5f, 1f, 1f, 1f, game.getTextureAssets().getStoreBG());
        this.jarviSkin = new Button(5.5f,1f,1f,1f, game.getTextureAssets().getStoreBG());

        this.farmerLocked = new Button(3.5f, 2f, 1f, 1f, game.getTextureAssets().getStoreLocked());
        this.tikoLocked = new Button(4.5f, 2f, 1f, 1f, game.getTextureAssets().getStoreLocked());
        this.koronaLocked = new Button(5.5f,2f,1f,1f, game.getTextureAssets().getStoreLocked());

        this.bunnyLocked = new Button(2.5f, 1f, 1f, 1f, game.getTextureAssets().getStoreLocked());
        this.velhoLocked = new Button(3.5f,1f,1f,1f, game.getTextureAssets().getStoreLocked());
        this.banaaniLocked = new Button(4.5f, 1f, 1f, 1f, game.getTextureAssets().getStoreLocked());
        this.jarviLocked = new Button(5.5f,1f,1f,1f, game.getTextureAssets().getStoreLocked());


        this.font = game.getFont();
        tausta = game.getTextureAssets().getCommon();
        width = game.getWORLD_WIDTH();
        height = game.getWORLD_HEIGHT();


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(100 / 255f, 197 / 255f, 165 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        TextureRegion currentSkinFrame = game.getTextureAssets().getSkinAssets()
                .getAnimationFrame(game.getSkinName());
        currentSkinFrame.flip(false,true);



        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.texturesBatch.draw(tausta, 0, 0, width, height);
        this.texturesBatch.end();

        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        this.backButton.draw(texturesBatch);
        this.vakioSkin.draw(texturesBatch,
                game.getTextureAssets().getSkinAssets()
                        .getAnimationFrame(game.getTextureAssets().getPlayerChonkyAnimation()));
        this.koronaSkin.draw(texturesBatch,
                game.getTextureAssets().getSkinAssets()
                        .getAnimationFrame("korona"));
        this.velhoSkin.draw(texturesBatch,
                game.getTextureAssets().getSkinAssets()
                        .getAnimationFrame("velho"));
        this.jarviSkin.draw(texturesBatch,
                game.getTextureAssets().getSkinAssets()
                        .getAnimationFrame("jarviChan"));
        this.farmerSkin.draw(texturesBatch,
                game.getTextureAssets().getSkinAssets()
                        .getAnimationFrame("farmer"));
        this.tikoSkin.draw(texturesBatch,
                game.getTextureAssets().getSkinAssets()
                        .getAnimationFrame("tiko"));
        this.bunnySkin.draw(texturesBatch,
                game.getTextureAssets().getSkinAssets()
                        .getAnimationFrame("bunny"));
        this.banaaniSkin.draw(texturesBatch,
                game.getTextureAssets().getSkinAssets()
                        .getAnimationFrame("banaani"));
        this.texturesBatch.draw(game.getTextureAssets().getSkinAssets().getAnimationFrame(game.getSkinName()),
                1.2f,
                1f,
                0,
                0,
                1.2f,
                2f,
                1f,
                1f,
                0f
                );

        this.texturesBatch.end();

        // Unlocked tekstuurit
        this.texturesBatch.setProjectionMatrix(camera.combined);
        this.texturesBatch.begin();
        if(!isSkinUnlocked("korona")) {
            this.koronaLocked.draw(texturesBatch);
        }

        if(!isSkinUnlocked("velho")) {
            this.velhoLocked.draw(texturesBatch);
        }

        if(!isSkinUnlocked("jarviChan")) {
            this.jarviLocked.draw(texturesBatch);
        }

        if(!isSkinUnlocked("farmer")) {
            this.farmerLocked.draw(texturesBatch);
        }

        if(!isSkinUnlocked("tiko")) {
            this.tikoLocked.draw(texturesBatch);
        }

        if(!isSkinUnlocked("bunny")) {
            this.bunnyLocked.draw(texturesBatch);
        }

        if(!isSkinUnlocked("banaani")) {
            this.banaaniLocked.draw(texturesBatch);
        }
        this.texturesBatch.end();

        // Fontit
        game.getBatch().begin();
        this.font.draw(game.getBatch(), game.getText("shop"), game.getProjected().x * 0.4f,
                game.getProjected().y * 0.85f);
        this.font.draw(game.getBatch(),
                game.getText("youve") + game.getFlowerPoints() + " " +
                        game.getText("newFlowerPoints2"), game.getProjected().x * 0.35f,
                game.getProjected().y * 0.2f);

        // Skinien hinnat
        if(!isSkinUnlocked("farmer")) {
            this.font.draw(game.getBatch(), "100", game.getProjected().x * 0.47f,
                    game.getProjected().y * 0.65f);
        }

        if(!isSkinUnlocked("tiko")) {
            this.font.draw(game.getBatch(), "100", game.getProjected().x * 0.59f,
                    game.getProjected().y * 0.65f);
        }

        if(!isSkinUnlocked("korona")) {
            this.font.draw(game.getBatch(), "200", game.getProjected().x * 0.72f,
                    game.getProjected().y * 0.65f);
        }

        if(!isSkinUnlocked("bunny")) {
            this.font.draw(game.getBatch(), "200", game.getProjected().x * 0.345f,
                    game.getProjected().y * 0.40f);
        }

        if(!isSkinUnlocked("velho")) {
            this.font.draw(game.getBatch(), "500", game.getProjected().x * 0.47f,
                    game.getProjected().y * 0.40f);
        }

        if(!isSkinUnlocked("banaani")) {
            this.font.draw(game.getBatch(), "500", game.getProjected().x * 0.595f,
                    game.getProjected().y * 0.40f);
        }

        if(!isSkinUnlocked("jarviChan")) {
            this.font.draw(game.getBatch(), "1250", game.getProjected().x * 0.71f,
                    game.getProjected().y * 0.40f);
        }
        
        game.getBatch().end();
    }

    private boolean isSkinUnlocked(String skinName) {
        for(int i = 0; i < skinNames.length; i++) {
            if(skinNames[i].contentEquals(skinName) && isSkinUnlocked[i]) {
                return true;
            }
        }
        return false;
    }

    private void handleSkinClick(String name, int cost) {
        if(isSkinUnlocked(name)) {
            game.setSkinName(name);
        } else if(game.unlockSkin(name, cost)) {
            int count = 0;

            for(int i = 0; i < this.skinNames.length;i++) {

                if(this.skinNames[i].contentEquals(name)) {
                    count = i;
                }
            }

            game.setSkinName(name);
            this.isSkinUnlocked[count] = true;
        } else {

        }

    }

    @Override
    public void show() {

        Preferences skinPrefs = Gdx.app.getPreferences("skinPrefs");
        int skinCount = game.getTextureAssets().getSkinAssets().getAnimationTextures().size();
        this.isSkinUnlocked = new boolean[skinCount];
        this.skinNames = new String[skinCount];

        for(int i = 0; i < skinCount; i++) {
            skinNames[i] =  game.getTextureAssets().getSkinAssets().getNames().get(i);
            isSkinUnlocked[i] = skinPrefs.getBoolean( skinNames[i],false);

        }

        this.fontBatch = new SpriteBatch();
        this.texturesBatch = new SpriteBatch();
        this.isPossibleToLeave = true;
        this.flowerPointText = game.getText("flowerPoints");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWORLD_WIDTH(),game.getWORLD_HEIGHT());

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                Vector3 worldCoords = camera.unproject(new Vector3(screenX,screenY,0f));

                if( backButton.isInsideButton(worldCoords.x,worldCoords.y) ) {
                    game.setTitleScreen();

                } else if(vakioSkin.isInsideButton(worldCoords.x,worldCoords.y)) {
                    game.setSkinName("vakio");
                } else if(jarviSkin.isInsideButton(worldCoords.x,worldCoords.y)) {
                    handleSkinClick("jarviChan",1250);
                } else if(koronaSkin.isInsideButton(worldCoords.x,worldCoords.y)) {
                    handleSkinClick("korona",200);
                } else if(velhoSkin.isInsideButton(worldCoords.x,worldCoords.y)) {
                    handleSkinClick("velho",500);
                } else if(farmerSkin.isInsideButton(worldCoords.x, worldCoords.y)) {
                    handleSkinClick("farmer", 100);
                } else if(tikoSkin.isInsideButton(worldCoords.x, worldCoords.y)) {
                    handleSkinClick("tiko", 100);
                } else if(bunnySkin.isInsideButton(worldCoords.x, worldCoords.y)) {
                    handleSkinClick("bunny", 200);
                } else if(banaaniSkin.isInsideButton(worldCoords.x, worldCoords.y)) {
                    handleSkinClick("banaani", 500);
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
