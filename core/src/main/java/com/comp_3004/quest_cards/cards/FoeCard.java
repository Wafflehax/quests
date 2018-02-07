package com.comp_3004.quest_cards.cards;

public class FoeCard extends AdventureCard {
	
	//attributes
	private int battlePoints;					//the battlepoints the card adds to the player or quest stage
	private int altBattlePoints;					//the battlepoints used if the foe is named in the current quest
	
	//constructors
	public FoeCard(String n, int b) {			//constructor for normal foes
		this.name = n;
		this.battlePoints = b;
	}
	public FoeCard(String n, int b1, int b2) {	//constructor for foes with alternate battlepoints
		this.name = n;
		this.battlePoints = b1;
		this.altBattlePoints = b2;
	}
	
	//getters/setters
	public int getBattlePts() { return this.battlePoints; }
	public int getAltBattlePts() { return this.altBattlePoints; }
	
	//methods
	public void printCard() {				//prints the name, battlepoints/altbattlepoints, type, and ID of card
		System.out.printf("%-15s", name);
		if(altBattlePoints != 0)
			System.out.printf("%-15s", battlePoints + "/" + altBattlePoints);
		else
			System.out.printf("%-15s", battlePoints);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}

}
