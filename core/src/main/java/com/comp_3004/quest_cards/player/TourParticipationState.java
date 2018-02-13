package com.comp_3004.quest_cards.player;
import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;

public class TourParticipationState extends PlayerState{

	static Logger log = Logger.getLogger(TourParticipationState.class); //log4j logger

	@Override
	public boolean playCard(AdventureCard c, Player p) {
		log.info("Can't play during ask participation of tour");
		return false;
	}

	@Override
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		log.info("Can't discard during ask participation of tour");
		return false;
	}

	@Override
	public boolean userInput(boolean b, Player p) {
		if(b) {
			p.getTour().tourParticipate(p);
			return true;
		}
		return false;
	}
	
	
	
}