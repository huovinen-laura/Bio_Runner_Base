package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.collectibles.CollectibleSquare;
import com.mygdx.game.gamestate.LifeCounter;
import com.mygdx.game.obstacles.ObstacleRectangle;
import com.mygdx.game.screens.BallGame;

public class B2dContactListener implements ContactListener {
    Sound hurt = Gdx.audio.newSound(Gdx.files.internal("hurt.wav"));
    Sound collect = Gdx.audio.newSound(Gdx.files.internal("collect.wav"));

    @Override
    public void beginContact(Contact contact) {
        Object a = contact.getFixtureA().getBody().getUserData();
        Object b = contact.getFixtureB().getBody().getUserData();

        if ((a instanceof Player && b instanceof CollectibleSquare) ||
                (b instanceof Player && a instanceof CollectibleSquare)) {
            CollectibleSquare collectibleSquareB = (CollectibleSquare) b;
            collectibleSquareB.collect();
            BallGame.collectedStuffList.addStuff(collectibleSquareB);
            Gdx.app.log("DING", "" + BallGame.collectedStuffList.getAllShit().get(0).getCount()
                    + BallGame.collectedStuffList.getAllShit().get(0).getName());
            collect.play();
            BallGame.setPlayerScore();
            Gdx.app.log("Score:", "" + BallGame.playerScore);
        }

        if (a instanceof ObstacleRectangle && b instanceof Player) {
            ObstacleRectangle obstacle = (ObstacleRectangle) a;
            if(!obstacle.isDeleted()) {
                hurt.play();
                obstacle.delete();
                LifeCounter.loseLife();
            }
            //TODO

        } else if ( a instanceof Player && b instanceof ObstacleRectangle) {
            ObstacleRectangle obstacle = (ObstacleRectangle) b;
            if(!obstacle.isDeleted()) {
                hurt.play();
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