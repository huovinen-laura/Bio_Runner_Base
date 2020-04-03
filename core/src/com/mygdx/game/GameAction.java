package com.mygdx.game;

import com.badlogic.gdx.Gdx;


/**
 * Does something to the BiorunnerGame object supplied when doAction is called enough times
 *
 */
public class GameAction {
    protected BioRunnerGame game;
    private float waitLeft;
    private String name;

    public GameAction(float wait, BioRunnerGame game, String name) {
        this.waitLeft = wait;
        this.game = game;
        this.name = name;
    }

    public GameAction(BioRunnerGame game) {
        this.waitLeft = 0;
        this.game = game;
    }


     public boolean doAction() {
        if (this.waitLeft <= 0 ) {
            return true;
        } else {
            waitLeft -= Gdx.graphics.getDeltaTime();
            return false;
        }

    }
}
