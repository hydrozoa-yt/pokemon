package com.hydrozoa.pokemon.battle.animation;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.TweenManager;

/**
 * @author hydrozoa
 */
public abstract class BattleAnimation {
	
	/* offsets of the animated pokemon */
	private Vector2 primaryOffset = new Vector2();
	private Vector2 secondaryOffset = new Vector2();
	
	/* transparency of the animated pokemon */
	private float primaryAlpha = 1f;
	private float secondaryAlpha = 1f;
	
	/* multiplied by its regions dimensions */
	private float primaryWidth = 1f;
	private float primaryHeight = 1f;
	
	/* multiplied by its regions dimensions */
	private float secondaryWidth = 1f;
	private float secondaryHeight = 1f;
	
	private Texture primaryMask;
	private float primaryMaskAmount; 
	
	private Texture secondaryMask;
	private float secondaryMaskAmount; 
	
	/* sprites that need rendering in this animation */
	public List<BattleSprite> sprites;
	
	/* times the animation */
	private float timer = 0f;
	
	/* total time the animation lasts */
	private float duration;
	
	private AssetManager assetManager;
	private TweenManager tweenManager;
	
	public BattleAnimation(float duration) {
		this.duration = duration;
		sprites = new ArrayList<BattleSprite>();
	}
	
	public void initialize(AssetManager assetManager, TweenManager tweenManager) {
		this.assetManager = assetManager;
		this.tweenManager = tweenManager;
	}
	
	public void update(float delta) {
		timer += delta;
		if (timer > duration) {
			timer = duration;
		}
	}
	
	public float getPrimaryOffsetX() {
		return primaryOffset.x;
	}
	
	public float getPrimaryOffsetY() {
		return primaryOffset.y;
	}
	
	public float getSecondaryOffsetX() {
		return secondaryOffset.x;
	}
	
	public float getSecondaryOffsetY() {
		return secondaryOffset.y;
	}
	
	public float getPrimaryAlpha() {
		return primaryAlpha;
	}
	
	public float getSecondaryAlpha() {
		return secondaryAlpha;
	}
	
	public float getPrimaryWidth() {
		return primaryWidth;
	}
	
	public float getPrimaryHeight() {
		return primaryHeight;
	}
	
	public float getSecondaryWidth() {
		return secondaryWidth;
	}
	
	public float getSecondaryHeight() {
		return secondaryHeight;
	}
	
	public void setPrimaryX(float newX) {
		this.primaryOffset.x = newX;
	}
	
	public void setPrimaryY(float newY) {
		this.primaryOffset.y = newY;
	}
	
	public void setSecondaryX(float newX) {
		this.secondaryOffset.x = newX;
	}
	
	public void setSecondaryY(float newY) {
		this.secondaryOffset.y = newY;
	}

	public void setPrimaryAlpha(float primaryAlpha) {
		this.primaryAlpha = primaryAlpha;
	}

	public void setSecondaryAlpha(float secondaryAlpha) {
		this.secondaryAlpha = secondaryAlpha;
	}

	public void setPrimaryWidth(float primaryWidth) {
		this.primaryWidth = primaryWidth;
	}

	public void setPrimaryHeight(float primaryHeight) {
		this.primaryHeight = primaryHeight;
	}

	public void setSecondaryWidth(float secondaryWidth) {
		this.secondaryWidth = secondaryWidth;
	}

	public void setSecondaryHeight(float secondaryHeight) {
		this.secondaryHeight = secondaryHeight;
	}

	public Texture getPrimaryMask() {
		return primaryMask;
	}

	public void setPrimaryMask(Texture primaryMask) {
		this.primaryMask = primaryMask;
	}

	public float getPrimaryMaskAmount() {
		return primaryMaskAmount;
	}

	public void setPrimaryMaskAmount(float primaryMaskAmount) {
		this.primaryMaskAmount = primaryMaskAmount;
	}

	public Texture getSecondaryMask() {
		return secondaryMask;
	}

	public void setSecondaryMask(Texture secondaryMask) {
		this.secondaryMask = secondaryMask;
	}

	public float getSecondaryMaskAmount() {
		return secondaryMaskAmount;
	}

	public void setSecondaryMaskAmount(float secondaryMaskAmount) {
		this.secondaryMaskAmount = secondaryMaskAmount;
	}

	public List<BattleSprite> getSprites() {
		return sprites;
	}

	public boolean isFinished() {
		return (duration <= timer);
	}
	
	protected float getTimer() {
		return timer;
	}
	
	protected float getDuration() {
		return duration;
	}
	
	protected void addSprite(BattleSprite sprite) {
		sprites.add(sprite);
	}
	
	protected AssetManager getAssetManager() {
		return assetManager;
	}
	
	protected TweenManager getTweenManager() {
		return tweenManager;
	}

}
