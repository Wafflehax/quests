package com.comp_3004.quest_cards.cards;

public class AllyCard extends AdventureCard {
	
	//attributes
	private int bids;				//the bids the card adds to the player

	//constructors
	public AllyCard(String n, int bp, int bd) {
		this.name = n;
		this.battlePts = bp;
		this.bids = bd;
	}
	
	//getters/setters
	public int getBids() { return this.bids; }
	
	//methods
	public void printCard() {			//prints name, battlepoints, bids, type, and ID of the card
		System.out.printf("%-15s", name);
		System.out.printf("%-15s", battlePts + "/" + bids);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}

	
}
