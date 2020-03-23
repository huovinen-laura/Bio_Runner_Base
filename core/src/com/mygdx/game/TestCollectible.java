package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class TestCollectible extends CollectibleSquare{
    static Texture texture = new Texture("banaani.png");

    public TestCollectible(float x, float y) {
        super(texture,1f,x,y,"banaani");
    }
}
