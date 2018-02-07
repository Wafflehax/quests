package com.comp_3004.quest_cards.cards;

public abstract class Card {
	protected String name;			//name of the card
	private int ID;
	protected static int nextID=1;

	public Card() {
		ID = nextID;
		nextID++;
	}

	public String getName() { return this.name; }
	public int getID() { return this.ID; }
	public void setID() { this.ID = ID++; }
	abstract public void printCard();

}