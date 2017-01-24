package com.hydrozoa.pokemon.controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.hydrozoa.pokemon.dialogue.Dialogue;
import com.hydrozoa.pokemon.dialogue.DialogueNode;
import com.hydrozoa.pokemon.dialogue.DialogueTraverser;
import com.hydrozoa.pokemon.dialogue.DialogueNode.NODE_TYPE;
import com.hydrozoa.pokemon.ui.DialogueBox;
import com.hydrozoa.pokemon.ui.OptionBox;

/**
 * @author hydrozoa
 */
public class DialogueController extends InputAdapter {
	
	private DialogueTraverser traverser;
	private DialogueBox dialogueBox;
	private OptionBox optionBox;
	
	public DialogueController(DialogueBox box, OptionBox optionBox) {
		this.dialogueBox = box;
		this.optionBox = optionBox;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (dialogueBox.isVisible()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if (optionBox.isVisible()) {
			if (keycode == Keys.UP) {
				optionBox.moveUp();
				return true;
			} else if (keycode == Keys.DOWN) {
				optionBox.moveDown();
				return true;
			}
		}
		if (traverser != null && keycode == Keys.X && dialogueBox.isFinished()) { // continue through tree
			if (traverser.getType() == NODE_TYPE.END) {
				traverser = null;
				dialogueBox.setVisible(false);
			} else if (traverser.getType() == NODE_TYPE.LINEAR) {
				progress(0);
			} else if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
				progress(optionBox.getIndex());
			}
			return true;
		}
		if (dialogueBox.isVisible()) {
			return true;
		}
		return false;
	}
	
	public void update(float delta) {
		if (dialogueBox.isFinished() && traverser != null) {
			if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
				optionBox.setVisible(true);
			}
		}
	}
	
	public void startDialogue(Dialogue dialogue) {
		traverser = new DialogueTraverser(dialogue);
		dialogueBox.setVisible(true);
		dialogueBox.animateText(traverser.getText());
		if (traverser.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
			optionBox.clear();
			for (String s : dialogue.getNode(dialogue.getStart()).getLabels()) {
				optionBox.addOption(s);
			}
		}
	}
	
	private void progress(int index) {
		optionBox.setVisible(false);
		DialogueNode nextNode = traverser.getNextNode(index);
		dialogueBox.animateText(nextNode.getText());
		if (nextNode.getType() == NODE_TYPE.MULTIPLE_CHOICE) {
			optionBox.clearChoices();
			for (String s : nextNode.getLabels()) {
				optionBox.addOption(s);
			}
		}
	}
	
	public boolean isDialogueShowing() {
		return dialogueBox.isVisible();
	}
}
