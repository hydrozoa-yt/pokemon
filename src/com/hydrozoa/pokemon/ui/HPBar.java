package com.hydrozoa.pokemon.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Widget that displays HP.
 * 
 * @author hydrozoa
 */
public class HPBar extends Widget {
	
	private enum STATE {
		ANIMATING, IDLE;
	}
	
	private Skin skin;
	
	private float hpAmount = 1f;
	private Table hp;
	
	private Drawable green;
	private Drawable yellow;
	private Drawable red;
	private Drawable background_hpbar;
	private Drawable hp_left;
	private Drawable hp_bar;
	
	private STATE state = STATE.IDLE;
	
	private float timer = 0f;
	private float animationDuration = 0f;
	
	private float hpStart;
	private float hpEnd;
	
	public HPBar(Skin skin) {
		super();
		this.skin = skin;
		
		green = skin.getDrawable("green");
		yellow = skin.getDrawable("yellow");
		red = skin.getDrawable("red");
		background_hpbar = skin.getDrawable("background_hpbar");
		hp_left = skin.getDrawable("hpbar_side");
		hp_bar = skin.getDrawable("hpbar_bar");
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		int padLeft = 1;
		int padRight = 2;
		int padTop = 2;
		int padBottom = 2;
		
		float hpWidth = hpAmount * (hp_bar.getMinWidth()-padLeft-padRight);
		
		Drawable hpColor = null;
		if (hpAmount <= 0.1) {
			hpColor = red;
		} else if (hpAmount <= 0.5) {
			hpColor = yellow;
		} else {
			hpColor = green;
		}
		
		/* HP LOGO TO THE LEFT */
		hp_left.draw(batch, this.getX(), this.getY(), hp_left.getMinWidth(), hp_left.getMinHeight());
		
		/* BACKGROUND OF THE BAR */
		background_hpbar.draw(batch, this.getX()+hp_left.getMinWidth()+padLeft, this.getY()+padBottom, hp_bar.getMinWidth()-padRight-padLeft, hp_bar.getMinHeight()-padTop-padBottom);
		
		/* ACTUAL COLORED HP */
		hpColor.draw(batch, this.getX()+hp_left.getMinWidth()+padLeft, this.getY()+padBottom, hpWidth, (hp_bar.getMinHeight()-padTop-padBottom));
		
		/* HP BAR FRAME */
		hp_bar.draw(batch, this.getX()+hp_left.getMinWidth(), this.getY(), hp_bar.getMinWidth(), hp_bar.getMinHeight());
	}
	
	@Override
	public float getMinHeight() {
		return hp_left.getMinHeight();
	}
	
	@Override
	public float getMinWidth() {
		return hp_left.getMinWidth()+hp_bar.getMinWidth();
	}
	
	/**
	 * Makes the bar display a specific amount of HP left.
	 * @param hp	float value between 0 and 1
	 */
	public void displayHPLeft(float hp) {
		this.hpAmount = hp;
		hpAmount = MathUtils.clamp(hpAmount, 0f, 1f);
	}
}
