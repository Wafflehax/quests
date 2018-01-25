package com.comp_3004.quest_cards.core;

public abstract class Adventure extends Card {
	protected Player owner;	//player that currently owns the card (in hand/in play)
	State state;
	//create enum to represent state
	protected enum State {
		HAND, PLAY, DECK, DISCARD
	}
	
	//constructor
	public Adventure() {
		this.owner = null;
		this.state = State.DECK;
	}
}

