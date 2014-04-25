package de.ramuh.game.engine.scene.example;

import de.ramuh.game.engine.scene.Scene;

public class TestScene implements Scene {

	@Override
	public void init() {
		// nothing to do

	}

	@Override
	public void tick() {
	}

	@Override
	public void render() {
		// no render, boring
	}

	@Override
	public void deinit() {
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

}
