package com.comp_3004.quest_cards.cards;

public class TestCard extends AdventureCard {
	
	protected int mbid = 0; // minimum bids
	
	//constructor cards with no minimum bids
	public TestCard(String n) {
		this.name = n;
	}
	
	public TestCard(String n, int min) {
		this.name = n;
		this.mbid = min;
	}
	
	//methods
	public void printCard() {			//prints the name, type, and ID of the cards
		System.out.printf("%-30s", name);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}
	
	public int getBids() { return this.mbid; }

}