package com.comp_3004.quest_cards.core;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;

//TODO: implement state pattern (sponsorship state, set up state, participation state ...)
public class Quest {
	
	//attributes
	static Logger log = Logger.getLogger(Quest.class); //log4j logger
	private QuestCard quest;
	private Players players;
	private Player sponsor;
	private int cardsUsedToSponsor;
	private ArrayList<Player> participants;
	private QuestStage[] stages;
	private QuestStage stage;
	private AdventureDeck advDeck;
	Scanner sc = new Scanner(System.in);		//used for user input for now
	
	//getters/setters
	public QuestStage getStage(int i) { return this.stages[i]; }
	public QuestCard getQuest() { return this.quest; }

	//constructor
	public Quest(QuestCard q, Players p, AdventureDeck d) {
		this.quest = q;
		this.players = p;
		this.sponsor = null;
		cardsUsedToSponsor = 0;
		participants = new ArrayList<Player>();
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
		
		
		//determine participation
		boolean noParticipants = false;
		questParticipation();
		
		if(participants.size() == 0) {
			System.out.println("No one participated in the quest"); 	//if true, quest is over - go to quest clean up
			noParticipants = true;
		}
		
		//playing a quest
		if(!noParticipants) {
			//for each stage of the quest
			int i=0;
			for(QuestStage s : stages) {
				log.info("Stage " + i++);
				//each participating player draws a card from the adventure deck
				for(Player p : participants)
					p.drawCard(advDeck);
				
				//reveal if stage contains a foe or a test
				AdventureCard stageCard =  stage.getSponsorCards().get(0);
				System.out.printf("contains a %s\n", stageCard.getClass().getSimpleName());
				
				//if test - implement later (move on to next stage)
				if(stageCard instanceof TestCard) {
					System.out.println("Test cards not yet implemented, moving to next stage...");
				}
				
				//if foe - players play ally, amour, weapon cards
				else if(stageCard instanceof FoeCard) {
					for(Player p : participants) {
						int c;
						boolean doneAddingCards = false;
						while(!doneAddingCards) {
							System.out.printf("%s, select card\n", p.getName());
							p.printHand();
							c = sc.nextInt();
							AdventureCard cardToPlay = p.getHand().get(c);
							p.playStageCard(cardToPlay);
							p.printStage();
							System.out.println("Would you like to add more cards to the quest? (1: yes 0: no)" );
							int choice = sc.nextInt();
							if(choice == 0)
								doneAddingCards = true;
						}
					}
			
					//reveal foe+weapons for stage
					printStages(s);
					
					//players reveal cards played for stage (cards go from hidden to players active)
					for(Player p : participants) {
						p.revealStageCards();
						p.printActive();
					}
					
					//for each player
					ArrayList<Player> failedStage = new ArrayList<Player>();
					for(Player p : participants) {
						//if they have more battle points then stage they pass
						int battlePoints = p.getRankBattlePts();
						for(AdventureCard activeCard : p.getActive()) {
							if(activeCard instanceof AllyCard)
								battlePoints += ((AllyCard)activeCard).getBattlePts();
							else if(activeCard instanceof AmourCard)
								battlePoints += ((AmourCard)activeCard).getBattlePts();
							else if(activeCard instanceof WeaponCard)
								battlePoints += ((WeaponCard)activeCard).getBattlePts();
						}
						if(s.getBattlePts() > battlePoints) {
							System.out.printf("%s did not have enough battle points to pass stage\n", p.getName());
							System.out.printf("Stage BPs: %s  Player BPs: %s\n", s.getBattlePts(), battlePoints);
							failedStage.add(p);
						}
						else {
							System.out.printf("%s passed stage\n", p.getName());
							System.out.printf("Stage BPs: %s  Player BPs: %s\n", s.getBattlePts(), battlePoints);
						}
					}
					for(Player p : failedStage) {
						participants.remove(p);
					}
				}
				//stage clean up
				for(Player p : players.getPlayers()) {
					//weapons discarded from active
					if(p != sponsor)
						p.discardWeaponsActive(advDeck);
				}
			}
		}
		//quest cleanup
		System.out.println("Winners: ");
		for(Player p : participants) {
			System.out.println(p.getName());
			//all players who completed the quest get shields = numStages
			p.addShields(quest.getStages());
		}
		//sponsor draws cards = num used to sponsor + numStages
		for(int i=0; i<(quest.getStages() + cardsUsedToSponsor); i++) {
			sponsor.drawCard(advDeck);
		}
		//all players discard amours
		for(Player p : players.getPlayers()) {
			p.discardAmoursActive(advDeck);
		}
		
		//all cards used to sponsor go to discard pile
		for(int i=0; i<quest.getStages(); i++) {	
			stages[i].discardCards(advDeck);
		}
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
							cardsUsedToSponsor++;
							testAdded = true;
						}
					}
					else {
						if(sponsor.playCard(cardToPlay, this, s))
							cardsUsedToSponsor++;
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
				for(AdventureCard card : stages[i].getSponsorCards()) {
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
					stage.sendCardsBackToPlayer(sponsor);
				}
			}
			printStages();
		}
		
	}
	
	private void questParticipation() {
		int choice;
		int sponsorIndex = -1; //used to determine which player declares participation next (clockwise after sponsor)
		for(int i=0; i<players.getPlayers().size(); i++) {
			if(sponsor == players.getPlayers().get(i))
				sponsorIndex = i;
		}
		System.out.println("Sponsor Index: " + sponsorIndex);
		//remaining players decide if they want to participate in the quest
		for(int i=sponsorIndex + 1; i % players.getPlayers().size() != sponsorIndex; i = ((i+1) % players.getPlayers().size())) {
			System.out.printf("%s, would you like to participate in quest? (1: yes 0: no)\n", players.getPlayerAtIndex(i).getName());
			choice = sc.nextInt();
			if(choice == 1)
				participants.add(players.getPlayerAtIndex(i));
		}
		for(Player p : participants)
			System.out.printf("%s is participating\n", p.getName());
	}
	
	public void printStages() {
		for(int i=0; i<quest.getStages(); i++) {
			System.out.printf("Stage %s: \n", i);
			for(AdventureCard card : stages[i].getSponsorCards())
				card.printCard();
		}
	}
	public void printStages(QuestStage s) {
		System.out.println("Stage: ");
		for(AdventureCard card : s.getSponsorCards())
			card.printCard();
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
			if(stage.getSponsorCards().size() == 0) {
				System.out.println("Quest does not have a card in each stage");
				return false;
			}
		}
		return true;
	}

}
