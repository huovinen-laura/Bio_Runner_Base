package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TextBubble {
    private String text;
    private Texture bubbleTexture;
    private Vector2 position;
    private Vector2 dimensions;
    private BioRunnerGame game;
    private float margin;

    public TextBubble(String text, Vector2 position, Vector2 dimensions, BioRunnerGame game) {
        this.text = text;
        this.game = game;
        this.position = position;
        this.dimensions = dimensions;
        this.bubbleTexture = game.textureAssets.getBubble();
        this.margin = 0;
    }

    public void DrawBubble(SpriteBatch textureBatch) {
        textureBatch.draw(bubbleTexture,this.position.x,this.position.y,this.dimensions.x,this.dimensions.y);
    }

    public void DrawFont(SpriteBatch fontBatch, Vector3 projected) {

        Gdx.app.log("DrawFont",""+game.getBubbleFont());
        Gdx.app.log("DrawFont",""+this.text);
        Gdx.app.log("DrawFont",""+projected);
        Gdx.app.log("DrawFont",""+projected.y);
        game.getBubbleFont().draw(game.batch, this.text, projected.x * (this.position.x/game.WORLD_WIDTH),
                projected.y * ((this.position.y)/game.WORLD_HEIGHT),0, this.text.length(),
                (Gdx.graphics.getWidth()/800f) * (this.dimensions.x) * 100f,10,true);


    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }
}
