package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.collectibles.CollectibleSquare;
import com.mygdx.game.collectibles.TestCollectible;
import com.mygdx.game.screens.BallGame;

import java.util.ArrayList;

public class ShitCollection {
    private ArrayList<ObstacleCollection.Obstacle> allShit;
    private int minimumAmountOfCollectables;
    private float timeBetweenCollectables;
    private float timeFromLastCollectable;
    private Vector2 lastCollectablePosition;
    private float minY;
    private float maxY;

    public ShitCollection() {
        this.allShit = new ArrayList<>();
        this.minimumAmountOfCollectables = 10;

        this.allShit.add(new ObstacleCollection.Obstacle(
                "banaani", TestCollectible.texture,50f));

        this.allShit.add(new ObstacleCollection.Obstacle(
                "luu", new Texture("luu.png"),25f ));

        this.allShit.add(new ObstacleCollection.Obstacle(
                "tee", new Texture("tee.png"), 26f));

        this.timeBetweenCollectables = 1f;
        this.timeFromLastCollectable = 0f;
        this.minY = 0.34f;
        this.maxY = 3f;
    }

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

    public CollectibleSquare getRandomCollectible() {
        float roll = (float) Math.random()*100;
        roll++;
        Gdx.app.log("ShitCollection", "Random: " + roll );
        float positionX = BallGame.WORLD_WIDTH;
        float positionY = this.minY + (this.maxY - this.minY)* ((float) Math.random());
        this.lastCollectablePosition = new Vector2(positionX,positionY);

        for (int i = 0 ; i < this.allShit.size();i++) {

            if(this.allShit.get(i).getProbability() >= roll) {
                return(new CollectibleSquare(this.allShit.get(i).getTexture(),
                        1f,positionX,positionY,this.allShit.get(i).getName()));
            } else {
                roll-= this.allShit.get(i).getProbability();
            }
        }



        return(new CollectibleSquare(new Texture("badlogic.jpg"),1f,positionX,positionY,"error"));
    }

    public boolean isNextCollectibleComing(int count) {
        if( count < this.minimumAmountOfCollectables) {

            if(this.timeFromLastCollectable >= this.timeBetweenCollectables) {
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
