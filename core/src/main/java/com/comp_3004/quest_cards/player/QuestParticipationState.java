package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class QuestParticipationState extends PlayerState {
	static Logger log = Logger.getLogger(QuestParticipationState.class); //log4j logger

	public boolean playCard(AdventureCard c, Player p) {
		//player does not play cards in this state
		return false;
	}
	
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		//player does not discard cards in this state
		return false;
	}

	//handle user input: YES/NO for determining participation
	public boolean userInput(boolean b) {
		// TODO Auto-generated method stub
		return false;
	}

}
