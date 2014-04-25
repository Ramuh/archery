package de.ramuh.game.engine.scene;

public interface Scene {

	// called after push();
	public void init();
	
	// advance Scene for delta time 
	public void tick();
	
	// render objects in scene
	public void render();

	// destroy shit
	public void deinit();
	
	// touch stuff, will see if needed
	public boolean touchDown (int x, int y, int pointer, int button);

	public boolean fling(float velocityX, float velocityY, int button);

	public boolean pan(float x, float y, float deltaX, float deltaY);
	
	public boolean panStop(float x, float y, int pointer, int button);
	
}
