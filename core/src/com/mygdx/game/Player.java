package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends GameObject {
    public Player( World world) {
        super(new Texture("ball.png"),
                1.25f,2f, 2f,2000f,0f,0.5f,true,false);

    }





    @Override
    public boolean Move() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            if(this.getObjectBody().getPosition().y < 0.52f) {
                this.getObjectBody().applyLinearImpulse(
                        new Vector2(0, 5000f), this.getObjectBody().getWorldCenter(), true);
            } else {
                this.getObjectBody().applyLinearImpulse(
                        new Vector2(0, -10000f), this.getObjectBody().getWorldCenter(), true);
            }
        }
        return true;
    }

    @Override
    public String Collide() {
        return ("player");
    }
}
