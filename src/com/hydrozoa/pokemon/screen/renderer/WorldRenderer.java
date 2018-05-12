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
	
	private TextureAtlas atlas;
	
	private List<Integer> renderedObjects = new ArrayList<Integer>();
	private List<YSortable> forRendering = new ArrayList<YSortable>();
	
	public WorldRenderer(AssetManager assetManager, World world) {
		this.assetManager = assetManager;
		this.world = world;;
		
		atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
	}
	
	public void render(SpriteBatch batch, Camera camera) {
		float worldStartX = Gdx.graphics.getWidth()/2 - camera.getCameraX()*Settings.SCALED_TILE_SIZE;
		float worldStartY = Gdx.graphics.getHeight()/2 - camera.getCameraY()*Settings.SCALED_TILE_SIZE;
		
		/* render tile terrains */
		for (int x = 0; x < world.getMap().getWidth(); x++) {
			for (int y = 0; y < world.getMap().getHeight(); y++) {
				String imageName = world.getMap().getTile(x, y).getTerrain().getImageName();
				TextureRegion render = null;
				if (!imageName.isEmpty()) { // Terrain NONE has no image
					render = atlas.findRegion(world.getMap().getTile(x, y).getTerrain().getImageName());
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
			if (loc instanceof Actor) {
				Actor a = (Actor)loc;
				if (!a.isVisible()) {
					continue;
				}
			}
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
