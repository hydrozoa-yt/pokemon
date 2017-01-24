package com.hydrozoa.pokemon.battle.event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hydrozoa.pokemon.battle.BATTLE_PARTY;

/**
 * Changes the Pokemon sprites in the battle.
 * 
 * @author hydrozoa
 */
public class PokeSpriteEvent extends Event {
	
	private Texture region;
	private BATTLE_PARTY party;
	
	public PokeSpriteEvent(Texture region, BATTLE_PARTY party) {
		this.region = region;
		this.party = party;
	}
	
	@Override
	public void begin(EventPlayer player) {
		super.begin(player);
		player.setPokemonSprite(region, party);
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public boolean finished() {
		return true;
	}

}
