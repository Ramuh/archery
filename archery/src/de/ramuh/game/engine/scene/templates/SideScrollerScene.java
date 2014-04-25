package de.ramuh.game.engine.scene.templates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import de.ramuh.game.engine.scene.Scene;

public class SideScrollerScene implements Scene {

	protected String mapFileName;	
	protected TiledMap map;
	protected MapRenderer mapRenderer;
	protected OrthographicCamera camera;
	
	protected float tx = 0, ty = 0;
	
	@Override
	public void init() {
		TmxMapLoader loader = new TmxMapLoader();
		map = loader.load(mapFileName);
		float unitScale = 1 / 32f;
		mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		
		camera.translate(new Vector2(0, 79));
		
		camera.update();
		
		mapRenderer.setView(camera);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		mapRenderer.setView(camera);
		mapRenderer.render();
	}

	@Override
	public void deinit() {
		// TODO Auto-generated method stub
		
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
		//camera.translate(new Vector2(-deltaX*0.1f, deltaY*0.1f));
		//camera.update();
		
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

}
