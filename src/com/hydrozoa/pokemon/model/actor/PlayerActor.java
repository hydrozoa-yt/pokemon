package com.hydrozoa.pokemon.model.actor;

import com.hydrozoa.pokemon.model.world.World;
import com.hydrozoa.pokemon.model.world.cutscene.CutscenePlayer;
import com.hydrozoa.pokemon.util.AnimationSet;

/**
 * @author hydrozoa
 */
public class PlayerActor extends Actor {
	
	private CutscenePlayer cutscenePlayer;

	public PlayerActor(World world, int x, int y, AnimationSet animations, CutscenePlayer cutscenePlayer) {
		super(world, x, y, animations);
		this.cutscenePlayer = cutscenePlayer;
	}
	
	public CutscenePlayer getCutscenePlayer() {
		return cutscenePlayer;
	}
}
