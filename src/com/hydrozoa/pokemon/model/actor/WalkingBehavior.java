package com.hydrozoa.pokemon.model.actor;

import com.hydrozoa.pokemon.model.DIRECTION;

/**
 * @author hydrozoa
 */
public interface WalkingBehavior {
	
	/**
	 * Updates the state of the behavior
	 * @param delta	Seconds since last update
	 */
	public void update(float delta);
	
	/**
	 * @return	If the Actor should move at all
	 */
	public boolean shouldMove();
	
	/**
	 * @return	Direction the Actor should move in
	 */
	public DIRECTION moveDirection();

}
