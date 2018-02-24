package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class MordredState extends PlayerState {
	
	static Logger log = Logger.getLogger(MordredState.class); //log4j logger

	public boolean playCard(AdventureCard c, Player p) {
		//player cannot play cards in this state
		return true;
	}

	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		if(c.getOwner() == p &&  c.getState() == State.HAND) {
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
		return true;
	}

	public boolean userInput(int input, Player p) {
		AdventureCard mordred = null;
		for(AdventureCard c : p.getHand()) {
			if(c.getName() == "Mordred")
				mordred = c;
		}
		//input(cardID) dictates which ally will be killed
		for(Player pl : p.getQuest().getParticipants()) {
			for(AdventureCard c : pl.getActive()) {
				if(c.getID() == input) {
					log.info("Mordred kills "+c.getName());
					pl.discardCard(c, p.getQuest().getAdvDeck());
					p.discardCard(mordred, p.getQuest().getAdvDeck());
					p.setState("playQuest");
				}
			}
		}
		return true;
	}

}
