package com.hydrozoa.pokemon.battle.animation;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * @author hydrozoa
 */
public class BattleSpriteAccessor implements TweenAccessor<BattleSprite> {
	
	public static final int X = 0;
	public static final int Y = 1;
	public static final int WIDTH = 2;
	public static final int HEIGHT = 3;
	public static final int ALPHA = 4;
	public static final int ROTATION = 5;

	@Override
	public int getValues(BattleSprite target, int tweenType, float[] returnValues) {
		switch(tweenType) {
			case X:
				returnValues[0] = target.getX();
				return 1;
			case Y:
				returnValues[0] = target.getY();
				return 1;
			case WIDTH:
				returnValues[0] = target.getWidth();
				return 1;
			case HEIGHT:
				returnValues[0] = target.getHeight();
				return 1;
			case ALPHA:
				returnValues[0] = target.getAlpha();
				return 1;
			case ROTATION:
				returnValues[0] = target.getRotation();
				return 1;
			default: 
				assert false; 
				return -1;
		}
	}

	@Override
	public void setValues(BattleSprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
        	case X: 
        		target.setX(newValues[0]); 
        		break;
        	case Y: 
        		target.setY(newValues[0]); 
        		break;
        	case WIDTH:
        		target.setWidth(newValues[0]);
        		break;
        	case HEIGHT:
        		target.setHeight(newValues[0]);
        		break;
        	case ALPHA:
        		target.setAlpha(newValues[0]);
        		break;
        	case ROTATION:
        		target.setRotation(newValues[0]);
        		break;
        	default: 
        		assert false; 
        		break;
		}
	}

}
