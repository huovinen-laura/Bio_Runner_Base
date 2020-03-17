package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Button {
    protected Rectangle buttonRectangle;
    protected Vector2 position;
    protected Texture buttonTexture;
    protected float height;
    protected float width;

    public Button(float x,float y, float height, float width) {

        this.position = new Vector2(x,y);
        this.buttonTexture = new Texture("button.png");
        this.height = height;
        this.width = width;
    }

    public Button( float x,float y, float height, float width, Texture texture) {
        this(x,y,height,width);
        this.buttonTexture.dispose();
        this.buttonTexture = texture;
    }

    public boolean isInsideButton(float x, float y) {
        Gdx.app.log("","checking if inside");
        Gdx.app.log("x",""+ x);
        Gdx.app.log("y",""+ y);

        if((x >= this.position.x && x <= (this.position.x + width) )
                && ( y >= this.position.y && y <= (this.position.y + height))) {
            Gdx.app.log("button", "pressed");
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

}
