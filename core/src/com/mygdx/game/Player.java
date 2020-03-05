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
    }

    @Override
    protected FixtureDef getFixtureDef() {
        FixtureDef playerFixtureDef = new FixtureDef();

        //Mass per square meter
        playerFixtureDef.density = 1;

        //How bouncy is the object? 0-1
        playerFixtureDef.restitution = 0.1f;

        //How slippery the object is? 0-1
        playerFixtureDef.friction = 0.5f;

        //Create circle shape
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.radius);

        //Add shape to the fixture
        playerFixtureDef.shape = circleShape;
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
    public void Move() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.getObjectBody().applyForceToCenter(new Vector2(-5f, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            Gdx.app.log("asdf", "right");
            this.getObjectBody().applyForceToCenter(new Vector2(5f, 0), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.getObjectBody().applyLinearImpulse(new Vector2(0, 0.5f),
                    this.getObjectBody().getWorldCenter(), true);
        }
    }

    public void dispose() {
        this.objectTexture.dispose();
    }
}
