package com.comp_3004.quest_cards.core;

import java.util.ArrayList;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;

public class QuestStage {
	
	//attributes
	private ArrayList<AdventureCard> sponsorCards;
	private int battlePoints;

	//constructor
	public QuestStage() {
		this.sponsorCards = new ArrayList<AdventureCard>();
		this.battlePoints = 0;
	}
	
	//getters/setters
	public ArrayList<AdventureCard> getSponsorCards() { return this.sponsorCards; }
	public int getBattlePts() { return this.battlePoints; }
	
	//methods
	public boolean addSponsorCard(AdventureCard card) { 
		//adding test card
		if(card instanceof TestCard) {
			for(AdventureCard stageCard : sponsorCards) {
				if(stageCard instanceof FoeCard) {
					System.out.println("Can't add a test card to a stage that already has a foe in it");
					return false;
				}
			}
		}
		//adding foe card
		else if(card instanceof FoeCard) {
			for(AdventureCard stageCard : sponsorCards) {
				if((stageCard instanceof FoeCard) || (stageCard instanceof TestCard)) {
					System.out.println("Can't add a foe card to a stage that already has a foe or test in it");
					return false;
				}
			}
		}
		//adding weapon card
		else if(card instanceof WeaponCard) {
			boolean containsFoe = false;
			for(AdventureCard stageCard : sponsorCards) {
				if(stageCard instanceof TestCard) {
					System.out.println("Can't add a weapon card to a stage that already has a test in it");
					return false;
				}
				if(stageCard instanceof WeaponCard) {
					if(stageCard.getName() == card.getName()) {
						System.out.println("Can't add a weapon card to a stage that already has that weapon in it");
						return false;
					}
				}
				if(stageCard instanceof FoeCard)
					containsFoe = true;
			}
			if(!containsFoe) {
				System.out.println("Stage does not contain a foe card, cannot add a weapon");
				return false;
			}
		}
		else if(card instanceof AllyCard) {
			System.out.println("Can't add an ally card to a stage");
			return false;
		}
		else if(card instanceof AmourCard) {
			System.out.println("Can't add an amour card to a stage");
			return false;
		}
		sponsorCards.add(card);
		if(card instanceof FoeCard) {
			battlePoints += ((FoeCard)card).getBattlePts();	//need to handle named foes
		}
		if(card instanceof WeaponCard) {
			battlePoints += ((WeaponCard)card).getBattlePts();
		}
		return true;
	}
	
	//TODO:change to send single cards back to player when player drags card from stage back to hand
	public void sendCardsBackToPlayer(Player p) {
		System.out.println("Resetting quest set up...");
		for(AdventureCard card : sponsorCards) {
			card.setState(State.HAND);
			card.setOwner(p);
			p.getHand().add(card);
			System.out.printf("Sending %s back to %s's hand\n", card.getName(), p.getName());
		}
		sponsorCards.clear();
	}
	
	public void discardCards(AdventureDeck adv) {
		for(AdventureCard card : sponsorCards) {
			card.setState(State.DISCARD);
			card.setOwner(null);
			adv.getDiscard().add(card);
		}
		sponsorCards.clear();
	}
}
