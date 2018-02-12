package com.comp_3004.quest_cards.player;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class SponsorState extends PlayerState {
	static Logger log = Logger.getLogger(SponsorState.class); //log4j logger

	public boolean playCard(AdventureCard c, Player p) {
		return false; //need to fix this later
	}
	
	public boolean playCard(AdventureCard c, Player p, int stageNum) {
		if(p.getHand().contains(c)) {
			if(p.getQuest().addStageCard(c, stageNum)) {
				p.getHand().remove(c);
				c.setState(State.QUEST);
				return true;
			}else {
				log.info("Failed to play  " + c.getName());
				return false; 
			}
		}
		else {
			log.info("Error: " + p.getName() + " does not have the card " + c.getName() + " in hand");
			return false;
		}
	}
	
	public boolean discardCard(AdventureCard c, AdventureDeck d, Player p) {
		//handle player discarding cards during quest cleanup after drawing cards
		return false;
	}

	public boolean userInput(boolean b, Player p) {
		if(b) {
			if(p.getQuest().getSponsor() == p) {
				//quest set up complete
				return p.getQuest().checkQuestSetup();
			}
			return p.getQuest().questSponsorship(p);
		}
		else
			log.info(p.getName()+" declined sponsoring the quest");
		return false;
	}

}
