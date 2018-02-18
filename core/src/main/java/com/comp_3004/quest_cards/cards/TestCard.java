package com.comp_3004.quest_cards.cards;

public class TestCard extends AdventureCard {
	//attributes
	private int minBid;
	
	//constructor
	public TestCard(String n, int m) {
		this.name = n;
		this.minBid = m;
	}
	
	//constructor cards with no minimum bids
	public TestCard(String n) {
		this.name = n;
		this.minBid = 0;
	}
	
	//methods
	public int getMinBid() { return this.minBid; }
	public void setMinBid(int b) { this.minBid = b; }
	public String printCard() {			//prints the name, type, and ID of the cards
		return String.format("%-30s%-15s%s", name, minBid, getID());
	}

}