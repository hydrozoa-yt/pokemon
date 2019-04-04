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
import com.hydrozoa.pokemon.dialogue.Dialogue;
import com.hydrozoa.pokemon.dialogue.LinearDialogueNode;

/**
 * @author hydrozoa
 */
public class DialogueLoader extends AsynchronousAssetLoader<DialogueDb, DialogueLoader.DialogueDbParameter> {
	
	private DialogueDb diagDb = new DialogueDb();
	
	public DialogueLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager asman, String filename, FileHandle file, DialogueDbParameter parameter) {
		XmlReader xr = new XmlReader();
		
		Element root = null;
		try {
			root = xr.parse(file.reader());
		} catch (IOException e) {
			e.printStackTrace();
			Gdx.app.exit();
		}
		
		if (!root.getName().equals("Dialogues")) {
			System.err.println("Root node in "+filename+" is "+root.getName()+" expected Dialogues");
			Gdx.app.exit();
		}
		
		for (int i = 0; i < root.getChildCount(); i++) {
			Element loadDialogue = root.getChild(i);
			if (!loadDialogue.getName().equals("dialogue")) {
				System.err.println("Found " + loadDialogue.getName() +"-element where expected dialogue-element in "+filename);
				Gdx.app.exit();
			}
			String attrName = loadDialogue.getAttribute("name");
			
			Dialogue dialogue = new Dialogue();
			
			for (int k = 0; k < loadDialogue.getChildCount(); k++) {
				Element node = loadDialogue.getChild(i);
				if (node.getName().equalsIgnoreCase("linear")) {
					int id = Integer.parseInt(node.getAttribute("id"));
					String text = node.getAttribute("text");
					
					int target = -1;
					Element pointer = node.getChildByName("pointer");
					if (pointer != null) {
						target = Integer.parseInt(pointer.getAttribute("target"));
					}
					
					LinearDialogueNode linearNode = new LinearDialogueNode(text, id);
					if (target > -1) {
						linearNode.setPointer(target);
					}
					dialogue.addNode(linearNode);
				}
			}
			
			diagDb.addTerrain(attrName, dialogue);
			System.out.println("\t Loaded dialogue "+attrName);
		}
	}



	@Override
	public DialogueDb loadSync(AssetManager assetManager, String filename, FileHandle file, DialogueDbParameter parameter) {
		return diagDb;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String arg0, FileHandle arg1, DialogueDbParameter arg2) {
		return null;
	}
	
	static public class DialogueDbParameter extends AssetLoaderParameters<DialogueDb> {}

}
