package com.mygdx.game;

import com.badlogic.gdx.Gdx;
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
    private Texture kukka = new Texture("kukkaKuollut.png");
    private Texture mansikka = new Texture("mansikka.png");
    private Texture omena = new Texture("omena.png");
    private Texture patteri = new Texture( "patteri.png");
    private Texture pullo = new Texture("pullo.png");
    private Texture tupakka = new Texture("rööki.png");

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
        kukka.dispose();
        mansikka.dispose();
        omena.dispose();
        patteri.dispose();
        pullo.dispose();
        tupakka.dispose();
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
        Gdx.app.log("Assets","" + this.playerChonkyAnimation.getClass());
        return this.playerChonkyAnimation;
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

    public Texture getKukka() {
        return kukka;
    }

    public void setKukka(Texture kukka) {
        this.kukka = kukka;
    }

    public Texture getMansikka() {
        return mansikka;
    }

    public void setMansikka(Texture mansikka) {
        this.mansikka = mansikka;
    }

    public Texture getOmena() {
        return omena;
    }

    public void setOmena(Texture omena) {
        this.omena = omena;
    }

    public Texture getPatteri() {
        return patteri;
    }

    public void setPatteri(Texture patteri) {
        this.patteri = patteri;
    }

    public Texture getPullo() {
        return pullo;
    }

    public void setPullo(Texture pullo) {
        this.pullo = pullo;
    }

    public Texture getTupakka() {
        return tupakka;
    }

    public void setTupakka(Texture tupakka) {
        this.tupakka = tupakka;
    }
}
