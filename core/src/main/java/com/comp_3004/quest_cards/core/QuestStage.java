package com.comp_3004.quest_cards.core;

import java.util.ArrayList;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;

public class QuestStage {
	
	//attributes
	private ArrayList<AdventureCard> stage;

	//constructor
	public QuestStage() {
		this.stage = new ArrayList<AdventureCard>();
	}
	
	//getters/setters
	public ArrayList<AdventureCard> getCards() { return this.stage; }
	
	//methods
	public boolean addCard(AdventureCard card) { 
		//adding test card
		if(card instanceof TestCard) {
			for(AdventureCard stageCard : stage) {
				if(stageCard instanceof FoeCard) {
					System.out.println("Can't add a test card to a stage that already has a foe in it");
					return false;
				}
			}
		}
		//adding foe card
		else if(card instanceof FoeCard) {
			for(AdventureCard stageCard : stage) {
				if((stageCard instanceof FoeCard) || (stageCard instanceof TestCard)) {
					System.out.println("Can't add a foe card to a stage that already has a foe or test in it");
					return false;
				}
			}
		}
		//adding weapon card
		else if(card instanceof WeaponCard) {
			boolean containsFoe = false;
			for(AdventureCard stageCard : stage) {
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
		stage.add(card);
		return true;
	}
	
	//TODO:change to send single cards back to player when player drags card from stage back to hand
	public void sendCardsBackToPlayer(Player p) {
		System.out.println("Resetting quest set up...");
		for(AdventureCard card : stage) {
			card.setState(State.HAND);
			card.setOwner(p);
			p.getHand().add(card);
			System.out.printf("Sending %s back to %s's hand\n", card.getName(), p.getName());
		}
	}

}
