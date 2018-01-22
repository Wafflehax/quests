package com.comp_3004.quest_cards.core;

public class Weapon extends Adventure {
	private int battlePoints;
	
	//constructor
	public Weapon(String n, int b) {
		this.name = n;
		this.battlePoints = b;
	}
	
	public void printCard() {
		System.out.printf("%-15s", this.name);
		System.out.printf("%-15s", this.battlePoints);
		System.out.printf("%s\n", this.getClass().getSimpleName());
	}

}