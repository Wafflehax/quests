package com.comp_3004.quest_cards.cards;

public class TournamentCard extends StoryCard {
	
	//attributes
	private int bonusShields;		//bonus shields added to player who wins tournament

	//constructor
	public TournamentCard(String n, int b) {
		this.name = n;
		this.bonusShields = b;
	}
	
	//getters/setters
	public int getBonusSh() { return this.bonusShields; }
}
