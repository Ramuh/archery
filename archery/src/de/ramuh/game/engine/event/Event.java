package de.ramuh.game.engine.event;

public abstract class Event<T> {

	public final String eventType;
	
	public Event(Class<T> eventType) {
		this.eventType = eventType.getSimpleName();
	}
	
	public String getEventType() {
		return eventType;
	}
	
	@SuppressWarnings("unchecked")
	public T cast() {
		return (T)this;
	}
	
	@Override
	public String toString() {
		return "Event[Type=" + eventType + "]";
	}
}
