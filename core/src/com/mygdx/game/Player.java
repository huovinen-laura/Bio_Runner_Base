package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends GameObject {
    boolean justChangedScreen;
    public Player( World world) {
        super(new Texture("ball.png"),
                1.25f,2f, 2f,2000f,0f,0.5f,true,false);
        this.justChangedScreen = false;

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

        if (LifeCounter.lives <= 0 && this.getObjectBody().getPosition().y < 0.52f) {
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
