package com.hydrozoa.pokemon.model;

import com.badlogic.gdx.graphics.Color;
import com.hydrozoa.pokemon.model.actor.Actor;
import com.hydrozoa.pokemon.model.actor.PlayerActor;
import com.hydrozoa.pokemon.model.world.Door;
import com.hydrozoa.pokemon.model.world.World;
import com.hydrozoa.pokemon.model.world.cutscene.ActorVisibilityEvent;
import com.hydrozoa.pokemon.model.world.cutscene.ActorWalkEvent;
import com.hydrozoa.pokemon.model.world.cutscene.ChangeWorldEvent;
import com.hydrozoa.pokemon.model.world.cutscene.CutsceneEventQueuer;
import com.hydrozoa.pokemon.model.world.cutscene.CutscenePlayer;
import com.hydrozoa.pokemon.model.world.cutscene.DoorEvent;
import com.hydrozoa.pokemon.model.world.cutscene.WaitEvent;

/**
 * @author hydrozoa
 */
public class TeleportTile extends Tile {
	
	private CutscenePlayer player;
	private CutsceneEventQueuer broadcaster;
	
	/* destination */
	private String worldName;
	private int x, y;
	private DIRECTION facing;
	
	/* transition color */
	private Color color;

	public TeleportTile(TERRAIN terrain, CutscenePlayer player, CutsceneEventQueuer broadcaster, String worldName, int x, int y, DIRECTION facing, Color color) {
		super(terrain);
		this.worldName = worldName;
		this.x= x;
		this.y=y;
		this.facing=facing;
		this.color=color;
		this.player = player;
		this.broadcaster = broadcaster;
	}
	
	@Override
	public void actorStep(Actor a) {
		if (a instanceof PlayerActor) {
			World targetWorld = player.getWorld(worldName);
			if (targetWorld.getMap().getTile(x, y).getObject() != null) {
				if (targetWorld.getMap().getTile(x, y).getObject() instanceof Door) {
					Door door = (Door)targetWorld.getMap().getTile(x, y).getObject();
					broadcaster.queueEvent(new ActorVisibilityEvent(a, true));
					broadcaster.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
					broadcaster.queueEvent(new DoorEvent(door, true));
					broadcaster.queueEvent(new WaitEvent(0.2f));
					broadcaster.queueEvent(new ActorVisibilityEvent(a, false));
					broadcaster.queueEvent(new WaitEvent(0.2f));
					broadcaster.queueEvent(new ActorWalkEvent(a, DIRECTION.SOUTH));
					broadcaster.queueEvent(new DoorEvent(door, false));
				}
			} else {
				broadcaster.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
			}
		}
	}
	
	@Override
	public boolean actorBeforeStep(Actor a) {
		if (a instanceof PlayerActor) {
			if (this.getObject() != null) {
				if (this.getObject() instanceof Door) {
					Door door = (Door)this.getObject();
					broadcaster.queueEvent(new DoorEvent(door, true));
					broadcaster.queueEvent(new ActorWalkEvent(a, DIRECTION.NORTH));
					broadcaster.queueEvent(new ActorVisibilityEvent(a, true));
					broadcaster.queueEvent(new DoorEvent(door, false));
					broadcaster.queueEvent(new ChangeWorldEvent(worldName, x, y, facing, color));
					broadcaster.queueEvent(new ActorVisibilityEvent(a, false));
					broadcaster.queueEvent(new ActorWalkEvent(a, DIRECTION.NORTH));
					return false;
				}
			}
		}
		return true;
	}
}
