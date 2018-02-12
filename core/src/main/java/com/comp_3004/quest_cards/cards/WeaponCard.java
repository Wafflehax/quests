package com.comp_3004.quest_cards.cards;

public class WeaponCard extends AdventureCard {
	
	//constructor
	public WeaponCard(String n, int b) {
		this.name = n;
		this.battlePts = b;
	}
	
	//methods
	public void printCard() {				//prints name, battlepoints, type, and ID of the card
		System.out.printf("%-15s", name);
		System.out.printf("%-15s", battlePts);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}
	

}