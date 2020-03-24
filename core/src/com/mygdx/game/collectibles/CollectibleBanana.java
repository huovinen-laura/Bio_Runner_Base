package com.mygdx.game.collectibles;

import com.badlogic.gdx.graphics.Texture;


public class CollectibleBanana extends CollectibleSquare {
    static Texture  texture = new Texture("banaani.png");

    public CollectibleBanana(float x, float y) {
        super(texture,1f,x,y,"banaani");
    }
}
