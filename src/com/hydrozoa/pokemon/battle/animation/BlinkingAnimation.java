package com.hydrozoa.pokemon.battle.animation;

import com.badlogic.gdx.assets.AssetManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * @author hydrozoa
 */
public class BlinkingAnimation extends BattleAnimation {
	
	private int blinks;

	public BlinkingAnimation(float duration, int blinks) {
		super(duration);
		this.blinks = blinks;
	}
	
	@Override
	public void initialize(AssetManager assetManager, TweenManager tweenManager) {
		super.initialize(assetManager, tweenManager);
		
		float secondsPerBlink = (getDuration()/(blinks*2));
		
		for (int i = 0; i < blinks*2; i++) {
			if (i % 2 == 0) {
				Tween.to(this, BattleAnimationAccessor.PRIMARY_ALPHA, 0f)
					.target(0f)
					.delay(i*secondsPerBlink)
					.start(tweenManager);
			} else {
				Tween.to(this, BattleAnimationAccessor.PRIMARY_ALPHA, 0f)
					.target(1f)
					.delay(i*secondsPerBlink)
					.start(tweenManager);
			}
		}
	}


}
