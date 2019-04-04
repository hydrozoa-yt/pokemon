package com.hydrozoa.pokemon.model.world.cutscene;

import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.actor.Actor;
import com.hydrozoa.pokemon.model.actor.Actor.MOVEMENT_STATE;

/**
 * @author hydrozoa
 */
public class ActorWalkEvent extends CutsceneEvent {
	
	private Actor a;
	private DIRECTION dir;
	
	int targetX, targetY;
	
	private boolean finished = false;
	
	public ActorWalkEvent(Actor a, DIRECTION dir) {
		this.a = a;
		this.dir = dir;
	}
	
	@Override
	public void begin(CutscenePlayer player) {
		super.begin(player);
		targetX = a.getX()+dir.getDX();
		targetY = a.getY()+dir.getDY();
	}

	@Override
	public void update(float delta) {
		if (a.getX() != targetX || a.getY() != targetY) {
			if (a.getMovementState() == MOVEMENT_STATE.STILL) {
				a.moveWithoutNotifications(dir);
			}
		} else {
			if (a.getMovementState() == MOVEMENT_STATE.STILL) {
				finished = true;
			}
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void screenShow() {}

}
