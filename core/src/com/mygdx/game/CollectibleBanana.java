package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;


public class CollectibleBanana extends CollectibleSquare {
    static Texture  texture = new Texture("banaani.png");

    public CollectibleBanana(float x, float y) {
        super(x, y, texture, "banaani");
    }
}
