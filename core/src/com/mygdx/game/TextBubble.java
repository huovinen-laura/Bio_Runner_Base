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

    public TextBubble(String text, Vector2 position, Vector2 dimensions, BioRunnerGame game) {
        this.text = text;
        this.game = game;
        this.position = position;
        this.dimensions = dimensions;
        this.bubbleTexture = game.textureAssets.getBubble();
    }

    public void DrawBubble(SpriteBatch textureBatch) {
        textureBatch.draw(bubbleTexture,this.position.x,this.position.y,this.dimensions.x,this.dimensions.y);
    }

    public void DrawFont(SpriteBatch fontBatch, Vector3 projected) {
        Gdx.app.log("bubble","" +  (this.position.x/game.WORLD_WIDTH));

        game.getFont().draw(fontBatch, this.text, projected.x * (this.position.x/game.WORLD_WIDTH),
                projected.y * ((this.position.y+2)/game.WORLD_HEIGHT));
    }
}
