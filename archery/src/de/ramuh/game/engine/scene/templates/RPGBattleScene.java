package de.ramuh.game.engine.scene.templates;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import de.ramuh.game.engine.Engine;
import de.ramuh.game.engine.systems.EntityManager;

public class RPGBattleScene extends SideScrollerScene {

	protected String menuFileName;
	protected TiledMap menu;
	protected MapRenderer menuRenderer;
	protected OrthographicCamera menucamera;

	protected float tx = 0, ty = 0;

	protected EntityManager em;
	protected AssetManager am;
	
	@Override
	public void init() {
		em = new EntityManager();
		am = new AssetManager();

		am.load("data/art/tilesets/rpg1.png", Texture.class);
		
		TmxMapLoader loader = new TmxMapLoader();
		
		menu = loader.load(menuFileName);
		float unitScale = 1 / 8f;
		menuRenderer = new OrthogonalTiledMapRenderer(menu, unitScale);

		menucamera = new OrthographicCamera();
		menucamera.setToOrtho(false, 40, 24);

		menucamera.update();

		menuRenderer.setView(menucamera);

		super.init();
	}

	@Override
	public void tick() {
		super.tick();
		em.tick();
	}

	@Override
	public void render() {
		super.render();

		menuRenderer.setView(menucamera);
		menuRenderer.render();

		// draw font crap

		int x = 3;
		int y = 15;
		Engine.font.renderTileAdjusted("Attack", x, y++);
		Engine.font.renderTileAdjusted("Magic", x, y++);
		Engine.font.renderTileAdjusted("Item", x, y++);
		
		em.render();
	}
}
