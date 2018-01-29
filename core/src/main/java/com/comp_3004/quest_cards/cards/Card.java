package com.comp_3004.quest_cards.cards;

public abstract class Card {
	protected String name;			//name of the card


	public String getName() { return this.name; }
	abstract protected void printCard();

}