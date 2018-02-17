package com.comp_3004.quest_cards.cards;

public class TestCard extends AdventureCard {
	
	//attributes
	int minBid;
	
	//constructor
	public TestCard(String n, int m) {
		this.name = n;
		this.minBid = m;
	}
	
	//methods
	public int getMinBid() { return this.minBid; }
	public String printCard() {			//prints the name, type, and ID of the cards
		return String.format("%-30s%-15s%s", name, minBid, getID());
	}

}