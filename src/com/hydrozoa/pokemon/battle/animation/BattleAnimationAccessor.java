package com.hydrozoa.pokemon.battle.animation;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * @author hydrozoa
 */
public class BattleAnimationAccessor implements TweenAccessor<BattleAnimation> {
	
	public static final int PRIMARY_OFFSET_X = 0;
	public static final int PRIMARY_OFFSET_Y = 1;
	public static final int SECONDARY_OFFSET_X = 2;
	public static final int SECONDARY_OFFSET_Y = 3;
	public static final int PRIMARY_ALPHA = 4;
	public static final int SECONDARY_ALPHA = 5;
	public static final int PRIMARY_WIDTH = 6;
	public static final int PRIMARY_HEIGHT = 7;
	public static final int SECONDARY_WIDTH = 8;
	public static final int SECONDARY_HEIGHT = 9;
	public static final int PRIMARY_MASK_AMOUNT = 10;
	public static final int SECONDARY_MASK_AMOUNT = 11;

	@Override
	public int getValues(BattleAnimation target, int tweenType, float[] returnValues) {
		switch (tweenType) {
			case PRIMARY_OFFSET_X:
				returnValues[0] = target.getPrimaryOffsetX();
				return 1;
			case PRIMARY_OFFSET_Y:
				returnValues[0] = target.getPrimaryOffsetY();
				return 1;
			case SECONDARY_OFFSET_X:
				returnValues[0] = target.getSecondaryOffsetX();
				return 1;
			case SECONDARY_OFFSET_Y:
				returnValues[0] = target.getSecondaryOffsetY();
				return 1;
			case PRIMARY_ALPHA:
				returnValues[0] = target.getPrimaryAlpha();
				return 1;
			case SECONDARY_ALPHA:
				returnValues[0] = target.getSecondaryAlpha();
				return 1;
			case PRIMARY_WIDTH:
				returnValues[0] = target.getPrimaryWidth();
				return 1;
			case PRIMARY_HEIGHT:
				returnValues[0] = target.getPrimaryHeight();
				return 1;
			case SECONDARY_WIDTH:
				returnValues[0] = target.getSecondaryWidth();
				return 1;
			case SECONDARY_HEIGHT:
				returnValues[0] = target.getSecondaryHeight();
				return 1;
			case PRIMARY_MASK_AMOUNT:
				returnValues[0] = target.getPrimaryMaskAmount();
				return 1;
			case SECONDARY_MASK_AMOUNT:
				returnValues[0] = target.getSecondaryMaskAmount();
				return 1;
			default:
				assert false;
				return -1;
		}
	}

	@Override
	public void setValues(BattleAnimation target, int tweenType, float[] newValues) {
		switch (tweenType) {
			case PRIMARY_OFFSET_X:
				target.setPrimaryX(newValues[0]);
				break;
			case PRIMARY_OFFSET_Y:
				target.setPrimaryY(newValues[0]);
				break;
			case SECONDARY_OFFSET_X:
				target.setSecondaryX(newValues[0]);
				break;
			case SECONDARY_OFFSET_Y:
				target.setSecondaryY(newValues[0]);
				break;
			case PRIMARY_ALPHA:
				target.setPrimaryAlpha(newValues[0]);
				break;
			case SECONDARY_ALPHA:
				target.setSecondaryAlpha(newValues[0]);
				break;
			case PRIMARY_WIDTH:
				target.setPrimaryWidth(newValues[0]);
				break;
			case PRIMARY_HEIGHT:
				target.setPrimaryHeight(newValues[0]);
				break;
			case SECONDARY_WIDTH:
				target.setSecondaryWidth(newValues[0]);
				break;
			case SECONDARY_HEIGHT:
				target.setSecondaryHeight(newValues[0]);
				break;
			case PRIMARY_MASK_AMOUNT:
				target.setPrimaryMaskAmount(newValues[0]);
				break;
			case SECONDARY_MASK_AMOUNT:
				target.setSecondaryMaskAmount(newValues[0]);
				break;
			default:
				assert false;
		}
	}

}
