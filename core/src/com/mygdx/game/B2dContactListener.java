package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class B2dContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if (a instanceof Player && b instanceof CollectibleSquare) {
            CollectibleSquare collectibleSquareB = (CollectibleSquare) b;
            collectibleSquareB.collect();
            BallGame.collectedStuffList.addStuff(collectibleSquareB.getName());
            Gdx.app.log("DING", "" + BallGame.collectedStuffList.getAllShit().get(0).getCount()
                    + BallGame.collectedStuffList.getAllShit().get(0).getName());
        } if (a instanceof CollectibleSquare && b instanceof CollectibleSquare) {
            CollectibleSquare collectibleSquareB = (CollectibleSquare) b;
            collectibleSquareB.collect();
            BallGame.collectedStuffList.addStuff(collectibleSquareB.getName());
            Gdx.app.log("DING", "" + BallGame.collectedStuffList.getAllShit().get(0).getCount()
                    + BallGame.collectedStuffList.getAllShit().get(0).getName());

            CollectibleSquare collectibleSquareA = (CollectibleSquare) a;
            collectibleSquareA.collect();
            BallGame.collectedStuffList.addStuff(collectibleSquareB.getName());
            Gdx.app.log("DING", "" + BallGame.collectedStuffList.getAllShit().get(0).getCount()
                    + BallGame.collectedStuffList.getAllShit().get(0).getName());
        }

        if (a instanceof ObstacleRectangle && b instanceof Player) {
            ObstacleRectangle obstacle = (ObstacleRectangle) a;
            if(!obstacle.isDeleted()) {
                obstacle.delete();
                LifeCounter.loseLife();
            }
            //TODO

        } else if ( a instanceof Player && b instanceof ObstacleRectangle) {
            ObstacleRectangle obstacle = (ObstacleRectangle) b;
            if(!obstacle.isDeleted()) {
                obstacle.delete();
                LifeCounter.loseLife();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
