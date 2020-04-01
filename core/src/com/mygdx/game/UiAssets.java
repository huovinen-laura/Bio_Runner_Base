package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class UiAssets {
    private Texture buttonBlue = new Texture("button_blank.png");
    private Texture logo = new Texture("LOGO.png");
    private Texture progressBar = new Texture("progressbar.png");

    public Texture getButtonBlue() {
        return buttonBlue;
    }

    public void setButtonBlue(Texture buttonBlue) {
        this.buttonBlue = buttonBlue;
    }

    public Texture getLogo() {
        return logo;
    }

    public void setLogo(Texture logo) {
        this.logo = logo;
    }
}
