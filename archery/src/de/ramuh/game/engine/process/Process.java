package de.ramuh.game.engine.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Process {

	public enum ProcessStates {
		UNINITIALIZED,
		REMOVED,
		
		RUNNING,
		PAUSED,
		
		SUCCEEDED,
		FAILED,
		ABORTED
	};

	private ProcessStates state;
	private Process childProcess;
	
	protected static final Logger LOG = LoggerFactory.getLogger(Process.class);
	
	public Process() {
		state = ProcessStates.UNINITIALIZED;
	}
	
	public ProcessStates getState() {
		return state;
	}

	public void initialize() {
		state = ProcessStates.RUNNING;
	}

	public abstract void tick(long deltaMs);

	public boolean isDead() {
		return state == ProcessStates.SUCCEEDED || state == ProcessStates.FAILED || state == ProcessStates.ABORTED;
	}

	public void success() {
		assert(state == ProcessStates.RUNNING || state == ProcessStates.PAUSED);
		state = ProcessStates.SUCCEEDED;
	}
	
	public void fail() {
		assert(state == ProcessStates.RUNNING || state == ProcessStates.PAUSED);
		state = ProcessStates.FAILED;
	}
	
	public void pause() {
		if(state == ProcessStates.RUNNING)
			state = ProcessStates.PAUSED;
		else
			LOG.warn("Pausing already paused process. {}", this);
	}
	
	public void unpause() {
		if(state == ProcessStates.PAUSED)
			state = ProcessStates.RUNNING;
		else
			LOG.warn("Pausing already paused process. {}", this);
	}
	
	public abstract void onSuccess();

	public abstract void onFail();

	public abstract void onAbort();

	public void attachChild(Process child) {
		this.childProcess = child;
	}
	
	public Process removeChild() {
		Process child = this.childProcess;
		this.childProcess = null;
		return child;
	}

}
