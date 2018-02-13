package com.comp_3004.quest_cards.player;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class TourPlayState extends PlayerState{

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userInput(boolean b, Player p) {
		return p.getTour().doneTurn();
	}
	
	
}