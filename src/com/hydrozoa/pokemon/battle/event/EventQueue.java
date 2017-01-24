package com.hydrozoa.pokemon.battle.event;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

/**
 * @author hydrozoa
 */
public class EventQueue {
	
	private ArrayDeque<Event> events = new ArrayDeque<Event>();
	
	public void addEvent(Event e) {
		events.add(e);
	}
	
	public Event peek() {
		return events.peek();
	}
	
	public Event pop() {
		return events.pop();
	}
	
	public Collection<Event> getEvents() {
		return Collections.unmodifiableCollection(events);
	}

}
