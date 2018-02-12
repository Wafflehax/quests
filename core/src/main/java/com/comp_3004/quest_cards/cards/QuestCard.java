package com.comp_3004.quest_cards.cards;

public class QuestCard extends StoryCard {
	private int stages;
	private String namedFoe = null;

	//constructors
	public QuestCard(String n, int s) {
		this.name = n;
		this.stages = s;
	}
	public QuestCard(String n, int s, String f) {
		this.name = n;
		this.stages = s;
		this.namedFoe = f;
	}
	
	//getters/setters
	public int getStages() { return this.stages; }
	public String getNamedFoe() { return this.namedFoe; }

}
