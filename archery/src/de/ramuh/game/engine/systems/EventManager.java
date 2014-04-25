package de.ramuh.game.engine.systems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;

import de.ramuh.game.engine.event.Event;
import de.ramuh.game.engine.event.EventListener;

public class EventManager {

	private static final int NUM_QUEUES = 2;
	protected static final Logger LOG = LoggerFactory.getLogger(EventManager.class);

	public static final long INFINITE_MS = Long.MAX_VALUE;
	
	private ConcurrentLinkedQueue<Event<?>> realtimeQueue;
	
	private ArrayListMultimap<String, EventListener<?>> eventListenerMap;
	private ArrayList<LinkedList<Event<?>>> eventQueues;
	
	private int activeQueue;
	
	
	public EventManager() {		
		eventListenerMap = ArrayListMultimap.create();
		
		eventQueues = new ArrayList<>();
		
		realtimeQueue = new ConcurrentLinkedQueue<>();
		for(int i = 0; i < NUM_QUEUES; i++)
			eventQueues.add(new LinkedList<Event<?>>());
	}
	
	public boolean addListener(EventListener<?> l) {

		if(!eventListenerMap.put(l.eventType, l))
			return false;
		return true;
	}
	
	public boolean deregisterListener(EventListener<?> l) {
		if(eventListenerMap.remove(l.eventType, l))
			return false;
		return true;
	}
	
	public boolean triggerEvent(Event<?> e) {
		Collection<EventListener<?>> listeners = eventListenerMap.get(e.getEventType());
		if(listeners.size() == 0)
			return false;
		
		for(EventListener<?> l : listeners) {
			l.preProcess(e);
		}
		
		return true;
	}
	
	public boolean queueEvent(Event<?> e) {
		Collection<EventListener<?>> listeners = eventListenerMap.get(e.eventType);
		if(listeners.size() == 0)
		{
			LOG.debug("No listener for event {} registered", e);
			return false;
		}
		eventQueues.get(activeQueue).add(e);
		
		return true;
	}
	
	public boolean tick(long maxMillis) {
		
		long currMillis = System.currentTimeMillis();
		long maxMs = (maxMillis == INFINITE_MS) ? INFINITE_MS : currMillis + maxMillis;
		
		// process realtime events
		while(!realtimeQueue.isEmpty()) {
			Event<?> e = realtimeQueue.poll();
			if(e != null) {
				queueEvent(e);
			}
			if(maxMillis != INFINITE_MS) {
				currMillis = System.currentTimeMillis();
				if(currMillis > maxMs) {
					LOG.error("Realtime process is spamming event manager.");
					System.exit(-1);
				}
			}
		}
		
		int queueToProcess = activeQueue;
		activeQueue = (++activeQueue)%NUM_QUEUES;
		eventQueues.get(activeQueue).clear();
		
		List<Event<?>> q = eventQueues.get(queueToProcess);
		
		while(!q.isEmpty()) {
			Event<?> e = q.remove(0);
			eventQueues.remove(e);
			
			LOG.debug("EventLoop\t\tProcessing Event {}", e);
			
			Collection<EventListener<?>> listeners = eventListenerMap.get(e.eventType);
			if(listeners.size() > 0) { 
				LOG.debug("EventLoop\t\tFound {} listeners", listeners.size());
				for(EventListener<?> l : listeners) {
					LOG.debug("EventLoop\t\tProcessing Event {} in listener", e);
					l.preProcess(e);
				}
			}
			currMillis = System.currentTimeMillis();
			if(currMillis > maxMs) {
				LOG.error("Processing time ran out. Aborting Event processing.");
				break;
			}
		}
		
		if(!eventQueues.isEmpty()) {
			
			while(!q.isEmpty()) {
				// put in front
				eventQueues.get(activeQueue).add(0, q.remove(q.size()-1));
			}
		}
		
		return true;
	}
}
