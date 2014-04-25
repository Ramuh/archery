package de.ramuh.game.engine.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ramuh.game.engine.lua.LuaStateManager;
import de.ramuh.game.engine.lua.ScriptExports;
import de.ramuh.game.engine.systems.EventManager;

public class LuaStateManagerTest {
	
	private class Event1 extends Event<Event1> {
		
		public int foo = 42;
		
		public int getFoo() {
			return foo;
		}

		public void setFoo(int foo) {
			this.foo = foo;
		}

		public Event1() {
			super(Event1.class);
		}
		
		public String toString() {
			return "Event1[foo=" + foo+ ", " + super.toString() + "]";
		}
	}
	
	protected static final Logger LOG = LoggerFactory.getLogger(LuaStateManagerTest.class);
	
	public static void main(String[] args) {
		LuaStateManagerTest x = new LuaStateManagerTest();
		
		LuaStateManager.i().executeString("print 'hello world'");
		LuaStateManager.i().executeString("x = 5\nprint(x)\nreturn x");
		LuaStateManager.i().executeString("LSM:printDebug('Hello World')");
		
		LuaStateManager.i().executeString("code = \"LSM:printDebug(\'Hello World from executed Lua Code called from Lua Code\')\"\n"
		+ "LSM:executeString(code)");
		
		EventManager em = new EventManager();
		
		ScriptExports.Register(em);
		
		String registerEvent =
				"listener = function(event) \n"
				+ " event:setFoo(23)\n"
				+ " print(event)\n"
				+ " LSM:printDebug(event)\n"
				+ "end\n"
				
				+ "SE:registerEventListener('Event1', listener)\n";
		
		LuaStateManager.i().executeString(registerEvent);
		
		Event1 e1 = x.new Event1();
		
		em.queueEvent(e1);

		em.tick(EventManager.INFINITE_MS);
		LOG.debug("exit");
	}

}
