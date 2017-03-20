package com.hydrozoa.pokemon.model.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.hydrozoa.pokemon.dialogue.Dialogue;
import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.YSortable;
import com.hydrozoa.pokemon.model.world.World;
import com.hydrozoa.pokemon.model.world.WorldObject;
import com.hydrozoa.pokemon.util.AnimationSet;

/**
 * @author hydrozoa
 */
public class Actor implements YSortable {
	
	private World world;
	private int x;
	private int y;
	private DIRECTION facing;
	
	private float worldX, worldY;
	
	/* state specific */
	private int srcX, srcY;
	private int destX, destY;
	private float animTimer;
	private float WALK_TIME_PER_TILE = 0.3f;
	private float REFACE_TIME = 0.1f;
	
	private float walkTimer;
	private boolean moveRequestThisFrame;
	
	private ACTOR_STATE state;
	
	private AnimationSet animations;
	
	private Dialogue dialogue;
	
	public Actor(World world, int x, int y, AnimationSet animations) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.worldX = x;
		this.worldY = y;
		this.animations = animations;
		this.state = ACTOR_STATE.STANDING;
		this.facing = DIRECTION.SOUTH;
	}
	
	public enum ACTOR_STATE {
		WALKING,
		STANDING,
		REFACING,
		;
	}
	
	public void update(float delta) {
		if (state == ACTOR_STATE.WALKING) {
			animTimer += delta;
			walkTimer += delta;
			worldX = Interpolation.linear.apply(srcX, destX, animTimer / WALK_TIME_PER_TILE);
			worldY = Interpolation.linear.apply(srcY, destY, animTimer / WALK_TIME_PER_TILE);
			if (animTimer > WALK_TIME_PER_TILE) {
				float leftOverTime = animTimer - WALK_TIME_PER_TILE;
				finishMove();
				if (moveRequestThisFrame) { // keep walking using the same animation time
					if (move(facing)) {
						animTimer += leftOverTime;
						worldX = Interpolation.linear.apply(srcX, destX, animTimer / WALK_TIME_PER_TILE);
						worldY = Interpolation.linear.apply(srcY, destY, animTimer / WALK_TIME_PER_TILE);
					}
				} else {
					walkTimer = 0f;
				}
			}
		}
		if (state == ACTOR_STATE.REFACING) {
			animTimer += delta;
			if (animTimer > REFACE_TIME) {
				state = ACTOR_STATE.STANDING;
			}
		}
		moveRequestThisFrame = false;
	}
	
	public boolean reface(DIRECTION dir) {
		if (state != ACTOR_STATE.STANDING) { // can only reface when standing
			return false;
		}
		if (facing == dir) { // can't reface if we already face a direction
			return true;
		}
		facing = dir;
		state = ACTOR_STATE.REFACING;
		animTimer = 0f;
		return true;
	}
	
	/**
	 * Changes the Players facing direction, without the walk-frame animation.
	 * This is used when loading maps, and in dialogue.
	 */
	public boolean refaceWithoutAnimation(DIRECTION dir) {
		if (state != ACTOR_STATE.STANDING) { // can only reface when standing
			return false;
		}
		this.facing = dir;
		return true;
	}
	
	/**
	 * Initializes a move. If you want to move an Actor, use this method.
	 * @param dir	Direction to move
	 * @return		If the move can be performed
	 */
	public boolean move(DIRECTION dir) {
		if (state == ACTOR_STATE.WALKING) {
			if (facing == dir) {
				moveRequestThisFrame = true;
			}
			return false;
		}
		// edge of world test
		if (x+dir.getDX() >= world.getMap().getWidth() || x+dir.getDX() < 0 || y+dir.getDY() >= world.getMap().getHeight() || y+dir.getDY() < 0) {
			reface(dir);
			return false;
		}
		// unwalkable tile test
		if (!world.getMap().getTile(x+dir.getDX(), y+dir.getDY()).walkable()) {
			reface(dir);
			return false;
		}
		// actor test
		if (world.getMap().getTile(x+dir.getDX(), y+dir.getDY()).getActor() != null) {
			reface(dir);
			return false;
		}
		// object test
		if (world.getMap().getTile(x+dir.getDX(), y+dir.getDY()).getObject() != null) {
			WorldObject o = world.getMap().getTile(x+dir.getDX(), y+dir.getDY()).getObject();
			if (!o.isWalkable()) {
				reface(dir);
				return false;
			}
		}
		initializeMove(dir);
		world.getMap().getTile(x, y).setActor(null);
		x += dir.getDX();
		y += dir.getDY();
		world.getMap().getTile(x, y).setActor(this);
		return true;
	}
	
	private void initializeMove(DIRECTION dir) {
		this.facing = dir;
		this.srcX = x;
		this.srcY = y;
		this.destX = x+dir.getDX();
		this.destY = y+dir.getDY();
		this.worldX = x;
		this.worldY = y;
		animTimer = 0f;
		state = ACTOR_STATE.WALKING;
	}
	
	private void finishMove() {
		state = ACTOR_STATE.STANDING;
		this.worldX = destX;
		this.worldY = destY;
		this.srcX = 0;
		this.srcY = 0;
		this.destX = 0;
		this.destY = 0;
		world.getMap().getTile(x, y).actorStep(this);
	}
	
	/**
	 * Changes the Players position internally.
	 */
	public void teleport(int x, int y) {
		this.x = x;
		this.y = y;
		this.worldX = x;
		this.worldY = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float getWorldX() {
		return worldX;
	}

	public float getWorldY() {
		return worldY;
	}
	
	public TextureRegion getSprite() {
		if (state == ACTOR_STATE.WALKING) {
			return animations.getWalking(facing).getKeyFrame(walkTimer);
		} else if (state == ACTOR_STATE.STANDING) {
			return animations.getStanding(facing);
		} else if (state == ACTOR_STATE.REFACING) {
			return animations.getWalking(facing).getKeyFrames()[0];
		}
		return animations.getStanding(DIRECTION.SOUTH);
	}

	@Override
	public float getSizeX() {
		return 1;
	}

	@Override
	public float getSizeY() {
		return 1.5f;
	}
	
	public void changeWorld(World world) {
		this.world.removeActor(this);
		this.world = world;
		this.world.addActor(this);
	}
	
	public ACTOR_STATE getState() {
		return state;
	}
	
	public DIRECTION getFacing() {
		return facing;
	}
	
	public void setDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}
	
	public Dialogue getDialogue() {
		return dialogue;
	}
}
