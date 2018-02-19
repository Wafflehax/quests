package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.Stories.QuestStage;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class EventState extends PlayerState {

	static Logger log = Logger.getLogger(EventState.class); //log4j logger

	@Override
	public boolean playCard(AdventureCard c, Player p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		if(c.getOwner() == p && (c.getState() == State.PLAY)) {
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
			return false;
		}
		else if (c.getOwner() == p && c.getState() == State.HAND) {
			if(p.getHand().contains(c)) {
				if(p.getEvent().discardCard(p, c)) {
					p.getHand().remove(c);
					log.info(p.getName() + " discarded " + c.getName() + " from hand");
					d.discardCard(c);
					c.setState(State.DISCARD);
					c.setOwner(null);
					if(p.getEvent().getHighestRank().size() > 0)
						return true;
					else 
						return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean userInput(int input, Player p) {
		// TODO Auto-generated method stub
		return false;
	}

}
