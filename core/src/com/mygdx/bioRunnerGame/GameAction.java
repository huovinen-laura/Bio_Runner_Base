package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.graphics.Texture;


/**
 * Does something, with a name, button, description and possibility to undo
 *
 */
public interface GameAction {

     /**
      * Executes the power up.
      */
     public abstract void doAction();

     /**
      * Gets the name of the power up.
      *
      * @return Gets the name of the power up
      */
     public abstract String getName();

     /**
      * Undos the power up.
      */
     public abstract void undoAction();

     /**
      * Describes the power up.
      *
      * @return Returns the description of the power up.
      */
     public abstract String getDescription();

     /**
      * Returns the button for the power up.
      *
      * @return Returns the button for the power up.
      */
     public abstract Texture getButtonTexture();
}
