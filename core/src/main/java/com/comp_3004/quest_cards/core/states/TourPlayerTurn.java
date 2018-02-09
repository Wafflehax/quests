package com.comp_3004.quest_cards.core.states;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameController;

public class TourPlayerTurn extends State {

	public TourPlayerTurn(GameController c) {
		super(c);
	}

	@Override
	public void msg() {
		String msg = "Hey, " + this.c.m.getPlayers().current().getName() + " Its your Tournament Turn" 
				+ " Play cards, or discard and press DoneTurn";
		log.info(msg);
	}

	@Override
	public void no() {
	
	}

	@Override
	public void yes() {
	
	}

	@Override
	public boolean handPress(Card c) {
		boolean played = false;
		if(c instanceof WeaponCard || c instanceof AmourCard || c instanceof AllyCard) {
			if((c instanceof AllyCard) == false && this.c.m.inPlay(c.getName())){
				log.info("Can't play two of same weapons, or more than one amour");
			}
			else {
				if(this.c.m.playCard(c)) {
					log.info("Card Played!");
					played = true;
				}else {
					log.info("Not Played!");
				}
			}
		}
		else
			log.info("You didn't play a valid Weapon,Ally,Amour card, Card not played");		
		return played;
	}

	@Override
	public boolean disCardPress(Card c) {
		if(this.c.m.disCard(c)) {
			log.info("Discarded Card " + c.getName());
			return true;
		}else {
			log.info("Failed to discard card " + c.getName());
			return false;
		}
	}

	@Override
	public boolean doneTurn() {
		//check not over 12 cards
		if(this.c.m.getcurrentTurn().tooManyHandCards()) {
			log.info("Can't end turn too many CARDS! Your hand can only have 12");
		}
		else {
			// set player to player next
			this.c.m.nextPlayer();
			this.c.m.sPop(); //pop off playerTurn
			this.c.m.StateMsg(); //go to next	
		}		
		return false;
	}
	
}