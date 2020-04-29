package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.bioRunnerGame.obstacles.ObstacleRectangle;

import java.util.ArrayList;

/**
 * Counts the number of obstacles hit and types of possible obstacles
 *
 */
public class ObstacleCollection {
    private ArrayList<Obstacle> allObstacles;
    private int minimumAmountOfObstacles;
    private float timeBetweenObstacles;
    private float timeFromLastObstacle;
    private Vector2 LastObstaclePosition;
    private float minY;
    private float maxY;
    private BioRunnerGame game;

    /**
     * Contructs the obstacles collection.
     *
     * @param game
     */
    public ObstacleCollection(BioRunnerGame game) {
        this.game = game;
        this.allObstacles = new ArrayList<>();
        this.minimumAmountOfObstacles = 4;
        this.allObstacles.add(new Obstacle("pullo", game.getTextureAssets().getPullo(),15,1f));
        this.allObstacles.add(new Obstacle("purkki", game.getTextureAssets().getPurkki(),15,1f));
        this.allObstacles.add(new Obstacle("pussi", game.getTextureAssets().getPussi(),15,1f));
        this.allObstacles.add( new Obstacle("pilleri", game.getTextureAssets().getPilleri(),15,1.2f));
        this.allObstacles.add( new Obstacle("pallo", game.getTextureAssets().getPallo(),10,1.2f));
        this.allObstacles.add(new Obstacle("suklaa", game.getTextureAssets().getSuklaa(),10,1f));
        this.allObstacles.add(new Obstacle("tupakka", game.getTextureAssets().getTupakka(),10,1.5f));
        this.allObstacles.add( new Obstacle("patteri", game.getTextureAssets().getPatteri(),10,1.5f));

        this.LastObstaclePosition = new Vector2(0f,0f);

        this.timeBetweenObstacles = 3f;
        this.timeFromLastObstacle = 0f;
        this.minY = 0.34f;
        this.maxY = 3f;
    }

    /**
     * Increases the count of given object or creates new entry if unknown
     *
     * @param object object that's count should be incremented
     */
    public void addStuff(GameObject object) {

        for(int i = 0; i < this.allObstacles.size(); i++) {

            if(this.allObstacles.get(i).getName().contentEquals(object.getName())) {
                this.allObstacles.get(i).incrementCount();
                break;

            }
        }
    }

    /**
     * Clears the list.
     */
    public void clear() {
        for (int i = 0; i < this.allObstacles.size(); i++) {
            this.allObstacles.get(i).setCount(0);
        }
    }

    /**
     * Returns all the obstacles.
     *
     * @return Returns all the obsctacles.
     */
    public ArrayList<Obstacle> getAllObstacles() {
        return allObstacles;
    }

    public void setAllObstacles(ArrayList<Obstacle> allObstacles) {
        this.allObstacles = allObstacles;
    }

    /**
     * Gives random obstacle
     *
     * @param max upper bound on the type of obstacle
     * @return random obstacle from the list
     */
    public ObstacleRectangle getRandomCollectible(int max) {
        float roll = (float) Math.random()*100;
        roll++;
        float positionX = game.getWORLD_WIDTH();
        float positionY = this.minY + (this.maxY - this.minY-1)* ((float) Math.random());
        LastObstaclePosition.x = positionX;
        LastObstaclePosition.y = positionY;
        while(positionY >= game.getLastCollectable().y-1 && positionY <= game.getLastCollectable().y+1 ) {
            positionY = this.minY + (this.maxY - this.minY)* ((float) Math.random());
        }
        game.setLastCollectable(new Vector2(positionX,positionY));

        int border = max;
        if (max > this.allObstacles.size()) {
            border = this.allObstacles.size();
        }

        while(true) {
            for (int i = 0; i < border; i++) {

                if (this.allObstacles.get(i).probability >= roll) {
                    return (new ObstacleRectangle(game, this.allObstacles.get(i).getTexture(), positionX, positionY,
                            this.allObstacles.get(i).getName(), this.allObstacles.get(i).getSize()));
                } else {
                    roll -= this.allObstacles.get(i).probability;
                }
            }
        }
    }

    /**
     * Tells if next obstacle should be spawned
     *
     * @param count amount of obstacles in game
     * @return true if obstacle should be spawned
     */
    public boolean isNextCollectibleComing(int count) {
        if( count < this.minimumAmountOfObstacles) {

            if(this.timeFromLastObstacle * 1 - 0.25 * (1 + game.getWorldSpeed()) * this.timeFromLastObstacle >=
                    this.timeBetweenObstacles) {
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

    /**
     * Sets the time between obstacles.
     *
     * @param time New time between obstacles.
     */
    public void setTimeBetweenObstacles(float time) {
        this.timeBetweenObstacles = time;
    }

    /**
     * Creates the obstacles.
     */
    public static class Obstacle {
        private String name;
        private int count;
        private Texture texture;
        private Float probability;
        private float size;

        /**
         * Creates an obstacle.
         *
         * @param banaani Name of the obstacle.
         * @param texture Texture of the obstacle.
         * @param probability Probability of the obstacle.
         */
        public Obstacle(String banaani, Texture texture, float probability) {
            this.name = banaani;
            this.texture = texture;
            this.probability = probability;
        }

        /**
         * Returns the name of the obstacle.
         *
         * @return Returns the name of the obstacle.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets a new name for the obstacle.
         *
         * @param name New name for the obstacle.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Returns the count of obstacle.
         *
         * @return The count of obstacle.
         */
        public int getCount() {
            return count;
        }

        /**
         * Increses the count.
         */
        public void incrementCount() {
            this.count++;
        }

        /**
         * Sets a new count.
         *
         * @param count New count.
         */
        public void setCount(int count) {
            this.count = count;
        }

        /**
         * Creates an obstacle and determines its size.
         *
         * @param name Name of the obstacle.
         * @param newTexture Texture for the obstacle.
         * @param prob Probability of the obstacle.
         * @param size Size of the obstacle.
         */
        public Obstacle(String name, Texture newTexture, float prob,float size) {
            this.name = name;
            this.count = 0;
            this.texture = newTexture;
            this.probability = prob;
            this.size = size;
        }

        /**
         * Returns obstacle's texture.
         *
         * @return Returns obstacle's texture.
         */
        public Texture getTexture() {
            return texture;
        }

        /**
         * Sets a texture for the obstacle.
         *
         * @param texture New texture.
         */
        public void setTexture(Texture texture) {
            this.texture = texture;
        }

        /**
         * Returns the probability of the obstacle.
         *
         * @return Returns the probability of the obstacle.
         */
        public Float getProbability() {
            return this.probability;
        }

        /**
         * Sets a new probability.
         *
         * @param probability New probability.
         */
        public void setProbability(Float probability) {
            this.probability = probability;
        }

        /**
         * Returns the size of the obstacle.
         *
         * @return Returns the size of the obstacle.
         */
        public float getSize() {
            return size;
        }

        /**
         * Sets a new size for the obstacle.
         *
         * @param size New size for the obstacle.
         */
        public void setSize(float size) {
            this.size = size;
        }
    }
}
