package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.BallGame;

public class Waypoint {
    float length;
    float barWidth;
    Texture progressBar;
    Texture progressMarker;
    float displayedProgress;
    float realProgress;
    float xStart=0.5f;
    float yStart=3.5f;
    float markerSize;


    public Waypoint(float length, BioRunnerGame game) {
        this.length = length;
        this.displayedProgress = 0;
        this.realProgress = 0;
        this.barWidth = 4f;
        this.markerSize = 0.2f;
        progressBar = game.textureAssets.getProgressBar();
        progressMarker = game.textureAssets.getPlayerChonky();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(progressBar, xStart,yStart,this.barWidth + markerSize,0.25f);
        batch.draw(this.progressMarker,xStart+this.barWidth*(displayedProgress/length),yStart,markerSize,markerSize);
    }
    public boolean isFinished() {
        if (realProgress >= length + 2f) {
            return(true);
        } else {
            return(false);
        }
    }
    public void move() {

        displayedProgress += Gdx.graphics.getDeltaTime();
        realProgress += Gdx.graphics.getDeltaTime();
        if (displayedProgress > length) {
            this.displayedProgress = length;
        }
    }
}
