package com.hydrozoa.pokemon.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Generates a Skin to slam on the UI. 
 * 
 * @author hydrozoa
 */
public class SkinGenerator {
	
	public static Skin generateSkin(AssetManager assetManager) {
		Skin skin = new Skin();
		
		if (!assetManager.isLoaded("res/graphics_packed/ui/uipack.atlas")) {
			throw new GdxRuntimeException("uipack.atlas was not loaded");
		}
		
		TextureAtlas uiAtlas = assetManager.get("res/graphics_packed/ui/uipack.atlas");
		
		NinePatch buttonSquareBlue = new NinePatch(uiAtlas.findRegion("dialoguebox"), 10, 10, 5, 5);
		skin.add("dialoguebox", buttonSquareBlue);
		
		NinePatch optionbox = new NinePatch(uiAtlas.findRegion("optionbox"),6, 6, 6, 6);
		skin.add("optionbox", optionbox);
		
		NinePatch battleinfobox = new NinePatch(uiAtlas.findRegion("battleinfobox"),14, 14, 5, 8);
		battleinfobox.setPadLeft((int)battleinfobox.getTopHeight());
		skin.add("battleinfobox", battleinfobox);
		
		skin.add("arrow", uiAtlas.findRegion("arrow"), TextureRegion.class);
		skin.add("hpbar_side", uiAtlas.findRegion("hpbar_side"), TextureRegion.class);
		skin.add("hpbar_bar", uiAtlas.findRegion("hpbar_bar"), TextureRegion.class);
		skin.add("green", uiAtlas.findRegion("green"), TextureRegion.class);
		skin.add("yellow", uiAtlas.findRegion("yellow"), TextureRegion.class);
		skin.add("red", uiAtlas.findRegion("red"), TextureRegion.class);
		skin.add("background_hpbar", uiAtlas.findRegion("background_hpbar"), TextureRegion.class);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("res/font/pkmnrsi.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 12;
		parameter.color = new Color(96f/255f, 96f/255f, 96f/255f, 1f);
		parameter.shadowColor = new Color(208f/255f, 208f/255f, 200f/255f, 1f);
		parameter.shadowOffsetX = 1;
		parameter.shadowOffsetY = 1;
		parameter.characters = "!  \"  #  $  %  &  '  (  )  *  +  ,  -  .  /  0  1  2  3  4  5  6  7  8  9  :  ;  <  =  >  ?  @  A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z  [  \\  ]  ^  _  `  a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  t  u  v  w  x  y  z  {  |  }  ~  \u2190  \u2191  \u2192  \u2193  \u2640  \u2642";
		
		BitmapFont font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		font.getData().setLineHeight(16f);
		skin.add("font", font);
		
		BitmapFont smallFont = assetManager.get("res/font/small_letters_font.fnt", BitmapFont.class);
		skin.add("small_letters_font", smallFont);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = skin.getFont("font");
		skin.add("default", labelStyle);
		
		LabelStyle labelStyleSmall = new LabelStyle();
		labelStyleSmall.font = skin.getFont("small_letters_font");
		skin.add("smallLabel", labelStyleSmall);
		
		return skin;
	}

}
