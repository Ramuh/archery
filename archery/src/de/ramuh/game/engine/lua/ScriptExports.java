package de.ramuh.game.engine.lua;

import org.luaj.vm2.LuaValue;

import de.ramuh.game.engine.event.LuaEventListener;
import de.ramuh.game.engine.systems.EventManager;

public class ScriptExports {

	private static ScriptExports instance;
	
	private EventManager em;
	
	public static void Register(EventManager em) {
		
		if(instance == null)
			instance = new ScriptExports(); 
		
		assert(em != null) : "EventManager is null; No EventManager was registered in ScriptExports";
		
		instance.em = em;
		
        LuaStateManager.i().registerBinding("SE", instance);
	}
	
	public void registerEventListener(String eventType, LuaValue callback) {
		LuaEventListener lel = new LuaEventListener(callback, eventType);

		em.addListener(lel);
	}
}
