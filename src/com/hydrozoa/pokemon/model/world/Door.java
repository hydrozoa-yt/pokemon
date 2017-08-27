package com.hydrozoa.pokemon.model.world;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * @author hydrozoa
 */
public class Door extends WorldObject {
	
	private Animation openAnimation;
	private Animation closeAnimation;
	
	private float animationTimer = 0f;
	private float animationTime = 0.5f;
	
	private STATE state = STATE.CLOSED;
	
	public enum STATE {
		OPEN,
		CLOSED,
		OPENING,
		CLOSING,
		;
	}

	public Door(int x, int y, Animation openAnimation, Animation closeAnimation) {
		super(x, y, true, openAnimation.getKeyFrames()[0], 1f, 1.5f, new GridPoint2(0,0));
		this.openAnimation = openAnimation;
		this.closeAnimation = closeAnimation;
	}
	
	public void open() {
		state = STATE.OPENING;
	}
	
	public void close() {
		state = STATE.CLOSING;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);

		if (state == STATE.CLOSING || state == STATE.OPENING) {
			animationTimer += delta;
			if (animationTimer >= animationTime) {
				if (state == STATE.CLOSING) {
					state = STATE.CLOSED;
				} else if (state == STATE.OPENING) {
					state = STATE.OPEN;
				}
				animationTimer = 0f;
			}
		}
	}
	
	public STATE getState() {
		return state;
	}
	
	public TextureRegion getSprite() {
		if (state == STATE.OPEN) {
			return closeAnimation.getKeyFrames()[3];
		} else if (state == STATE.CLOSED) {
			return closeAnimation.getKeyFrames()[0];
		} else if (state == STATE.OPENING) {
			return openAnimation.getKeyFrame(animationTimer);
		} else if (state == STATE.CLOSING) {
			return closeAnimation.getKeyFrame(animationTimer);
		} 
		return null;
	}

}
