package com.comp_3004.quest_cards.cards;

public abstract class StoryCard extends Card {
	
	//methods
	public String printCard() {			//prints the name, type, and ID of the card
		return String.format("%-40s%s", name, getID());
	}
}
