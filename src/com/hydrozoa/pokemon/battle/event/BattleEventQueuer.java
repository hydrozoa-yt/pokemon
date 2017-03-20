package com.hydrozoa.pokemon.battle.event;

/**
 * @author hydrozoa
 */
public interface BattleEventQueuer {

	public void queueEvent(BattleEvent event);
	
}
