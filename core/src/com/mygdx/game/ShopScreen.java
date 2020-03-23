package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ShopScreen extends ScreenAdapter {
    BioRunnerGame game;
    public Stage stage;
    public Label outputLabel;

    public ShopScreen(BioRunnerGame game) {
        /*
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin mySkin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        Label title = new Label("Buttons with skins", mySkin, "big-black");
        title.setSize(Gdx.graphics.getWidth(), 2f);
        title.setPosition(0, Gdx.graphics.getHeight() - 2f);
        title.setAlignment(Align.center);
        stage.addActor(title);

        TextButton button1 = new TextButton("Text Button", mySkin, "small");
        button1.setSize(1f, 1f);
        button1.setPosition(2f, 2f);
        button1.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Press this button");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Pressed this button");
                return true;
            }
        });

        stage.addActor(button1);

        outputLabel = new Label("Press this button", mySkin, "black");
        outputLabel.setSize(Gdx.graphics.getWidth(), 2f);
        outputLabel.setPosition(0, 2f);
        outputLabel.setAlignment(Align.center);
        stage.addActor(outputLabel);
    }

    @Override
    public void show() {
        game.batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setGameScreen();
                    BallGame.clearScore();
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                game.setGameScreen();
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(100/255f, 197/255f, 165/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        */
    }
         
}
