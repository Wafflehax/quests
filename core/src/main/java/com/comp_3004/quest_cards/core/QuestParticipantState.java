package com.comp_3004.quest_cards.core;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class QuestParticipantState extends PlayerState {
	static Logger log = Logger.getLogger(QuestParticipantState.class); //log4j logger

	public boolean playCard(AdventureCard c, Player p) {
		if(p.getHand().contains(c)) {
			//cannot play test or foe cards
			if((c instanceof TestCard) || (c instanceof FoeCard)) {
				log.info(p.getName() + " Error: cant play foe or test cards when participating in a quest");
				return false;
			}
			else if(c instanceof AmourCard) {
				//can only have 1 amour active
				for(AdventureCard activeCard : p.getActive()) {
					if(activeCard instanceof AmourCard) {
						log.info(p.getName() + " Error: cant have more than one amour active");
						return false;
					}
				}
				for(AdventureCard stageCard : p.getStage()) {
					if(stageCard instanceof AmourCard) {
						log.info(p.getName() + " Error: cant play more than one amour in a stage");
						return false;
					}
				}
			}
			else if(c instanceof WeaponCard) {
			//can't have 2 weapons with same name
				for(AdventureCard stageCard : p.getStage()) {
					if(stageCard.getName() == c.getName()) {
						log.info(p.getName() + " Error: cant play more than one weapon with the same name in a stage");
						return false;
					}
				}
			}
			p.getStage().add(c);
			p.getHand().remove(c);
			c.setState(State.STAGE);
			log.info(p.getName() + " played card " + c.getName());
			return true;
		}
		else {
			log.info("Error: " + p.getName() + " does not have the card " + c.getName() + " in hand");
			return false; 
		}
	}

	public boolean userInput() {
		// TODO Auto-generated method stub
		return false;
	}

}
