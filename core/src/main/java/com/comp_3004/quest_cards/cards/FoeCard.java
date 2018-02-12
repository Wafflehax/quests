package com.comp_3004.quest_cards.cards;

public class FoeCard extends AdventureCard {
	
	//attributes
	private int altBattlePts;					//the battlepoints used if the foe is named in the current quest
	
	//constructors
	public FoeCard(String n, int b) {			//constructor for normal foes
		this.name = n;
		this.battlePts = b;
	}
	public FoeCard(String n, int b1, int b2) {	//constructor for foes with alternate battlepoints
		this.name = n;
		this.battlePts = b1;
		this.altBattlePts = b2;
	}
	
	//getters/setters
	public int getAltBattlePts() { return this.altBattlePts; }
	
	//methods
	public void printCard() {				//prints the name, battlepoints/altbattlepoints, type, and ID of card
		System.out.printf("%-15s", name);
		if(altBattlePts != 0)
			System.out.printf("%-15s", battlePts + "/" + altBattlePts);
		else
			System.out.printf("%-15s", battlePts);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}

}
