package com.hydrozoa.pokemon.model.actor;

import com.hydrozoa.pokemon.model.world.World;
import com.hydrozoa.pokemon.util.AnimationSet;

public class NPCActor extends Actor {
	
	private WalkingBehavior behavior;

	public NPCActor(World world, int x, int y, AnimationSet animations, WalkingBehavior behavior) {
		super(world, x, y, animations);
		this.behavior = behavior;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		if (getState() == ACTOR_STATE.STANDING) {
			behavior.update(delta);
			if (behavior.shouldMove()) {
				move(behavior.moveDirection());
			}
		}
	}

}
