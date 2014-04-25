package de.ramuh.game.engine.entity;

import com.badlogic.gdx.assets.AssetManager;

public abstract class Entity {

	private boolean removed = false;
	
	@SuppressWarnings("unused")
	private AssetManager am;
	
	public Entity(AssetManager am) {
		this.am = am;
	}
	
	// advance Scene for delta time 
	public abstract void tick();
	
	// render objects in scene
	public abstract void render();
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
}
