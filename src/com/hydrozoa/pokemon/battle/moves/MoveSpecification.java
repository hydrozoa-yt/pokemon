package com.hydrozoa.pokemon.battle.moves;

/**
 * @author hydrozoa
 */
public class MoveSpecification {
	
	private MOVE_TYPE type;
	private MOVE_CATEGORY category;
	
	private int power;
	private float accuracy;
	private int pp;
	
	private String name;
	private String description;
	
	public MoveSpecification(MOVE_TYPE type, MOVE_CATEGORY category, int power, float accuracy, int pp, String name, String description) {
		this.type = type;
		this.category = category;
		this.power = power;
		this.accuracy = accuracy;
		this.pp = pp;
		this.name = name;
		this.description = description;
	}
	public MOVE_TYPE getType() {
		return type;
	}
	public MOVE_CATEGORY getCategory() {
		return category;
	}
	public int getPower() {
		return power;
	}
	public float getAccuracy() {
		return accuracy;
	}
	public int getPP() {
		return pp;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
	

}
