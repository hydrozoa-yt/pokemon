package com.hydrozoa.pokemon.battle.animation;

import com.badlogic.gdx.assets.AssetManager;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * @author hydrozoa
 */
public class FaintingAnimation extends BattleAnimation {

	public FaintingAnimation() {
		super(0.3f);
	}
	
	@Override
	public void initialize(AssetManager assetManager, TweenManager tweenManager) {
		super.initialize(assetManager, tweenManager);
		
		Tween.to(this, BattleAnimationAccessor.PRIMARY_OFFSET_Y, 0.3f)
			.target(-0.5f)
			.start(tweenManager);
		Tween.to(this, BattleAnimationAccessor.PRIMARY_ALPHA, 0.3f)
			.target(0f)
			.start(tweenManager);
	}

}
