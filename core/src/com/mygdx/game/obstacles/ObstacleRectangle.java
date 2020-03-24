package com.mygdx.game.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameObject;
import com.mygdx.game.screens.BallGame;

public class ObstacleRectangle extends GameObject {

    String name;
    private boolean setForDelete;

    public ObstacleRectangle(Texture texture, float x, float y) {
        super(texture,0.5f,x,y, 0f,0f,0f,
                new Vector2(BallGame.worldSpeed,0),0f);
        this.name = "Unknown";
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
        if (this.setForDelete) {
            Gdx.app.log("OBSTACLe", "setFOrDelete");
            this.dispose();
            return(false);
        }
        return true;
    }

    @Override
    public String Collide() {
        return null;
    }
}
