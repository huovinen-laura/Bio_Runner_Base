package com.mygdx.game.collectibles;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.collectibles.CollectibleSquare;

public class TestCollectible extends CollectibleSquare {
    public static Texture texture = new Texture("banaani.png");

    public TestCollectible(float x, float y) {
        super(texture,1f,x,y,"banaani");
    }
}
