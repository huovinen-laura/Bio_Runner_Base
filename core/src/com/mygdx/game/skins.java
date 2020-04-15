package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class skins {
    private Texture velhoAnimaatio = new Texture("velhoAnimaatio.png");
    private Texture jarviAnimaatio = new Texture("jarviAnimaatio.png");
    private Texture playerAnimation = new Texture("playerAnimation.png");
    private Texture koronaAnimaatio = new Texture("koronaAnimaatio.png");

    private ArrayList<Texture> animations;
    private ArrayList<String> names;

    public skins() {
        this.animations = new ArrayList<>();
        this.names = new ArrayList<>();
        this.animations.add(playerAnimation);
        this.names.add("vakio");
        this.animations.add(koronaAnimaatio);
        this.names.add("korona");
        this.animations.add(velhoAnimaatio);
        this.names.add("velho");
        this.animations.add(jarviAnimaatio);
        this.names.add("jarviChan");
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

    public ArrayList<Texture> getAnimations() {
        return animations;
    }

    public ArrayList<String> getNames() {
        return names;
    }
}
