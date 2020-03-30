package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.BallGame;

public class ScrollingBackground {
    Texture img;
    float x1, x2;
    float width;
    float height;

    public ScrollingBackground(float worldSpeed) {
        img = new Texture("tausta.png");
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;
        x1 = 0;
        x2 = width;
    }

    public void updateAndRender(float deltaTime, SpriteBatch batch) {
        //If image goes over screen borders, it loops back to the start
        if (x1 + width <= 0) {
            x1 = x2 + width;
            Gdx.app.log("","x1");
        }

        if (x2 + width <= 0) {
            x2 = x1 + width;
            Gdx.app.log("","x2");
        }

        batch.draw(img, x1,0, width, height);
        batch.draw(img, x2, 0, width, height);

        x1 += BallGame.worldSpeed * deltaTime;
        x2 += BallGame.worldSpeed * deltaTime;
    }

    public void setSpeed (int goalSpeed) {
        BallGame.worldSpeed = goalSpeed;
    }
    public void dispose() {
        img.dispose();
    }
}
