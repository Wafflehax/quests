package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class TourPlayState extends PlayerState{

	static Logger log = Logger.getLogger(TourPlayState.class); //log4j logger
	
	@Override
	public boolean playCard(AdventureCard c, Player p) {
		if(p.getHand().contains(c)) {
			if(c instanceof WeaponCard || c instanceof AmourCard || c instanceof AllyCard) {
				if((c instanceof AllyCard) == false && p.existsActive(c.getName())){
					log.info("Can't play two of same weapons, or more than one amour");
					return false;
				}
				else {
					p.getActive().add(c);
					p.getHand().remove(c);
					c.setState(State.PLAY);
					log.info(p.getName() + " played card " + c.getName());
					return true;
				}
			}
			else {
				log.info("You didn't play a valid Weapon,Ally,Amour card, Card not played");	
				return false;
			}
		}
		log.info("Error: " + p.getName() + " does not have the card " + c.getName() + " in hand");
		return false;
	}

	@Override
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		//user can only discard from their hand, not active
		if(p.getHand().contains(c)) {
			p.getHand().remove(c);
			d.discardCard(c);
			c.setState(State.DISCARD);
			log.info(p.getName() + " discarded their hand" + c.getName());
			return true;
		}
		log.info(p.getName() + " can't discard a card they don't own :=>" + c.getName() + " kept");
		return false;
	}

	@Override
	public boolean userInput(int input, Player p) {
		return p.getTour().doneTurn();
	}
	
	
}