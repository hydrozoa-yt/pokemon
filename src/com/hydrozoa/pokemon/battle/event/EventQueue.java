package com.hydrozoa.pokemon.battle.event;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

/**
 * XXX: Not really necessary. This should be implemented in the BattleEventPlayer.
 * 
 * @author hydrozoa
 */
public class EventQueue {
	
	private ArrayDeque<BattleEvent> events = new ArrayDeque<BattleEvent>();
	
	public void addEvent(BattleEvent e) {
		events.add(e);
	}
	
	public BattleEvent peek() {
		return events.peek();
	}
	
	public BattleEvent pop() {
		return events.pop();
	}
	
	public Collection<BattleEvent> getEvents() {
		return Collections.unmodifiableCollection(events);
	}
}
