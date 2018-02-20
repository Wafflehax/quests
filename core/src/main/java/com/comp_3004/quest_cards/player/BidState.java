package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class BidState extends PlayerState {
	
	static Logger log = Logger.getLogger(BidState.class); //log4j logger

	public boolean playCard(AdventureCard c, Player p) {
		//player does not play any cards during this state
		return false;
	}
	
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		if(c.getOwner() == p && c.getState() == State.HAND) {
			if(p.getHand().contains(c)) {
				p.getHand().remove(c);
				log.info(p.getName() + " discarded " + c.getName() + " from hand");
			}
			d.discardCard(c);
			c.setState(State.DISCARD);
			c.setOwner(null);
			if(p.getQuest().discardedACard())
				return true;
			else
				return false;
		}
		log.info(p.getName()+" does not have "+c.getName()+" in their hand");
		return false;
	}


	public boolean userInput(int input, Player p) {
		if(!p.getQuest().placeBid(input, p))
			return false;
		return true;
	}

}
