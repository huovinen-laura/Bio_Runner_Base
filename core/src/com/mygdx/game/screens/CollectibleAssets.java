package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;

public class CollectibleAssets {
    private Texture kissa = new Texture("kijssa.png");
    private Texture luu = new Texture("luu.png");
    private Texture pilleri = new Texture("pilleri.png");
    private Texture banaani = new Texture("banaani.png");
    private Texture tee = new Texture("tee.png");

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
}
