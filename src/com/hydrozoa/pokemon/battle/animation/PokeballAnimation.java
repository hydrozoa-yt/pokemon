package com.hydrozoa.pokemon.battle.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;
import aurelienribon.tweenengine.equations.Sine;

/**
 * @author hydrozoa
 */
public class PokeballAnimation extends BattleAnimation {
	
	/* Delay from start till the pokeball opens */
	private float pokeballOpen = 0.5f;
	
	private Texture whiteMask;

	public PokeballAnimation() {
		super(1.5f);
	}
	
	@Override
	public void initialize(AssetManager assetManager, TweenManager tweenManager) {
		super.initialize(assetManager, tweenManager);
		
		TextureAtlas atlas = assetManager.get("res/graphics_packed/battle/battlepack.atlas", TextureAtlas.class);
		
		/* pokeball being thrown */
		TextureRegion pokeball = atlas.findRegion("pokeball");
		BattleSprite pokeballSprite = new BattleSprite(pokeball, -1f, 0f, 1f, 1f);
		addSprite(pokeballSprite);
		
		/*
		 * MOVEMENT OF POKEBALL
		 */
		Tween.to(pokeballSprite, BattleSpriteAccessor.X, pokeballOpen)
			.target(0f)
			.ease(Linear.INOUT)
			.start(tweenManager);
		Tween.to(pokeballSprite, BattleSpriteAccessor.Y, 0.25f)
			.target(1f)
			.ease(Sine.OUT)
			.start(tweenManager);
		Tween.to(pokeballSprite, BattleSpriteAccessor.Y, 0.25f)
			.target(0f)
			.ease(Sine.IN)
			.delay(0.25f)
			.start(tweenManager);
		
		/*
		 * ROTATION OF POKEBALL
		 */
		Tween.to(pokeballSprite, BattleSpriteAccessor.ROTATION, 0.5f)
			.target(2f*360f)
			.ease(Linear.INOUT)
			.start(tweenManager);
		
		Tween.to(pokeballSprite, BattleSpriteAccessor.ALPHA, 0.25f)
			.target(0f)
			.ease(Linear.INOUT)
			.delay(0.7f)
			.start(tweenManager);
		
		
		/* effects when the ball opens */
		Animation pokeballEffect = new Animation(0.025f, atlas.findRegions("pokeball_effect"));
		
		addEffectSprite(pokeballEffect, 0.5f, 0.62f, tweenManager);
		addEffectSprite(pokeballEffect, -0.5f, 0.62f, tweenManager);
		addEffectSprite(pokeballEffect, 0f, 0.8f, tweenManager);
		addEffectSprite(pokeballEffect, 0.8f, 0f, tweenManager);
		addEffectSprite(pokeballEffect, -0.8f, 0f, tweenManager);
		
		this.setPrimaryWidth(0f);
		this.setPrimaryHeight(0f);
		Tween.to(this, BattleAnimationAccessor.PRIMARY_WIDTH, 0.5f)
			.target(1f)
			.delay(pokeballOpen)
			.start(tweenManager);
		Tween.to(this, BattleAnimationAccessor.PRIMARY_HEIGHT, 0.5f)
			.target(1f)
			.delay(pokeballOpen)
			.start(tweenManager);
		
		whiteMask = assetManager.get("res/graphics/statuseffect/white.png", Texture.class);
		this.setPrimaryMask(whiteMask);
		this.setPrimaryMaskAmount(1f);
		Tween.to(this, BattleAnimationAccessor.PRIMARY_MASK_AMOUNT, 1f)
			.target(0f)
			.ease(Linear.INOUT)
			.delay(pokeballOpen+0.3f)
			.start(tweenManager);
	}
	
	private void addEffectSprite(Animation anim, float endX, float endY, TweenManager tweenManager) {
		AnimatedBattleSprite effectSprite = new AnimatedBattleSprite(
				anim, 
				0f, 
				0f, 
				1f, 
				1f, 
				pokeballOpen);
		effectSprite.setAnimationMode(PlayMode.LOOP);
		effectSprite.setAlpha(0f);
		addSprite(effectSprite);
		
		Tween.to(effectSprite, BattleSpriteAccessor.ALPHA, 0f)
				.target(1f)
				.delay(pokeballOpen)
				.start(tweenManager);
		
		Tween.to(effectSprite, BattleSpriteAccessor.Y, 1f)
				.target(endY)
				.ease(Linear.INOUT)
				.delay(pokeballOpen)
				.start(tweenManager);
		
		Tween.to(effectSprite, BattleSpriteAccessor.X, 1f)
				.target(endX)
				.ease(Linear.INOUT)
				.delay(pokeballOpen)
				.start(tweenManager);
	}

}
