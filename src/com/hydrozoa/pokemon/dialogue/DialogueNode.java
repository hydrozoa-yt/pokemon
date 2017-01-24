package com.hydrozoa.pokemon.dialogue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hydrozoa
 */
public class DialogueNode {
	
	private ArrayList<Integer> pointers = new ArrayList<Integer>();
	private ArrayList<String> labels = new ArrayList<String>();
	
	private String text;
	private int id;
	
	private NODE_TYPE type;
	
	public enum NODE_TYPE {
		MULTIPLE_CHOICE, // text, pointers with labels
		LINEAR,			 // text, single unlabeled pointer
		END,			 // text, no pointer
		;
	}
	
	public DialogueNode(String text, int id) {
		this.text = text;
		this.id = id;
		type = NODE_TYPE.END;
	}
	
	public void addChoice(String option, int nodeId) {
		if (type == NODE_TYPE.LINEAR) {
			pointers.clear();
		}
		labels.add(option);
		pointers.add(nodeId);
		type = NODE_TYPE.MULTIPLE_CHOICE;
	}
	
	public void makeLinear(int nodeId) {
		pointers.clear();
		labels.clear();
		pointers.add(nodeId);
		type = NODE_TYPE.LINEAR;
	}
	
	public List<Integer> getPointers() {
		return pointers;
	}
	
	public List<String> getLabels() {
		return labels;
	}
	
	public NODE_TYPE getType() {
		return type;
	}
	
	public String getText() {
		return text;
	}
	
	public int getID() {
		return id;
	}
}
