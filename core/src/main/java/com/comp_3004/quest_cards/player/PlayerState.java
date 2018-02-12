package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;

public abstract class PlayerState {
	static Logger log;  //log4j logger

	//methods will have different functionality in different states
	public abstract boolean playCard(AdventureCard c, Player p);		//play cards
	public abstract boolean discardCard(AdventureCard c, AdventureDeck d, Player p);	//discard cards
	public abstract boolean userInput(boolean b);								//used in yes/no situations (confirm participation in...)

}
