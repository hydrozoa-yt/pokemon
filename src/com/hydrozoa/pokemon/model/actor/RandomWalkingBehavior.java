package com.hydrozoa.pokemon.model.actor;

import java.util.Random;

import com.hydrozoa.pokemon.model.DIRECTION;

/**
 * Behavior that will make an Actor walk around it's initial position randomly. 
 * NOTE: This behavior doesn't confine an Actor to a specific area.
 * 
 * @author hydrozoa
 */
public class RandomWalkingBehavior implements WalkingBehavior {
	
	private float moveIntervalMinimum;
	private float moveIntervalMaximum;
	private Random random;
	
	private float timer;
	private float currentWaitTime;
	
	private boolean shouldMove;
	private DIRECTION moveDirection;

	/**
	 * @param distance				Euclidian distance the Actor can maximally walk away from initial position.
	 * @param moveIntervalMinimum	Shortest amount of seconds between two moves
	 * @param moveIntervalMaximum	Longest amount of seconds between two moves
	 * @param random				RNG
	 */
	public RandomWalkingBehavior(float moveIntervalMinimum, float moveIntervalMaximum, Random random) {
		this.moveIntervalMinimum = moveIntervalMinimum;
		this.moveIntervalMaximum = moveIntervalMaximum;
		this.random = random;
		this.timer = 0f;
		this.currentWaitTime = calculateWaitTime();
		this.moveDirection = DIRECTION.NORTH;
	}

	@Override
	public void update(float delta) {
		timer += delta;
		if (timer >= currentWaitTime) {
			
			int directionIndex = random.nextInt(DIRECTION.values().length);
			moveDirection = DIRECTION.values()[directionIndex];
			shouldMove = true;
					
			currentWaitTime = calculateWaitTime();
			timer = 0f;
		} else {
			shouldMove = false;
		}
	}
	
	private float calculateWaitTime() {
		return random.nextFloat() * (moveIntervalMaximum - moveIntervalMinimum) + moveIntervalMinimum;
	}

	@Override
	public boolean shouldMove() {
		return shouldMove;
	}

	@Override
	public DIRECTION moveDirection() {
		return moveDirection;
	}

}
