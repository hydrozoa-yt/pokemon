package com.hydrozoa.pokemon.model.actor;

import com.hydrozoa.pokemon.model.DIRECTION;

public interface ActorObserver {
	
	/**
	 * Called from an Actor when he/she is finished moving.
	 * 
	 * @param a				Actor in question
	 * @param direction		The direction of the move
	 * @param x				Location after the move
	 * @param y
	 */
	public void actorMoved(Actor a, DIRECTION direction, int x, int y);
	
	/**
	 * Called from an Actor when he/she attempted to move, but was unsuccessful
	 * @param a				Actor in question
	 * @param direction		The direction of the move
	 */
	public void attemptedMove(Actor a, DIRECTION direction);
	
	public void actorBeforeMoved(Actor a, DIRECTION direction);

}
