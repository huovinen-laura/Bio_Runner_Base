package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollectibleSquare extends GameObject {
    Boolean setForDelete;
    static int collected;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CollectibleSquare(float x, float y, Texture texture, String name) {
        super(texture,0.5f,x,y, 0f,0f,0f,
                new Vector2(BallGame.worldSpeed,0),0f);
        this.setForDelete = false;
        this.name = name;
    }


    public void draw(float x, float y,SpriteBatch batch) {
        batch.draw(this.getObjectTexture(),
                x,
                y,
                0f,
                0f,
                this.spriteWidth,
                this.spriteHeight,
                1.0f,
                1.0f,
                this.getObjectBody().getTransform().getRotation() * MathUtils.radiansToDegrees,
                0,
                0,
                this.objectTexture.getWidth(),
                this.objectTexture.getHeight(),
                this.flipSpriteX,
                this.flipSpriteY);
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
