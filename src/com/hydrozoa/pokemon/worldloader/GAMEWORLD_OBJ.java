package com.hydrozoa.pokemon.worldloader;

import com.badlogic.gdx.math.GridPoint2;

public enum GAMEWORLD_OBJ {
	
	/*
	 * BUILDINGS
	 */
	SMALL_HOUSE("small_house", 5f, 5f,	new GridPoint2(0,0), 
										new GridPoint2(1,0), 
										new GridPoint2(2,0), 
										new GridPoint2(4,0), 
								
										new GridPoint2(0,1),
										new GridPoint2(1,1), 
										new GridPoint2(2,1), 
										new GridPoint2(3,1),
										new GridPoint2(4,1),
								
										new GridPoint2(0,2),
										new GridPoint2(1,2), 
										new GridPoint2(2,2), 
										new GridPoint2(3,2),
										new GridPoint2(4,2)),
	
	LABORATORY("laboratory", 7f, 5f,	new GridPoint2(0,0), 
										new GridPoint2(1,0), 
										new GridPoint2(2,0), 
										new GridPoint2(3,0),
										new GridPoint2(5,0),
										new GridPoint2(6,0), 
										
										new GridPoint2(0,1),
										new GridPoint2(6,1), 
										
										new GridPoint2(0,2), 
										new GridPoint2(6,2),
			
										new GridPoint2(0,3), 
										new GridPoint2(1,3), 
										new GridPoint2(2,3), 
										new GridPoint2(3,3),
										new GridPoint2(4,3), 
										new GridPoint2(5,3), 
										new GridPoint2(6,3)),
	
	/*
	 * FOLIAGE
	 */
	BIG_TREE("large_tree", 2f, 3f,		new GridPoint2(0,0), 
										new GridPoint2(1,0), 
										new GridPoint2(0,1), 
										new GridPoint2(1,1)),
	
	/*
	 * 1x1 OBJECTS
	 */
	SIGN("sign", 1f, 1f, new GridPoint2(0,0)),
	
	;
	
	private String path; 		// name of sprite on atlas
	private float sizeX,sizeY;	// size of object in tiles
	private GridPoint2[] tiles;
	
	private GAMEWORLD_OBJ(String path, float sizeX, float sizeY, GridPoint2... tiles) {
		this.path = path;
		this.sizeX = sizeX;
		this.sizeY= sizeY;
		this.tiles = tiles;
	}
	
	public String getPath() {
		return path;
	}
	
	public float getSizeX() {
		return sizeX;
	}
	
	public float getSizeY() {
		return sizeY;
	}
	
	public GridPoint2[] getTiles() {
		return tiles;
	}
}
