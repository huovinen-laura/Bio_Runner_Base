package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollectibleSquare extends GameObject {
    Float sideLength;
    Boolean setForDelete;
    static int collected;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
                //TODO

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

    public CollectibleSquare(float x, float y, Texture texture, String name) {
        super(texture,0.5f,x,y, 0f,0f,0f,
                new Vector2(BallGame.worldSpeed,0),0f);
        BallGame.world.setContactListener(new B2dContactListener());
        this.setForDelete = false;
        this.name = name;
    }

    static int getNumberOfCollected() {
        return(collected);
    }

    public void collideWithPlayer() {
        BallGame.collectedStuffList.addStuff(this.name);

    }

    public void collect() {
        this.setForDelete = true;
        collected++;
    }


    @Override
    public boolean Move() {
        if(this.getObjectBody().getPosition().x <= 0) {
            return(false);
        } else if (this.setForDelete) {
            return(false);
        }
    return(true);
    }

    @Override
    public String Collide() {
        return this.name;
    }
}
