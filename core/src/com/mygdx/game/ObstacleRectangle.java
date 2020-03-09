package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class ObstacleRectangle extends GameObject {

    String name;
    public ObstacleRectangle(Texture texture, float x, float y) {
        super(texture,0.5f,x,y,0f,0f,0f);
        this.name = "Unknown";
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
