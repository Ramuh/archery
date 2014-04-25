package de.ramuh.game.engine.systems;

import java.util.ArrayList;
import java.util.List;

import de.ramuh.game.engine.entity.Entity;

public class EntityManager {

	private List<Entity> entities = new ArrayList<>();
	
	public EntityManager() {		
	}
	
	public void addEntity(Entity e) {
		if(!entities.contains(e))
			entities.add(e);
	}
	
	public void tick() {
		// remove expired entities
		for(Entity e : entities) {
			if(e.isRemoved())
				entities.remove(e);
		}
		// tick entities
		for(Entity e : entities) {
			e.tick();
		}
	}
	
	public void render() {
		for(Entity e : entities) {
			if(!e.isRemoved())
				e.render();
		}
	}
}
