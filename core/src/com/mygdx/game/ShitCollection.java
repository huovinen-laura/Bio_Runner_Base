package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.collectibles.CollectibleSquare;
import com.mygdx.game.collectibles.TestCollectible;
import com.mygdx.game.screens.BallGame;

import java.util.ArrayList;

public class ShitCollection {
    private ArrayList<Shit> allShit;
    private int minimumAmountOfCollectables;
    private float timeBetweenCollectables;
    private float timeFromLastCollectable;
    private Vector2 LastCollectablePosition;

    public ShitCollection() {
        this.allShit = new ArrayList<>();
        this.minimumAmountOfCollectables = 2;
        this.allShit.add(new Shit("banaani", TestCollectible.texture,50f));
        this.allShit.add(new Shit("luu", new Texture("luu.png"),25f ));
        this.allShit.add(new Shit("tee", new Texture("tee.png"), 25f));
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

    public ArrayList<Shit> getAllShit() {
        return allShit;
    }

    public void setAllShit(ArrayList<Shit> allShit) {
        this.allShit = allShit;
    }

    public CollectibleSquare getRandomCollectible() {
        float roll = (float) Math.random()*100;
        roll++;
        Gdx.app.log("ShitCollection", "Random: " + roll );
        float positionX = BallGame.WORLD_WIDTH;
        float positionY = BallGame.WORLD_HEIGHT * ((float) Math.random());
        this.LastCollectablePosition = new Vector2(positionX,positionY);

        for (int i = 0 ; i < this.allShit.size();i++) {

            if(this.allShit.get(i).probability >= roll) {
                return(new CollectibleSquare(this.allShit.get(i).getTexture(),
                        1f,positionX,positionY,this.allShit.get(i).getName()));
            } else {
                roll-= this.allShit.get(i).probability;
            }
        }



        return(new CollectibleSquare(new Texture("badlogic.jpg"),1f,positionX,positionY,"error"));
    }

    public boolean isNextCollectibleComing(int count) {
        if( count < this.minimumAmountOfCollectables) {
            return(true);
        } else {
            return(false);
        }
    }



    public class Shit {
        private String name;
        private int count;
        private Texture texture;
        private Float probability;
        private float minY;
        private float maxY;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }
        public void incrementCount() {
            this.count++;
        }
        public void setCount(int count) {
            this.count = count;
        }

        public Shit(String name, Texture newTexture, float prob) {
            this.name = name;
            this.count = 0;
            this.texture = newTexture;
            this.probability = prob;
        }

        public Texture getTexture() {
            return texture;
        }

        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        public Float getProbability() {
            return this.probability;
        }

        public void setProbability(Float probability) {
            this.probability = probability;
        }
    }
}
