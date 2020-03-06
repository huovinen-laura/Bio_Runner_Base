package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends GameObject {
    float radius = 0.5f;

    public Player( World world) {
        super(new Texture("ball.png"));
        this.objectBody = world.createBody(this.getBodyDef());
        this.objectBody.createFixture(this.getFixtureDef());
        this.objectBody.setUserData(this);
    }

    @Override
    protected FixtureDef getFixtureDef() {
        FixtureDef playerFixtureDef = new FixtureDef();

        //Mass per square meter
        playerFixtureDef.density = 1000f;

        //How bouncy is the object? 0-1
        playerFixtureDef.restitution = 0.1f;

        //How slippery the object is? 0-1
        playerFixtureDef.friction = 0.5f;

        //Create circle shape
        PolygonShape rectangleShape = new PolygonShape();
        rectangleShape.setAsBox(1/2f,1/2f,new Vector2(0f,0f),0f);

        //Add shape to the fixture
        playerFixtureDef.shape = rectangleShape;
        return playerFixtureDef;
    }

    @Override
    protected BodyDef getBodyDef() {
        BodyDef myBodyDef = new BodyDef();

        //What type of body? This one moves.
        myBodyDef.type = BodyDef.BodyType.DynamicBody;

        //Body's position
        myBodyDef.position.set(BallGame.WORLD_WIDTH/2, BallGame.WORLD_HEIGHT/2);
        return myBodyDef;
    }

    @Override
    public void Draw(SpriteBatch batch) {
        batch.draw(this.getObjectTexture(),
                this.getObjectBody().getPosition().x - radius,
                this.getObjectBody().getPosition().y - radius,
                radius,
                radius,
                radius * 2,
                radius * 2,
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

    @Override
    public boolean Move() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            if(this.getObjectBody().getPosition().y < 1.1) {
                this.getObjectBody().applyLinearImpulse(
                        new Vector2(0, 5000f), this.getObjectBody().getWorldCenter(), true);
            } else {
                this.getObjectBody().applyLinearImpulse(
                        new Vector2(0, -10000f), this.getObjectBody().getWorldCenter(), true);
            }
        }
        return true;
    }

    @Override
    public String Collide() {
        return ("player");
    }

    public void dispose() {
        this.objectTexture.dispose();
    }
}
