package com.hydrozoa.pokemon.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hydrozoa.pokemon.PokemonGame;
import com.hydrozoa.pokemon.screen.transition.Action;
import com.hydrozoa.pokemon.screen.transition.Transition;

/**
 * Used for transitions between screens.
 * 
 * @author hydrozoa 
 */
public class TransitionScreen extends AbstractScreen {
	
	private AbstractScreen from;
	private AbstractScreen to;
	
	private Transition outTransition;
	private Transition inTransition;
	
	private Action action;

	private SpriteBatch batch;
	private Viewport viewport;
	
	private TRANSITION_STATE state;
	
	private enum TRANSITION_STATE {
		OUT,
		IN,
		;
	}
	
	public TransitionScreen(PokemonGame app) {
		super(app);
		batch = new SpriteBatch();
		viewport = new ScreenViewport();
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}
	
	@Override
	public void update(float delta) {
		if (state == TRANSITION_STATE.OUT) {
			outTransition.update(delta);
			if (outTransition.isFinished()) {
				action.action();
				state = TRANSITION_STATE.IN;
				return;
			}
		} else if (state == TRANSITION_STATE.IN) {
			inTransition.update(delta);
			if (inTransition.isFinished()) {
				getApp().setScreen(to);
			}
		}
	}

	@Override
	public void render(float delta) {
		if (state == TRANSITION_STATE.OUT) {
			from.render(delta);
			
			viewport.apply();
			outTransition.render(delta, batch);
		} else if (state == TRANSITION_STATE.IN) {
			to.render(delta);
			
			viewport.apply();
			inTransition.render(delta, batch);
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		to.resize(width, height);
		from.resize(width, height);
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		
	}
	
	public void startTransition(AbstractScreen from, AbstractScreen to, Transition out, Transition in, Action action) {
		this.from = from;
		this.to = to;
		this.outTransition = out;
		this.inTransition = in;
		this.action = action;
		this.state = TRANSITION_STATE.OUT;
		getApp().setScreen(this);
	}
}
