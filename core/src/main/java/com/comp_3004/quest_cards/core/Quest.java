package com.comp_3004.quest_cards.core;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryCard;

public class Quest {
	
	//attributes
	static Logger log = Logger.getLogger(Event.class); //log4j logger
	private StoryCard quest;
	private Players players;
	private AdventureDeck advDeck;

	//constructor
	public Quest(StoryCard q, Players p, AdventureDeck d) {
		this.quest = q;
		this.players = p;
		this.advDeck = d;
	}
	
	public void runQuest() {
		log.info(players.current().getName() + " drew quest " + quest.getName());
	}

}
