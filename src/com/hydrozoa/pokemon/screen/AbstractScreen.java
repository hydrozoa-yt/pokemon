package com.hydrozoa.pokemon.screen;

import com.badlogic.gdx.Screen;
import com.hydrozoa.pokemon.PokemonGame;

/**
 * @author hydrozoa
 */
public abstract class AbstractScreen implements Screen {
	
	private PokemonGame app;
	
	public AbstractScreen(PokemonGame app) {
		this.app = app;
	}

	@Override
	public abstract void dispose();

	@Override
	public abstract void hide();

	@Override
	public abstract void pause();
	
	public abstract void update(float delta);

	@Override
	public abstract void render(float delta);

	@Override
	public abstract void resize(int width, int height);

	@Override
	public abstract void resume();

	@Override
	public abstract void show();
	
	public PokemonGame getApp() {
		return app;
	}

}
