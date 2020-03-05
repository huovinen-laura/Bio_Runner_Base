package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollectibleSquare extends GameObject {
    Float sideLength;
    static int collected;


    public CollectibleSquare(float x, float y, Texture texture, World world) {
        super(texture);
        this.sideLength = 0.5f;
        this.objectBody = world.createBody(this.getBodyDef(x,y));
        this.objectBody.createFixture(this.getFixtureDef());
    }

    static int getNumberOfCollected() {
        return(collected);
    }
    @Override
    protected FixtureDef getFixtureDef() {
        FixtureDef playerFixtureDef = new FixtureDef();

        //Mass per square meter
        playerFixtureDef.density = 0f;

        //How bouncy is the object? 0-1
        playerFixtureDef.restitution = 0.1f;

        //How slippery the object is? 0-1
        playerFixtureDef.friction = 0.5f;

        //Create Rectangular Shape
        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.setAsBox(this.sideLength/2,this.sideLength/2,new Vector2(0f,0f),0f);
        //Add shape to the fixture
        playerFixtureDef.shape = rectangleShape;
        return playerFixtureDef;
    }

    @Override
    protected BodyDef getBodyDef() {
        return null;
    }


    protected BodyDef getBodyDef(float x, float y) {
        BodyDef myBodyDef = new BodyDef();

        //What type of body? This one moves.
        myBodyDef.type = BodyDef.BodyType.DynamicBody;

        //Body's position
        myBodyDef.position.set(x,y);
        myBodyDef.linearVelocity.set(BallGame.worldSpeed,0);
        myBodyDef.gravityScale=0;

        return myBodyDef;
    }

    @Override
    public void Draw(SpriteBatch batch) {
        batch.draw(this.getObjectTexture(),
                this.getObjectBody().getPosition().x -sideLength/2,
                this.getObjectBody().getPosition().y -sideLength/2,
                0,
                0,
                sideLength,
                sideLength,
                1.0f,
                1.0f,
                this.getObjectBody().getTransform().getRotation() * MathUtils.radiansToDegrees,
                0,
                0,
                this.objectTexture.getWidth(),
                this.objectTexture.getHeight(),
                false,
                false);
    }

    public void dispose() {
        while(this.getObjectBody().getFixtureList().size > 0) {
            this.getObjectBody().destroyFixture(this.getObjectBody().getFixtureList().get(0));
        }
    }

    @Override
    public boolean Move() {
        Gdx.app.log("move","" + this.getObjectBody().getPosition().x);
        if(this.getObjectBody().getPosition().x <= 0) {
            return(false);
        }
    return(true);
    }
}
