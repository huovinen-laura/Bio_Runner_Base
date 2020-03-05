package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public abstract class GameObjectAdapter extends GameObject {
    public GameObjectAdapter() {
        super(new Texture("badlogic.jpg"));
    }

    public GameObjectAdapter(Texture texture) {
        super(texture);
    }

    @Override
    protected FixtureDef getFixtureDef() {
        return null;
    }

    @Override
    protected BodyDef getBodyDef() {
        return null;
    }

    @Override
    public void Draw(SpriteBatch batch) {

    }

    @Override
    public boolean Move() {
        return false;
    }

    @Override
    public String Collide() {
        return null;
    }

    @Override
    public Texture getObjectTexture() {
        return super.getObjectTexture();
    }

    @Override
    public void setObjectTexture(Texture objectTexture) {
        super.setObjectTexture(objectTexture);
    }

    @Override
    public Body getObjectBody() {
        return super.getObjectBody();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public Vector2 getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Vector2 position) {
        super.setPosition(position);
    }
}