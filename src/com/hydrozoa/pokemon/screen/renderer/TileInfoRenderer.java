package com.hydrozoa.pokemon.screen.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.hydrozoa.pokemon.Settings;
import com.hydrozoa.pokemon.model.Camera;
import com.hydrozoa.pokemon.model.world.World;

public class TileInfoRenderer {
	
	private BitmapFont font = new BitmapFont();
	
	private World world;
	private Camera cam;
	
	public TileInfoRenderer(World world, Camera cam) {
		this.world = world;
		this.cam = cam;
	}
	
	public void render(SpriteBatch batch, float mouseX, float mouseY) {
		float worldStartX = Gdx.graphics.getWidth()/2 - cam.getCameraX()*Settings.SCALED_TILE_SIZE;
		float worldStartY = Gdx.graphics.getHeight()/2 - cam.getCameraY()*Settings.SCALED_TILE_SIZE;
		
		float worldFloatX = mouseX-worldStartX;
		float worldFloatY = (Gdx.graphics.getHeight()-mouseY)-worldStartY;
		
		int tileX = (int) (worldFloatX/Settings.SCALED_TILE_SIZE);
		int tileY = (int) (worldFloatY/Settings.SCALED_TILE_SIZE);
		
		font.draw(batch, "X="+tileX+", Y="+tileY, 10f, Gdx.graphics.getHeight()-10f);
		
		
	}

}
