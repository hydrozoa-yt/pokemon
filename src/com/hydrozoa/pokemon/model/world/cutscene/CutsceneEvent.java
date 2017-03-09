package com.hydrozoa.pokemon.model.world.cutscene;

/**
 * Analog to a BattleEvent.
 * 
 * @author hydrozoa
 */
public abstract class CutsceneEvent {
	
	public abstract void begin(CutscenePlayer player);
	
	public abstract void update(float delta);
	
	public abstract boolean isFinished();
	
	protected abstract CutscenePlayer getPlayer();

}
