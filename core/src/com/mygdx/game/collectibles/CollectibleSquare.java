package com.mygdx.game.collectibles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.BioRunnerGame;
import com.mygdx.game.GameObject;
import com.mygdx.game.screens.BallGame;

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

    public CollectibleSquare(BioRunnerGame world, Texture texture, float size, float x, float y, String collName) {
        super(world,texture,size,x,y,0f,0f,0f,new Vector2(world.worldSpeed,0f),0f);
        this.setForDelete = false;
        this.name = collName;
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
        if(this.getObjectBody().getPosition().x <= (0-this.spriteWidth)) {
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
