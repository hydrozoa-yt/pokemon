package com.hydrozoa.pokemon.battle.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author hydrozoa
 */
public class AnimatedBattleSprite extends BattleSprite {
	
	private Animation animation;
	
	private long startTime;

	public AnimatedBattleSprite(Animation animation, float x, float y, float width, float height, float delay) {
		super(animation.getKeyFrames()[0], x, y, width, height);
		this.animation = animation;
		this.startTime = System.currentTimeMillis()+(long)(delay*1000l);
	}
	
	public void setAnimationMode(Animation.PlayMode mode) {
		this.animation.setPlayMode(mode);
	}
	
	@Override
	public TextureRegion getRegion() {
		if (startTime > System.currentTimeMillis()) { // not yet
			return super.getRegion(); // return first frame
		} else { // it's time babyyy
			float stateTime = ((System.currentTimeMillis()-startTime)/1000f);
			return animation.getKeyFrame(stateTime);
		}
	}
}
