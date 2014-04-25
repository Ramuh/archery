package de.ramuh.game.engine.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ramuh.game.engine.systems.EventManager;

public class EventManagerTest {
	protected static final Logger LOG = LoggerFactory.getLogger(EventManagerTest.class);
	
	private class TestEvent<T> extends Event<T> {

		public TestEvent(Class<T> eventType) {
			super(eventType);
		}
		
	}
	
	private class Event1 extends TestEvent<Event1> {
		
		public Event1() {
			super(Event1.class);
		}		
	}
	
	private class Event2 extends TestEvent<Event2> {
		
		public Event2() {
			super(Event2.class);
		}		
	}
	
	private class EventListener1 extends EventListener<Event1> {

		public EventListener1() {
			super(Event1.class);
		}

		@Override
		public void process(Event1 e) {
			LOG.info("Received Event1 {}", e);
		}		
	}
	
	private class EventListener2 extends EventListener<Event2> {

		public EventListener2() {
			super(Event2.class);
		}

		@Override
		public void process(Event2 e) {
			LOG.info("Received Event2 {}", e);
		}
		
	}
	
	public static void main(String[] args) {
		EventManagerTest x = new EventManagerTest();
		
		EventManager em = new EventManager();
		
		EventListener1 l1 = x.new EventListener1();
		EventListener2 l2 = x.new EventListener2();
		
		em.addListener(l1);
		em.addListener(l2);
		
		Event1 e1 = x.new Event1();
		Event2 e2 = x.new Event2();
		
		em.queueEvent(e1);
		em.queueEvent(e2);
		
		em.tick(EventManager.INFINITE_MS);
	}
}
