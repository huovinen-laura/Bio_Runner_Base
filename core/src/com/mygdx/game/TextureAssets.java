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
    private Texture closeButton = new Texture("close_btn.png");
    private Texture finnishButton = new Texture("finland_flag.png");
    private Texture englishButton = new Texture("british_flag.png");
    private Texture doublePoint = new Texture("double_points.png");
    private Texture logo = new Texture("LOGO.png");
    private Texture progressBar = new Texture("progressbar.png");
    private Texture menu = new Texture("menu_final.png");
    private Texture common = new Texture("yleinen_tausta_final.png");
    private Texture recycle = new Texture("recycle_final.png");
    private Texture end = new Texture("end_screen_final.png");

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
    private skins skinAssets = new skins();

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

    public Texture getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(Texture closeButton) {
        this.closeButton = closeButton;
    }

    public Texture getFinnishButton() {
        return finnishButton;
    }

    public void setFinnishButton(Texture finnishButton) {
        this.finnishButton = finnishButton;
    }

    public Texture getEnglishButton() {
        return englishButton;
    }

    public void setEnglishButton(Texture englishButton) {
        this.englishButton = englishButton;
    }


    public Texture getDoublePoint() {
        return doublePoint;
    }

    public void setDoublePoint(Texture doublePoint) {
        this.doublePoint = doublePoint;
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
        closeButton.dispose();
        finnishButton.dispose();
        englishButton.dispose();
        doublePoint.dispose();
        logo.dispose();
        menu.dispose();
        common.dispose();
        recycle.dispose();
        end.dispose();
        kissa.dispose();
        luu.dispose();
        pilleri.dispose();
        banaani.dispose();
        tee.dispose();
        happyGirl.dispose();
        sadGirl.dispose();

        this.skinAssets.dispose();
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

    public Texture getMenu() {
        return menu;
    }

    public void setMenu(Texture menu) {
        this.menu = menu;
    }

    public Texture getCommon() {
        return common;
    }

    public void setCommon(Texture common) {
        this.common = common;
    }

    public Texture getRecycle() {
        return recycle;
    }

    public void setRecycle(Texture recycle) {
        this.recycle = recycle;
    }

    public Texture getEnd() {
        return end;
    }

    public void setEnd(Texture end) {
        this.end = end;
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

    public skins getSkinAssets() {
        return skinAssets;
    }
}
