package com.comp_3004.quest_cards.cards;

public class AllyCard extends AdventureCard {
	private int battlePoints;
	private int bids;

	//constructors
	public AllyCard(String n, int bp, int bd) {
		this.name = n;
		this.battlePoints = bp;
		this.bids = bd;
	}
	
	//getters/setters
	public int getBattlePts() { return this.battlePoints; }
	
	//methods
	public void printCard() {
		System.out.printf("%-15s", name);
		System.out.printf("%-15s", battlePoints + "/" + bids);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}

	
}
