package com.hydrozoa.pokemon.model;

import com.badlogic.gdx.graphics.Color;
import com.hydrozoa.pokemon.model.actor.Actor;
import com.hydrozoa.pokemon.model.actor.PlayerActor;
import com.hydrozoa.pokemon.model.world.cutscene.ChangeWorldEvent;
import com.hydrozoa.pokemon.model.world.cutscene.CutsceneEventQueuer;

/**
 * @author hydrozoa
 */
public class TeleportTile extends Tile {
	
	private CutsceneEventQueuer broadcaster;
	
	/* destination */
	private String worldName;
	private int x, y;
	private DIRECTION facing;
	
	/* transition color */
	private Color color;

	public TeleportTile(TERRAIN terrain, CutsceneEventQueuer broadcaster, String worldName, int x, int y, DIRECTION facing, Color color) {
		super(terrain);
		this.worldName = worldName;
		this.x= x;
		this.y=y;
		this.facing=facing;
		this.color=color;
		this.broadcaster = broadcaster;
	}
	
	@Override
	public void actorStep(Actor a) {
		if (a instanceof PlayerActor) {
			broadcaster.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
		}
	}
}
