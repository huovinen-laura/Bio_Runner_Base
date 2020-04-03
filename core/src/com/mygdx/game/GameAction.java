package com.mygdx.game;

import com.badlogic.gdx.Gdx;


/**
 * Does something to the BiorunnerGame object supplied when doAction is called enough times
 *
 */
public class GameAction {
    protected BioRunnerGame game;
    private float waitLeft;

    public GameAction(float wait, BioRunnerGame game) {
        this.waitLeft = wait;
        this.game = game;
    }

    public GameAction(BioRunnerGame game) {
        this.waitLeft = 0;
        this.game = game;
    }


     public void doAction() {
        if (this.waitLeft <= 0 ) {

        } else {
            waitLeft -= Gdx.graphics.getDeltaTime();
            return;
        }

    }
}
