package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Waypoint {
    float length;
    float barWidth;
    Texture progressBar;
    TextureRegion progressMarker;
    float displayedProgress;
    float realProgress;
    float xStart=0.5f;
    float yStart=3.5f;
    float markerSize;


    public Waypoint(float length, BioRunnerGame game) {
        this.length = length;
        this.displayedProgress = 0f;
        this.realProgress = 0f;
        this.barWidth = 4f;
        this.markerSize = 0.2f;
        progressBar = game.getTextureAssets().getProgressBar();
        progressMarker = game.getTextureAssets().getSkinAssets().getAnimationFrame(game.getSkinName());
        progressMarker.flip(true,false);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(progressBar, xStart,yStart,this.barWidth + markerSize,0.25f);
        batch.draw(this.progressMarker,xStart+this.barWidth*(displayedProgress/length),yStart,markerSize,markerSize);
        batch.flush();
    }

    public boolean isFinished() {
        if (realProgress >= length + 10f) {
            return(true);
        } else {
            return(false);
        }
    }
    public boolean move() {
        displayedProgress += Gdx.graphics.getDeltaTime();
        realProgress += Gdx.graphics.getDeltaTime();

        if (displayedProgress > length) {
            this.displayedProgress = length;
            return(true);
        }
        return false;
    }
}
