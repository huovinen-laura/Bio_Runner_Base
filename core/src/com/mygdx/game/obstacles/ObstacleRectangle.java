package com.mygdx.game.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.GameObject;

public class ObstacleRectangle extends GameObject {

    private boolean setForDelete;

    public ObstacleRectangle(BioRunnerGame game, Texture texture, float x, float y, String name, float size) {
        super(game,texture,size,x,y, 0f,0f,0f,
                new Vector2(game.getWorldSpeed(),0),0f);
        this.setName(name);
        this.setForDelete = false;
    }

    public void delete(){
        this.setForDelete = true;
    }

    public boolean isDeleted() {
        return(this.setForDelete);
    }

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

    @Override
    public String Collide() {
        return null;
    }
}
