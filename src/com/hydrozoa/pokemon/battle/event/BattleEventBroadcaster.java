package com.hydrozoa.pokemon.battle.event;

/**
 * @author hydrozoa
 */
public interface BattleEventBroadcaster {

	public void broadcastEvent(BattleEvent event);
	
}
