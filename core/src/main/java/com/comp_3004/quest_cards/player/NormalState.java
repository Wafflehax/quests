package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
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
	
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		if(c.getOwner() == p && (c.getState() == State.PLAY || c.getState() == State.HAND)) {
			if(p.getActive().contains(c)){
				p.getActive().remove(c);
				log.info(p.getName() + " discarded " + c.getName() + " from active");
			}
			else if(p.getHand().contains(c)) {
				p.getHand().remove(c);
				log.info(p.getName() + " discarded " + c.getName() + " from hand");
			}
			d.discardCard(c);
			c.setState(State.DISCARD);
			c.setOwner(null);
		}
		return false;
	}

	public boolean userInput(boolean b, Player p) {
		// TODO: handle user input during normal state
		return false;
	}

}
