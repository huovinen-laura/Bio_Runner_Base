package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Button {
    protected Rectangle buttonRectangle;
    protected Vector2 position;
    protected Texture buttonTexture;
    protected float height;
    protected float width;

    public Button(float x,float y, float height, float width, Texture texture) {

        this.position = new Vector2(x,y);
        this.buttonTexture = texture;
        this.height = height;
        this.width = width;
    }



    public boolean isInsideButton(float x, float y) {

        if((x >= this.position.x && x <= (this.position.x + width) )
                && ( y >= this.position.y && y <= (this.position.y + height))) {
            return(true);
        }

        return(false);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.buttonTexture,
                this.position.x,
                this.position.y,
                this.width,
                this.height);
    }

    public void draw(SpriteBatch batch,TextureRegion animationTexture) {

        batch.draw(this.buttonTexture,
                this.position.x,
                this.position.y,
                this.width,
                this.height);

        batch.draw(animationTexture,
                this.position.x,
                this.position.y,
                this.width,
                this.height);

    }

}
