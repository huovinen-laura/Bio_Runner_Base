package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.BallGame;

import java.util.ArrayList;

public class WasteDisplayRecycle {

    private float startPosition ;
    private float endPosition ;
    private int time;
    private float positionY;
    int numberOfDrawnCollectables;
    int[] duplicateCount; // counts the duplicates of each collectable
    float imageGap;
    int waitFrames;
    int numberOfCollectableTypes;
    int countOfCollectedStuff = 0;
    int waitTime;
    ArrayList<ObstacleCollection.Obstacle> collectionOfStuff;



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


    private float getNextPositionX(int i) {
        return (this.startPosition + (this.imageGap * (i-1)));

    }

    private float getNextPositionY(int i) {
        return (this.positionY);

    }

    private boolean isFinished() {
        return (false);
    }

    public boolean draw(SpriteBatch batch) {

        int drawnCount = 1;
        Texture nextTextureToDraw;

            while (drawnCount <= numberOfDrawnCollectables) {

                batch.draw(this.getNextTexture(drawnCount),
                            this.getNextPositionX(drawnCount),
                            this.getNextPositionY(drawnCount),
                            1f,
                            1f
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

