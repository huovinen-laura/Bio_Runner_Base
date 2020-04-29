package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Displays the waypoint.
 */
public class Waypoint {

    /**
     * Tells how far player is in the level.
     */
    float length;

    /**
     * Width of the bar.
     */
    float barWidth;

    /**
     * Texture for the progress bar.
     */
    Texture progressBar;

    /**
     * TextureRegion for the progress bar's marker.
     */
    TextureRegion progressMarker;

    /**
     * Position of the marker.
     */
    float displayedProgress;

    /**
     * How far the player is from the beginning.
     */
    float realProgress;

    /**
     * Starting x position for the progress bar.
     */
    float xStart=0.5f;

    /**
     * Starting y position for the progress bar.
     */
    float yStart=3.5f;
    float markerSize;

    /**
     * Constructs the waypoint.
     *
     * @param length
     * @param game
     */
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

    /**
     * Draws the progress bar.
     *
     * @param batch Batch required to draw the progress bar.
     */
    public void draw(SpriteBatch batch) {
        batch.draw(progressBar, xStart,yStart,this.barWidth + markerSize,0.25f);
        batch.draw(this.progressMarker,xStart+this.barWidth*(displayedProgress/length),yStart,markerSize,markerSize);
        batch.flush();
    }

    /**
     * Checks if the level is over or not.
     *
     * @return Tells if the level is over or not.
     */
    public boolean isFinished() {
        if (realProgress >= length + 10f) {
            return(true);
        } else {
            return(false);
        }
    }

    /**
     * Moves the progress bar's mark.
     *
     * @return
     */
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
