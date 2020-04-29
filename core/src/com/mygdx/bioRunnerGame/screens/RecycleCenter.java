package com.mygdx.bioRunnerGame.screens;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.bioRunnerGame.BioRunnerGame;
import com.mygdx.bioRunnerGame.GameObject;

/**
 * Displays the recycle center
 */
public class RecycleCenter extends GameObject {
    public RecycleCenter(BioRunnerGame game, float size) {
        super(game, game.getTextureAssets().getLaitos(), size, 8f, 0.0f, 10f
                , 0f, 0f,new Vector2(0f,0f),0f);
    }

    /**
     * Moves the sprite at the speed of the world.
     *
     * @return
     */
    @Override
    public boolean Move() {
        this.getObjectBody().setLinearVelocity(this.getGame().getWorldSpeed(),0f);
        return false;
    }

}
