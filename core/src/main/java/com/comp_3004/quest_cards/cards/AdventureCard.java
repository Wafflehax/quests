package com.comp_3004.quest_cards.cards;

import com.comp_3004.quest_cards.player.Player;

public abstract class AdventureCard extends Card {
	
	//attributes
	protected Player owner;		//player that currently owns the card (in hand/in play)
	protected int battlePts;		//the battlepoints the card adds to the player or quest stage
	State state;					//where the card is currently located
	public enum State {
		HAND, PLAY, QUEST, STAGE, DECK, DISCARD
	}
	
	//constructor
	public AdventureCard() {
		this.owner = null;
		this.state = State.DECK;
	}
	
	//get set methods
	public void setOwner(Player p) { this.owner = p; }
	public Player getOwner() { return this.owner; }
	public int getBattlePts() { return this.battlePts; }
	public void setState(State s) {this.state = s; }
	public State getState() { return this.state; }

}

