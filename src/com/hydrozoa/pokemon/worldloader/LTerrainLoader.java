package com.hydrozoa.pokemon.worldloader;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LTerrainLoader extends AsynchronousAssetLoader<LTerrainDb, LTerrainLoader.LTerraintDbParameter> {
	
	private LTerrainDb terrainDb = new LTerrainDb();
	
	public LTerrainLoader(FileHandleResolver resolver) {
		super(resolver);
	}
	
	@Override
	public void loadAsync(AssetManager asman, String filename, FileHandle file, LTerraintDbParameter parameter) {
		XmlReader xr = new XmlReader();
		
		Element root = null;
		try {
			root = xr.parse(file.reader());
		} catch (IOException e) {
			e.printStackTrace();
			Gdx.app.exit();
		}
		
		if (!root.getName().equals("LTerrain")) {
			System.err.println("Root node in "+filename+" is "+root.getName()+" expected LTerrain");
			Gdx.app.exit();
		}
		
		for (int i = 0; i < root.getChildCount(); i++) {
			Element obj = root.getChild(i);
			if (!obj.getName().equals("terrain")) {
				System.err.println("Found " + obj.getName() +"-element where expected terrain-element in "+filename);
				Gdx.app.exit();
			}
			String name = obj.get("name");
			
			Element imageName = obj.getChild(0);
			if (!imageName.getName().equals("imageName")) {
				System.err.println("Found " + imageName.getName() +"-element where expected imageName-element in "+filename);
				Gdx.app.exit();
			}
			String imageNameString;
			if (imageName.getText() != null) {
				imageNameString = imageName.getText();
			} else {
				imageNameString = "";
			}
			
			LTerrain justLoaded = new LTerrain(imageNameString);
			terrainDb.addTerrain(name, justLoaded);
		}
	}
	
	@Override
	public LTerrainDb loadSync(AssetManager arg0, String arg1, FileHandle arg2, LTerraintDbParameter arg3) {
		return terrainDb;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Array<AssetDescriptor> getDependencies(String filename, FileHandle file, LTerraintDbParameter parameter) {
		Array<AssetDescriptor> ad = new Array<AssetDescriptor>();
		return ad;
	}
	
	static public class LTerraintDbParameter extends AssetLoaderParameters<LTerrainDb> {
	}

}
