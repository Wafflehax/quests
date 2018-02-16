package com.comp_3004.quest_cards.cards;

public class TestCard extends AdventureCard {
	
	//constructor
	public TestCard(String n) {
		this.name = n;
	}
	
	//methods
	public String printCard() {			//prints the name, type, and ID of the cards
		return String.format("%-30s%s", name, getID());
	}

}