package com.hydrozoa.pokemon.model.world.cutscene;

import com.badlogic.gdx.graphics.Color;
import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.world.World;

public class ChangeWorldEvent extends CutsceneEvent {
	
	private String newWorld;
	private int newX;
	private int newY;
	private DIRECTION newFacing;
	private Color transitionColor;
	
	private boolean isFinished = false;
	
	public ChangeWorldEvent(String newWorld, int x, int y, DIRECTION facing, Color color) {
		this.newWorld = newWorld;
		this.newX = x;
		this.newY = y;
		this.newFacing = facing;
		this.transitionColor = color;
	}
	
	@Override
	public void begin(CutscenePlayer player) {
		super.begin(player);
		World world = player.getWorld(newWorld);
		player.changeLocation(world, newX, newY, newFacing, transitionColor);
	}
	
	@Override
	public void update(float delta) {}

	@Override
	public boolean isFinished() {
		return isFinished;
	}
	
	@Override
	public void screenShow() {
		isFinished = true;
	}

}
