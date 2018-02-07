package com.comp_3004.quest_cards.cards;

public class AmourCard extends AdventureCard {
	private int battlePoints;
	
	//constructor
	public AmourCard() {
		this.name = "Amour";
		this.battlePoints = 10;
	}
	
	//getters/setters
	public int getBattlePts() { return this.battlePoints; }
	
	
	//methods
	public void printCard() {
		System.out.printf("%-15s", name);
		System.out.printf("%-15s", battlePoints);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}
	
	

}