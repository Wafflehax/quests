package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.Stories.QuestStage;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;

public class EventState extends PlayerState {

	static Logger log = Logger.getLogger(EventState.class); //log4j logger

	@Override
	public boolean playCard(AdventureCard c, Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userInput(int input, Player p) {
		// TODO Auto-generated method stub
		return false;
	}

}
