package com.hydrozoa.pokemon.battle.event;

/**
 * @author hydrozoa
 */
public abstract class Event {
	
	private EventPlayer player;
	
	public void begin(EventPlayer player) {
		this.player = player;
	}
	
	public abstract void update(float delta);
	
	public abstract boolean finished();
	
	protected EventPlayer getPlayer() {
		return player;
	}
}
