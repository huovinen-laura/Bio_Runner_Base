package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Sets text to a certain place.
 */
public class TextBubble {
    private String text;
    private Vector2 position;
    private Vector2 dimensions;
    private BioRunnerGame game;
    private float margin;
    private BitmapFont font;

    /**
     * Construct the bubble with a certain text.
     *
     * @param text Text in the bubble.
     * @param position Position of the bubble.
     * @param dimensions Dimensions of the bubble.
     * @param game BioRunner game.
     */
    public TextBubble(String text, Vector2 position, Vector2 dimensions, BioRunnerGame game) {
        this.text = text;
        this.game = game;
        this.position = position;
        this.dimensions = dimensions;
        this.margin = 0;
        this.font = game.getBubbleFont();
        this.font.getData().setScale(0.3f * Gdx.graphics.getWidth()/800f);
    }

    /**
     * Draws the text in the bubble.
     *
     * @param fontBatch The font for the text.
     * @param projected Vector3.
     */
    public void DrawFont(SpriteBatch fontBatch, Vector3 projected) {

        this.font.draw(game.getBatch(), this.text, projected.x * (this.position.x/ game.getWORLD_WIDTH()),
                projected.y * ((this.position.y)/ game.getWORLD_HEIGHT()),0, this.text.length(),
                (Gdx.graphics.getWidth()/800f) * (this.dimensions.x) * 100f,10,true);


    }

    /**
     * Returns the margin.
     *
     * @return Returns the margin.
     */
    public float getMargin() {
        return margin;
    }

    /**
     * Sets the margin.
     *
     * @param margin New margin.
     */
    public void setMargin(float margin) {
        this.margin = margin;
    }
}
