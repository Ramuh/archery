package de.ramuh.game.engine.event;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaEventListener extends EventListener<Event<?>> {

	LuaValue callback;
	
	public LuaEventListener(LuaValue callback, String eventType) {
		super(eventType);
		
		this.callback = callback;
	}

	@Override
	public void preProcess(Event<?> e) {
		// give to Lua etc.
		process(e);
	}

	@Override
	public void process(Event<?> e) {
		if(callback.isfunction())
		{
			LuaValue coerced = CoerceJavaToLua.coerce(e);
			
			LOG.debug("{}", coerced);
			callback.call(coerced);
		}else{
			LOG.debug("callback is not a function {}", e);
		}
	}
}
