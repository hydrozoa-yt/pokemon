package com.hydrozoa.pokemon.battle.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * @author hydrozoa
 */
public class ChargeAnimation extends BattleAnimation {
	
	private BattleSprite hitmark;

	public ChargeAnimation() {
		super(0.5f);
	}
	
	@Override
	public void initialize(AssetManager assetManager, TweenManager tweenManager) {
		super.initialize(assetManager, tweenManager);
		
		TextureAtlas atlas = assetManager.get("res/graphics_packed/battle/battlepack.atlas", TextureAtlas.class);
		TextureRegion region = atlas.findRegion("hitmark");
		
		hitmark = new BattleSprite(region, 1.8f, 0.3f, 1f, 1f);
		hitmark.setAlpha(0f);
		addSprite(hitmark);
		
		Tween.to(this, BattleAnimationAccessor.PRIMARY_OFFSET_X, getDuration()/2)
			.target(1f)
			.start(tweenManager);
		Tween.to(this, BattleAnimationAccessor.PRIMARY_OFFSET_X, getDuration()/2)
			.target(0f)
			.delay(getDuration()/2)
			.start(tweenManager);
		
		Tween.to(hitmark, BattleSpriteAccessor.ALPHA, 0f)
			.target(1f)
			.delay(getDuration()/10*3)
			.start(tweenManager);
		Tween.to(hitmark, BattleSpriteAccessor.ALPHA, 0f)
			.target(0f)
			.delay(getDuration()/10*7)
			.start(tweenManager);
	}
}
