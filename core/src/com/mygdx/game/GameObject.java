package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public abstract class GameObject {
    protected Texture objectTexture;
    protected Body objectBody;

    public GameObject( Texture texture) {
        this.objectTexture = texture;

    }

    protected abstract FixtureDef getFixtureDef();

    protected abstract BodyDef getBodyDef();

    public abstract void Draw(SpriteBatch batch);

    public abstract void Move();

    public Texture getObjectTexture() {
        return objectTexture;
    }

    public void setObjectTexture(Texture objectTexture) {
        this.objectTexture = objectTexture;
    }

    public Body getObjectBody() {
        return objectBody;
    }


}
