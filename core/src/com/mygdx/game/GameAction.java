package com.mygdx.game;

import com.badlogic.gdx.Gdx;


/**
 * Does something to the BiorunnerGame object supplied called enough times
 *
 */
public interface GameAction {
     public abstract void doAction();
     public abstract String getName();
     public abstract void undoAction();
}
