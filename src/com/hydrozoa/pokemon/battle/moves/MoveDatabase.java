package com.hydrozoa.pokemon.battle.moves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hydrozoa.pokemon.battle.animation.ChargeAnimation;

/**
 * Collection of all moves known by the game. Only create one of these, preferably at a high level.
 * Note that this class returns clones of Moves. The returned moves are therefore safe to use.
 * 
 * @author hydrozoa
 */
public class MoveDatabase {
	
	private List<Move> moves = new ArrayList<Move>();
	private HashMap<String, Integer> mappings = new HashMap<String, Integer>();
	
	public MoveDatabase() {
		initializeMoves();
	}

	private void initializeMoves() {
		addMove(new DamageMove(
				new MoveSpecification(
						MOVE_TYPE.NORMAL, 
						MOVE_CATEGORY.PHYSICAL, 
						50, 
						1f, 
						35, 
						"Tackle", 
						"Charges the foe with a full-body tackle."), 
				ChargeAnimation.class));
		addMove(new DamageMove(
				new MoveSpecification(
						MOVE_TYPE.WATER, 
						MOVE_CATEGORY.SPECIAL, 
						40, 
						1f, 
						25, 
						"Water Gun", 
						"Squirts water to attack the foe."), 
				ChargeAnimation.class));
		addMove(new DamageMove(
				new MoveSpecification(
						MOVE_TYPE.NORMAL, 
						MOVE_CATEGORY.PHYSICAL, 
						40, 
						1f, 
						35, 
						"Scratch", 
						"Scratches the foe with sharp claws."), 
				ChargeAnimation.class));
		addMove(new DamageMove(
				new MoveSpecification(
						MOVE_TYPE.DRAGON, 
						MOVE_CATEGORY.PHYSICAL, 
						80, 
						1f, 
						15, 
						"Dragon Claw", 
						"Hooks and slashes the foe with long, sharp claws."), 
				ChargeAnimation.class));
	}
	
	private void addMove(Move move) {
		moves.add(move);
		mappings.put(move.getName(), moves.size()-1);
	}
	
	/**
	 * @param moveName	Name of the Move you want
	 * @return			Clone of the move
	 */
	public Move getMove(String moveName) {
		return moves.get(mappings.get(moveName)).clone();
	}
	
	/**
	 * @param index		Index of wanted move
	 * @return			Clone of move
	 */
	public Move getMove(int index) {
		return moves.get(index).clone();
	}
}
