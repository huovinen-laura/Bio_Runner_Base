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

    public ScrollingBackground(float worldSpeed) {
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;
        x1 = 0;
        x2 = width;
        x3 = 0;
        x4 = width;
    }

    public void updateAndRender(float deltaTime, SpriteBatch batch) {
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

        // Draws the sky
        batch.draw(BioRunnerGame.textureAssets.getSky(), x1,0, width, height);
        batch.draw(BioRunnerGame.textureAssets.getSky(), x2, 0, width, height);

        // Draws the grass
        batch.draw(BioRunnerGame.textureAssets.getGrass(), x3,0, width, height);
        batch.draw(BioRunnerGame.textureAssets.getGrass(), x4, 0, width, height);

        // Speeds up the sky
        x1 += (BallGame.worldSpeed * deltaTime) * 0.5;
        x2 += (BallGame.worldSpeed * deltaTime) * 0.5;

        // Speeds up the grass
        x3 += (BallGame.worldSpeed * deltaTime) * 1f;
        x4 += (BallGame.worldSpeed * deltaTime) * 1f;
    }

    public void setSpeed (int goalSpeed) {
        BallGame.worldSpeed = goalSpeed;
    }
}
