package com.hydrozoa.pokemon;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * @author hydrozoa
 */
public class Main {
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.height = 400;
		config.width = 600;
		config.vSyncEnabled = false;
		config.foregroundFPS = 200;
		
		new LwjglApplication(new PokemonGame(), config);
	}

}
