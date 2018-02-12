package com.comp_3004.quest_cards.Stories;


import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;

public class QuestStage {
	
	//attributes
	private ArrayList<AdventureCard> cards;
	private int battlePoints;
	static Logger log = Logger.getLogger(QuestStage.class); //log4j logger

	//constructor
	public QuestStage() {
		this.cards = new ArrayList<AdventureCard>();
		this.battlePoints = 0;
	}
	
	//getters/setters
	public ArrayList<AdventureCard> getSponsorCards() { return this.cards; }
	public int getBattlePts() { return this.battlePoints; }
	
	//methods
	public boolean addCard(AdventureCard card, String namedFoe) { 
		//adding test card
		if(card instanceof TestCard) {
			for(AdventureCard stageCard : cards) {
				if(stageCard instanceof FoeCard) {
					log.info("Error: Can't add a test card to a stage that already has a foe in it");
					return false;
				}
			}
		}
		//adding foe card
		else if(card instanceof FoeCard) {
			for(AdventureCard stageCard : cards) {
				if((stageCard instanceof FoeCard) || (stageCard instanceof TestCard)) {
					log.info("Error: Can't add a foe card to a stage that already has a foe or test in it");
					return false;
				}
			}
		}
		//adding weapon card
		else if(card instanceof WeaponCard) {
			boolean containsFoe = false;
			for(AdventureCard stageCard : cards) {
				if(stageCard instanceof TestCard) {
					log.info("Error: Can't add a weapon card to a stage that already has a test in it");
					return false;
				}
				if(stageCard instanceof WeaponCard) {
					if(stageCard.getName() == card.getName()) {
						log.info("Error: Can't add a weapon card to a stage that already has that weapon in it");
						return false;
					}
				}
				if(stageCard instanceof FoeCard)
					containsFoe = true;
			}
			if(!containsFoe) {
				log.info("Error: Stage does not contain a foe card, cannot add a weapon");
				return false;
			}
		}
		else if(card instanceof AllyCard) {
			log.info("Error: Can't add an ally card to a stage");
			return false;
		}
		else if(card instanceof AmourCard) {
			log.info("Error: Can't add an amour card to a stage");
			return false;
		}
		cards.add(card);
		if(namedFoe == card.getName())
			battlePoints += ((FoeCard) card).getAltBattlePts();
		else if(namedFoe == "allSaxons") {
			if(card.getName() == "Saxons" || card.getName() == "Saxon Knight")
				battlePoints += ((FoeCard) card).getAltBattlePts();
		}
		else if(namedFoe == "all") {
			if(((FoeCard) card).getAltBattlePts() != 0)
				battlePoints += ((FoeCard) card).getAltBattlePts();
			else
				battlePoints += card.getBattlePts();
		}
		else
			battlePoints += card.getBattlePts();
		return true;
	}
	
	public void removeCard(AdventureCard c, String namedFoe) {
		cards.remove(c);
		if(namedFoe == c.getName())
			battlePoints -= ((FoeCard) c).getAltBattlePts();
		else if(namedFoe == "allSaxons") {
			if(c.getName() == "Saxons" || c.getName() == "Saxon Knight")
				battlePoints -= ((FoeCard) c).getAltBattlePts();
		}
		else if(namedFoe == "all") {
			if(((FoeCard) c).getAltBattlePts() != 0)
				battlePoints -= ((FoeCard) c).getAltBattlePts();
			else
				battlePoints -= c.getBattlePts();
		}
		else
			battlePoints -= c.getBattlePts();
	}
	
	//TODO:change to send single cards back to player when player drags card from stage back to hand
	public void sendCardsBackToPlayer(Player p) {
		System.out.println("Resetting quest set up...");
		for(AdventureCard card : cards) {
			card.setState(State.HAND);
			card.setOwner(p);
			p.getHand().add(card);
			System.out.printf("Sending %s back to %s's hand\n", card.getName(), p.getName());
		}
		cards.clear();
	}
	
	public void discardCards(AdventureDeck adv) {
		for(AdventureCard card : cards) {
			card.setState(State.DISCARD);
			card.setOwner(null);
			adv.getDiscard().add(card);
			log.info(card.getName()+" used to set up quest is discarded");
		}
		cards.clear();
	}
}
