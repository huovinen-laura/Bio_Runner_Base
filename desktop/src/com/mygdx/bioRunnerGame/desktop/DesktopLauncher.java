package com.mygdx.bioRunnerGame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.bioRunnerGame.BioRunnerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 400;
		config.x = 100;
		config.y = 100;
		new LwjglApplication(new BioRunnerGame(), config);
	}
}
