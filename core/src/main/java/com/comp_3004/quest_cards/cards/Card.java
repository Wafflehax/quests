package com.comp_3004.quest_cards.cards;

public abstract class Card {
	protected String name;			//name of the card

<<<<<<< HEAD
	public String getName() { return this.name; }
=======
	public String getName() { return name; }
	
>>>>>>> eccd63a8da0172f0d9843a57673c2208bceef47e
	abstract protected void printCard();

}