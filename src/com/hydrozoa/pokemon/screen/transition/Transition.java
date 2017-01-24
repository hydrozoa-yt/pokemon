package com.hydrozoa.pokemon.screen.transition;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.TweenManager;

/**
 * @author hydrozoa
 */
public abstract class Transition {
	
	private float timer;
	private float duration;
	
	private boolean isFinished = false;
	
	private TweenManager tweenManager;
	private AssetManager assetManager;
	
	public Transition(float duration, TweenManager tweenManager, AssetManager assetManager) {
		this.duration = duration;
		this.timer = 0f;
		this.tweenManager = tweenManager;
		this.assetManager = assetManager;
	}
	
	public void update(float delta) {
		timer += delta;
		if (timer > duration) {
			isFinished = true;
		}
	}
	
	/**
	 * Keep in mind that the Screen supplies it's own rendering techniques. 
	 * Therefore, the spritebatch given here is not started.
	 */
	public abstract void render(float delta, SpriteBatch batch);
	
	public boolean isFinished() {
		return isFinished;
	}
	
	public float getProgress() {
		return (timer/duration);
	}
	
	public float getDuration() {
		return duration;
	}
}
