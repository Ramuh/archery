package de.ramuh.game.engine.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EventListener<T> {

	public final String eventType;
	protected static final Logger LOG = LoggerFactory.getLogger(EventListener.class);
	
	public EventListener(Class<T> eventType) {
		this.eventType = eventType.getSimpleName();
	}

	// for lua event listeners calls
	protected EventListener(String eventType) {
		this.eventType = eventType;
	}
	
	public void preProcess(Event<?> e) {
		@SuppressWarnings("unchecked")
		T e2 = (T) e.cast();
		process(e2);
	}
	
	public abstract void process(T e);
}
