package com.hydrozoa.pokemon.controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.hydrozoa.pokemon.dialogue.Dialogue;
import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.Tile;
import com.hydrozoa.pokemon.model.actor.Actor;
import com.hydrozoa.pokemon.model.world.World;

/**
 * Controller that interacts with what is in front of the Actor being controlled.
 * 
 * @author hydrozoa
 */
public class InteractionController extends InputAdapter {
	
	private Actor a;
	private World w;
	private DialogueController dialogueController;
	
	public InteractionController(Actor a, World w, DialogueController dialogueController) {
		this.a = a;
		this.w = w;
		this.dialogueController = dialogueController;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.X) {
			Tile target = w.getMap().getTile(a.getX()+a.getFacing().getDX(), a.getY()+a.getFacing().getDY());
			if (target.getActor() != null) {
				Actor targetActor = target.getActor();
				if (targetActor.getDialogue() != null) {
					if (targetActor.refaceWithoutAnimation(DIRECTION.getOpposite(a.getFacing()))){
						Dialogue dialogue = targetActor.getDialogue();
						dialogueController.startDialogue(dialogue);
					}
				}
			}
			return false;
		}
		return false;
	}

}
