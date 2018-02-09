package com.comp_3004.quest_cards.core;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;

public class Quest {
	
	//attributes
	static Logger log = Logger.getLogger(Event.class); //log4j logger
	private QuestCard quest;
	private Players players;
	private Player sponsor;
	private QuestStage[] stages;
	private QuestStage stage;
	private AdventureDeck advDeck;
	Scanner sc = new Scanner(System.in);		//used for user input for now
	
	//getters/setters
	public QuestStage getStage(int i) { return this.stages[i]; }

	//constructor
	public Quest(QuestCard q, Players p, AdventureDeck d) {
		this.quest = q;
		this.players = p;
		this.sponsor = null;
		this.stages = new QuestStage[quest.getStages()];
		for(int i=0; i<quest.getStages(); i++) {
			stage = new QuestStage();
			stages[i] = stage;
		}
		this.advDeck = d;
		
	}
	
	//abstract this method into submethods that are called such as questSponsorship(), questSetUp(), questPlay()
	public void runQuest() {
		log.info(players.current().getName() + " drew quest " + quest.getName());
		
		//game announces the quest drawn (show quest card on screen)
		System.out.printf("Starting %s\n", quest.getName());
		
		//get a sponsor for the quest
		questSponsorship();
		//if no sponsor, quest is done, turn is over
		if(sponsor == null) {
			System.out.println("No one sponsored quest, turn over");
			return;
		}
		
		//sponsor sets up quest
		questSetup();
		
		
		//remaining players decide if they want to participate in the quest
		
		//playing a quest
			//each participating player draws a card from the adventure deck
			//TODO: finish steps in playing a quest
		
		
	}
	
	private void questSponsorship() {
		//players starting with current player accept/decline sponsoring quest
		int choice;
		boolean questSponsored = false;
		for(Player p : players.getPlayers()) {
			System.out.printf("%s, do you want to Sponsor this Quest? (1: yes 0: no)\n", p.getName());
			choice = sc.nextInt();
			if(choice == 1) {
				//check if player has foe/test cards at least equal to the number of stages in the quest
				int numFoeTest = 0;
				boolean testCardCounted = false;
				for(AdventureCard card : p.getHand()) {
					if(card instanceof FoeCard)
						numFoeTest++;
					if(card instanceof TestCard) {
						if(!testCardCounted) {
							numFoeTest++;
							testCardCounted = true;
						}
					}	
				}
				if(numFoeTest >= quest.getStages()) {
					sponsor = p;
					System.out.printf("%s sponsored the quest!\n", sponsor.getName());
					break;
				}
				else
					System.out.printf("%s, you do not have the required cards to sponsor the quest. "
							+ "(Quest requires %s foe+test cards, you have %s\n", p.getName(), quest.getStages(), numFoeTest);
			}
		}
	}
	
	private void questSetup() {
		boolean setUpDone;
		boolean doneAddingCards;
		boolean stagesComplete;
		boolean testAdded = false;
		int c;		//stores player choice for card
		int s;		//stores player choice for which stage to play the card
		
		setUpDone = false;
		while(!setUpDone) {
			doneAddingCards = false;
			while(!doneAddingCards) {
				System.out.printf("%s, select card\n", sponsor.getName());
				sponsor.printHand();
				c = sc.nextInt();
				AdventureCard cardToPlay = sponsor.getHand().get(c);
				//if quest has a test, can't add another test
				if(testAdded && (cardToPlay instanceof TestCard))
					System.out.println("Quest already contains a test, cannot add another");
				else {
					System.out.printf("%s, select stage to play %s\n", sponsor.getName(), cardToPlay.getName());
					s = sc.nextInt();
					if(cardToPlay instanceof TestCard) {
						if(sponsor.playCard(cardToPlay, this, s)) {
							testAdded = true;
						}
					}
					else {
						sponsor.playCard(cardToPlay, this, s);
					}
					printStages();
				}
				System.out.println("Would you like to add more cards to the quest? (1: yes 0: no)" );
				int choice = sc.nextInt();
				if(choice == 0)
					doneAddingCards = true;
			}
				
			//check if quest is set up correctly
			//check that each stage has increasing battlepoints, ignore 0's as they are stages with tests
			boolean increasingBPs = false;
			int[] stageBPs = new int[quest.getStages()];
			for(int i=0; i<quest.getStages(); i++) {
				for(AdventureCard card : stages[i].getCards()) {
					if(card instanceof FoeCard) {
						if(quest.getNamedFoe() == card.getName())
							stageBPs[i] += ((FoeCard) card).getAltBattlePts();
						else if(quest.getNamedFoe() == "allSaxons") {
							if(card.getName() == "Saxons" || card.getName() == "Saxon Knight")
								stageBPs[i] += ((FoeCard) card).getAltBattlePts();
						}
						else if(quest.getNamedFoe() == "all") {
							if(((FoeCard) card).getAltBattlePts() != 0)
								stageBPs[i] += ((FoeCard) card).getAltBattlePts();
							else
								stageBPs[i] += ((FoeCard) card).getBattlePts();
						}
						else
							stageBPs[i] += ((FoeCard) card).getBattlePts();
					}
					if(card instanceof WeaponCard)
						stageBPs[i] += ((WeaponCard) card).getBattlePts();
				}
			}
			for(int i=0; i<quest.getStages(); i++) {
				System.out.printf("Stage %s has %s battlepoints\n", i, stageBPs[i]);
			}
			if(isSorted(stageBPs))
				increasingBPs = true;
			else
				System.out.println("Battlepoints do not increase from stage to stage");
			
			//check all stages of quest are completed
			stagesComplete = stagesComplete();
			
			if(increasingBPs && stagesComplete) {
				setUpDone = true;
				System.out.println("Quest successfully set up");
			}
			else {
				for(QuestStage stage : stages) {
					System.out.println("Cards sent back to sponsor's hand");
					stage.sendCardsBackToPlayer(sponsor);
				}
			}
			printStages();
		}
		
	}
	
	public void printStages() {
		for(int i=0; i<quest.getStages(); i++) {
			System.out.printf("Stage %s: \n", i);
			for(AdventureCard card : stages[i].getCards())
				card.printCard();
		}
	}
	
	private boolean isSorted(int[] data){
	    for(int i = 1; i < data.length; i++){
	    		if(data[i] == 0)
	    			continue;
	        if(data[i-1] > data[i]){
	            return false;
	        }
	    }
	    return true;
	}
	
	private boolean stagesComplete() {
		for(QuestStage stage : stages) {
			if(stage.getCards().size() == 0) {
				System.out.println("Quest does not have a card in each stage");
				return false;
			}
		}
		return true;
	}

}
