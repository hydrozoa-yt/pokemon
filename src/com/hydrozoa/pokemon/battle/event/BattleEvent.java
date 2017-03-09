package com.hydrozoa.pokemon.battle.event;

/**
 * Any visual change in a Battle. These are queued up and displayed chonologically on the BattleScreen.
 * 
 * @author hydrozoa
 */
public abstract class BattleEvent {
	
	private BattleEventPlayer player;
	
	public void begin(BattleEventPlayer player) {
		this.player = player;
	}
	
	public abstract void update(float delta);
	
	public abstract boolean finished();
	
	protected BattleEventPlayer getPlayer() {
		return player;
	}
}
