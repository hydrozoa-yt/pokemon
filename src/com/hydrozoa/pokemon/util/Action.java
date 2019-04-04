package com.hydrozoa.pokemon.util;

/**
 * Contains a single method. 
 * 
 * This is used to pass functionality to TransitionScreen#startTransition, 
 * about what's going to happen during a transition.
 * 
 * Also used to attach execution to UI elements.
 * 
 * @author hydrozoa
 */
public interface Action {

	public void action();
	
}
