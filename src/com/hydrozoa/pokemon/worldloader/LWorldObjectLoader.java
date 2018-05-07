package com.hydrozoa.pokemon.worldloader;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LWorldObjectLoader extends AsynchronousAssetLoader<LWorldObjectDb, LWorldObjectLoader.LWorldObjectDbParameter> {
	
	private LWorldObjectDb objDb = new LWorldObjectDb();
	
	public LWorldObjectLoader(FileHandleResolver resolver) {
		super(resolver);
	}
	
	@Override
	public void loadAsync(AssetManager asman, String filename, FileHandle file, LWorldObjectDbParameter parameter) {
		XmlReader xr = new XmlReader();
		
		Element root = null;
		try {
			root = xr.parse(file.reader());
		} catch (IOException e) {
			e.printStackTrace();
			Gdx.app.exit();
		}
		
		if (!root.getName().equals("LWorldObjects")) {
			System.err.println("Root node in "+filename+" is "+root.getName()+" expected LWorldObjects");
			Gdx.app.exit();
		}
		
		for (int i = 0; i < root.getChildCount(); i++) {
			Element obj = root.getChild(i);
			if (!obj.getName().equals("obj")) {
				System.err.println("Found " + obj.getName() +"-element where expected obj-element in "+filename);
				Gdx.app.exit();
			}
			String name = obj.get("name");
			
			Element imageName = obj.getChild(0);
			if (!imageName.getName().equals("imageName")) {
				System.err.println("Found " + imageName.getName() +"-element where expected imageName-element in "+filename);
				Gdx.app.exit();
			}
			String imageNameString = imageName.getText();
			
			Element size = obj.getChild(1);
			if (!size.getName().equals("size")) {
				System.err.println("Found " + imageName.getName() +"-element where expected size-element in "+filename);
				Gdx.app.exit();
			}
			float sizeX = size.getFloat("x");
			float sizeY = size.getFloat("y");
			
			Element tilesElement = obj.getChild(2);
			if (!tilesElement.getName().equals("tiles")) {
				System.err.println("Found " + imageName.getName() +"-element where expected tiles-element in "+filename);
				Gdx.app.exit();
			}
			
			GridPoint2[] tiles = new GridPoint2[tilesElement.getChildCount()];
			
			for (int k = 0; k < tilesElement.getChildCount(); k++) {
				Element tileElement = tilesElement.getChild(k);
				int x = tileElement.getInt("x");
				int y = tileElement.getInt("y");
				tiles[k] = new GridPoint2(x,y);
			}
			
			LWorldObject justLoaded = new LWorldObject(imageNameString, sizeX, sizeY, tiles);
			objDb.addObject(name, justLoaded);
		}
	}
	
	@Override
	public LWorldObjectDb loadSync(AssetManager arg0, String arg1, FileHandle arg2, LWorldObjectDbParameter arg3) {
		return objDb;
	}
	
	@Override
	public Array<AssetDescriptor> getDependencies(String filename, FileHandle file, LWorldObjectDbParameter parameter) {
		Array<AssetDescriptor> ad = new Array<AssetDescriptor>();
		//ad.add(new AssetDescriptor("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class));
		return ad;
	}
	
	static public class LWorldObjectDbParameter extends AssetLoaderParameters<LWorldObjectDb> {
	}

}
