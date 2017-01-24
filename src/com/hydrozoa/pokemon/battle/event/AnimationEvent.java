package com.hydrozoa.pokemon.battle.event;

import com.hydrozoa.pokemon.battle.BATTLE_PARTY;
import com.hydrozoa.pokemon.battle.animation.BattleAnimation;

/**
 * @author hydrozoa
 */
public class AnimationEvent extends Event {
	
	private BATTLE_PARTY primary;
	private BattleAnimation animation;

	public AnimationEvent(BATTLE_PARTY primary, BattleAnimation animation) {
		this.animation = animation;
		this.primary = primary;
	}

	@Override
	public void begin(EventPlayer player) {
		super.begin(player);
		player.playBattleAnimation(animation, primary);
	}
	
	@Override
	public void update(float delta) {
		animation.update(delta);
	}

	@Override
	public boolean finished() {
		return this.getPlayer().getBattleAnimation().isFinished();
	}

}
