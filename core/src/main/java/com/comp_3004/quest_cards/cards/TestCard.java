package com.comp_3004.quest_cards.cards;

public class TestCard extends AdventureCard {
	
	//constructor
	public TestCard(String n) {
		this.name = n;
	}
	
	//methods
	public void printCard() {
		System.out.printf("%-30s", name);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}

}