package com.comp_3004.quest_cards.cards;

public class AmourCard extends AdventureCard {
	private int battlePoints;
	
	//constructor
	public AmourCard() {
		this.name = "Amour";
		this.battlePoints = 10;
	}
	
	public void printCard() {
		System.out.printf("%-15s", this.name);
		System.out.printf("%-15s", this.battlePoints);
		System.out.printf("%s\n", this.getClass().getSimpleName());
	}
	
	public int getBattlePts() {
		return battlePoints;
	}

}