package com.hydrozoa.pokemon.battle.moves;

import com.hydrozoa.pokemon.battle.BATTLE_PARTY;
import com.hydrozoa.pokemon.battle.BattleMechanics;
import com.hydrozoa.pokemon.battle.STAT;
import com.hydrozoa.pokemon.battle.animation.BattleAnimation;
import com.hydrozoa.pokemon.battle.animation.BlinkingAnimation;
import com.hydrozoa.pokemon.battle.event.AnimationBattleEvent;
import com.hydrozoa.pokemon.battle.event.BattleEventQueuer;
import com.hydrozoa.pokemon.battle.event.HPAnimationEvent;
import com.hydrozoa.pokemon.battle.event.TextEvent;
import com.hydrozoa.pokemon.model.Pokemon;

/**
 * We're going to do some real important shit around here Morty.
 * 
 * @author hydrozoa
 */
public class DamageMove extends Move {

	public DamageMove(MoveSpecification spec, Class<? extends BattleAnimation> clazz) {
		super(spec, clazz);
	}

	@Override
	public BattleAnimation animation() {
		try {
			return animationClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	@Override
	public String message() {
		return null;
	}

	@Override
	public boolean isDamaging() {
		return true;
	}
	
	@Override
	public int useMove(BattleMechanics mechanics, Pokemon user, Pokemon target, BATTLE_PARTY party, BattleEventQueuer broadcaster) {
		int hpBefore = target.getCurrentHitpoints();
		int damage = super.useMove(mechanics, user, target, party, broadcaster);
		
		/* Broadcast animations */
		broadcaster.queueEvent(new AnimationBattleEvent(party, animation()));
		
		/* Broadcast blinking */
		broadcaster.queueEvent(new AnimationBattleEvent(BATTLE_PARTY.getOpposite(party), new BlinkingAnimation(1f, 5)));
		
		//float hpPercentage = ((float)target.getCurrentHitpoints())/(float)target.getStat(STAT.HITPOINTS);
		
		/* Broadcast HP change */
		broadcaster.queueEvent(
				new HPAnimationEvent(
						BATTLE_PARTY.getOpposite(party), 
						hpBefore,
						target.getCurrentHitpoints(), 
						target.getStat(STAT.HITPOINTS), 
						0.5f));
		
		if (mechanics.hasMessage()) {
			broadcaster.queueEvent(new TextEvent(mechanics.getMessage(), 0.5f));
		}
		return damage;
	}

	@Override
	public Move clone() {
		return new DamageMove(spec, animationClass);
	}
}
