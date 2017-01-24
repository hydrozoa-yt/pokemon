package com.hydrozoa.pokemon.screen.transition;

/**
 * Contains a single method. This is to pass functionality to TransitionScreen#startTransition, 
 * about what's going to happen during a transition.
 * 
 * @author hydrozoa
 */
public interface Action {

	public void action();
}
