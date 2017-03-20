package com.hydrozoa.pokemon.model.world.cutscene;

/**
 * Analog to a BattleEvent.
 * 
 * @author hydrozoa
 */
public abstract class CutsceneEvent {
	
	private CutscenePlayer player;
	
	public void begin(CutscenePlayer player) {
		this.player = player;
	}
	
	public abstract void update(float delta);
	
	public abstract boolean isFinished();
	
	protected CutscenePlayer getPlayer() {
		return player;
	}
	
	/**
	 * Called when the CutscenePlayer is finished changing screens, as it does in a world change
	 */
	public abstract void screenShow();

}
