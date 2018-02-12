package com.comp_3004.quest_cards.player;

import java.util.Comparator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.*;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class Player{
	static Logger log = Logger.getLogger(Player.class); //log4j logger
	public enum Rank implements Comparable<Rank>{ 
		SQUIRE(0), KNIGHT(1), CHAMPION_KNIGHT(2), KNIGHT_OF_THE_ROUND_TABLE(3) ;
		private int index;
		Rank(int i) {
			this.index = i;
		}
		public int getIndex() { return this.index; }
	}
	class RankComparator implements Comparator<Rank> {

		  @Override
		  public int compare(final Rank o1, final Rank o2) {
		      int returnValue = 0;
		      if (o1.getIndex() > o2.getIndex()) {
		          returnValue = 1;
		      } else if (o1.getIndex() < o2.getIndex()) {
		          returnValue = -1;
		      }
		      return returnValue;
		  }
	}
	private String name;
	private boolean kingsRecognitionBonus = false;		//if true, gain bonus shields when quest completed
	private Rank rank;
	private int shields;
	private LinkedList<AdventureCard> playerHandCards;
	private LinkedList<AdventureCard> playerActiveCards;
	private LinkedList<AdventureCard> playerStageCards;
	private boolean participateQuest;
	public volatile boolean participateTournament;
	private PlayerState state_;
	
	// constructor
	public Player(String name) {
		this.name = name;
		this.rank = Rank.SQUIRE;
		this.shields = 0;
		this.playerHandCards = new LinkedList<AdventureCard>();
		this.playerActiveCards = new LinkedList<AdventureCard>();
		this.playerStageCards = new LinkedList<AdventureCard>();
		this.state_ = new NormalState();
	}
	
	// getters/setters
	public String getName() { return this.name; }
	public Rank getRank() { return this.rank; }
	public String getRankS() { return rankS(); }
	public int getShields() { return this.shields; }
	public boolean participantInTournament() { return participateTournament; }
	public void participateTour(boolean b) { participateTournament = b; }
	public int numberOfHandCards() { return playerHandCards.size(); }
	public int numberOfActiveCards() { return playerActiveCards.size(); }
	public LinkedList<AdventureCard> getHand() { return this.playerHandCards; }
	public LinkedList<AdventureCard> getActive() { return this.playerActiveCards; }
	public LinkedList<AdventureCard> getStage() { return this.playerStageCards; }
	public boolean getKingsRecognitionBonus() { return this.kingsRecognitionBonus; }
	public void setKingsRecognitionBonus(boolean b) { this.kingsRecognitionBonus = b; }
	
	public void setState(String s) { 
		if(s == "normal")
			state_ = new NormalState();
		else if(s == "sponsor")
			state_ = new SponsorState();
		else if(s == "questParticipant")
			state_ = new QuestParticipationState();
		else if(s == "playQuest")
			state_ = new QuestPlayState();
		}
	
	public void setHand(String[] cards) { 		//used in testing
		CardSpawner spawner = new CardSpawner();
		for(String name : cards)
			playerHandCards.add(spawner.spawnAdventureCard(name));
		for(AdventureCard card : playerHandCards) {
			card.setState(State.HAND);
			card.setOwner(this);
		}
	}     
	
	public void setActiveHand(String[] cards) {	//used in testing
		CardSpawner spawner = new CardSpawner();
		for(String name : cards)
			playerActiveCards.add(spawner.spawnAdventureCard(name));
		for(AdventureCard card : playerActiveCards) {
			card.setState(State.PLAY);
			card.setOwner(this);
		}
	} 
	
	//used in testing needed due to playing card needs to be same one in hand
	public void setHand(LinkedList<AdventureCard> l) {
		this.playerHandCards = l;
	}
	
	public int getRankBattlePts() {
		if(rank == Rank.SQUIRE)
			return 5;
		else if(rank == Rank.KNIGHT)
			return 10;
		else if(rank == Rank.CHAMPION_KNIGHT || rank == Rank.KNIGHT_OF_THE_ROUND_TABLE)
			return 20;
		return 0;
	}

	public boolean drawCard(AdventureDeck d) {
		// can't have more than 12 cards
		//TODO: allow player to play cards if player is already at hand limit
		if(playerHandCards.size() >= 12) {
			return false;
		}
		else {
			//call drawCard from adventure deck
			AdventureCard card = d.drawCard();
			playerHandCards.add(card);
			card.setOwner(this);
			card.setState(State.HAND);
			log.info(name + " drew " + card.getName() + " from adventure deck");
			return true;
		}
	}
	
	protected AdventureCard getHandCard(int pos) {
		return playerHandCards.get(pos);
	}
	
	// used for tournaments beginning everyone has to draw a card.
	public void forceDrawAdventure(AdventureDeck d) {
		//call drawCard from adventure deck
		AdventureCard card = d.drawCard();
		playerHandCards.add(card);
		card.setOwner(this);
		card.setState(State.HAND);		
		log.info("Forced player:" + name + " to draw card from adventure deck");
	}
	
	public boolean tooManyHandCards() {
		return (playerHandCards.size() > 12);
	}
	
	public boolean existsActive(String cardName) {
		for(int i = 0; i < playerActiveCards.size(); i++) {
			if(playerActiveCards.get(i).getName().equalsIgnoreCase(cardName))
				return true;
		}
		return false;
	}
	
	//play card functionality based on current state of the player
	public boolean playCard(AdventureCard c) {
		return state_.playCard(c, this);
	}
	
	//discard a card from hand or play
	public boolean discardCard(AdventureCard c, AdventureDeck d) {
		return state_.discardCard(c, d, this);
	}
	
	//do something based on user input (Yes/No/Done)
	public void userInput(boolean b) {
		state_.userInput(b);
	}
	
	//during quest, reveals cards played in a stage
	public void revealStageCards() {
		for(AdventureCard stageCard : playerStageCards) {
			playerActiveCards.add(stageCard);
			stageCard.setState(State.PLAY);
			log.info(name + " reveals " + stageCard.getName());
		}
		playerStageCards.clear();
	}
	
	
	
	//discards all the players active weapsons
	public void discardWeaponsActive(AdventureDeck d) {
		for(int i = 0; i < playerActiveCards.size(); i++) {
			if(playerActiveCards.get(i) instanceof WeaponCard) {
				discardCard(playerActiveCards.get(i), d);
			}
		}
	}
	
	//discards the players active amour
	public void discardAmoursActive(AdventureDeck deck) {
		for(int i = 0; i < playerActiveCards.size(); i++) {
			if(playerActiveCards.get(i) instanceof AmourCard) {
				discardCard(playerActiveCards.get(i), deck);
			}
		}
	}
	
	//add shields to a player
	public void addShields(int sh) {
		shields += sh;
		log.info(name + " gained " + sh + " shields.");
		log.info((shields - sh) + " -> " + shields);
		if(shields >= 5 && rank == Rank.SQUIRE) {
			rank = Rank.KNIGHT;
			shields -= 5;
			log.info(name + " ranked up to " + rank + ".");
		}
		if(shields >= 7 && rank == Rank.KNIGHT) {
			rank = Rank.CHAMPION_KNIGHT;
			shields -= 7;
			log.info(name + " ranked up to " + rank + ".");
		}
		if(shields >= 10 && rank == Rank.CHAMPION_KNIGHT) {
			rank = Rank.KNIGHT_OF_THE_ROUND_TABLE;
			log.info(name + " ranked up to " + rank + ".");
			//triggers winning condition
		}
	}
	
	
	public void loseShields(int sh) {
		log.info(name + " lost " + sh + " shields.");
		if(shields < sh) {
			log.info(shields + " -> 0");
			shields = 0;
		}
		else {
			log.info(shields + " -> " + (shields - sh));
			shields -= sh;
		}
	}
	
	public void printHand() {
		System.out.printf("Hand:\n");
		System.out.printf("%-15s%-15s%s\n", "Name", "Battle Points", "Type");
		System.out.printf("==================================\n");
		for(AdventureCard a : this.playerHandCards) {
			a.printCard();
		}
		System.out.printf("Number of cards: %s\n", this.playerHandCards.size());
	}
	
	private String rankS() {
		if(this.rank == Rank.SQUIRE)
			return "Squire";
		else if(this.rank == Rank.KNIGHT)
			return "Knight";
		else if(this.rank == Rank.CHAMPION_KNIGHT)
			return "Champion Knight";
		else if(this.rank == Rank.KNIGHT_OF_THE_ROUND_TABLE)
			return "Knight of the Round Table";
		return "";
	}
	
	public void printActive() {
		System.out.printf("Active:\n");
		System.out.printf("%-15s%-15s%s\n", "Name", "Battle Points", "Type");
		System.out.printf("==================================\n");
		for(AdventureCard a : this.playerActiveCards) {
			a.printCard();
		}
		System.out.printf("Number of cards: %s\n", this.playerActiveCards.size());
	}
	
	public void printStage() {
		System.out.printf("Stage:\n");
		System.out.printf("%-15s%-15s%s\n", "Name", "Battle Points", "Type");
		System.out.printf("==================================\n");
		for(AdventureCard a : this.playerStageCards) {
			a.printCard();
		}
		System.out.printf("Number of cards: %s\n", this.playerStageCards.size());
	}
}