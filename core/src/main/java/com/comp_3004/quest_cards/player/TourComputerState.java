package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;
import com.comp_3004.quest_cards.cards.AdventureDeck;

public class TourComputerState extends PlayerState {

	//state allows for discarding of cards from active hand and hand.
	
	static Logger log = Logger.getLogger(TourComputerState.class); //log4j logger
	
	
			@Override
			public boolean playCard(AdventureCard c, Player p) {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
				// TODO Auto-generated method stub
				//discards card from either hand or active hand
				if(p.getHand().contains(c)) {
					p.getHand().remove(c);
					d.discardCard(c);
					c.setState(State.DISCARD);
					log.info(p.getName() + " discarded their hand" + c.getName());
					return true;
				}
				else if(p.getActive().contains(c)) {
					p.getActive().remove(c);
					d.discardCard(c);
					c.setState(State.DISCARD);
					log.info(p.getName() + " discarded from active" + c.getName());
					return true;
				}
				log.info(p.getName() + " can't discard a card they don't own :=>" + c.getName() + " kept");
				return false;
			}
			@Override
			public boolean userInput(int input, Player p) {
				// TODO Auto-generated method stub
				return false;
			}
			
	
	
}

