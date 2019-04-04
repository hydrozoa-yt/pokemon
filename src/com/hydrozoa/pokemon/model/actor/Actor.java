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
	private boolean visible = true;
	
	private float worldX, worldY;
	
	// For callbacks to World
	private ActorObserver observer;
	
	/* state specific */
	private int srcX, srcY;
	private int destX, destY;
	private float animTimer;
	private float WALK_TIME_PER_TILE = 0.3f;
	private float BIKE_TIME_PER_TILE = 0.1f;
	private float REFACE_TIME = 0.1f;
	private boolean noMoveNotifications = false;
	
	private float moveTimer;
	private boolean moveRequestThisFrame;
	
	private MOVEMENT_STATE state;		// if the Actor is currently moving, standing or refacing
	private MOVEMENT_MODE mode;			// if Actor is running or walking etc
	
	private AnimationSet animations;
	
	private Dialogue dialogue;
	
	public Actor(World world, int x, int y, AnimationSet animations) {
		this.observer = world;
		this.world = world;
		this.x = x;
		this.y = y;
		this.worldX = x;
		this.worldY = y;
		this.animations = animations;
		this.mode = MOVEMENT_MODE.WALKING;
		this.state = MOVEMENT_STATE.STILL;
		this.facing = DIRECTION.SOUTH;
	}
	
	public enum MOVEMENT_STATE {
		MOVING,
		STILL,
		REFACING,
		;
	}
	
	public enum MOVEMENT_MODE {
		WALKING,
		BIKING,
		RUNNING,
		;
	}
	
	public void update(float delta) {
		if (state == MOVEMENT_STATE.MOVING) {
			animTimer += delta;
			moveTimer += delta;
			
			float timePerTile = WALK_TIME_PER_TILE;
			if (mode == MOVEMENT_MODE.BIKING) {
				timePerTile = BIKE_TIME_PER_TILE;
			}
			
			worldX = Interpolation.linear.apply(srcX, destX, animTimer / timePerTile);
			worldY = Interpolation.linear.apply(srcY, destY, animTimer / timePerTile);
			if (animTimer > timePerTile) {
				float leftOverTime = animTimer - timePerTile;
				finishMove();
				if (moveRequestThisFrame) { // keep walking using the same animation time
					if (move(facing)) {
						animTimer += leftOverTime;
						worldX = Interpolation.linear.apply(srcX, destX, animTimer / timePerTile);
						worldY = Interpolation.linear.apply(srcY, destY, animTimer / timePerTile);
					}
				} else {
					moveTimer = 0f;
				}
			}
		}
		if (state == MOVEMENT_STATE.REFACING) {
			animTimer += delta;
			if (animTimer > REFACE_TIME) {
				state = MOVEMENT_STATE.STILL;
			}
		}
		moveRequestThisFrame = false;
	}
	
	public boolean reface(DIRECTION dir) {
		if (state != MOVEMENT_STATE.STILL) { // can only reface when standing
			return false;
		}
		if (facing == dir) { // can't reface if we already face a direction
			return true;
		}
		facing = dir;
		state = MOVEMENT_STATE.REFACING;
		animTimer = 0f;
		return true;
	}
	
	/**
	 * Changes the Players facing direction, without the walk-frame animation.
	 * This is used when loading maps, and in dialogue.
	 */
	public boolean refaceWithoutAnimation(DIRECTION dir) {
		if (state != MOVEMENT_STATE.STILL) { // can only reface when standing
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
		if (state == MOVEMENT_STATE.MOVING) {
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
		if (world.getMap().getTile(x+dir.getDX(), y+dir.getDY()).actorBeforeStep(this) == true) {
			initializeMove(dir);
			world.getMap().getTile(x, y).setActor(null);
			x += dir.getDX();
			y += dir.getDY();
			world.getMap().getTile(x, y).setActor(this);
			return true;
		}
		return false;
	}
	
	/**
	 * Same as #move() but doesn't notify receiving Tile.
	 * Doesn't obey Tile#actorBeforeStep and Tile#actorStep
	 */
	public boolean moveWithoutNotifications(DIRECTION dir) {
		noMoveNotifications = true;
		if (state == MOVEMENT_STATE.MOVING) {
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
		state = MOVEMENT_STATE.MOVING;
	}
	
	private void finishMove() {
		state = MOVEMENT_STATE.STILL;
		this.worldX = destX;
		this.worldY = destY;
		this.srcX = 0;
		this.srcY = 0;
		this.destX = 0;
		this.destY = 0;
		if (!noMoveNotifications) {
			world.getMap().getTile(x, y).actorStep(this);
		} else {
			noMoveNotifications = false;
		}
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
		if (mode == MOVEMENT_MODE.WALKING) {
			if (state == MOVEMENT_STATE.MOVING) {
				return animations.getWalking(facing).getKeyFrame(moveTimer);
			} else if (state == MOVEMENT_STATE.STILL) {
				return animations.getStanding(facing);
			} else if (state == MOVEMENT_STATE.REFACING) {
				return animations.getWalking(facing).getKeyFrames()[0];
			}
		} else if (mode == MOVEMENT_MODE.BIKING) {
			if (state == MOVEMENT_STATE.MOVING) {
				return animations.getBiking(facing).getKeyFrame(moveTimer);
			} else if (state == MOVEMENT_STATE.STILL) {
				return animations.getBiking(facing).getKeyFrames()[1];
			} else if (state == MOVEMENT_STATE.REFACING) {
				return animations.getBiking(facing).getKeyFrames()[0];
			}
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
	
	public void changeWorld(World world, int newX, int newY) {
		this.world.removeActor(this);
		this.teleport(newX, newY);
		this.world = world;
		this.world.addActor(this);
	}
	
	public MOVEMENT_STATE getMovementState() {
		return state;
	}
	
	/**
	 * Changes the Actors mode of movement
	 * @param newMode
	 */
	public void setMode(MOVEMENT_MODE newMode) {
		this.mode = newMode;
	}
	
	public MOVEMENT_MODE getMovementMode() {
		return mode;
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
	
	public World getWorld() {
		return world;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
}
