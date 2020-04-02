package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.collectibles.CollectibleSquare;
import com.mygdx.game.obstacles.ObstacleRectangle;
import com.mygdx.game.screens.BallGame;

import java.util.ArrayList;

public class ObstacleCollection {
    private ArrayList<Obstacle> allObstacles;
    private int minimumAmountOfObstacles;
    private float timeBetweenObstacles;
    private float timeFromLastObstacle;
    private Vector2 LastObstaclePosition;
    private float minY;
    private float maxY;

    public ObstacleCollection() {
        this.allObstacles = new ArrayList<>();
        this.minimumAmountOfObstacles = 4;
        this.allObstacles.add( new Obstacle("kissa",BioRunnerGame.textureAssets.getKissa(),10,1f));
        this.allObstacles.add( new Obstacle("pilleri", BioRunnerGame.textureAssets.getPilleri(),91,0.5f));
        this.LastObstaclePosition = new Vector2(0f,0f);

        this.timeBetweenObstacles = 3f;
        this.timeFromLastObstacle = 0f;
        this.minY = 0.34f;
        this.maxY = 3f;
    }

    public void addStuff(GameObject object) {

        for(int i = 0; i < this.allObstacles.size(); i++) {

            if(this.allObstacles.get(i).getName().contentEquals(object.getName())) {
                this.allObstacles.get(i).incrementCount();
                break;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < this.allObstacles.size(); i++) {
            this.allObstacles.get(i).setCount(0);
        }
    }

    public ArrayList<Obstacle> getAllObstacles() {
        return allObstacles;
    }

    public void setAllObstacles(ArrayList<Obstacle> allObstacles) {
        this.allObstacles = allObstacles;
    }

    public ObstacleRectangle getRandomCollectible() {
        float roll = (float) Math.random()*100;
        roll++;
        float positionX = BallGame.WORLD_WIDTH;
        float positionY = this.minY + (this.maxY - this.minY-1)* ((float) Math.random());
        LastObstaclePosition.x = positionX;
        LastObstaclePosition.y = positionY;

        for (int i = 0; i < this.allObstacles.size(); i++) {

            if(this.allObstacles.get(i).probability >= roll) {
                return(new ObstacleRectangle(this.allObstacles.get(i).getTexture(),positionX,positionY,
                        this.allObstacles.get(i).getName(),this.allObstacles.get(i).getSize()));
            } else {
                roll-= this.allObstacles.get(i).probability;
            }
        }


        Gdx.app.log("ObsCol", "error roll: " + roll);
        return(new ObstacleRectangle(new Texture("badlogic.jpg"),
                positionX,positionY,"error",1f));
    }

    public boolean isNextCollectibleComing(int count) {
        if( count < this.minimumAmountOfObstacles) {

            if(this.timeFromLastObstacle >= this.timeBetweenObstacles) {
                this.timeFromLastObstacle = 0f;
                return(true);
            } else {
                this.timeFromLastObstacle += Gdx.graphics.getDeltaTime();
                return(false);
            }

        } else {
            return(false);
        }
    }

    public void setTimeBetweenObstacles(float time) {
        this.timeBetweenObstacles = time;
    }

    public static class Obstacle {
        private String name;
        private int count;
        private Texture texture;
        private Float probability;
        private float size;

        public Obstacle(String banaani, Texture texture, float probability) {
            this.name = banaani;
            this.texture = texture;
            this.probability = probability;
        }

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

        public Obstacle(String name, Texture newTexture, float prob,float size) {
            this.name = name;
            this.count = 0;
            this.texture = newTexture;
            this.probability = prob;
            this.size = size;
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

        public float getSize() {
            return size;
        }

        public void setSize(float size) {
            this.size = size;
        }
    }
}
