package de.ramuh.game.engine.systems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemManager {

	public enum Priority {
		HIGH, MEDIUM, LOW,
	};

	private Map<Priority, List<ISystem>> systems;

	public SystemManager() {
		systems = new HashMap<>();
	}

	public void addSystem(ISystem system, Priority priority) {
		systems.get(priority).add(system);
		system.start();
	}
	
	public void update(double delta) {
		for(Priority p : Priority.values())
		{
			for(ISystem system : systems.get(p)) {
				system.update(delta);
			}
		}
	}
	
	public void removeSystem(ISystem system) {
		for(Priority p : Priority.values())
		{
			system.stop();
			systems.get(p).remove(system);
		}
	}
}
