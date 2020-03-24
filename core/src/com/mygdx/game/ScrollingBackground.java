package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.BallGame;

public class ScrollingBackground {
    Texture img;
    float x1, x2;
    float speed;
    float width;
    float height;

    public ScrollingBackground(float worldSpeed) {
        img = new Texture("bg.png");
        width = BallGame.WORLD_WIDTH;
        height = BallGame.WORLD_HEIGHT;
        x1 = 0;
        x2 = width;
        speed = worldSpeed;
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

        batch.draw(img, x1,0,width,height,0, 0, img.getWidth(), img.getHeight(), true, false);
        batch.draw(img, x2, 0, width, height);

        x1 += speed * deltaTime;
        x2 += speed * deltaTime;
    }

    public void setSpeed (int goalSpeed) {
        this.speed = goalSpeed;
    }
    public void dispose() {
        img.dispose();
    }
}
