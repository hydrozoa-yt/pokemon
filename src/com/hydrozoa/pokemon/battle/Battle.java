package com.hydrozoa.pokemon.battle;

import java.util.ArrayList;
import java.util.List;

import com.hydrozoa.pokemon.battle.animation.FaintingAnimation;
import com.hydrozoa.pokemon.battle.animation.PokeballAnimation;
import com.hydrozoa.pokemon.battle.event.AnimationBattleEvent;
import com.hydrozoa.pokemon.battle.event.BattleEvent;
import com.hydrozoa.pokemon.battle.event.BattleEventQueuer;
import com.hydrozoa.pokemon.battle.event.BattleEventPlayer;
import com.hydrozoa.pokemon.battle.event.HPAnimationEvent;
import com.hydrozoa.pokemon.battle.event.NameChangeEvent;
import com.hydrozoa.pokemon.battle.event.PokeSpriteEvent;
import com.hydrozoa.pokemon.battle.event.TextEvent;
import com.hydrozoa.pokemon.battle.moves.Move;
import com.hydrozoa.pokemon.model.Pokemon;

/**
 * A 100% real Pokemon fight! Right in your livingroom.
 * 
 * @author hydrozoa
 */
public class Battle implements BattleEventQueuer {
	
	public enum STATE {
		READY_TO_PROGRESS,
		SELECT_NEW_POKEMON,
		RAN,
		WIN,
		LOSE,
		;
	}
	
	private STATE state;
	
	private BattleMechanics mechanics;
	
	private Pokemon player;
	private Pokemon opponent;
	
	private Trainer playerTrainer;
	private Trainer opponentTrainer;

	private BattleEventPlayer eventPlayer; 
	
	public Battle(Trainer player, Pokemon opponent) {
		this.playerTrainer = player;
		this.player = player.getPokemon(0);
		this.opponent = opponent;
		mechanics = new BattleMechanics();
		this.state = STATE.READY_TO_PROGRESS;
	}
	
	/**
	 * Plays appropiate animation for starting a battle
	 */
	public void beginBattle() {
		queueEvent(new PokeSpriteEvent(opponent.getSprite(), BATTLE_PARTY.OPPONENT));
		queueEvent(new TextEvent("Go "+player.getName()+"!", 1f));
		queueEvent(new PokeSpriteEvent(player.getSprite(), BATTLE_PARTY.PLAYER));
		queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation()));
	}
	
	
	/**
	 * Progress the battle one turn. 
	 * @param input		Index of the move used by the player
	 */
	public void progress(int input) {
		if (state != STATE.READY_TO_PROGRESS) {
			return;
		}
		if (mechanics.goesFirst(player, opponent)) {
			playTurn(BATTLE_PARTY.PLAYER, input);	
			if (state == STATE.READY_TO_PROGRESS) {
				playTurn(BATTLE_PARTY.OPPONENT, 0);
			}
		} else {
			playTurn(BATTLE_PARTY.OPPONENT, 0);
			if (state == STATE.READY_TO_PROGRESS) {
				playTurn(BATTLE_PARTY.PLAYER, input);
			}
		}
		/*
		 * XXX: Status effects go here.
		 */
	}
	
	/**
	 * Sends out a new Pokemon, in the case that the old one fainted.
	 * This will NOT take up a turn.
	 * @param pokemon	Pokemon the trainer is sending in
	 */
	public void chooseNewPokemon(Pokemon pokemon) {
		this.player = pokemon;
		queueEvent(new HPAnimationEvent(
				BATTLE_PARTY.PLAYER, 
				pokemon.getCurrentHitpoints(), 
				pokemon.getCurrentHitpoints(), 
				pokemon.getStat(STAT.HITPOINTS), 
				0f));
		queueEvent(new PokeSpriteEvent(pokemon.getSprite(), BATTLE_PARTY.PLAYER));
		queueEvent(new NameChangeEvent(pokemon.getName(), BATTLE_PARTY.PLAYER));
		queueEvent(new TextEvent("Go get 'em, "+pokemon.getName()+"!"));
		queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation()));
		this.state = STATE.READY_TO_PROGRESS;
	}
	
	/**
	 * Attempts to run away
	 */
	public void attemptRun() {
		queueEvent(new TextEvent("Got away successfully...", true));
		this.state = STATE.RAN;
	}
	
	private void playTurn(BATTLE_PARTY user, int input) {
		BATTLE_PARTY target = BATTLE_PARTY.getOpposite(user);
		
		Pokemon pokeUser = null;
		Pokemon pokeTarget = null;
		if (user == BATTLE_PARTY.PLAYER) {
			pokeUser = player;
			pokeTarget = opponent;
		} else if (user == BATTLE_PARTY.OPPONENT) {
			pokeUser = opponent;
			pokeTarget = player;
		}
		
		Move move = pokeUser.getMove(input);
		
		/* Broadcast the text graphics */
		queueEvent(new TextEvent(pokeUser.getName()+" used\n"+move.getName().toUpperCase()+"!", 0.5f));
		
		if (mechanics.attemptHit(move, pokeUser, pokeTarget)) {
			move.useMove(mechanics, pokeUser, pokeTarget, user, this);
		} else { // miss
			/* Broadcast the text graphics */
			queueEvent(new TextEvent(pokeUser.getName()+"'s\nattack missed!", 0.5f));
		}
		
		if (player.isFainted()) {
			queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new FaintingAnimation()));
			boolean anyoneAlive = false;
			for (int i = 0; i < getPlayerTrainer().getTeamSize(); i++) {
				if (!getPlayerTrainer().getPokemon(i).isFainted()) {
					anyoneAlive = true;
					break;
				}
			}
			if (anyoneAlive) {
				queueEvent(new TextEvent(player.getName()+" fainted!", true));
				this.state = STATE.SELECT_NEW_POKEMON;
			} else {
				queueEvent(new TextEvent("Unfortunately, you've lost...", true));
				this.state = STATE.LOSE;
			}
		} else if (opponent.isFainted()) {
			queueEvent(new AnimationBattleEvent(BATTLE_PARTY.OPPONENT, new FaintingAnimation()));
			queueEvent(new TextEvent("Congratulations! You Win!", true));
			this.state = STATE.WIN;
		}
	}
	
	public Pokemon getPlayerPokemon() {
		return player;
	}
	
	public Pokemon getOpponentPokemon() {
		return opponent;
	}
	
	public Trainer getPlayerTrainer() {
		return playerTrainer;
	}
	
	public Trainer getOpponentTrainer() {
		return opponentTrainer;
	}
	
	public STATE getState() {
		return state;
	}

	public void setEventPlayer(BattleEventPlayer player) {
		this.eventPlayer = player;
	}
	
	@Override
	public void queueEvent(BattleEvent event) {
		eventPlayer.queueEvent(event);
	}
}
