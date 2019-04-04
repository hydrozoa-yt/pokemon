package com.hydrozoa.pokemon.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.actor.Actor;
import com.hydrozoa.pokemon.model.actor.Actor.MOVEMENT_MODE;

/**
 * Controller that can move an Actor around.
 * 
 * @author hydrozoa
 */
public class ActorMovementController extends InputAdapter {
	
	private Actor player;
	
	private boolean[] directionPress;		// which arrow-keys are pressed
	private float[] directionPressTimer;	// how long have they been pressed for
	
	private boolean isRunning = false;
	
	private float WALK_REFACE_THRESHOLD = 0.07f;
	
	public ActorMovementController(Actor p) {
		this.player = p;
		directionPress = new boolean[DIRECTION.values().length];
		directionPress[DIRECTION.NORTH.ordinal()] = false;
		directionPress[DIRECTION.SOUTH.ordinal()] = false;
		directionPress[DIRECTION.EAST.ordinal()] = false;
		directionPress[DIRECTION.WEST.ordinal()] = false;
		directionPressTimer = new float[DIRECTION.values().length];
		directionPressTimer[DIRECTION.NORTH.ordinal()] = 0f;
		directionPressTimer[DIRECTION.SOUTH.ordinal()] = 0f;
		directionPressTimer[DIRECTION.EAST.ordinal()] = 0f;
		directionPressTimer[DIRECTION.WEST.ordinal()] = 0f;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// enable running if appropiate
		if (keycode == Keys.SHIFT_LEFT && player.getMovementMode() == MOVEMENT_MODE.WALKING) {
			player.setNextMode(MOVEMENT_MODE.RUNNING);
		}
		
		// update arrow key pressing
		if (keycode == Keys.UP) {
			directionPress[DIRECTION.NORTH.ordinal()] = true;
		}
		if (keycode == Keys.DOWN) {
			directionPress[DIRECTION.SOUTH.ordinal()] = true;
		}
		if (keycode == Keys.LEFT) {
			directionPress[DIRECTION.WEST.ordinal()] = true;
		}
		if (keycode == Keys.RIGHT) {
			directionPress[DIRECTION.EAST.ordinal()] = true;
		}
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		// disable running if appropiate
		if (keycode == Keys.SHIFT_LEFT && player.getMovementMode() == MOVEMENT_MODE.RUNNING) {
			player.setNextMode(MOVEMENT_MODE.WALKING);
		}
		
		// update biking/not biking
		if (keycode == Keys.F1) {
			if (player.getMovementMode() == MOVEMENT_MODE.WALKING) {
				player.setNextMode(MOVEMENT_MODE.BIKING);
			} else if (player.getMovementMode() == MOVEMENT_MODE.BIKING) {
				player.setNextMode(MOVEMENT_MODE.WALKING);
			}
		}
		
		// update arrow key pressing// update 
		if (keycode == Keys.UP) {
			releaseDirection(DIRECTION.NORTH);
		}
		if (keycode == Keys.DOWN) {
			releaseDirection(DIRECTION.SOUTH);
		}
		if (keycode == Keys.LEFT) {
			releaseDirection(DIRECTION.WEST);
		}
		if (keycode == Keys.RIGHT) {
			releaseDirection(DIRECTION.EAST);
		}
		return false;
	}
	
	public void update(float delta) {
		if (directionPress[DIRECTION.NORTH.ordinal()]) {
			updateDirection(DIRECTION.NORTH, delta);
			return;
		}
		if (directionPress[DIRECTION.SOUTH.ordinal()]) {
			updateDirection(DIRECTION.SOUTH, delta);
			return;
		}
		if (directionPress[DIRECTION.WEST.ordinal()]) {
			updateDirection(DIRECTION.WEST, delta);
			return;
		}
		if (directionPress[DIRECTION.EAST.ordinal()]) {
			updateDirection(DIRECTION.EAST, delta);
			return;
		}
	}
	
	/**
	 * Runs every frame, for each direction whose key is pressed.
	 */
	private void updateDirection(DIRECTION dir, float delta) {
		directionPressTimer[dir.ordinal()] += delta;
		considerMove(dir);
	}
	
	/**
	 * Runs when a key is released, argument is its corresponding direction.
	 */
	private void releaseDirection(DIRECTION dir) {
		directionPress[dir.ordinal()] = false;
		considerReface(dir);
		directionPressTimer[dir.ordinal()] = 0f;
	}
	
	private void considerMove(DIRECTION dir) {
		if (directionPressTimer[dir.ordinal()] > WALK_REFACE_THRESHOLD) {
			player.move(dir);
		}
	}
	
	private void considerReface(DIRECTION dir) {
		if (directionPressTimer[dir.ordinal()] < WALK_REFACE_THRESHOLD) {
			player.reface(dir);
		}
	}
}
