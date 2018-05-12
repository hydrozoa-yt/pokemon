package com.hydrozoa.pokemon.worldloader;

import java.util.HashMap;

public class LTerrainDb {
	
	private HashMap<String,LTerrain> knownTerrain = new HashMap<String,LTerrain>();
	
	protected void addTerrain(String name, LTerrain obj) {
		knownTerrain.put(name, obj);
	}
	
	public LTerrain getLTerrain(String name) {
		if (!knownTerrain.containsKey(name)) {
			throw new NullPointerException("Could not find LTerrain of name "+name);
		}
		return knownTerrain.get(name);
	}

}
