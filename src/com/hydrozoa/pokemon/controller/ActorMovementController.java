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
	
	private boolean[] buttonPress;
	private float[] buttonTimer;
	
	private float WALK_REFACE_THRESHOLD = 0.07f;
	
	public ActorMovementController(Actor p) {
		this.player = p;
		buttonPress = new boolean[DIRECTION.values().length];
		buttonPress[DIRECTION.NORTH.ordinal()] = false;
		buttonPress[DIRECTION.SOUTH.ordinal()] = false;
		buttonPress[DIRECTION.EAST.ordinal()] = false;
		buttonPress[DIRECTION.WEST.ordinal()] = false;
		buttonTimer = new float[DIRECTION.values().length];
		buttonTimer[DIRECTION.NORTH.ordinal()] = 0f;
		buttonTimer[DIRECTION.SOUTH.ordinal()] = 0f;
		buttonTimer[DIRECTION.EAST.ordinal()] = 0f;
		buttonTimer[DIRECTION.WEST.ordinal()] = 0f;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.UP) {
			buttonPress[DIRECTION.NORTH.ordinal()] = true;
		}
		if (keycode == Keys.DOWN) {
			buttonPress[DIRECTION.SOUTH.ordinal()] = true;
		}
		if (keycode == Keys.LEFT) {
			buttonPress[DIRECTION.WEST.ordinal()] = true;
		}
		if (keycode == Keys.RIGHT) {
			buttonPress[DIRECTION.EAST.ordinal()] = true;
		}
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.F1) {
			if (player.getMovementMode() == MOVEMENT_MODE.WALKING) {
				player.setMode(MOVEMENT_MODE.BIKING);
			} else {
				player.setMode(MOVEMENT_MODE.WALKING);
			}
		}
		
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
		if (buttonPress[DIRECTION.NORTH.ordinal()]) {
			updateDirection(DIRECTION.NORTH, delta);
			return;
		}
		if (buttonPress[DIRECTION.SOUTH.ordinal()]) {
			updateDirection(DIRECTION.SOUTH, delta);
			return;
		}
		if (buttonPress[DIRECTION.WEST.ordinal()]) {
			updateDirection(DIRECTION.WEST, delta);
			return;
		}
		if (buttonPress[DIRECTION.EAST.ordinal()]) {
			updateDirection(DIRECTION.EAST, delta);
			return;
		}
	}
	
	/**
	 * Runs every frame, for each direction whose key is pressed.
	 */
	private void updateDirection(DIRECTION dir, float delta) {
		buttonTimer[dir.ordinal()] += delta;
		considerMove(dir);
	}
	
	/**
	 * Runs when a key is released, argument is its corresponding direction.
	 */
	private void releaseDirection(DIRECTION dir) {
		buttonPress[dir.ordinal()] = false;
		considerReface(dir);
		buttonTimer[dir.ordinal()] = 0f;
	}
	
	private void considerMove(DIRECTION dir) {
		if (buttonTimer[dir.ordinal()] > WALK_REFACE_THRESHOLD) {
			player.move(dir);
		}
	}
	
	private void considerReface(DIRECTION dir) {
		if (buttonTimer[dir.ordinal()] < WALK_REFACE_THRESHOLD) {
			player.reface(dir);
		}
	}
}
