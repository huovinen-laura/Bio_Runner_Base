package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class skins {
    
    private Texture velhoAnimaatio = new Texture("velhoAnimaatio2.png");
    private Texture jarviAnimaatio = new Texture("jarviAnimaatio2.png");
    private Texture playerAnimation = new Texture("playerAnimation.png");
    private Texture koronaAnimaatio = new Texture("koronaAnimaatio.png");

    private ArrayList<Texture> animationTextures;
    private ArrayList<String> names;
    private ArrayList<Animation> animations;

    public skins() {
        this.animationTextures = new ArrayList<>();
        this.names = new ArrayList<>();
        this.animationTextures.add(playerAnimation);
        this.names.add("vakio");
        this.animationTextures.add(koronaAnimaatio);
        this.names.add("korona");
        this.animationTextures.add(velhoAnimaatio);
        this.names.add("velho");
        this.animationTextures.add(jarviAnimaatio);
        this.names.add("jarviChan");
        this.animations = new ArrayList<>();

        for(Texture animationTexture : this.animationTextures) {
            this.animations.add(this.createAnimation(animationTexture));
        }
    }

    public Animation getAnimation(String skinName) {

        for(int i = 0; i < this.animationTextures.size(); i++) {
            if(this.names.get(i).contentEquals(skinName)) {
                return(this.animations.get(i));
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
        velhoAnimaatio.dispose();
        jarviAnimaatio.dispose();
        playerAnimation.dispose();
        koronaAnimaatio.dispose();
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

    public ArrayList<Texture> getAnimationTextures() {
        return animationTextures;
    }

    public ArrayList<String> getNames() {
        return names;
    }
}
