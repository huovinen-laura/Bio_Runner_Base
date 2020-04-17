package com.mygdx.game;

import com.badlogic.gdx.Gdx;


/**
 *
 *
 */
public interface GameAction {
     public abstract void doAction();
     public abstract String getName();
     public abstract void undoAction();
     public abstract String getDescription();
}
