package com.comp_3004.quest_cards.Stories;


import java.util.ArrayList;

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
	private Player drewQuest;				//player who drew quest
	private int cardsUsedToSponsor;			//number of cards the sponsor uses to set up the quest
	private ArrayList<Player> participants;	//players who choose to participate in the quest
	private QuestStage[] stages;				//stores all the stages of the current quest
	private AdventureDeck advDeck;			//deck of adventure cards
	boolean testAdded = false;
	private int numDeclines;
	private AdventureCard stageCard;
	private int currentStage;

	//constructor
	public Quest(QuestCard q, Players p, AdventureDeck d) {
		this.quest = q;
		this.players = p;
		for(Player pl : players.getPlayers()) {
			pl.setQuest(this);
			pl.setState("sponsor");
		}
		this.sponsor = null;
		this.drewQuest = players.current();
		this.cardsUsedToSponsor = 0;
		this.participants = new ArrayList<Player>();
		this.stages = new QuestStage[quest.getStages()];
		for(int i=0; i<quest.getStages(); i++) {
			QuestStage stage = new QuestStage();
			stages[i] = stage;
		}
		this.advDeck = d;
		this.numDeclines = 0;
		this.currentStage = 0;
		log.info(players.current().getName() + " drew quest " + quest.getName());
	}
	
	//getters/setters
	public QuestStage getStage(int i) { return this.stages[i]; }
	public QuestCard getQuest() { return this.quest; }
	public void setSponsor(Player p) { this.sponsor = p; }
	public Player getSponsor() { return this.sponsor; }
	public int getNumDeclines() { return this.numDeclines; }
	public ArrayList<Player> getPlayers() { return this.players.getPlayers(); }
	public ArrayList<Player> getParticipants() { return this.participants; }
	
	//methods
	public void increaseNumDeclines() {
		numDeclines++;
	}
	
	//determines who will sponsor the quest
	public boolean questSponsorship(Player p, boolean b) {
		//player declines sponsoring the quest
		if(!b) {
			log.info(p.getName()+" declined sponsoring the quest");
			players.next();
			numDeclines++;
			return checkNoSponsor();
		}
		//player accepts sponsoring the quest
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
		//player has the right amount of cards to sponsor
		if(numFoeTest >= quest.getStages()) {
			sponsor = p;
			sponsor.setState("sponsor");
			log.info(sponsor.getName() + " sponsored the quest!");
			return true;
		}
		//player does not have the required cards to sponsor
		else {
			log.info(p.getName() + " does not have the required cards to sponsor the quest. ");
			log.info("Quest requires "+quest.getStages()+" foe + at most 1 test card. Eligible cards: "+numFoeTest);
			numDeclines++;
			players.next();
			return checkNoSponsor();
		}
	}
	
	//checks if all players have declined sponsorship
	private boolean checkNoSponsor() {
		if(numDeclines == players.getNumPlayers()) {
			log.info("No one wanted to sponsor the quest");
			log.info(players.current().getName()+"'s turn is over");
			players.next();
			//this return value is passed back to the presenter 
			return false;	//presenter begins next turn
		}
		return true;			//same turn, next player decides sponsorship
	}
	
	//sponsor adds card to a stage
	public boolean addStageCard(AdventureCard c, int stageNum) {
		//if quest has a test, can't add another test
		if(testAdded && (c instanceof TestCard)) {
			log.info("Error: Quest already contains a test, cannot add another");
			return false;
		}
		else {
			if(c instanceof TestCard) {
				if(stages[stageNum].addCard(c, quest.getNamedFoe())) {
						log.info(sponsor.getName()+" played "+c.getName()+" to stage "+stageNum);
						cardsUsedToSponsor++;
						testAdded = true;
						printStage(stages[stageNum]);
						return true;
				}
				else
					return false;
			}
			else {
				if(stages[stageNum].addCard(c, quest.getNamedFoe())) {
					log.info(sponsor.getName()+" played "+c.getName()+" to stage "+stageNum);
					cardsUsedToSponsor++;
					printStage(stages[stageNum]);
					return true;
				}
				else
					return false;
			}
		}
	}
	
	//determines if the way the quest is set up is legal
	public boolean checkQuestSetup() {
		//check that each stage has increasing battlepoints, ignore 0's as they are stages with tests
		boolean stagesComplete = false;
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
			log.info("Quest set up complete.");
			for(int i=0; i<quest.getStages(); i++) {
				log.info("Stage "+i+": "+stageBPs[i]+" battlepoints");
			}
			players.next();
			for(Player p : players.getPlayers()) {
				if(p != sponsor) {
					p.setState("questParticipant");
				}
			}
		}
		else {
			for(QuestStage stage : stages) {
				stage.sendCardsBackToPlayer(sponsor);
				cardsUsedToSponsor = 0;
			}
		}
		for(int i=0; i<quest.getStages(); i++)
			printStage(stages[i]);
		return true;
	}
	
	//determines if the player wants to participate in the quest
	public void questParticipation(Player p) {
		participants.add(p);
		log.info(p.getName() + " is participating in the quest");
		if(sponsor == players.peekNext()) {
			players.next();	//move current player forward twice to skip sponsor
			players.next();
			for(Player pl : participants)
				pl.setState("playQuest");
			startStage(currentStage);
			return;
		}
		players.next();
	}
	
	//starts playing the stage indicated by stageNum
	private void startStage(int stageNum) {
		//each participating player draws a card from the adventure deck
		for(Player p : participants)
			p.drawCard(advDeck);
		
		//reveal if stage contains a foe or a test
		stageCard =  stages[stageNum].getSponsorCards().get(0);
		log.info("Stage " + stageNum + " contains a " + stageCard.getClass().getSimpleName());
		
		//if test - implement later (move on to next stage)
		if(stageCard instanceof TestCard) {
			System.out.println("Test cards not yet implemented, moving to next stage...");
		}
	}
	
	public boolean doneAddingCards() {
		log.info(players.current().getName()+" is done playing cards for stage "+currentStage);
		if(sponsor == players.peekNext()) {
			players.next();	//move current player forward twice to skip sponsor
			players.next();
			return revealStage(currentStage);
		}
		players.next();
		return true;
	}
	
	//reveals to the participants what cards are in the stage and resolves stage
	public boolean revealStage(int stageNum) {
		//reveal foe+weapons for stage
		log.info(sponsor.getName() + " reveals stage " + stageNum);
		printStage(stages[stageNum]);
		
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
			if(stages[stageNum].getBattlePts() > battlePoints) {
				log.info(p.getName() +" did not have enough battle points to pass stage");
				log.info("Stage BPs: "+stages[stageNum].getBattlePts()+"  Player BPs: "+battlePoints);
				failedStage.add(p);
			}
			else {
				log.info(p.getName()+" passed the stage");
				log.info("Stage BPs: "+stages[stageNum].getBattlePts()+"  Player BPs: "+battlePoints);
			}
		}
		for(Player p : failedStage) {
			participants.remove(p);
		}
		//stage clean up
		for(Player p : players.getPlayers()) {
			//weapons discarded from active
			if(p != sponsor)
				p.discardWeaponsActive(advDeck);
		}
		if(currentStage < quest.getStages()-1)
			startStage(++currentStage);
		else
			return questCleanup();
		return true;
	}
	
	//determines winner(s), cleans up cards, and deals cards to sponsor
	private boolean questCleanup() {
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
		
		//set current turn back to player who drew quest
		players.setCurrent(drewQuest);
		players.next();
		
		//ends turn
		return false;
	}
	
	//used to print stage cards to console
	public void printStage(QuestStage s) {
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
