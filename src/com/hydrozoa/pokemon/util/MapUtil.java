package com.hydrozoa.pokemon.util;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.hydrozoa.pokemon.dialogue.Dialogue;
import com.hydrozoa.pokemon.dialogue.DialogueNode;
import com.hydrozoa.pokemon.dialogue.LinearDialogueNode;
import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.TERRAIN;
import com.hydrozoa.pokemon.model.TeleportTile;
import com.hydrozoa.pokemon.model.Tile;
import com.hydrozoa.pokemon.model.actor.Actor;
import com.hydrozoa.pokemon.model.actor.LimitedWalkingBehavior;
import com.hydrozoa.pokemon.model.world.World;
import com.hydrozoa.pokemon.model.world.WorldObject;
import com.hydrozoa.pokemon.model.world.cutscene.CutsceneEventQueuer;
import com.hydrozoa.pokemon.model.world.cutscene.CutscenePlayer;

/**
 * Will load a few simple World setups. Allows us to work with multiple maps without map loading.
 * 
 * @author hydrozoa
 */
public class MapUtil {
	
	private Animation flowerAnimation;
	
	private AnimationSet npcAnimations;
	
	private AssetManager assetManager;
	private CutsceneEventQueuer broadcaster;
	
	public MapUtil(AssetManager assetManager, CutsceneEventQueuer broadcaster, AnimationSet npcAnimations) {
		this.assetManager = assetManager;
		this.broadcaster = broadcaster;
		this.npcAnimations = npcAnimations;
		
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		flowerAnimation = new Animation(0.8f, atlas.findRegions("flowers"), PlayMode.LOOP_PINGPONG);
	}
	
	/**
	 * @return	An outdoors test-level.
	 */
	public World loadWorld1() {
		World world = new World("test_level",20,20);
		for (int xi = 0; xi < 20; xi++) {
			for (int yi = 0; yi < 20; yi++) {
				if (xi==0 || xi==18) {
					if (world.getMap().getTile(xi, yi).getObject() == null) {
						addTree(world,xi,yi);
					}
				}
				if (yi==0||yi==18) {
					if (world.getMap().getTile(xi, yi).getObject() == null) {
						addTree(world,xi,yi);
					}
				}
			}
		}
		
		
		addHouse(world,10,10);
		addFlowers(world,5,5);
		for (int i = 0; i < 10; i++) {
			int xr = MathUtils.random(19);
			int yr = MathUtils.random(19);
			if (world.getMap().getTile(xr, yr).getObject() == null) {
				addFlowers(world,xr,yr);
			}
		}
		
		Actor actor = new Actor(world, 3, 3, npcAnimations);
		LimitedWalkingBehavior brain = new LimitedWalkingBehavior(actor, 1, 1, 1, 1, 0.3f, 1f, new Random());
		world.addActor(actor, brain);
		
		Dialogue greeting = new Dialogue();
		DialogueNode firstNode = new LinearDialogueNode("Hello!\nI've been roaming here for days", 0);
		greeting.addNode(firstNode);
		actor.setDialogue(greeting);
		
		return world;
	}
	
	/**
	 * @return	An indoors test-level.
	 */
	public World loadWorld2() {
		World world = new World("test_indoors",10,10);
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (y==0) {
					world.getMap().setTile(new Tile(null, false), x, y);
					continue;
				}
				if (y > 0) {
					world.getMap().setTile(new Tile(TERRAIN.INDOOR_TILES, true), x, y);
				}
				if (x==0 || y==7) {
					world.getMap().setTile(new Tile(TERRAIN.INDOOR_TILES_SHADOW, true), x, y);
				}
				if (y==8) {
					world.getMap().setTile(new Tile(TERRAIN.WALL_BOTTOM, false), x, y);
				}
				if (y==9) {
					world.getMap().setTile(new Tile(TERRAIN.WALL_TOP, false), x, y);
				}
			}
		}
		
		world.getMap().setTile(new TeleportTile(null, broadcaster, "test_level", 13,10,DIRECTION.SOUTH,Color.WHITE), 4, 0);
		addRug(world,3,0);
		
		return world;
	}
	
	private void addRug(World world, int x, int y) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion rugRegion = atlas.findRegion("rug");
		GridPoint2[] gridArray = new GridPoint2[3*2];
		gridArray[0] = new GridPoint2(0,0);
		gridArray[1] = new GridPoint2(0,1);
		gridArray[2] = new GridPoint2(0,2);
		gridArray[3] = new GridPoint2(1,0);
		gridArray[4] = new GridPoint2(1,1);
		gridArray[5] = new GridPoint2(1,2);
		WorldObject rug = new WorldObject(x, y, true, rugRegion, 3f, 2f, gridArray);
		world.addObject(rug);
	}
	
	private void addHouse(World world, int x, int y) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion houseRegion = atlas.findRegion("small_house");
		GridPoint2[] gridArray = new GridPoint2[5*4-1];
		int index = 0;
		for (int loopX = 0; loopX < 5; loopX++) {
			for (int loopY = 0; loopY < 4; loopY++) {
				if (loopX==3&&loopY==0) {
					continue;
				}
				gridArray[index] = new GridPoint2(loopX, loopY);
				index++;
			}
		}
		WorldObject house = new WorldObject(x, y, false, houseRegion, 5f, 5f, gridArray);
		world.addObject(house);
	}
	
	private void addFlowers(World world, int x, int y) {
		GridPoint2[] gridArray = new GridPoint2[1];
		gridArray[0] = new GridPoint2(0,0);
		WorldObject flowers = new WorldObject(x, y, true, flowerAnimation, 1f, 1f, gridArray);
		world.addObject(flowers);
	}
	
	private void addTree(World world, int x, int y) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion treeRegion = atlas.findRegion("large_tree");
		GridPoint2[] gridArray = new GridPoint2[2*2];
		gridArray[0] = new GridPoint2(0,0);
		gridArray[1] = new GridPoint2(0,1);
		gridArray[2] = new GridPoint2(1,1);
		gridArray[3] = new GridPoint2(1,0);
		WorldObject tree = new WorldObject(x, y, false, treeRegion, 2f, 3f, gridArray);
		world.addObject(tree);
	}
}
