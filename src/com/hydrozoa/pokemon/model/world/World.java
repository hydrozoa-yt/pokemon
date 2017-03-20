package com.hydrozoa.pokemon.model.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.math.GridPoint2;
import com.hydrozoa.pokemon.model.TileMap;
import com.hydrozoa.pokemon.model.actor.Actor;
import com.hydrozoa.pokemon.model.actor.ActorBehavior;

/**
 * Contains data about the game world, such as references to Actors, and WorldObjects.
 * Query the world from here.
 * 
 * @author hydrozoa
 */
public class World {
	
	/** Unique name used to refer to this world */
	private String name;
	
	private TileMap map;
	private List<Actor> actors;
	private HashMap<Actor, ActorBehavior> brains;
	private List<WorldObject> objects;
	
	public World(String name, int width, int height) {
		this.name = name;
		this.map = new TileMap(width, height);
		actors = new ArrayList<Actor>();
		brains = new HashMap<Actor, ActorBehavior>();
		objects = new ArrayList<WorldObject>();
	}
	
	public void addActor(Actor a) {
		map.getTile(a.getX(), a.getY()).setActor(a);
		actors.add(a);
	}
	
	public void addActor(Actor a, ActorBehavior b) {
		addActor(a);
		brains.put(a, b);
	}
	
	public void addObject(WorldObject o) {
		for (GridPoint2 p : o.getTiles()) {
			map.getTile(o.getX()+p.x, o.getY()+p.y).setObject(o);
		}
		objects.add(o);
	}
	
	public void removeActor(Actor actor) {
		map.getTile(actor.getX(), actor.getY()).setActor(null);
		actors.remove(actor);
		if (brains.containsKey(actor)) {
			brains.remove(actor);
		}
	}
	
	public void update(float delta) {
		for (Actor a : actors) {
			if (brains.containsKey(a)) {
				brains.get(a).update(delta);
			}
			a.update(delta);
		}
		for (WorldObject o : objects) {
			o.update(delta);
		}
	}

	public TileMap getMap() {
		return map;
	}
	
	public List<Actor> getActors() {
		return actors;
	}
	
	public List<WorldObject> getWorldObjects() {
		return objects;
	}

	public String getName() {
		return name;
	}
}
