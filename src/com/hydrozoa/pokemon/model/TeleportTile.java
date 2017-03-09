package com.hydrozoa.pokemon.model;

import com.badlogic.gdx.graphics.Color;
import com.hydrozoa.pokemon.model.actor.Actor;
import com.hydrozoa.pokemon.model.actor.PlayerActor;
import com.hydrozoa.pokemon.model.world.World;
import com.hydrozoa.pokemon.model.world.script.WorldInterface;

/**
 * @author hydrozoa
 */
public class TeleportTile extends Tile {
	
	private WorldInterface worldInterface;
	
	/* destination */
	private String worldName;
	private int x, y;
	private DIRECTION facing;
	
	/* transition color */
	private Color color;

	public TeleportTile(TERRAIN terrain, WorldInterface worldInterface, String worldName, int x, int y, DIRECTION facing, Color color) {
		super(terrain);
		this.worldName = worldName;
		this.x= x;
		this.y=y;
		this.facing=facing;
		this.color=color;
		this.worldInterface = worldInterface;
	}
	
	@Override
	public void actorStep(Actor a) {
		if (a instanceof PlayerActor) {
			World targetWorld = worldInterface.getWorld(worldName);
			worldInterface.changeLocation(targetWorld, x, y, facing, color);
		}
	}

}
