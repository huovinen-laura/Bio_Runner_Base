package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.obstacles.ObstacleRectangle;

public class FishObstacle extends ObstacleRectangle {
    static Texture texture = new Texture("pilleri.png");

    public FishObstacle( float x, float y) {
        super(FishObstacle.texture, x, y);
    }
}
