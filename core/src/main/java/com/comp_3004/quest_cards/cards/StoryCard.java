package com.comp_3004.quest_cards.cards;

public abstract class StoryCard extends Card {
	
	
	public void printCard() {
		System.out.printf("%-40s", this.name);
		System.out.printf("%s\n", this.getClass().getSimpleName());
	}
	
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
