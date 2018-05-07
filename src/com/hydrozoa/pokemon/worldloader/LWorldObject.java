package com.hydrozoa.pokemon.worldloader;

import com.badlogic.gdx.math.GridPoint2;

public class LWorldObject {
	
	private String imageName;
	private float sizeX,sizeY;
	private GridPoint2[] tiles;
	
	public LWorldObject(String imageName, float sizeX, float sizeY, GridPoint2... tiles) {
		this.imageName = imageName;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.tiles = tiles;
	}

	public String getImageName() {
		return imageName;
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
