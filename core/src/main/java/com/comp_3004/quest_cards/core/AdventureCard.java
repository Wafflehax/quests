package com.comp_3004.quest_cards.core;

public abstract class AdventureCard extends Card {
	protected Player owner;	//player that currently owns the card (in hand/in play)
	State state;				//where the card is currently located
	protected enum State {
		HAND, PLAY, DECK, DISCARD
	}
	
	//constructor
	public AdventureCard() {
		this.owner = null;
		this.state = State.DECK;
	}
	
	//get set methods
		public void setOwner(Player p) { this.owner = p; }
		public Player getOwner() { return this.owner; }
		public void setState(State s) {this.state = s; }
		public State getState() { return this.state; }

}

