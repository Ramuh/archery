package de.ramuh.game.engine.entity;

import com.badlogic.gdx.assets.AssetManager;

public abstract class Mob extends Entity {

	public Mob(AssetManager am) {
		super(am);
		// TODO Auto-generated constructor stub
	}

	// every mob has a position
	protected int x, y;
}
