package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;

public class BidState extends PlayerState {
	
	static Logger log = Logger.getLogger(BidState.class); //log4j logger

	public boolean playCard(AdventureCard c, Player p) {
		//player does not play any cards during this state
		return false;
	}
	
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		//player chooses cards to discard based off of bid number
		return false;
	}


	public boolean userInput(int input, Player p) {
		// TODO get current amount player wants to bid, update TestBids with player bid
		return false;
	}

}
