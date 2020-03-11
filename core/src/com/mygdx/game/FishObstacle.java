package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class FishObstacle extends ObstacleRectangle {
    static Texture texture = new Texture("obstacle.png");

    public FishObstacle( float x, float y) {
        super(FishObstacle.texture, x, y);
    }
}
