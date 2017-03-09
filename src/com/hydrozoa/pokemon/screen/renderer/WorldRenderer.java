package com.hydrozoa.pokemon.screen.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hydrozoa.pokemon.Settings;
import com.hydrozoa.pokemon.model.Camera;
import com.hydrozoa.pokemon.model.TERRAIN;
import com.hydrozoa.pokemon.model.YSortable;
import com.hydrozoa.pokemon.model.actor.Actor;
import com.hydrozoa.pokemon.model.world.World;
import com.hydrozoa.pokemon.model.world.WorldObject;

/**
 * @author hydrozoa
 */
public class WorldRenderer {
	
	private AssetManager assetManager;
	private World world;
	
	private TextureRegion grass1;
	private TextureRegion grass2;
	private TextureRegion indoorTiles;
	private TextureRegion indoorTilesShadow;
	private TextureRegion wallBottom;
	private TextureRegion wallTop;
	
	private List<Integer> renderedObjects = new ArrayList<Integer>();
	private List<YSortable> forRendering = new ArrayList<YSortable>();
	
	public WorldRenderer(AssetManager assetManager, World world) {
		this.assetManager = assetManager;
		this.world = world;;
		
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		grass1 = atlas.findRegion("grass1");
		grass2 = atlas.findRegion("grass2");
		indoorTiles = atlas.findRegion("indoor_tiles");
		indoorTilesShadow = atlas.findRegion("indoor_tiles_shadow");
		wallBottom = atlas.findRegion("wall_bottom");
		wallTop = atlas.findRegion("wall_top");
	}
	
	public void render(SpriteBatch batch, Camera camera) {
		float worldStartX = Gdx.graphics.getWidth()/2 - camera.getCameraX()*Settings.SCALED_TILE_SIZE;
		float worldStartY = Gdx.graphics.getHeight()/2 - camera.getCameraY()*Settings.SCALED_TILE_SIZE;
		
		/* render tile terrains */
		for (int x = 0; x < world.getMap().getWidth(); x++) {
			for (int y = 0; y < world.getMap().getHeight(); y++) {
				TextureRegion render;
				if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.GRASS_1) {
					render = grass1;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.GRASS_2) {
					render = grass2;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_TILES) {
					render = indoorTiles;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.INDOOR_TILES_SHADOW) {
					render = indoorTilesShadow;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.WALL_BOTTOM) {
					render = wallBottom;
				} else if (world.getMap().getTile(x, y).getTerrain() == TERRAIN.WALL_TOP) {
					render = wallTop;
				} else {
					render = null;
				}
				
				if (render != null) {
					batch.draw(render, 
							(int)(worldStartX+x*Settings.SCALED_TILE_SIZE),
							(int)(worldStartY+y*Settings.SCALED_TILE_SIZE),
							(int)(Settings.SCALED_TILE_SIZE),
							(int)(Settings.SCALED_TILE_SIZE));
				}
			}
		}
		
		/* collect objects and actors */
		for (int x = 0; x < world.getMap().getWidth(); x++) {
			for (int y = 0; y < world.getMap().getHeight(); y++) {
				if (world.getMap().getTile(x, y).getActor() != null) {
					Actor actor = world.getMap().getTile(x, y).getActor();
					forRendering.add(actor);
				}
				if (world.getMap().getTile(x, y).getObject() != null) {
					WorldObject object = world.getMap().getTile(x, y).getObject();
					if (renderedObjects.contains(object.hashCode())) { // test if it's already drawn
						continue;
					}
					if (object.isWalkable()) {  		// if it's walkable, draw it right away
						batch.draw(object.getSprite(),	// chances are it's probably something on the ground
								worldStartX+object.getWorldX()*Settings.SCALED_TILE_SIZE,
								worldStartY+object.getWorldY()*Settings.SCALED_TILE_SIZE,
								Settings.SCALED_TILE_SIZE*object.getSizeX(),
								Settings.SCALED_TILE_SIZE*object.getSizeY());
						continue;
					} else {	// add it to the list of YSortables
						forRendering.add(object);
						renderedObjects.add(object.hashCode());
					}
				}
			}
		}
		
		Collections.sort(forRendering, new WorldObjectYComparator());
		Collections.reverse(forRendering);
		
		for (YSortable loc : forRendering) {
			batch.draw(loc.getSprite(), 
					worldStartX+loc.getWorldX()*Settings.SCALED_TILE_SIZE,
					worldStartY+loc.getWorldY()*Settings.SCALED_TILE_SIZE,
					Settings.SCALED_TILE_SIZE*loc.getSizeX(),
					Settings.SCALED_TILE_SIZE*loc.getSizeY());
		}
		
		renderedObjects.clear();
		forRendering.clear();
	}
	
	public void setWorld(World world) {
		this.world = world;
	}

}
