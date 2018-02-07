package com.comp_3004.quest_cards.cards;

public abstract class Card {
	
	//attributes
	protected String name;					//name of the card
	private int ID;							//unique card ID
	protected static int nextID=1;			//used to generate unique ID's

	//constructor
	public Card() {
		ID = nextID;
		nextID++;
	}

	//getters/setters
	public String getName() { return this.name; }
	public int getID() { return this.ID; }

	//abstract methods
	abstract public void printCard();		//prints name, stats, type, and ID of card

}