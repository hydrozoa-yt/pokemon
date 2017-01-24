package com.hydrozoa.pokemon.battle;

/**
 * @author hydrozoa
 */
public enum BATTLE_PARTY {
	PLAYER,
	OPPONENT,
	;
	
	public static BATTLE_PARTY getOpposite(BATTLE_PARTY party) {
		switch(party) {
		case PLAYER:
			return OPPONENT;
		case OPPONENT:
			return PLAYER;
		default:
			return null;
		}
	}
}
