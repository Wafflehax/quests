package com.comp_3004.quest_cards.cards;

public abstract class StoryCard extends Card {
	
	//methods
	public void printCard() {			//prints the name, type, and ID of the card
		System.out.printf("%-40s", this.name);
		System.out.printf("%-20s", this.getClass().getSimpleName());
		System.out.printf("%s\n" , this.getID());
	}
}
