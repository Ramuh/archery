package de.ramuh.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.ramuh.game.archery.scenes.Testlevel;
import de.ramuh.game.engine.Engine;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "archery";
		cfg.useGL20 = true;
		cfg.width = 1280;
		cfg.height = 720;
		cfg.vSyncEnabled = false;
		
		new LwjglApplication(new Engine(new Testlevel()), cfg);
	}
}
