package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Waypoint {
    float length;
    Texture progressBar;
    Texture progressMarker;
    float progress;


    public Waypoint(float length) {
        this.length = length;
        this.progress = length;
        progressBar = new Texture("badlogic.jpg");
        progressMarker = new Texture("ball.png");
    }

    public void draw(SpriteBatch batch) {
        batch.draw(progressBar, 1f,1f,5f,0.25f);
    }
    public boolean isFinished() {
        if (progress <= 0) {
            return(true);
        } else {
            return(false);
        }
    }
    public void move() {
        progress += BallGame.worldSpeed/60;
        Gdx.app.log("Waypoint", "" + progress);
    }
}
