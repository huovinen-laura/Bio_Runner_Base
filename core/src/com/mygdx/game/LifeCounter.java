package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class LifeCounter {
    static int lives = 3;
    private float startX;
    private float startY;
    private float margin;
    private float size;
    private Texture lifeTexture;

    public LifeCounter() {
        this.lifeTexture = new Texture( "ball.png");
        this.size = 0.5f;
        this.startX = 4f;
        this.startY = 3.25f;
        this.margin = 0.25f;
    }

    public void draw(SpriteBatch batch) {
        if( LifeCounter.lives >= 1) {
            batch.draw(this.lifeTexture,
                    this.startX,
                    this.startY,
                    0f,
                    0f,
                    this.size,
                    this.size,
                    1.0f,
                    1.0f,
                    0f,
                    0,
                    0,
                    this.lifeTexture.getWidth(),
                    this.lifeTexture.getHeight(),
                    false,
                    false);

        }
        if (LifeCounter.lives >= 2) {
            batch.draw(this.lifeTexture,
                    this.startX +this.size+ this.margin,
                    this.startY,
                    0f,
                    0f,
                    this.size,
                    this.size,
                    1.0f,
                    1.0f,
                    0f,
                    0,
                    0,
                    this.lifeTexture.getWidth(),
                    this.lifeTexture.getHeight(),
                    false,
                    false);


        }
        if (LifeCounter.lives >= 3) {
            batch.draw(this.lifeTexture,
                    this.startX + this.margin*2 + this.size*2,
                    this.startY,
                    0f,
                    0f,
                    this.size,
                    this.size,
                    1.0f,
                    1.0f,
                    0f,
                    0,
                    0,
                    this.lifeTexture.getWidth(),
                    this.lifeTexture.getHeight(),
                    false,
                    false);


        }
    }

    static void loseLife() {
        LifeCounter.lives--;
    }

    static void gainLife() {
        LifeCounter.lives++;
    }
}
