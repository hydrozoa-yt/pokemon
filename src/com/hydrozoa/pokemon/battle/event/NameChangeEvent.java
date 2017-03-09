package com.hydrozoa.pokemon.battle.event;

import com.hydrozoa.pokemon.battle.BATTLE_PARTY;

/**
 * A BattleEvent where a Pokemon's name is changed, on the StatusBox.
 * This event takes no time.
 * 
 * @author hydrozoa
 */
public class NameChangeEvent extends BattleEvent {
	
	private String name;
	private BATTLE_PARTY party;
	
	public NameChangeEvent(String name, BATTLE_PARTY party) {
		this.name = name;
		this.party = party;
	}
	
	@Override
	public void begin(BattleEventPlayer player) {
		super.begin(player);
		player.getStatusBox(party).setText(name);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public boolean finished() {
		return true;
	}

}
