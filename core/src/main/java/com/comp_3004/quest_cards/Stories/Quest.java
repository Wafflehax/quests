package com.comp_3004.quest_cards.Stories;


import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Players;

//TODO: implement state pattern (sponsorship state, set up state, participation state ...)
public class Quest {
	
	//attributes
	static Logger log = Logger.getLogger(Quest.class); //log4j logger
	private QuestCard quest;					//current quest
	private Players players;					//players in the game
	private Player sponsor;					//player who decides to sponsor quest
	private int cardsUsedToSponsor;			//number of cards the sponsor uses to set up the quest
	private ArrayList<Player> participants;	//players who choose to participate in the quest
	private QuestStage[] stages;				//stores all the stages of the current quest
	private AdventureDeck advDeck;			//deck of adventure cards
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
			QuestStage stage = new QuestStage();
			stages[i] = stage;
		}
		this.advDeck = d;
		
	}
	
	//main quest method, handles sequence of quest "events"
	public void runQuest() {
		log.info(players.current().getName() + " drew quest " + quest.getName());
		
		//get a sponsor for the quest
		questSponsorship();
		//if no sponsor, quest is done, turn is over
		if(sponsor == null) {
			log.info("No one sponsored quest, turn over");
			return;
		}
		
		//sponsor sets up quest
		questSetup();
		
		
		//determine participation
		boolean noParticipants = false;
		questParticipation();
		
		if(participants.size() == 0) {
			log.info("No one participated in the quest"); 	//if true, quest is over - go to quest clean up
			noParticipants = true;
		}
		
		//playing a quest
		if(!noParticipants)
			playQuest();
		
		//quest cleanup
		questCleanup();
	}
	
	private void questSponsorship() {
		//players starting with current player accept/decline sponsoring quest
		int choice;
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
					sponsor.setState("sponsor");
					log.info(sponsor.getName() + " sponsored the quest!");
					break;
				}
				else {
					log.info(p.getName() + " does not have the required cards to sponsor the quest. ");
					log.info("Quest requires "+quest.getStages()+" foe + at most 1 test card. Eligible cards: "+numFoeTest);
				}
			}
			else
				log.info(p.getName()+" declined to sponsor quest");
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
					log.info("Error: Quest already contains a test, cannot add another");
				else {
					System.out.printf("%s, select stage to play %s\n", sponsor.getName(), cardToPlay.getName());
					s = sc.nextInt();
					if(cardToPlay instanceof TestCard) {
						//if(sponsor.playCard(cardToPlay, this, s)) {
						if(stages[s].addCard(cardToPlay, quest.getNamedFoe())) {
							if(sponsor.playCard(cardToPlay)) {
								log.info(sponsor.getName()+" played "+cardToPlay.getName()+" to stage "+s);
								cardsUsedToSponsor++;
								testAdded = true;
							}
							else
								stages[s].removeCard(cardToPlay, quest.getNamedFoe());
						}
					}
					else {
						if(stages[s].addCard(cardToPlay, quest.getNamedFoe())) {
							if(sponsor.playCard(cardToPlay)) {
								log.info(sponsor.getName()+" played "+cardToPlay.getName()+" to stage "+s);
								cardsUsedToSponsor++;
							}
							else
								stages[s].removeCard(cardToPlay, quest.getNamedFoe());
						}
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
			if(isSorted(stageBPs))
				increasingBPs = true;
			else
				log.info("Error: Battlepoints do not increase from stage to stage");
			
			//check all stages of quest are completed
			stagesComplete = stagesComplete();
			
			if(increasingBPs && stagesComplete) {
				setUpDone = true;
				log.info("Quest set up complete.");
				for(int i=0; i<quest.getStages(); i++) {
					log.info("Stage "+i+": "+stageBPs[i]+" battlepoints");
				}
			}
			else {
				for(QuestStage stage : stages) {
					stage.sendCardsBackToPlayer(sponsor);
					cardsUsedToSponsor = 0;
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
		//remaining players decide if they want to participate in the quest
		for(int i=sponsorIndex + 1; i % players.getPlayers().size() != sponsorIndex; i = ((i+1) % players.getPlayers().size())) {
			System.out.printf("%s, would you like to participate in quest? (1: yes 0: no)\n", players.getPlayerAtIndex(i).getName());
			choice = sc.nextInt();
			if(choice == 1) {
				participants.add(players.getPlayerAtIndex(i));
				players.getPlayerAtIndex(i).setState("questParticipant");
				log.info(players.getPlayerAtIndex(i).getName() + " is participating in the quest");
			}
		}
	}
	
	private void playQuest() {
		//for each stage of the quest
		int i=0;
		for(Player p : participants) {
			p.setState("playQuest");
		}
		for(QuestStage s : stages) {
			//each participating player draws a card from the adventure deck
			for(Player p : participants)
				p.drawCard(advDeck);
			
			//reveal if stage contains a foe or a test
			AdventureCard stageCard =  s.getSponsorCards().get(0);
			log.info("Stage " + i + " contains a " + stageCard.getClass().getSimpleName());
			
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
						p.playCard(cardToPlay);
						p.printStage();
						System.out.println("Would you like to add more cards to the quest? (1: yes 0: no)" );
						int choice = sc.nextInt();
						if(choice == 0)
							doneAddingCards = true;
					}
				}
		
				//reveal foe+weapons for stage
				log.info(sponsor.getName() + " reveals stage " + i++);
				printStages(s);
				
				//players reveal cards played for stage (cards go from hidden to players active)
				for(Player p : participants) {
					p.revealStageCards();
					System.out.printf("%s:", p.getName());
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
						log.info(p.getName() +" did not have enough battle points to pass stage");
						log.info("Stage BPs: "+s.getBattlePts()+"  Player BPs: "+battlePoints);
						failedStage.add(p);
					}
					else {
						log.info(p.getName()+" passed the stage");
						log.info("Stage BPs: "+s.getBattlePts()+"  Player BPs: "+battlePoints);
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
	
	private void questCleanup() {
		for(Player p : participants)
			log.info(p.getName()+" has completed the quest!");
		for(Player p : participants)
			p.addShields(quest.getStages());		//all players who completed the quest get shields = numStages
		
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
		
		//reset player states back to normal
		for(Player p : players.getPlayers())
			p.setState("normal");
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
	
	//used in determining if each stage has more battle points than the previous stage
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
	
	//checks if each card has at least one  card added to it during set up
	private boolean stagesComplete() {
		for(QuestStage stage : stages) {
			if(stage.getSponsorCards().size() == 0) {
				log.info("Error: Quest does not have a card in each stage");
				return false;
			}
		}
		return true;
	}

}
