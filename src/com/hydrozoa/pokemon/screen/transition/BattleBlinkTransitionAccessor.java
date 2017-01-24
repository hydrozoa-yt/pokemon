package com.hydrozoa.pokemon.screen.transition;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * @author hydrozoa
 */
public class BattleBlinkTransitionAccessor implements TweenAccessor<BattleBlinkTransition> {
	
	public static final int ALPHA = 0;

	@Override
	public int getValues(BattleBlinkTransition target, int tweenType, float[] returnValues) {
		switch(tweenType) {
		case ALPHA:
			returnValues[0] = target.getAlpha();
			return 1;
		default: 
			assert false; 
			return -1;
		}
	}

	@Override
	public void setValues(BattleBlinkTransition target, int tweenType, float[] newValues) {
		switch (tweenType) {
    	case ALPHA:
    		target.setAlpha(newValues[0]);
    		break;
    	default: 
    		assert false; 
    		break;
		}
	}

}
