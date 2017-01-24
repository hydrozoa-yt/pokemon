package com.hydrozoa.pokemon.battle.event;

import com.hydrozoa.pokemon.battle.BATTLE_PARTY;

/**
 * @author hydrozoa
 */
public class NameChangeEvent extends Event {
	
	private String name;
	private BATTLE_PARTY party;
	
	public NameChangeEvent(String name, BATTLE_PARTY party) {
		this.name = name;
		this.party = party;
	}
	
	@Override
	public void begin(EventPlayer player) {
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
