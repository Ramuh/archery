package de.ramuh.game.engine.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.ramuh.game.engine.Engine;
import de.ramuh.game.engine.scene.Constants;
import de.ramuh.game.engine.systems.EventManager;

public class DebugInputHandler extends InputHandler {

	protected static final Logger LOG = LoggerFactory.getLogger(EventManager.class);
	
	@Override
	public boolean keyDown(int k) {
		// DEBUG CODE
		if (Constants.DEBUG) {
			LOG.debug("Keydown " + k);
			if (k == Input.Keys.F5) {
				LOG.debug("Resizing to 1080p");
				Engine.resizeWindow(1920, 1080);
			}

			if (k == Input.Keys.F6) {
				LOG.debug("Resizing to 720");
				Engine.resizeWindow(1280, 720);
			}

			if (k == Input.Keys.Q) {
				LOG.debug("Quit");
				Gdx.app.exit();
			}
		}
		return false;
	}
}
