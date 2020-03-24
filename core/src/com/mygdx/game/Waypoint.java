package com.mygdx.game;

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


    public Waypoint(float length) {
        this.length = length;
        this.displayedProgress = 0;
        this.realProgress = 0;
        this.barWidth = 4f;
        this.markerSize = 0.2f;
        progressBar = new Texture("progressbar.png");
        progressMarker = new Texture("ball.png");
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

        displayedProgress -= BallGame.worldSpeed/60;
        realProgress -= BallGame.worldSpeed/60;
        if (displayedProgress > length) {
            this.displayedProgress = length;
        }
    }
}
