package com.comp_3004.quest_cards.core;

public class FoeCard extends AdventureCard {
	private int battlePoints;
	private int altBattlePoints;
	
	//constructor
	public FoeCard(String n, int b) {
		this.name = n;
		this.battlePoints = b;
	}
	public FoeCard(String n, int b1, int b2) {
		super();
		this.name = n;
		this.battlePoints = b1;
		this.altBattlePoints = b2;
	}
	
	public void printCard() {
		System.out.printf("%-15s", this.name);
		if(altBattlePoints != 0)
			System.out.printf("%-15s", this.battlePoints + "/" + this.altBattlePoints);
		else
			System.out.printf("%-15s", this.battlePoints);
		System.out.printf("%s\n", this.getClass().getSimpleName());
	}

}
