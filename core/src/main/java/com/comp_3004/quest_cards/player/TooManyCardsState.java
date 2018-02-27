package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.Stories.QuestStage;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class TooManyCardsState extends PlayerState {
	
	static Logger log = Logger.getLogger(TooManyCardsState.class); //log4j logger

	public TooManyCardsState() {
		// TODO Auto-generated constructor stub
	}

	public boolean playCard(AdventureCard c, Player p) {
		// can only add cards to table from your hand
		if((c instanceof FoeCard)) {
			log.info("Can't play foe cards at this time");
			return true;
		}
		else if((c instanceof TestCard)) {
			log.info("Can't play test cards at this time");
			return true;
		}
		if(p.getHand().contains(c)) {
			p.getActive().add(c);
			p.getHand().remove(c);
			c.setState(State.PLAY);
			log.info(p.getName() + " played card " + c.getName());
			if(p.getHand().size() > 12)
				return true;
			else if(p.getHand().size() <= 12) {
				p.setState("normal");
				log.info(p.getName()+" is no longer above the hand limit");
			}
			if(p.checkForTooManyCards()) 
				return true;
			return false;
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
			if((p.getHand().size() > 12))
				return true;
			else if(p.getHand().size() <= 12) {
				p.setState("normal");
				log.info(p.getName()+" is no longer above the hand limit");
			}
			//if(p.checkForTooManyCards()) 
			//	return true;
			return false;
		}
		log.info(p.getName()+" does not have "+c.getName()+" in their hand");
		return false;
	}

	@Override
	public boolean userInput(int input, Player p) {
		// TODO Auto-generated method stub
		return false;
	}

}
