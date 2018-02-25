package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class BidState extends PlayerState {
	
	static Logger log = Logger.getLogger(BidState.class); //log4j logger

	public boolean playCard(AdventureCard c, Player p) {
		//player can only play mordred in this state
		if(c.getName() == "Mordred") {
			p.setState("mordred");
		}
		return true;
	}
	
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		if(c.getOwner() == p && (c.getState() == State.HAND || c.getState() == State.PLAY)) {
			if(p.getHand().contains(c)) {
				p.getHand().remove(c);
				log.info(p.getName() + " discarded " + c.getName() + " from hand");
			}
			else if(p.getActive().contains(c)) {
				p.getActive().remove(c);
				log.info(p.getName() + " discarded " + c.getName() + " from active");
			}
			d.discardCard(c);
			c.setState(State.DISCARD);
			c.setOwner(null);
			if(p.getQuest().getParticipants().size() == 1) {
				if(p.getQuest().discardedACard())
					return true;
				else
					return false;
			}
		}
		return false;
	}


	public boolean userInput(int input, Player p) {
		if(!p.getQuest().placeBid(input, p))
			return false;
		return true;
	}

}
