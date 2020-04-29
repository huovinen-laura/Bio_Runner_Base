package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Displays a set of stuff in a flashy manner in certain position.
 */
public class WasteDisplayRecycle {

    private float startPosition ;
    private float endPosition ;
    private int time;
    private float positionY;

    /**
     * The number of drawn collectables.
     */
    int numberOfDrawnCollectables;

    /**
     * Counts the duplicates of each collectable.
     */
    int[] duplicateCount;

    /**
     * The gap between images.
     */
    float imageGap;

    /**
     * How many frames should appear.
     */
    int waitFrames;

    /**
     * How many different kind of collectables is there.
     */
    int numberOfCollectableTypes;

    /**
     * Count of collectables.
     */
    int countOfCollectedStuff = 0;

    /**
     * How long has been waited after the latest texture.
     */
    int waitTime;

    /**
     * List with collectables info.
     */
    ArrayList<ObstacleCollection.Obstacle> collectionOfStuff;

    /**
     * Constructs the waste display.
     *
     * @param collection Obstacle list.
     * @param positionX The starting x coordinate for the display.
     * @param positionY The starting y coordinate for the display.
     * @param length The length of the display.
     * @param time Determines the waitTime.
     */
    public WasteDisplayRecycle(ArrayList<ObstacleCollection.Obstacle> collection,float positionX ,
                               float positionY,float length, int time) {
        this.numberOfDrawnCollectables = 0;
        this.collectionOfStuff = collection;
        this.startPosition = positionX;
        this.positionY = positionY;
        this.endPosition = positionX + length;
        this.time = time;
        waitTime = 0;
        this.numberOfCollectableTypes = this.collectionOfStuff.size();
        this.duplicateCount = new int[this.numberOfCollectableTypes];
        int countObjects = 0;
        this.countOfCollectedStuff = 0;

        for (int i = 0; i < this.numberOfCollectableTypes; i++) {
            duplicateCount[i] = this.collectionOfStuff.get(i).getCount();
            this.countOfCollectedStuff+=duplicateCount[i];

        }

        if (this.countOfCollectedStuff > 0) {
            this.imageGap = (endPosition - startPosition) / countOfCollectedStuff;
            this.waitFrames = this.time / this.countOfCollectedStuff;
        } else {
            this.imageGap = 0f;
            this.waitFrames = 0;
        }
    }

    /**
     * Returns the next texture if there's still more on the list.
     *
     * @param i The number of the texture.
     * @return Returns the texture.
     */
    private Texture getNextTexture(int i) {
        int countObjects = 0;
            for (int j = 0; this.numberOfCollectableTypes >= j; j++) {
            if (countObjects + this.collectionOfStuff.get(j).getCount() >= i) {
                return (this.collectionOfStuff.get(j).getTexture());
            } else {
                countObjects += this.collectionOfStuff.get(j).getCount();
            }
        }
        return null;
    }

    /**
     * Returns the next position of the collectable.
     *
     * @param i Position.
     * @return Returns the new position.
     */
    private float getNextPositionX(int i) {
        return (this.startPosition + (this.imageGap * (i-1)));

    }

    /**
     * Checks if the list is empty.
     *
     * @return Tells if the list is empty or not.
     */
    public boolean isEmpty() {

        if(this.countOfCollectedStuff <= 0) {
            return (true);
        } else {
            return false;
        }
    }

    /**
     * Returns the new y position.
     *
     * @param i
     * @return Returns the y position.
     */
    private float getNextPositionY(int i) {
        return (this.positionY);

    }

    /**
     * Tells if display is finished or not.
     *
     * @return Tells if display is finished or not.
     */
    private boolean isFinished() {
        return (false);
    }

    /**
     * Draws the display and checks if there's more to draw.
     *
     * @param batch Game required to draw textures.
     * @return Returns
     */
    public boolean draw(SpriteBatch batch) {

        int drawnCount = 1;
        Texture nextTextureToDraw;

            while (drawnCount <= numberOfDrawnCollectables) {

                Texture next = getNextTexture(drawnCount);

                batch.draw(next,
                            this.getNextPositionX(drawnCount),
                            this.getNextPositionY(drawnCount),
                            1f,
                        ((float) next.getHeight() / (float) next.getWidth())
                );

                drawnCount++;
            }

        if (this.numberOfDrawnCollectables < this.countOfCollectedStuff) {

            if (this.waitTime >= this.waitFrames) {
                this.numberOfDrawnCollectables++;
                this.waitTime = 0;
            } else {
                this.waitTime++;
            }
            return(false);
        } else {
            return(true);
        }
    }
}

