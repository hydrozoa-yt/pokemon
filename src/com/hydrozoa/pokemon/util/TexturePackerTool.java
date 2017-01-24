package com.hydrozoa.pokemon.util;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * @author hydrozoa
 */
public class TexturePackerTool {
	
	public static void main(String[] args) {
		TexturePacker.process(
				"res/graphics_unpacked/ui/", 
				"res/graphics_packed/ui/", 
				"uipack");
		TexturePacker.process(
				"res/graphics_unpacked/tiles/", 
				"res/graphics_packed/tiles/", 
				"tilepack");
		TexturePacker.process(
				"res/graphics_unpacked/battle/", 
				"res/graphics_packed/battle/", 
				"battlepack");
	}

}
