package com.hydrozoa.pokemon.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author hydrozoa
 */
public interface YSortable {
	
	public float getWorldX();
	
	public float getWorldY();
	
	public TextureRegion getSprite();
	
	public float getSizeX();
	
	public float getSizeY();
}
