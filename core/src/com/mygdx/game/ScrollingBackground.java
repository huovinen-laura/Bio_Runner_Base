package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.BallGame;

public class ScrollingBackground {

    float x1, x2;
    float x3, x4;
    float width;
    float height;
    private BioRunnerGame game;

    public ScrollingBackground(float worldSpeed, BioRunnerGame game) {
        this.game = game;
        width = game.WORLD_WIDTH;
        height = game.WORLD_HEIGHT;
        x1 = 0;
        x2 = width;
        x3 = 0;
        x4 = width;
    }

    public void update(float deltaTime) {
        // Loops the sky
        if (x1 + width <= 0) {
            x1 = x2 + width;
            Gdx.app.log("","x1");
        }

        if (x2 + width <= 0) {
            x2 = x1 + width;
            Gdx.app.log("","x2");
        }

        // Loops the grass
        if (x3 + width <= 0) {
            x3 = x4 + width;
            Gdx.app.log("","grass 1");
        }

        if (x4 + width <= 0) {
            x4 = x3 + width;
            Gdx.app.log("","grass 2");
        }

        // Speeds up the sky
        x1 += (game.worldSpeed * deltaTime) * 0.5;
        x2 += (game.worldSpeed * deltaTime) * 0.5;

        // Speeds up the grass
        x3 += (game.worldSpeed * deltaTime) * 1f;
        x4 += (game.worldSpeed * deltaTime) * 1f;
    }

    public void drawGrass(SpriteBatch batch) {
        batch.draw(game.textureAssets.getGrass(), x3,0, width, height);
        batch.draw(game.textureAssets.getGrass(), x4, 0, width, height);
    }

    public void drawSky(SpriteBatch batch) {
        // Draws the sky
        batch.draw(game.textureAssets.getSky(), x1,0, width, height);
        batch.draw(game.textureAssets.getSky(), x2, 0, width, height);
    }

    public void setSpeed (int goalSpeed) {
        game.worldSpeed = goalSpeed;
    }
}
