package com.hydrozoa.pokemon.screen.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * Debug rendering on top of the BattleRenderer. This has access to all the features of the BattleRenderer.
 * 
 * @author hydrozoa
 */
public class BattleDebugRenderer {
	
	private BattleRenderer battleRenderer;
	private ShapeRenderer shapeRenderer;
	
	public BattleDebugRenderer(BattleRenderer battleRenderer) {
		this.battleRenderer = battleRenderer;
		shapeRenderer = new ShapeRenderer();
	}
	
	public void render() {
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(
				battleRenderer.playerSquareMiddleX()-battleRenderer.squareSize(), 
				battleRenderer.playerSquareMiddleY()-battleRenderer.squareSize(), 
				battleRenderer.squareSize()*2, 
				battleRenderer.squareSize()*2);
		shapeRenderer.rect(
				battleRenderer.opponentSquareMiddleX()-battleRenderer.squareSize(), 
				battleRenderer.opponentSquareMiddleY()-battleRenderer.squareSize(), 
				battleRenderer.squareSize()*2, 
				battleRenderer.squareSize()*2);
		shapeRenderer.end();
	}

}
