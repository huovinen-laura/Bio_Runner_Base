package com.mygdx.game.gamestate;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.BioRunnerGame;



public class LifeCounter {
    private int lives = 3;
    private float startX;
    private float startY;
    private float margin;
    private float size;
    private Texture texture;
    private BioRunnerGame game;

    public LifeCounter(Texture lifeTexture, BioRunnerGame game) {
        this.texture = lifeTexture;
        this.game = game;
        this.size = 0.5f;
        this.startX = 5f;
        this.startY = 3.25f;
        this.margin = 0.25f;
        this.lives= 3;
    }

    public void setLives(int i) {
        this.lives = i;
    }

    public void draw(SpriteBatch batch) {
        if( this.lives >= 1) {
            batch.draw(this.texture,
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
                    this.texture.getWidth(),
                    this.texture.getHeight(),
                    true,
                    false);

        }
        if (this.lives >= 2) {
            batch.draw(this.texture,
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
                    this.texture.getWidth(),
                    this.texture.getHeight(),
                    true,
                    false);


        }
        if (this.lives >= 3) {
            batch.draw(this.texture,
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
                    this.texture.getWidth(),
                    this.texture.getHeight(),
                    true,
                    false);


        }
    }

    public void loseLife() {
        this.lives--;
    }

    public void gainLife() {
        this.lives++;
    }

    public int getLivesAmount() {
        return(this.lives);
    }
}
