package com.mygdx.bioRunnerGame.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.bioRunnerGame.BioRunnerGame;
import com.mygdx.bioRunnerGame.GameObject;

/**
 * Stores obstacle data and draws it
 *
 */
public class ObstacleRectangle extends GameObject {

    private boolean setForDelete;

    /**
     * Initializes the obstacle
     *
     * @param game BioRunnerGame object
     * @param texture Texture for the obstacle
     * @param x position x
     * @param y position y
     * @param name String name of the obstacle
     * @param size size of the obstacle
     */
    public ObstacleRectangle(BioRunnerGame game, Texture texture, float x, float y, String name, float size) {
        super(game,texture,size,x,y, 0f,0f,0f,
                new Vector2(game.getWorldSpeed(),0),0f);
        this.setName(name);
        this.setForDelete = false;
    }

    /**
     * Sets the object for delete in next move iteration
     *
     */
    public void delete(){
        this.setForDelete = true;
    }

    /**
     * Checks if object is set for deleta
     *
     * @return true if setfordelete
     */
    public boolean isDeleted() {
        return(this.setForDelete);
    }

    /**
     * Should be called every render, checks if object should be destroyed
     *
     * @return
     */
    @Override
    public boolean Move() {

        if(this.getObjectBody().getPosition().x <= (0-this.spriteWidth)) {
            this.getObjectBody().getWorld().destroyBody(this.getObjectBody());
            this.dispose();

            return(false);
        } else if (this.setForDelete) {
            this.getObjectBody().getWorld().destroyBody(this.getObjectBody());
            this.dispose();

            return(false);
        }

        return(true);
    }
}
