package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class TestCollectible extends CollectibleSquare{
    static Texture texture = new Texture("banaani.png");

    public TestCollectible(float x, float y) {
        super(x, y, texture, "banaani");
    }
}
