package com.comp_3004.quest_cards.cards;

public class WeaponCard extends AdventureCard {
	
	//attributes
	private int battlePoints;		//the battlepoints the card adds to the player or quest stage
	
	//constructor
	public WeaponCard(String n, int b) {
		this.name = n;
		this.battlePoints = b;
	}
	
	//getters/setters
	public int getBattlePts() { return this.battlePoints; }
	
	//methods
	public void printCard() {				//prints name, battlepoints, type, and ID of the card
		System.out.printf("%-15s", name);
		System.out.printf("%-15s", battlePoints);
		System.out.printf("%-20s", getClass().getSimpleName());
		System.out.printf("%s\n", getID());
	}
	

}