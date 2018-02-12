package com.comp_3004.quest_cards.core;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class SponsorState extends PlayerState {
	static Logger log = Logger.getLogger(SponsorState.class); //log4j logger

	public boolean playCard(AdventureCard c, Player p) {
		if(p.getHand().contains(c)) {
			p.getHand().remove(c);
			c.setState(State.QUEST);
			return true;
		}else {
			log.info("Error: " + p.getName() + " does not have the card " + c.getName() + " in hand");
			return false; 
		}
	}

	public boolean userInput() {
		// TODO: handle user input during sponsor state
		return false;
	}

}
