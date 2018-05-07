package com.hydrozoa.pokemon.worldloader;

import java.util.HashMap;

/**
 * Database of all gameobjects. These can be referenced when making maps to easily import objects into the world. 
 * @author Hydrozoa
 */
public class LWorldObjectDb {
	
	private HashMap<String,LWorldObject> knownObjects = new HashMap<String,LWorldObject>();
	
	protected void addObject(String name, LWorldObject obj) {
		knownObjects.put(name, obj);
	}
	
	public LWorldObject getLWorldObject(String name) {
		if (!knownObjects.containsKey(name)) {
			throw new NullPointerException("Could not find LWorldObject of name "+name);
		}
		return knownObjects.get(name);
	}

}
