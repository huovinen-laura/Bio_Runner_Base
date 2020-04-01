package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class GameAssets {
    private Texture playerChonky= new Texture("player2.png");
    private Texture playerOldFit= new Texture("ball.png");
    private Texture playerChonkyAnimation = new Texture("playerAnimation.png");

    private Texture grass = new Texture("grass.png");
    private Texture sky = new Texture("sky.png");

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
}
