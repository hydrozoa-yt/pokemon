package com.hydrozoa.pokemon.battle.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.hydrozoa.pokemon.util.TextureAnimation;
import com.hydrozoa.pokemon.util.TextureAnimation.PlayMode;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

/**
 * @author hydrozoa
 */
public class StatChangeAnimation extends BattleAnimation {
	
	private TextureAnimation animation;

	public StatChangeAnimation() {
		super(1f);
	}
	
	@Override
	public void initialize(AssetManager assetManager, TweenManager tweenManager) {
		super.initialize(assetManager, tweenManager);
		Texture[] statChange = new Texture[32];
		for (int i = 0; i < 32; i++) {
			statChange[i] = assetManager.get("res/graphics/statuseffect/attack_"+i+".png", Texture.class);
		}
		animation = new TextureAnimation(0.01f, statChange);
		animation.setPlayMode(PlayMode.LOOP);
		
		Tween.to(this, BattleAnimationAccessor.PRIMARY_MASK_AMOUNT, 0.25f)
			.target(0.8f)
			.ease(Linear.INOUT)
			.start(tweenManager);
		Tween.to(this, BattleAnimationAccessor.PRIMARY_MASK_AMOUNT, 0.25f)
			.target(0f)
			.delay(0.75f)
			.ease(Linear.INOUT)
			.start(tweenManager);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		this.setPrimaryMask(animation.getKeyFrame(this.getTimer()));
	}
}
