package com.comp_3004.quest_cards.cards;

public class AmourCard extends AdventureCard {
	
	//attributes
	private int bids;					//the bids the card adds to the player
	
	//constructor
	public AmourCard() {
		this.name = "Amour";
		this.battlePts = 10;
		this.bids = 1;
	}
	
	//getters/setters
	public int getBids() { return this.bids; }
	
	//methods
	public void printCard() {				//prints name, battlepoints, bids, type, and ID of the card
		System.out.printf("%-15s", name);
		System.out.printf("%-15s", battlePts);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}
	
	

}