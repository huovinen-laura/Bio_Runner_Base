package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class assetManager {
    public static Texture badlogic= new Texture("badlogic.jpg");
    public static Texture playerChonky= new Texture("player2.png");
    public static Texture playerOldFit= new Texture("ball.png");
    public static Texture banaani = new Texture("banaani.png");
    public static Texture grass = new Texture("grass.png");
    public static Texture sky = new Texture("sky.png");
    public static Texture buttonBlue = new Texture("button_blank.png");
    public static Texture kissa = new Texture("kijssa.png");
    public static Texture logo = new Texture("LOGO.png");
    public static Texture luu = new Texture("luu.png");
    public static Texture pilleri = new Texture("pilleri.png");
    public static Texture playerChonkyAnimation = new Texture("playerAnimation.png");
    public static Texture progressBar = new Texture("progressbar.png");
    public static Texture happyGirl = new Texture("recycleGallHappy.png");
    public static Texture sadGirl = new Texture("recycleGallSad.png");
    public static Texture tee = new Texture("tee.png");

    public static void dispose() {
        badlogic.dispose();
        pilleri.dispose();
        banaani.dispose();
        grass.dispose();
        sky.dispose();
        buttonBlue.dispose();
        kissa.dispose();
        logo.dispose();
        luu.dispose();
        playerChonkyAnimation.dispose();
        progressBar.dispose();
        happyGirl.dispose();
        sadGirl.dispose();
        tee.dispose();
        playerChonky.dispose();
        playerOldFit.dispose();
    }

}
