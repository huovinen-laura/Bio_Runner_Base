package com.mygdx.bioRunnerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Player object draws player model and handles controlling it.
 */
public class Player extends GameObject {

    /**
     * Tells if the screen just changed.
     */
    boolean justChangedScreen;
    private Animation walkAnimation;
    private TextureRegion currentFrameTexture;

    /**
     * The state time.
     */
    public float stateTime;

    /**
     * BioRunner game.
     */
    public BioRunnerGame game;
    private boolean isDefault;

    /**
     * Tells if the player is in the middle of a jump or not.
     */
    public static boolean isJumping = false;

    /**
     * Tells if the player is diving down.
     */
    public static boolean isDiving = false;

    /**
     * Constructs the player.
     *
     * @param animationTexture Animation textures for the player.
     * @param game BioRunner game.
     */
    public Player(Texture animationTexture, BioRunnerGame game) {
        super(game,true, game.getCurrentAnimation(),
                1.5f,1f, 0.6f,1000f,0f,1f);

        this.game = game;

        if(this.game.getSkinName().contentEquals("vakio")) {
            isDefault = true;
        } else {
            isDefault = false;
        }


        this.justChangedScreen = false;
        this.getObjectBody().setFixedRotation(true);
        walkAnimation = game.getTextureAssets().getSkinAssets().getAnimation(game.getSkinName());

        currentFrameTexture = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

    }

    /**
     * Draws the player with it's current frame.
     *
     * @param batch Batch for drawing the object.
     */
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


    /**
     * Resets the player model to the default position
     *
     */
    public void update() {
        this.justChangedScreen = true;
        this.getObjectBody().setTransform(1.5f,0.6f,0f);
        walkAnimation = game.getTextureAssets().getSkinAssets().getAnimation(game.getSkinName());

        currentFrameTexture = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
    }

    /**
     * Puts animation frames inside TextureRegion and returns them.
     *
     * @param tr TextureRegion.
     * @param cols Amount of columns in the textureRegion.
     * @param rows Amount of rows in the textureRegion.
     * @return Returns the animation frames.
     */
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

    /**
     * Moves the player animation accordingly
     *
     */
    public void moveAnimation() {
        stateTime += Gdx.graphics.getDeltaTime() * (1 +  0.50 * (-game.getWorldSpeed() - 1f));
        currentFrameTexture = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
    }


    /**
     * Reads user input
     *
     * @return false if game should end
     */
    @Override
    public boolean Move() {


        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            if(Player.isDiving && this.getObjectBody().getPosition().y < 2.0f) {
                if(!this.justChangedScreen) {
                    Player.isJumping = true;
                }
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

        if (game.getLifeCounter().getLivesAmount() <= 0 && this.getObjectBody().getPosition().y < 0.52f) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the player is on the ground or not
     *
     */
    public boolean isGrounded() {
        if (this.getObjectBody().getLinearVelocity().y == 0f && this.getObjectBody().getPosition().y < 0.53f) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the screen was just changed or not.
     *
     * @return Returns the boolean.
     */
    public boolean isJustChangedScreen() {
        return justChangedScreen;
    }

    /**
     * Sets the value for the boolean.
     *
     * @param justChangedScreen New value for the boolean.
     */
    public void setJustChangedScreen(boolean justChangedScreen) {
        this.justChangedScreen = justChangedScreen;
    }
}
