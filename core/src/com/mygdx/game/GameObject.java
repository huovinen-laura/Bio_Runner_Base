package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class GameObject {
    protected Texture objectTexture;
    protected Body objectBody;
    protected Vector2 position;

    public GameObject( Texture texture) {
        this.objectTexture = texture;

    }

    protected abstract FixtureDef getFixtureDef();

    protected abstract BodyDef getBodyDef();

    public abstract void Draw(SpriteBatch batch);

    public abstract boolean Move();
    public abstract String Collide();

    public Texture getObjectTexture() {
        return objectTexture;
    }

    public void setObjectTexture(Texture objectTexture) {
        this.objectTexture = objectTexture;
    }

    public Body getObjectBody() {
        return objectBody;
    }

    public void dispose() {
        while(this.getObjectBody().getFixtureList().size > 0) {
            this.getObjectBody().destroyFixture(this.getObjectBody().getFixtureList().get(0));
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
