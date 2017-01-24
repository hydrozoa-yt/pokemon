package com.hydrozoa.pokemon.screen.transition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * Two short blinks and a special effect.
 * 
 * @author hydrozoa
 */
public class BattleBlinkTransition extends BattleTransition {
	
	/* progression (0f-1f) - not seconds! */
	private float normalBlinkDuration;
	private float specialEffectStart;
	
	private float alpha = 0;
	
	private Color color;

	public BattleBlinkTransition(float duration, int transitionID, Color color, ShaderProgram transitionShader, TweenManager tweenManager, AssetManager assetManager) {
		super(duration, transitionID, false, transitionShader, tweenManager, assetManager);
		this.color = color;
		this.specialEffectStart = 0.2f;
		this.normalBlinkDuration = specialEffectStart/4f;
		
		Tween.to(this, BattleBlinkTransitionAccessor.ALPHA, normalBlinkDuration*getDuration())
			.target(1f)
			.start(tweenManager);
		Tween.to(this, BattleBlinkTransitionAccessor.ALPHA, normalBlinkDuration*getDuration())
			.target(0f)
			.delay(normalBlinkDuration*getDuration())
			.start(tweenManager);
		Tween.to(this, BattleBlinkTransitionAccessor.ALPHA, normalBlinkDuration*getDuration())
			.target(1f)
			.delay(2f*normalBlinkDuration*getDuration())
			.start(tweenManager);
		Tween.to(this, BattleBlinkTransitionAccessor.ALPHA, normalBlinkDuration*getDuration())
			.target(0f)
			.delay(3f*normalBlinkDuration*getDuration())
			.start(tweenManager);
	}

	@Override
	public void render(float delta, SpriteBatch batch) {
		if (getProgress() < specialEffectStart) { // two first blinks are normal
			batch.begin();
			batch.setColor(color.r, color.g, color.b, alpha);
			batch.draw(white, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.end();
		} else {
			defaultShader = batch.getShader();
			
			batch.setShader(transitionShader);
			batch.begin();
			transitionShader.setUniformf("u_cutoff", (getProgress()-specialEffectStart)/(1f-specialEffectStart));
			batch.draw(transition, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.end();
			batch.setShader(defaultShader);
		}
	}

	protected float getAlpha() {
		return alpha;
	}
	
	protected void setAlpha(float newAlpha) {
		this.alpha = newAlpha;
	}
}
