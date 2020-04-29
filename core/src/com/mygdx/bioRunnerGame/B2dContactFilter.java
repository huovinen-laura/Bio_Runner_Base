package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Makes objects ignore collisions if not with player
 */
public class B2dContactFilter implements ContactFilter {

    /**
     * Checks if the objects should ignore the collision or not.
     *
     * @param fixtureA First object.
     * @param fixtureB Second object.
     * @return Returns the boolean that determines should the object ignore the collision.
     */
    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {

        if(fixtureA.getBody().getUserData() instanceof Player ||
                fixtureB.getBody().getUserData() instanceof Player) {
            return(true);
        }

        return false;
    }
}
