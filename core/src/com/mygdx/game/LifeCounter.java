package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LifeCounter {
    static int lives = 3;
    private float startX;
    private float startY;
    private float margin;
    private float size;
    private Texture life;

    public LifeCounter() {
        this.size = 0.5f;
        this.startX = 5f;
        this.startY = 3.25f;
        this.margin = 0.25f;
        life = new Texture(Gdx.files.internal("player2.png"));
    }

    public void draw(SpriteBatch batch) {
        if( LifeCounter.lives >= 1) {
            batch.draw(life,
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
                    life.getWidth(),
                    life.getHeight(),
                    true,
                    false);

        }
        if (LifeCounter.lives >= 2) {
            batch.draw(life,
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
                    life.getWidth(),
                    life.getHeight(),
                    true,
                    false);


        }
        if (LifeCounter.lives >= 3) {
            batch.draw(life,
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
                    life.getWidth(),
                    life.getHeight(),
                    true,
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
