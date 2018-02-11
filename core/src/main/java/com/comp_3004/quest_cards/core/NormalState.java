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
			log.info("played card " + c.getName());
			return true;
		}else {
			log.info("Failed you do not have this card " + c.getName());
			return false; 
		}
	}

	public boolean userInput() {
		// TODO: handle user clicks during normal state
		return false;
	}

}
