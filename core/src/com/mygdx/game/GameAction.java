package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


/**
 *
 *
 */
public interface GameAction {
     public abstract void doAction();
     public abstract String getName();
     public abstract void undoAction();
     public abstract String getDescription();
     public abstract Texture getButtonTexture();
}
