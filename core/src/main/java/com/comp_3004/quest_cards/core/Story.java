package com.comp_3004.quest_cards.core;

public abstract class Story extends Card {

	protected void printCard() {
		System.out.printf("%-40s", this.name);
		System.out.printf("%s\n", this.getClass().getSimpleName());
	}
}
