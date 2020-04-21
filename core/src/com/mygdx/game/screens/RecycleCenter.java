package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.GameObject;

public class RecycleCenter extends GameObject {
    public RecycleCenter(BioRunnerGame game, float size) {
        super(game, game.textureAssets.getLaitos(), size, 8f, 0.0f, 10f
                , 0f, 0f,new Vector2(0f,0f),0f);
    }

    @Override
    public boolean Move() {
        this.getObjectBody().setLinearVelocity(this.getGame().worldSpeed,0f);
        return false;
    }

    @Override
    public String Collide() {
        return null;
    }

}
