package de.ramuh.game.engine.lua;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LuaStateManager {

	/**
	 * Internal class to hide most of LSMs implementation from Lua
	 * @author rarem_000
	 *
	 */
	private class LuaStateManagerExports {
		private LuaStateManager lsm;
		
		public LuaStateManagerExports(LuaStateManager lsm) {
			this.lsm = lsm;
		}
		
		// Callable from Lua
		
		@SuppressWarnings("unused")
		public void printDebug(String debug) {
			lsm.printDebug(debug);
		}
		
		@SuppressWarnings("unused")
		public void executeString(String chunk) {
			lsm.executeString(chunk);
		}
		
		@SuppressWarnings("unused")
		public void executeFile(String path) {
			lsm.executeFile(path);
		}
	}
	
	private static LuaStateManager i;
	private static Globals globals;
		
	protected static final Logger LOG = LoggerFactory.getLogger(LuaStateManager.class);
	private ScriptEngineFactory scriptEngineFactory;
	private ScriptEngine scriptEngine;
	private LuaStateManagerExports lsmExports;
	
	public static LuaStateManager i() {
		if(i==null) {
			i = new LuaStateManager();
			init();
		}
		return i;
	}

	private static void init() {
		globals = JsePlatform.standardGlobals();
		
		ScriptEngineManager sem = new ScriptEngineManager();
        i.scriptEngine = sem.getEngineByName("luaj");
        i.scriptEngineFactory = i.scriptEngine.getFactory();

        LOG.debug( "Engine name: {}", i.scriptEngineFactory.getEngineName() );
        LOG.debug( "Engine Version: {}", i.scriptEngineFactory.getEngineVersion() );
        LOG.debug( "LanguageName: {}", i.scriptEngineFactory.getLanguageName() );
        LOG.debug( "Language Version: {}", i.scriptEngineFactory.getLanguageVersion() );
        
        i.lsmExports = i.new LuaStateManagerExports(i);
        
        // put empty binding
        Bindings sb = new SimpleBindings();
        
        i.scriptEngine.setBindings(sb, ScriptContext.ENGINE_SCOPE);
        i.registerBinding("LSM", i.lsmExports);
	}

	public void registerBinding(String key, Object value) {
		Bindings sb = i.scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE);
		sb.put(key, value);
		i.scriptEngine.setBindings(sb, ScriptContext.ENGINE_SCOPE);
	}
	
	public void executeFile(String path) {
		// load via Gdx and pass to executeString
		FileHandle handle = Gdx.files.internal(path);		
		executeString(handle.readString());
	}
		
	public void executeString(String chunk) {
		if(chunk.length() > 0)
		{
			try {
				scriptEngine.eval(chunk);				
			} catch ( ScriptException se ) {
            	LOG.warn("script exception thrown for buggy script.", se);
            }
		}
	}
		
	public void printDebug(String debug) {
		LOG.debug(debug);
	}
}
