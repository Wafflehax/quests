package com.comp_3004.quest_cards.cards;

public class WeaponCard extends AdventureCard {
	
	//constructor
	public WeaponCard(String n, int b) {
		this.name = n;
		this.battlePts = b;
	}
	
	//methods
	public String printCard() {				//prints name, battlepoints, type, and ID of the card
		return String.format("%-15s%-15s%s", name, battlePts, getID());
	}
	

}