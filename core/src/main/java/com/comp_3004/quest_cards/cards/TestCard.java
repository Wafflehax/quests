package com.comp_3004.quest_cards.cards;

public class TestCard extends AdventureCard {
	
	//constructor
	public TestCard(String n) {
		this.name = n;
	}

	public void printCard() {
		System.out.printf("%-30s", this.name);
		System.out.printf("%s\n", this.getClass().getSimpleName());
	}

}