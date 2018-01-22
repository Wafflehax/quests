package com.comp_3004.quest_cards.core;

public class Test extends Adventure {
	
	//constructor
	public Test(String n) {
		this.name = n;
	}

	public void printCard() {
		System.out.printf("%-30s", this.name);
		System.out.printf("%s\n", this.getClass().getSimpleName());
	}

}