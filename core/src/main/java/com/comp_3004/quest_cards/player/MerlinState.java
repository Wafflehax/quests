package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;

public class MerlinState extends PlayerState {
	
	static Logger log = Logger.getLogger(MerlinState.class); //log4j logger

	public boolean playCard(AdventureCard c, Player p) {
		//player cannot play cards in this state
		return true;
	}

	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		//player cannot discard cards in this state
		return false;
	}

	public boolean userInput(int input, Player p) {
		if(input > (p.getQuest().getQuest().getStages()) - 1) {
			log.info(p.getName()+" tried to view stage "+(input+1)+" but the current quest only has "+
					p.getQuest().getQuest().getStages()+" stages");
			return true;
		}
		if(input > -1) {
			log.info(p.getName()+" uses merlins ability to see stage "+(input+1));
			p.getQuest().merlinRevealsStage(input);
			p.setMerlinUsed(true);
			p.setState("playQuest");
		}
		else {
			log.info(p.getName()+" declines using merlins ability at this time");
			p.setState("playQuest");
		}
		return true;
	}

}
