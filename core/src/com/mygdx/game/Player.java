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
    private Animation walkAnimation;
    private TextureRegion currentFrameTexture;
    public float stateTime;
    public BioRunnerGame game;
    private boolean isDefault;

    public static boolean isJumping = false;
    public static boolean isDiving = false;

    public Player(Texture animationTexture, BioRunnerGame game) {
        super(game,true,game.getCurrentAnimation(),
                1.5f,1f, 2f,1000f,0f,1f);
        this.game = game;
        if(this.game.getSkinName().contentEquals("vakio")) {
            isDefault = true;
        } else {
            isDefault = false;
        }


        this.justChangedScreen = false;
        this.getObjectBody().setFixedRotation(true);
        walkAnimation = game.textureAssets.getSkinAssets().getAnimation(game.getSkinName());

        currentFrameTexture = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

    }

    @Override
    public void Draw(SpriteBatch batch) {

            currentFrameTexture.flip(true,false);
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
            currentFrameTexture.flip(true,false);

    }



    public void update() {
        walkAnimation = game.textureAssets.getSkinAssets().getAnimation(game.getSkinName());

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
        stateTime += Gdx.graphics.getDeltaTime() * (1 +  0.50 * (-game.worldSpeed - 1f));
        currentFrameTexture = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
    }

    @Override
    public boolean Move() {


        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            if(Player.isDiving && this.getObjectBody().getPosition().y < 2.0f) {
                Player.isJumping = true;
            } else if(this.getObjectBody().getPosition().y < 1.0f && !this.justChangedScreen) {
                if(Player.isDiving) {
                    Player.isJumping = true;
                } else if (this.objectBody.getLinearVelocity().y <= 0) {
                  Player.isJumping = true;
                }

            } else {
                Player.isDiving = true;
            }
        }

        if(this.isGrounded() && Player.isJumping) {
            this.getObjectBody().applyLinearImpulse(
                    new Vector2(0, 5000f), this.getObjectBody().getWorldCenter(), true);
            Player.isJumping = false;
            Player.isDiving = false;
        } else if(Player.isDiving) {
            this.getObjectBody().applyLinearImpulse(
                    new Vector2(0, -10000f), this.getObjectBody().getWorldCenter(), true);
            Player.isDiving = false;
        }

        if (game.lifeCounter.getLivesAmount() <= 0 && this.getObjectBody().getPosition().y < 0.52f) {
            return false;
        }
        return true;
    }

    public boolean isGrounded() {
        if (this.getObjectBody().getLinearVelocity().y == 0f && this.getObjectBody().getPosition().y < 0.53f) {
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
