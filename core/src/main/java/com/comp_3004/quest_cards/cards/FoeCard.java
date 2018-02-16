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
	public String printCard() {				//prints the name, battlepoints/altbattlepoints, and ID of card
		if(altBattlePts != 0)
			return String.format("%-15s%-15s%s", name, battlePts + "/" + altBattlePts, getID());
		else
			return String.format("%-15s%-15s%s", name, battlePts, getID());
	}

}
