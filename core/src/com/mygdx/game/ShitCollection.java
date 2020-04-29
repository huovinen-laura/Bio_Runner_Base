package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.collectibles.CollectibleSquare;
import com.mygdx.game.screens.BallGame;

import java.util.ArrayList;

/**
 * Stores the data for possible types of collectibles and gives random collectibles
 *
 */
public class ShitCollection {
    private ArrayList<ObstacleCollection.Obstacle> allShit;
    private int minimumAmountOfCollectables;
    private float timeBetweenCollectables;
    private float timeFromLastCollectable;
    private Vector2 lastCollectablePosition;
    private float minY;
    private float maxY;
    private BioRunnerGame game;

    public ShitCollection(BioRunnerGame game) {
        this.game = game;
        this.allShit = new ArrayList<>();
        this.minimumAmountOfCollectables = 10;

        this.allShit.add(new ObstacleCollection.Obstacle(
                "banaani", game.getTextureAssets().getBanaani(),25f));
        this.allShit.add(new ObstacleCollection.Obstacle(
                "leipa", game.getTextureAssets().getLeipa(),5f));

        this.allShit.add(new ObstacleCollection.Obstacle(
                "omena", game.getTextureAssets().getOmena(),10f));

        this.allShit.add(new ObstacleCollection.Obstacle(
                "tee", game.getTextureAssets().getTee(), 10f));

        this.allShit.add(new ObstacleCollection.Obstacle(
                "lehti", game.getTextureAssets().getLehti(), 10f));

        this.allShit.add(new ObstacleCollection.Obstacle(
                "luu", game.getTextureAssets().getLuu(),10f));

        this.allShit.add(new ObstacleCollection.Obstacle(
                "kukka", game.getTextureAssets().getKukka(),10f));

        this.allShit.add(new ObstacleCollection.Obstacle(
                "mansikka", game.getTextureAssets().getMansikka(),5f));

        this.timeBetweenCollectables = 1f;
        this.timeFromLastCollectable = 0f;
        this.minY = 0.34f;
        this.maxY = 3f;
    }

    /**
     * Increments the count of given object
     *
     * @param object object with name
     */
    public void addStuff(GameObject object) {

        for(int i = 0; i < this.allShit.size();i++) {

            if(this.allShit.get(i).getName().contentEquals(object.getName())) {
                this.allShit.get(i).incrementCount();
                break;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < this.allShit.size(); i++) {
            this.allShit.get(i).setCount(0);
        }
    }

    public ArrayList<ObstacleCollection.Obstacle> getAllShit() {
        return allShit;
    }

    public void setAllShit(ArrayList<ObstacleCollection.Obstacle> allShit) {
        this.allShit = allShit;
    }

    /**
     * Gives a random collectible
     *
     * @param max maximum key
     * @return collectible
     */
    public CollectibleSquare getRandomCollectible(int max) {
        float roll = (float) Math.random()*100;
        roll++;
        float positionX = game.getWORLD_WIDTH();
        float positionY = this.minY + (this.maxY - this.minY)* ((float) Math.random());
        while(positionY >= game.getLastCollectable().y-1 && positionY <= game.getLastCollectable().y+1 ) {
            positionY = this.minY + (this.maxY - this.minY)* ((float) Math.random());
        }

        int border = max;
        if(max > this.allShit.size()) {
            border = this.allShit.size();
        }
        boolean notReady = true;
        while(notReady) {
            for (int i = 0; i < border; i++) {

                if (this.allShit.get(i).getProbability() >= roll) {
                    game.setLastCollectable(new Vector2(positionX, positionY));
                    notReady = false;
                    return (new CollectibleSquare(game, this.allShit.get(i).getTexture(),
                            1f, positionX, positionY, this.allShit.get(i).getName()));
                } else {
                    roll -= this.allShit.get(i).getProbability();
                }
            }
        }



        return(new CollectibleSquare(game,new Texture("badlogic.jpg"),1f,positionX,positionY,"error"));
    }

    /**
     * Returns whether the next collectable should be spawned
     *
     * @param count
     * @return
     */
    public boolean isNextCollectibleComing(int count) {
        if( count < this.minimumAmountOfCollectables) {

            if(this.timeFromLastCollectable * -game.getWorldSpeed() >= this.timeBetweenCollectables*1) {
                this.timeFromLastCollectable = 0f;
                return(true);
            } else {
                this.timeFromLastCollectable+= Gdx.graphics.getDeltaTime();
                return(false);
            }

        } else {
            return(false);
        }
    }

    public void setTimeBetweenCollectables(float time) {
        this.timeBetweenCollectables = time;
    }

}
