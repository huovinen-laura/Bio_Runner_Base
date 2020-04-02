package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class TextureAssets {
    private Texture playerChonky= new Texture("player2.png");
    private Texture playerOldFit= new Texture("ball.png");
    private Texture playerChonkyAnimation = new Texture("playerAnimation.png");

    private Texture grass = new Texture("grass.png");
    private Texture sky = new Texture("sky.png");
    private Texture buttonBlue = new Texture("button_blank.png");
    private Texture logo = new Texture("LOGO.png");
    private Texture progressBar = new Texture("progressbar.png");

    private Texture kissa = new Texture("kijssa.png");
    private Texture luu = new Texture("luu.png");
    private Texture pilleri = new Texture("pilleri.png");
    private Texture banaani = new Texture("banaani.png");
    private Texture tee = new Texture("tee.png");

    private Texture happyGirl = new Texture("recycleGallHappy.png");
    private Texture sadGirl = new Texture("recycleGallSad.png");

    public Texture getKissa() {
        return kissa;
    }

    public void setKissa(Texture kissa) {
        this.kissa = kissa;
    }

    public Texture getLuu() {
        return luu;
    }

    public void setLuu(Texture luu) {
        this.luu = luu;
    }

    public Texture getPilleri() {
        return pilleri;
    }

    public void setPilleri(Texture pilleri) {
        this.pilleri = pilleri;
    }

    public Texture getBanaani() {
        return banaani;
    }

    public void setBanaani(Texture banaani) {
        this.banaani = banaani;
    }

    public Texture getTee() {
        return tee;
    }

    public void setTee(Texture tee) {
        this.tee = tee;
    }

    public Texture getButtonBlue() {
        return buttonBlue;
    }

    public void setButtonBlue(Texture buttonBlue) {
        this.buttonBlue = buttonBlue;
    }

    public Texture getLogo() {
        return logo;
    }

    public void setLogo(Texture logo) {
        this.logo = logo;
    }


    public Texture getPlayerChonky() {
        return playerChonky;
    }

    public void setPlayerChonky(Texture playerChonky) {
        this.playerChonky = playerChonky;
    }

    public Texture getPlayerOldFit() {
        return playerOldFit;
    }

    public void setPlayerOldFit(Texture playerOldFit) {
        this.playerOldFit = playerOldFit;
    }

    public Texture getGrass() {
        return grass;
    }

    public void setGrass(Texture grass) {
        this.grass = grass;
    }

    public Texture getSky() {
        return sky;
    }

    public void setSky(Texture sky) {
        this.sky = sky;
    }

    public void dispose() {
        playerChonky.dispose();
        playerChonkyAnimation.dispose();
        playerOldFit.dispose();
        grass.dispose();
        sky.dispose();
        progressBar.dispose();
        buttonBlue.dispose();
        logo.dispose();
        kissa.dispose();
        luu.dispose();
        pilleri.dispose();
        banaani.dispose();
        tee.dispose();
        happyGirl.dispose();
        sadGirl.dispose();
    }

    public Texture getPlayerChonkyAnimation() {
        return playerChonkyAnimation;
    }

    public void setPlayerChonkyAnimation(Texture playerChonkyAnimation) {
        this.playerChonkyAnimation = playerChonkyAnimation;
    }

    public Texture getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(Texture progressBar) {
        this.progressBar = progressBar;
    }

    public Texture getHappyGirl() {
        return happyGirl;
    }

    public void setHappyGirl(Texture happyGirl) {
        this.happyGirl = happyGirl;
    }

    public Texture getSadGirl() {
        return sadGirl;
    }

    public void setSadGirl(Texture sadGirl) {
        this.sadGirl = sadGirl;
    }
}
