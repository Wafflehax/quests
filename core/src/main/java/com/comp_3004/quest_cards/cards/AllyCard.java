package com.comp_3004.quest_cards.cards;

public class AllyCard extends AdventureCard {
	
	//attributes
	protected int bids;				//the bids the card adds to the player

	//constructors
	public AllyCard(String n, int bp, int bd) {
		this.name = n;
		this.battlePts = bp;
		this.bids = bd;
	}
	
	//getters/setters
	public int getBids() { return this.bids; }
	
	//methods
	public String printCard() {			//prints name, battlepoints, bids, type, and ID of the card
		return String.format("%-15s%-15s%s", name, battlePts + "/" + bids, getID());
	}

	
}
