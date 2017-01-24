package com.hydrozoa.pokemon.screen.transition;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import aurelienribon.tweenengine.TweenManager;

/**
 * @author hydrozoa
 */
public class BattleTransition extends Transition {
	
	private boolean backwards;
	
	protected Texture white;
	protected Texture transition;
	
	protected ShaderProgram defaultShader;
	protected ShaderProgram transitionShader;

	public BattleTransition(float duration, int transitionID, boolean backwards, ShaderProgram transitionShader, TweenManager tweenManager, AssetManager assetManager) {
		super(duration, tweenManager, assetManager);
		this.transitionShader = transitionShader;
		this.backwards = backwards;
		
		white = assetManager.get("res/graphics/statuseffect/white.png", Texture.class);
		transition = assetManager.get("res/graphics/transitions/transition_"+transitionID+".png", Texture.class);
	}

	@Override
	public void render(float delta, SpriteBatch batch) {
		defaultShader = batch.getShader();
		
		float cutoff = getProgress();
		if (backwards) {
			cutoff = (1-getProgress());
		}
		
		batch.setShader(transitionShader);
		batch.begin();
		transitionShader.setUniformf("u_cutoff", cutoff);
		batch.draw(transition, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		batch.setShader(defaultShader);
	}

}
