package com.hydrozoa.pokemon.model.world.cutscene;

public class WaitEvent extends CutsceneEvent {
	
	private float timer = 0f;
	private float waitTime;
	
	private boolean finished = false;
	
	public WaitEvent(float waitTime) {
		this.waitTime = waitTime;
	}

	@Override
	public void update(float delta) {
		timer += delta;
		if (timer >= waitTime) {
			finished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void screenShow() {}

}
