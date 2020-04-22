package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class skins {

    private AssetManager skinManager;
    private Texture velhoAnimaatio = new Texture("velhoAnimaatio2.png");
    private Texture jarviAnimaatio = new Texture("jarviAnimaatio2.png");
    private Texture playerAnimation = new Texture("playerAnimation.png");
    private Texture koronaAnimaatio = new Texture("koronaAnimaatio.png");

    private ArrayList<String> animationTextures;
    private ArrayList<String> names;
    private Animation[] animations;

    public skins() {
        skinManager = new AssetManager();
        skinManager.load("velhoAnimaatio2.png", Texture.class);

        skinManager.load("jarviAnimaatio2.png", Texture.class);
        skinManager.load("playerAnimation.png", Texture.class);
        skinManager.load("koronaAnimaatio.png", Texture.class);

        this.animationTextures = new ArrayList<>();
        this.names = new ArrayList<>();

        this.names.add("vakio");
        this.animationTextures.add("playerAnimation.png");
        this.names.add("korona");
        this.animationTextures.add("koronaAnimaatio.png");
        this.names.add("velho");
        this.animationTextures.add("velhoAnimaatio2.png");
        this.names.add("jarviChan");
        this.animationTextures.add("jarviAnimaatio2.png");

        this.animations = new Animation[this.names.size()];


    }

    public boolean update() {

        if(this.skinManager.update()) {

            Gdx.app.log("TextureAssets","skinAssets loaded" + this.animationTextures.size());

            for(int i = 0; i < this.animationTextures.size();i++){ {
                Gdx.app.log("TextureAssets","skinAssets animations" + i);
                this.animations[i]= this.createAnimation(skinManager.get(this.animationTextures.get(i), Texture.class));
            }

            return(true);

        }
        }

        return(false);

    }

    public Texture getAnimationTexture(String skinName) {
        for(int i = 0; i < this.animationTextures.size(); i++) {
            if(this.names.get(i).contentEquals(skinName)) {
                return(this.skinManager.get(this.animationTextures.get(i), Texture.class));
            }
        }

        return (null);
    }

    public Animation getAnimation(String skinName) {

        for(int i = 0; i < this.animationTextures.size(); i++) {
            if(this.names.get(i).contentEquals(skinName)) {
                return(this.animations[i]);
            }
        }

        return (null);
    }

    private Animation createAnimation(Texture texture) {
        final int FRAME_COLS = 4;
        final int FRAME_ROWS = 1;

        int tileWidth = texture.getWidth() / FRAME_COLS;
        int tileHeight = texture.getHeight() / FRAME_ROWS;

        TextureRegion[][] tmp = TextureRegion.split(texture, tileWidth, tileHeight);

        TextureRegion[] allFrames = toTextureArray(tmp, FRAME_COLS, FRAME_ROWS);

        return(new Animation(4 / 60f, allFrames));

    }

    public TextureRegion[] toTextureArray(TextureRegion[][]tr, int cols, int rows) {
        TextureRegion[] frames = new TextureRegion[cols * rows];

        int index = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                frames[index++] = tr[i][j];
            }
        }

        return frames;
    }

    public void dispose() {
        this.skinManager.dispose();
    }

    public Texture getVelhoAnimaatio() {
        return velhoAnimaatio;
    }

    public TextureRegion getAnimationFrame( Texture animationTexture) {
        final int FRAME_COLS = 4;
        final int FRAME_ROWS = 1;

        int tileWidth = animationTexture.getWidth() / FRAME_COLS;
        int tileHeight = animationTexture.getHeight() / FRAME_ROWS;

        TextureRegion[][] tmp = TextureRegion.split(animationTexture, tileWidth, tileHeight);

        return tmp[0][0];
    }

    public void setVelhoAnimaatio(Texture velhoAnimaatio) {
        this.velhoAnimaatio = velhoAnimaatio;
    }

    public Texture getJarviAnimaatio() {
        return jarviAnimaatio;
    }

    public void setJarviAnimaatio(Texture jarviAnimaatio) {
        this.jarviAnimaatio = jarviAnimaatio;
    }

    public Texture getPlayerAnimation() {
        return playerAnimation;
    }

    public void setPlayerAnimation(Texture playerAnimation) {
        this.playerAnimation = playerAnimation;
    }

    public Texture getKoronaAnimaatio() {
        return koronaAnimaatio;
    }

    public void setKoronaAnimaatio(Texture koronaAnimaatio) {
        this.koronaAnimaatio = koronaAnimaatio;
    }

    public Animation[] getAnimationTextures() {
        return animations;
    }

    public ArrayList<String> getNames() {
        return names;
    }
}
