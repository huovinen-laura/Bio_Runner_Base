package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class B2dContactFilter implements ContactFilter {

    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
        if(fixtureA.getBody().getUserData() instanceof Player ||
                fixtureB.getBody().getUserData() instanceof Player) {
            return(true);
        }
        return false;
    }
}
