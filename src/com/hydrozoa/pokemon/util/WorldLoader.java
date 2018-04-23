package com.hydrozoa.pokemon.util;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.TERRAIN;
import com.hydrozoa.pokemon.model.TeleportTile;
import com.hydrozoa.pokemon.model.world.World;
import com.hydrozoa.pokemon.model.world.WorldObject;

/**
 * Small loader to load World.class into AssetManager.
 * 
 * @author Hydrozoa
 */
public class WorldLoader extends AsynchronousAssetLoader<World, WorldLoader.WorldParameter> {
	
	private World world;
	
	private Animation flowerAnimation;
	private Animation doorOpen;
	private Animation doorClose;
	
	public WorldLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager asman, String filename, FileHandle file, WorldParameter parameter) {
		TextureAtlas atlas = asman.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		
		flowerAnimation = new Animation(0.8f, atlas.findRegions("flowers"), PlayMode.LOOP_PINGPONG);
		doorOpen = new Animation(0.8f/4f, atlas.findRegions("woodenDoor"), PlayMode.NORMAL);
		doorClose = new Animation(0.5f/4f, atlas.findRegions("woodenDoor"), PlayMode.REVERSED);
		
		BufferedReader reader = new BufferedReader(file.reader());
		int currentLine = 0;
		try {
			while (reader.ready()) {
				String line = reader.readLine();
				currentLine++;
				
				// header of file
				if (currentLine == 1) {
					String[] tokens = line.split("\\s+");
					world = new World(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
					continue;
				}
				
				if (line.isEmpty() || line.startsWith("//")) {
					continue;
				}
				
				// functions
				String[] tokens = line.split("\\s+");
				switch (tokens[0]) {
				case "fillTerrain":
					fillTerrain(tokens[1]);
					break;
				case "setTerrain":
					setTerrain(tokens[1], tokens[2], tokens[3]);
					break;
				case "addTree":
					addTree(asman, tokens[1], tokens[2]);
					break;
				case "addFlowers":
					addFlowers(tokens[1], tokens[2]);
					break;
				case "addRug":
					addRug(asman, tokens[1], tokens[2]);
					break;
				case "addHouse":
					addHouse(asman, tokens[1], tokens[2]);
					break;
				case "teleport":
					teleport(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8]);
					break;
				case "unwalkable":
					unwalkable(tokens[1], tokens[2]);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fillTerrain(String terrain) {
		TERRAIN t = TERRAIN.valueOf(terrain);
		for (int x = 0; x < world.getMap().getWidth(); x++) {
			for (int y = 0; y < world.getMap().getHeight(); y++) {
				world.getMap().getTile(x, y).setTerrain(t);
			}
		}
	}
	
	private void setTerrain(String x, String y, String terrain) {
		int ix = Integer.parseInt(x);
		int iy = Integer.parseInt(y);
		TERRAIN t = TERRAIN.valueOf(terrain);
		world.getMap().getTile(ix, iy).setTerrain(t);
	}
	
	private void addTree(AssetManager assetManager, String sx, String sy) {
		int x = Integer.parseInt(sx);
		int y = Integer.parseInt(sy);
		
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
	
	private void addFlowers(String sx, String sy) {
		int x = Integer.parseInt(sx);
		int y = Integer.parseInt(sy);
		
		GridPoint2[] gridArray = new GridPoint2[1];
		gridArray[0] = new GridPoint2(0,0);
		WorldObject flowers = new WorldObject(x, y, true, flowerAnimation, 1f, 1f, gridArray);
		world.addObject(flowers);
	}
	
	private void addRug(AssetManager assetManager, String sx, String sy) {
		int x = Integer.parseInt(sx);
		int y = Integer.parseInt(sy);
		
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
	
	private void addHouse(AssetManager assetManager, String sx, String sy) {
		int x = Integer.parseInt(sx);
		int y = Integer.parseInt(sy);
		
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
	
	private void teleport(String sx, String sy, String sterrain, String stargetWorld, String stargetX, String stargetY, String stargetDir, String stransitionColor) {
		int x = Integer.parseInt(sx);
		int y = Integer.parseInt(sy);
		
		int targetX = Integer.parseInt(stargetX);
		int targetY = Integer.parseInt(stargetY);
		
		TERRAIN terrain = TERRAIN.valueOf(sterrain);
		DIRECTION targetDir = DIRECTION.valueOf(stargetDir);
		
		Color transitionColor;
		switch (stransitionColor) {
		case "WHITE":
			transitionColor = Color.WHITE;
			break;
		case "BLACK":
			transitionColor = Color.BLACK;
			break;
		default:
			transitionColor = Color.BLACK;
			break;
		}
		
		TeleportTile tile = new TeleportTile(terrain, stargetWorld, targetX, targetY, targetDir, transitionColor);
		world.getMap().setTile(tile, x, y);
	}
	
	private void unwalkable(String sx, String sy) {
		int x = Integer.parseInt(sx);
		int y = Integer.parseInt(sy);
		world.getMap().getTile(x, y).setWalkable(false);
	}

	@Override
	public World loadSync(AssetManager arg0, String arg1, FileHandle arg2, WorldParameter arg3) {
		return world;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String filename, FileHandle file, WorldParameter parameter) {
		Array<AssetDescriptor> ad = new Array<AssetDescriptor>();
		ad.add(new AssetDescriptor("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class));
		return ad;
	}
	
	static public class WorldParameter extends AssetLoaderParameters<World> {
	}
}
