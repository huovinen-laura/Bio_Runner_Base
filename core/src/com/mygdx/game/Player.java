package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.gamestate.LifeCounter;

public class Player extends GameObject {
    boolean justChangedScreen;
    private static Animation walkAnimation;
    public static Texture playerTexture;
    private static TextureRegion currentFrameTexture;
    public float stateTime;

    public Player( World world) {
        super(Player.currentFrameTexture, assetManager.playerChonkyAnimation,
                1.5f,1f, 2f,1000f,0f,1f);
        this.justChangedScreen = false;
        this.getObjectBody().setFixedRotation(true);
        createAnimation();

    }

    @Override
    public void Draw(SpriteBatch batch) {
        batch.draw(currentFrameTexture,
                this.getObjectBody().getPosition().x,
                this.getObjectBody().getPosition().y,
                0f,
                0f,
                this.spriteWidth,
                this.spriteHeight,
                1.0f,
                1.0f,
                this.getObjectBody().getTransform().getRotation() * MathUtils.radiansToDegrees
                );
    }

    private void createAnimation() {
        final int FRAME_COLS = 4;
        final int FRAME_ROWS = 1;

        int tileWidth = assetManager.playerChonkyAnimation.getWidth() / FRAME_COLS;
        int tileHeight = assetManager.playerChonkyAnimation.getHeight() / FRAME_ROWS;

        TextureRegion[][] tmp = TextureRegion.split(assetManager.playerChonkyAnimation, tileWidth, tileHeight);

        TextureRegion[] allFrames = toTextureArray(tmp, FRAME_COLS, FRAME_ROWS);

        walkAnimation = new Animation(4 / 60f, allFrames);

        currentFrameTexture = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
    }

    public TextureRegion[] toTextureArray(TextureRegion[][]tr, int cols, int rows) {
        TextureRegion[] frames = new TextureRegion[cols * rows];

        int index = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                frames[index++] = tr[i][j];
            }
        }

        return frames;
    }

    public void moveAnimation() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrameTexture = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
    }

    @Override
    public boolean Move() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            if(this.getObjectBody().getPosition().y < 0.52f && !this.justChangedScreen) {
                this.getObjectBody().applyLinearImpulse(
                        new Vector2(0, 5000f), this.getObjectBody().getWorldCenter(), true);
            } else {
                this.getObjectBody().applyLinearImpulse(
                        new Vector2(0, -10000f), this.getObjectBody().getWorldCenter(), true);
            }
        }

        if (LifeCounter.getLives() <= 0 && this.getObjectBody().getPosition().y < 0.52f) {
            return false;
        }
        return true;
    }

    public boolean isGrounded() {
        if (this.getObjectBody().getPosition().y < 0.52f) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String Collide() {
        return ("player");
    }

    public boolean isJustChangedScreen() {
        return justChangedScreen;
    }

    public void setJustChangedScreen(boolean justChangedScreen) {
        this.justChangedScreen = justChangedScreen;
    }
}
