package com.hydrozoa.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FontRenderingTest extends ApplicationAdapter {
	
	private OrthographicCamera camera;
	private BitmapFont font;
	private SpriteBatch batch;
	
	@Override
	public void create() {
		font = new BitmapFont(Gdx.files.internal("res/font/small_letters_font.fnt"));
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 130, 130);
	}
	
	@Override
	public void render() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font.draw(batch, "ABCDEFGHIJKLMNOPQRSTUVWXYZ\nabcdefghijklmnopqrstuvwxyz", 0+1, 130-2);
		batch.end();
	}
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Font Rendering Test";
		config.height = 400;
		config.width = 400;
		
		new LwjglApplication(new FontRenderingTest(), config);
	}
}
