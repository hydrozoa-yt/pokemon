package com.hydrozoa.pokemon.model.world.script;

import com.badlogic.gdx.graphics.Color;
import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.world.World;

/**
 * @author hydrozoa
 */
public interface WorldInterface {
	
	/**
	 * Smooth transition to another world.
	 * @param newWorld
	 * @param x
	 * @param y
	 * @param facing
	 * @param color
	 */
	public void changeLocation(World newWorld, int x, int y, DIRECTION facing, Color color);
	
	/**
	 * Get a loaded World from name
	 * @param worldName
	 * @return
	 */
	public World getWorld(String worldName);
}
