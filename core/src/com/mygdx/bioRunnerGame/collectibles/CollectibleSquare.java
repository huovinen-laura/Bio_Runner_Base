package com.mygdx.bioRunnerGame.collectibles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.bioRunnerGame.BioRunnerGame;
import com.mygdx.bioRunnerGame.GameObject;

/**
 * Stores data for collectibles
 *
 */
public class CollectibleSquare extends GameObject {
    Boolean setForDelete;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Creates collectible object
     *
     * @param world    game world where collectable will be bound
     * @param texture  collectable texture
     * @param size     width of the collectible
     * @param x        position
     * @param y        position
     * @param collName name
     */
    public CollectibleSquare(BioRunnerGame world, Texture texture, float size, float x, float y, String collName) {
        super(world, texture, size, x, y, 0f, 0f, 0f, new Vector2(world.getWorldSpeed(), 0f), 0f);
        this.setForDelete = false;
        this.name = collName;
    }

    /**
     * Draws the object
     *
     * @param x     position x
     * @param y     position y
     * @param batch
     */
    public void draw(float x, float y, SpriteBatch batch) {
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


    /**
     * Sets the object for delete
     */
    public void collect() {
        this.setForDelete = true;
    }


    /**
     * Is called every render, checks if the collectible needs to be destroyed
     *
     * @return false if disposed
     */
    @Override
    public boolean Move() {
        if (this.getObjectBody().getPosition().x <= (0 - this.spriteWidth)) {
            this.dispose();
            this.getObjectBody().getWorld().destroyBody(this.getObjectBody());
            return (false);
        } else if (this.setForDelete) {
            this.dispose();
            this.getObjectBody().getWorld().destroyBody(this.getObjectBody());
            return (false);
        }
        return (true);
    }
}


