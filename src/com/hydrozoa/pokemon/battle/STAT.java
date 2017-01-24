package com.hydrozoa.pokemon.battle;

/**
 * Elements that determine certain aspects on battles.
 * 
 * @author hydrozoa
 */
public enum STAT {
	
	/** Determines show much damage a Pokemon can recieve before fainting */
	HITPOINTS,
	
	/** Partly dtermines how much damage a Pokemon deals when using a physical move */
	ATTACK,
	
	/** Partly determines how much damage a Pokemon recieves when hit from a physical move */
	DEFENCE,
	
	/** Partly determines how much damage a Pokemon deals when using a special move */
	SPECIAL_ATTACK,
	
	/** Partly determines how much damage a Pokemon recieves when hit from a special move */
	SPECIAL_DEFENCE,
	
	/** Determines how quickly a Pokemon can act in battle */
	SPEED,
	;
}
