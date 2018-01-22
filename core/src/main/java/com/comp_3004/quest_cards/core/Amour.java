package com.comp_3004.quest_cards.core;

public class Amour extends Adventure {
	private int battlePoints;
	
	//constructor
	public Amour() {
		this.name = "Amour";
		this.battlePoints = 10;
	}
	
	public void printCard() {
		System.out.printf("%-15s", this.name);
		System.out.printf("%-15s", this.battlePoints);
		System.out.printf("%s\n", this.getClass().getSimpleName());
	}

}