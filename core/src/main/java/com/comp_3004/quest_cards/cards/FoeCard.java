package com.comp_3004.quest_cards.cards;

public class FoeCard extends AdventureCard {
	private int battlePoints;
	private int altBattlePoints;
	
	//constructors
	public FoeCard(String n, int b) {
		this.name = n;
		this.battlePoints = b;
	}
	public FoeCard(String n, int b1, int b2) {
		this.name = n;
		this.battlePoints = b1;
		this.altBattlePoints = b2;
	}
	
	//getters/setters
	public int getBattlePts() { return this.battlePoints; }
	public int getAltBattlePts() { return this.altBattlePoints; }
	
	//methods
	public void printCard() {
		System.out.printf("%-15s", name);
		if(altBattlePoints != 0)
			System.out.printf("%-15s", battlePoints + "/" + altBattlePoints);
		else
			System.out.printf("%-15s", battlePoints);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}

}
