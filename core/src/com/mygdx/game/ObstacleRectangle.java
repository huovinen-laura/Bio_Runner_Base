package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class ObstacleRectangle extends GameObject {
    public ObstacleRectangle(Texture texture) {
        super(texture);
    }

    @Override
    protected FixtureDef getFixtureDef() {
        return null;
    }

    @Override
    protected BodyDef getBodyDef() {
        return null;
    }

    @Override
    public void Draw(SpriteBatch batch) {

    }

    @Override
    public boolean Move() {
        return false;
    }

    @Override
    public String Collide() {
        return null;
    }
}
