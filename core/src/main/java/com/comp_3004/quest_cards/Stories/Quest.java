package com.comp_3004.quest_cards.Stories;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
	private LinkedHashMap<Player, Integer> currentBids;
	private int highestBid;
	private int minBid;
	private boolean questComplete;

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
		this.currentBids = new LinkedHashMap<Player, Integer>();
		this.highestBid = 0;
		this.currentBids.put(null, 0);
		this.minBid = 0;
		this.questComplete = false;
		log.info(players.current().getName() + " drew quest " + quest.getName());
		
		if(players.current().isAi()) {
			players.current().getAI().DoISponsorAQuest();
		}
	}
	
	//getters/setters
	public QuestStage getStage(int i) { return this.stages[i]; }
	public QuestStage getCurrentStage() { return this.stages[currentStage]; }
	public int getCurrentStageNum() { return this.currentStage; }
	public QuestCard getQuest() { return this.quest; }
	public void setSponsor(Player p) { this.sponsor = p; }
	public Player getSponsor() { return this.sponsor; }
	public int getNumDeclines() { return this.numDeclines; }
	public ArrayList<Player> getPlayers() { return this.players.getPlayers(); }
	public ArrayList<Player> getParticipants() { return this.participants; }
	public AdventureDeck getAdvDeck() { return this.advDeck; }
	public int getHighestBid() { return this.highestBid; }
	
	//methods
	public void increaseNumDeclines() {
		numDeclines++;
	}
	
	//determines who will sponsor the quest
	public boolean questSponsorship(Player p, int input) {
		//player declines sponsoring the quest
		if(input == 0) {
			log.info(p.getName()+" declined sponsoring the quest");
			if(p == sponsor)
				sponsor = null;
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
		if(players.current().isAi())
			players.current().getAI().DoISponsorAQuest();
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
						log.info(sponsor.getName()+" played "+c.getName()+" to stage "+(stageNum+1));
						cardsUsedToSponsor++;
						testAdded = true;
						printStage(stageNum);
						return true;
				}
				else
					return false;
			}
			else {
				if(stages[stageNum].addCard(c, quest.getNamedFoe())) {
					log.info(sponsor.getName()+" played "+c.getName()+" to stage "+(stageNum+1));
					cardsUsedToSponsor++;
					printStage(stageNum);
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
						else 
							stageBPs[i] += ((FoeCard) card).getBattlePts();
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
				log.info("Stage "+(i+1)+": "+stageBPs[i]+" battlepoints");
			}
			players.next();
			for(Player p : players.getPlayers()) {
				if(p != sponsor) {
					p.setState("questParticipant");
				}
			}
			if(players.current().isAi()) {
				if(players.current().getAI().doIParticipateInQuest())
					questParticipation(1, players.current());
				else
					questParticipation(0, players.current());
			return true;
			}
		}
		else {
			log.info("Resetting quest set up...");
			for(QuestStage stage : stages) {
				stage.sendCardsBackToPlayer(sponsor);
				cardsUsedToSponsor = 0;
			}
		}
		return true;
	}
	
	//determines if the player wants to participate in the quest
	public boolean questParticipation(int input, Player p) {
		if(input == 1) {
			participants.add(p);
			log.info(p.getName() + " is participating in the quest");
		}
		else
			log.info(p.getName() + " declined participating in the quest");
		if(sponsor == players.peekNext()) {
			if(participants.isEmpty()) {
				questCleanup();
				return true;
			}
			players.next();	//move current player forward twice to skip sponsor
			players.next();
			for(Player pl : participants)
				pl.setState("playQuest");
			startStage(currentStage);
			return true;
		}
		players.next();
		if(players.current().isAi()) {
			if(players.current().getAI().doIParticipateInQuest())
				questParticipation(1, players.current());
			else
				questParticipation(0, players.current());
		}
		return true;
	}
	
	//starts playing the stage indicated by stageNum
	private void startStage(int stageNum) {
		boolean tooManyCards = false;
		//each participating player draws a card from the adventure deck
		for(Player p : participants) {
			p.drawCard(advDeck);
			if(p.getHand().size() > 12) {
				p.setState("tooManyCards");
				tooManyCards = true;
				
			}
		}
		if(tooManyCards)
			tooManyCards();
		else
			runStage(stageNum);
	}
	
	private void tooManyCards() {
		for(int i=0; i<participants.size(); i++) {
			if(players.current().getHand().size() <= 12) {
				if(players.peekNext() == sponsor)
					players.next();
				players.next();
			}
		}
	}
		
	private void runStage(int stageNum) {
		for(Player pl : participants) {
			pl.setState("playQuest");
			for(AdventureCard c : pl.getActive()) {
				if(c.getName() == "Merlin" && pl.getMerlinUsed() == false)
					pl.setState("merlin");
			}
		}
		
		//reveal if stage contains a foe or a test
		stageCard =  stages[stageNum].getSponsorCards().get(0);
		
		//if only one participant, make them current player
		if(participants.size() == 1)
			players.setCurrent(participants.get(0));
		//make sure current player is participating
		while(!participants.contains(players.current()))
			players.next();
		
		//if stage contains a test
		if(stageCard instanceof TestCard) {
			log.info("Stage " + stageNum + " contains " + stageCard.getName());
			if(quest.getName() == "Search for the Questing Beast")
				minBid = 4;
			else
				minBid = ((TestCard) stageCard).getMinBid();
			for(Player p : participants)
				p.setState("bid");
		}
		else
			log.info("Stage " + (stageNum+1) + " contains a " + stageCard.getClass().getSimpleName());
		if(players.current().isAi())
			players.current().getAI().playInQuest(stageCard);
	}
	
	public boolean doneAddingCards() {
		log.info(players.current().getName()+" is done playing cards for stage "+(currentStage+1));
		if(participants.size() == 1) {
			players.next();
			return revealStage(currentStage);
		}
		else if(sponsor == players.peekNext()) {
			players.next();	//move current player forward twice to skip sponsor
			players.next();
			return revealStage(currentStage);
		}
		players.next();
		while(!participants.contains(players.current())) {
			players.next();
		}
		if(players.current().isAi()) {
			players.current().getAI().playInQuest(stageCard);
		}
		return true;
	}
	
	//reveals to the participants what cards are in the stage and resolves stage
	public boolean revealStage(int stageNum) {
		//reveal foe+weapons for stage
		log.info(sponsor.getName() + " reveals stage " + (stageNum+1));
		printStage(stageNum);
		
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
		for(Player p : participants) {
			p.addShields(quest.getStages());		//all players who completed the quest get shields = numStages
			if(p.getKingsRecognitionBonus()) {
				log.info(p.getName()+" gets bonus shields from Kings Recognition");
				p.addShields(2);
				p.setKingsRecognitionBonus(false);
			}
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
		
		//reset player states back to normal
		for(Player p : players.getPlayers())
			p.setState("normal");
		
		//reset merlins ability
		for(Player p : players.getPlayers()) {
			for(AdventureCard c : p.getActive()) {
				if(c.getName() == "Merlin")
					p.setMerlinUsed(false);
			}
		}
		questComplete = true;
		
		//check if sponsor has too many cards
		if(sponsor.getHand().size() > 12) {
			log.info(sponsor.getName()+" has "+(sponsor.getHand().size()-12)+" too many cards");
			sponsor.setState("tooManyCards");
			players.setCurrent(sponsor);
			return true;
		}
		
		//set current turn back to player who drew quest
		players.setCurrent(drewQuest);
		log.info(players.current().getName()+"'s turn is over.");
		players.next();
		
		
		//ends turn
		return false;
	}
	
	//used to print stage cards to console
	public void printStage(int i) {
		log.info("Stage: "+(i+1));
		log.info(String.format("%-15s%-15s%s", "Name", "Battle Points", "ID"));
		log.info("==================================");
		for(AdventureCard card : stages[i].getSponsorCards())
			log.info(card.printCard());
	}
	
	//used in determining if each stage has more battle points than the previous stage
	private boolean isSorted(int[] data){
	    for(int i = 1; i < data.length; i++){
	    		if(data[i] == 0)
	    			continue;
	        if(data[i-1] >= data[i]){
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
	
	public boolean placeBid(int bid, Player p) {
		if(bid == -1) {
			participants.remove(p);
			log.info(p.getName()+" declined to bid higher and is no longer participating");
			if(players.peekNext() == sponsor)
				players.next();
			players.next();
			if(participants.size() == 1) {	//last man standing
				log.info(participants.get(0).getName()+" wins the bidding war");
				players.setCurrent(participants.get(0));
				//currentBids.push(currentBids.pop() - players.current().getFreeBids());
				int curBid = currentBids.get(players.current()) - players.current().getFreeBids();
				currentBids.put(players.current(), curBid);
				log.info("Cards to discard: "+currentBids.get(players.current()));
				if(players.current().isAi())
					players.current().getAI().discardAfterWinningTest();
			}
			return true;
		}
		else if(bid < minBid) {
			log.info("Error "+p.getName()+": bid of "+bid+" < "+minBid+" (min bid for this quest)");
			return true;
		}
		else if(bid > p.numBidsAllowed()) {
			log.info(p.getName()+" bids "+bid);
			log.info("Error "+p.getName()+": cannot bid that many cards");
			return true;
		}
		else if(participants.size() == 1 && bid < 3) {
			log.info(p.getName()+" bids "+bid);
			log.info("Error "+p.getName()+": must bid at least 3 if they are the only player in the test");
			return true;
		}
		else if(bid > highestBid) {
			log.info(p.getName()+" bids "+bid);
			highestBid = bid;
			currentBids.put(players.current(), bid);
			log.info(p.getName()+" bid successful");
			if(participants.size() == 1) {	//last man standing
				log.info(participants.get(0).getName()+" wins the bidding war");
				players.setCurrent(participants.get(0));
				//currentBids.push(currentBids.pop() - players.current().getFreeBids());
				int curBid = currentBids.get(players.current()) - players.current().getFreeBids();
				currentBids.put(players.current(), curBid);
				log.info("Cards to discard: "+currentBids.get(players.current()));
				return true;
			}
			players.next();
			while(!participants.contains(players.current()))
				players.next();
			if(players.current().isAi())
				players.current().getAI().playInQuest(stageCard);
			return true;
		}
		else {
			log.info("Error "+p.getName()+": bid lower or equal to current bid");
			return true;
		}
	}
	
	public boolean discardedACard() {
		currentBids.put(players.current(), currentBids.get(players.current()) - 1);
		log.info(currentBids.get(players.current())+" cards left to discard");
		if(currentBids.get(players.current()) == 0) {
			for(Player p : players.getPlayers()) {
				if(p != sponsor)
					p.setState("playQuest");
			}
			if(currentStage < quest.getStages()-1)
				startStage(++currentStage);
			else
				return questCleanup();
		}
		return true;
	}
	
	public boolean checkForTooManyCards() {
		for(int i=0; i<players.getNumPlayers(); i++) {
			if(players.current().getState() == "tooManyCards") {
				return true;
			}
			players.next();
		}
		if(questComplete) {
			players.setCurrent(drewQuest);
			players.next();
			return false;
		}
		else {
			players.setCurrent(sponsor);
			players.next();
			runStage(currentStage);
			return true;
		}
	}
	
	public void merlinRevealsStage(int stage) {
		log.info("Stage: ");
		log.info(String.format("%-15s%-15s%s", "Name", "Battle Points", "ID"));
		log.info("==================================");
		for(AdventureCard a : stages[stage].getSponsorCards()) {
			log.info(a.printCard());
		}
	}
	
	public void checkBidStack() {
		log.info("Checking participants latest bids for invalid bids");
		Player currentTurn = players.current();
		//iterate through players, check for invalid bids
		for(int i=0; i<players.size(); i++) {
			players.next();
			if(!participants.contains(players.current())) {		//skips any players not currently participating
				continue;
			}
			int lastBid;
			if(currentBids.containsKey(players.current()))
				lastBid = currentBids.get(players.current());
			else
				lastBid = 0;
			int highestBidAllowed = players.current().getFreeBids()+players.current().getHand().size();
			if(lastBid > highestBidAllowed) {
				log.info(players.current().getName()+"'s bid of "+lastBid+" is no longer valid.");
				currentBids.put(players.current(), 0);
				int newHighestBid = 0;
				for(Player p : participants) {
					if(currentBids.containsKey(p) && currentBids.get(p) > newHighestBid) {
						log.info(p.getName()+" has the new highest bid: "+currentBids.get(p));
						newHighestBid = currentBids.get(p);
					}
				}
				highestBid = newHighestBid;
				log.info("Highest bid is now "+highestBid);
				break;
			}
		}

		if(currentBids.containsKey(currentTurn) && currentBids.get(currentTurn) == highestBid) {
			log.info("Since "+currentTurn.getName()+"'s bid is the current highest, they do not need to bid again");
			players.setCurrent(currentTurn);
			players.next();
			while(!participants.contains(players.current())) {
				players.next();
			}
		}
		else if(!currentBids.containsKey(currentTurn))
			players.setCurrent(currentTurn);
	}
	
	public void sponsorS2() {
		boolean hasTest = false;
		HashMap<Integer, AdventureCard> validCards = new HashMap<Integer, AdventureCard>();
		HashMap<Integer, AdventureCard> weapons = new HashMap<Integer, AdventureCard>();
		for(AdventureCard c : players.current().getHand()) {
			if(c instanceof FoeCard) {
				if(c.getName() == quest.getNamedFoe()) {
					if(!validCards.containsKey(((FoeCard) c).getAltBattlePts()))
						validCards.put(((FoeCard) c).getAltBattlePts(),c);
				}
				else if(quest.getNamedFoe() == "all" && ((FoeCard) c).getAltBattlePts() != 0) {
					if(!validCards.containsKey(((FoeCard) c).getAltBattlePts()))
						validCards.put(((FoeCard) c).getAltBattlePts(),c);
				}
				else {
					if(!validCards.containsKey(c.getBattlePts()))
						validCards.put(c.getBattlePts(), c);
				}
			}
			else if(c instanceof TestCard) {
				hasTest = true;
				validCards.put(0, c);
			}
			else if(c instanceof WeaponCard) {
				if(!weapons.containsKey(c.getBattlePts()))
					weapons.put(c.getBattlePts(), c);
			}
		}
		if(validCards.size() < quest.getStages()) {
			log.info(players.current().getName()+" does not have valid cards to sponsor quest");
			questSponsorship(players.current(), 0);
			return;
		}
		int largestFoe = 0;
		int largestWeapon = 0;
		for(int i : validCards.keySet()) {
			if(i > largestFoe)
				largestFoe = i;
		}
		for(int i : weapons.keySet()) {
			if(i > largestWeapon)
				largestWeapon = i;
		}
		if(largestFoe + largestWeapon < 40) {
			log.info(players.current().getName()+" cannot set up last stage to be at least 40 BP's");
			questSponsorship(players.current(), 0);
			return;
		}
		questSponsorship(players.current(), 1);
		
		//last stage
		int stageBP = 0;
		addStageCard(validCards.get(largestFoe), quest.getStages()-1);
		stageBP += largestFoe;
		while(stageBP < 40) {
			int weaponBP = 30;
			if(weapons.containsKey(weaponBP)) {
				addStageCard(weapons.get(weaponBP), quest.getStages()-1);
				stageBP += weaponBP;
			}
			weaponBP -= 5;
		}
		int stagesDone = quest.getStages()-1;
		
		//second last stage
		if(hasTest) {
			addStageCard(validCards.get(0), quest.getStages()-2);
			stagesDone = quest.getStages()-2;
		}
		
		int stageNum = 0;	
		for(int i : validCards.keySet()) {
			if(i == 0)
				continue;
			else {
				addStageCard(validCards.get(i), stageNum);
				stageNum++;
				if(stageNum == stagesDone)
					break;
			}
		}
		checkQuestSetup();
		
		return;
	}
}
