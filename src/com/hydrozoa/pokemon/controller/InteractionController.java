package com.hydrozoa.pokemon.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.hydrozoa.pokemon.dialogue.Dialogue;
import com.hydrozoa.pokemon.model.DIRECTION;
import com.hydrozoa.pokemon.model.Tile;
import com.hydrozoa.pokemon.model.actor.Actor;

/**
 * Controller that interacts with what is in front of the player Actor.
 * 
 * @author hydrozoa
 */
public class InteractionController extends InputAdapter {
	
	private Actor a;
	private DialogueController dialogueController;
	
	public InteractionController(Actor a, DialogueController dialogueController) {
		this.a = a;
		this.dialogueController = dialogueController;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.X) {
			Tile target = a.getWorld().getMap().getTile(a.getX()+a.getFacing().getDX(), a.getY()+a.getFacing().getDY());
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
