package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class WasteDisplayRecycle {

    private float startPosition = 3f;
    private float endPosition = 7f;
    int numberOfDrawnCollectables;
    int[] duplicateCount; // counts the duplicates of each collectable
    float imageGap;
    int waitFrames;
    int numberOfCollectableTypes;
    int countOfCollectedStuff = 0;
    int waitTime;


    public WasteDisplayRecycle() {
        this.numberOfDrawnCollectables = 0;
        waitTime = 0;
        this.numberOfCollectableTypes = BallGame.collectedStuffList.getAllShit().size();
        this.duplicateCount = new int[this.numberOfCollectableTypes];
        int countObjects = 0;
        this.countOfCollectedStuff = 0;

        for (int i = 0; i < this.numberOfCollectableTypes; i++) {
            duplicateCount[i] = BallGame.collectedStuffList.getAllShit().get(i).getCount();
            this.countOfCollectedStuff+=duplicateCount[i];

        }
        Gdx.app.log("RecycleConst",""+this.countOfCollectedStuff);

        this.imageGap = (endPosition - startPosition) / countOfCollectedStuff;
        this.waitFrames = 180 / this.countOfCollectedStuff;
    }

    private Texture getNextTexture(int i) {
        int countObjects = 0;
            for (int j = 0; this.numberOfCollectableTypes >= j; j++) {
            if (countObjects + BallGame.collectedStuffList.getAllShit().get(j).getCount() >= i) {
                return (BallGame.collectedStuffList.getAllShit().get(j).getTexture());
            } else {
                countObjects += BallGame.collectedStuffList.getAllShit().get(j).getCount();
            }
        }
        return null;
    }


    private float getNextPositionX(int i) {
        Gdx.app.log("Recycle","X pos :"+ (this.startPosition + (this.imageGap * i)));
        return (this.startPosition + (this.imageGap * i));

    }

    private float getNextPositionY(int i) {
        return (2f);

    }

    private boolean isFinished() {
        return (false);
    }

    public boolean draw(SpriteBatch batch) {

        int drawnCount = 0;
        Texture nextTextureToDraw;

            while (drawnCount < numberOfDrawnCollectables) {

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

