package com.comp_3004.quest_cards.core;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class NormalState extends PlayerState {

	static Logger log = Logger.getLogger(NormalState.class); //log4j logger
	
	public NormalState() {
		// TODO Auto-generated constructor stub
	}

	public boolean playCard(AdventureCard c, Player p) {
		// can only add cards to table from your hand
		if(p.getHand().contains(c)) {
			p.getActive().add(c);
			p.getHand().remove(c);
			c.setState(State.PLAY);
			log.info(p.getName() + " played card " + c.getName());
			return true;
		}else {
			log.info("Error: " + p.getName() + " does not have the card " + c.getName() + " in hand");
			return false; 
		}
	}

	public boolean userInput() {
		// TODO: handle user input during normal state
		return false;
	}

}
