package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Displays the scrolling sky and grass.
 */
public class ScrollingBackground {

    /**
     * X coordinates for the sky texture.
     */
    float x1, x2;

    /**
     * X coordinates for the grass texture.
     */
    float x3, x4;

    /**
     * Width of the world.
     */
    float width;

    /**
     * Height of the world.
     */
    float height;
    private BioRunnerGame game;

    /**
     * Constructs the scrolling background.
     *
     * @param worldSpeed Speed of the game.
     * @param game BioRunner game.
     */
    public ScrollingBackground(float worldSpeed, BioRunnerGame game) {
        this.game = game;
        width = game.getWORLD_WIDTH();
        height = game.getWORLD_HEIGHT();
        x1 = 0;
        x2 = width;
        x3 = 0;
        x4 = width;
    }

    /**
     * Updates the positions of the sky and grass textures.
     */
    public void update(float deltaTime) {
        // Loops the sky
        if (x1 + width <= 0) {
            x1 = x2 + width;
        }

        if (x2 + width <= 0) {
            x2 = x1 + width;
        }

        // Loops the grass
        if (x3 + width <= 0) {
            x3 = x4 + width;
        }

        if (x4 + width <= 0) {
            x4 = x3 + width;
        }

        // Speeds up the sky
        x1 += (game.getWorldSpeed() * deltaTime) * 0.5;
        x2 += (game.getWorldSpeed() * deltaTime) * 0.5;

        // Speeds up the grass
        x3 += (game.getWorldSpeed() * deltaTime) * 1f;
        x4 += (game.getWorldSpeed() * deltaTime) * 1f;
    }

    /**
     * Draws the grass texture.
     */
    public void drawGrass(SpriteBatch batch) {
        batch.draw(game.getTextureAssets().getGrass(), x3,0, width, height);
        batch.draw(game.getTextureAssets().getGrass(), x4, 0, width, height);
    }

    /**
     * Draws the sky texture.
     */
    public void drawSky(SpriteBatch batch) {
        // Draws the sky
        batch.draw(game.getTextureAssets().getSky(), x1,0, width, height);
        batch.draw(game.getTextureAssets().getSky(), x2, 0, width, height);
    }

    /**
     * Sets the speed.
     *
     * @param goalSpeed New speed.
     */
    public void setSpeed (int goalSpeed) {
        game.setWorldSpeed(goalSpeed);
    }
}