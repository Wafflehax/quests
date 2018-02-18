package com.comp_3004.quest_cards.player;

import java.util.Comparator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.Stories.Quest;
import com.comp_3004.quest_cards.Stories.Tour;
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
	private Quest currentQuest;
	private Tour currTour;
	private PlayerState state_;
	
	// constructor
	public Player(String name) {
		this.name = name;
		this.rank = Rank.SQUIRE;
		this.shields = 0;
		this.playerHandCards = new LinkedList<AdventureCard>();
		this.playerActiveCards = new LinkedList<AdventureCard>();
		this.playerStageCards = new LinkedList<AdventureCard>();
		this.currentQuest = null;
		this.currTour = null;
		this.state_ = new NormalState();
	}
	
	// getters/setters
	public String getName() { return this.name; }
	public Rank getRank() { return this.rank; }
	public String getRankS() { return rankS(); }
	public int getShields() { return this.shields; }
	public int numberOfHandCards() { return playerHandCards.size(); }
	public int numberOfActiveCards() { return playerActiveCards.size(); }
	public LinkedList<AdventureCard> getHand() { return this.playerHandCards; }
	public LinkedList<AdventureCard> getActive() { return this.playerActiveCards; }
	public LinkedList<AdventureCard> getStage() { return this.playerStageCards; }
	public boolean getKingsRecognitionBonus() { return this.kingsRecognitionBonus; }
	public void setKingsRecognitionBonus(boolean b) { this.kingsRecognitionBonus = b; }
	public void setQuest(Quest q) { this.currentQuest = q; }
	public Quest getQuest() { return this.currentQuest; }
	public void setTour(Tour t) { this.currTour = t; }
	public Tour getTour() { return this.currTour; }
	public void setState(String s) { 
		if(s == "normal")
			state_ = new NormalState();
		else if(s == "sponsor")
			state_ = new SponsorState();
		else if(s == "questParticipant")
			state_ = new QuestParticipationState();
		else if(s == "playQuest")
			state_ = new QuestPlayState();
		else if(s == "bid")
			state_ = new BidState();
		else if(s == "tooManyCards")
			state_ = new TooManyCardsState();
		else if(s == "event")
			state_ = new EventState();
		else if(s == "tourask")
			state_ = new TourParticipationState();
		else if(s== "playtour") 
			state_ = new TourPlayState();
		else if(s == "tourcomp")
			state_ = new TourComputerState();
	}

	public String getState() {
		String state = null;
		if(state_ instanceof NormalState)
			state = "normal";
		else if(state_ instanceof SponsorState)
			state = "sponsor";
		else if(state_ instanceof QuestParticipationState)
			state = "questParticipant";
		else if(state_ instanceof QuestPlayState)
			state = "playQuest";
		else if(state_ instanceof TooManyCardsState)
			state = "tooManyCards";
		else if(state_ instanceof EventState)
			state = "event";
		else if(state_ instanceof TourParticipationState)
			state = "tourask";
		else if(state_ instanceof TourPlayState)
			state = "playtour";
		else if(state_ instanceof TourComputerState)
			state = "tourcomp";
		return state;
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
		for(AdventureCard c : l) {
			c.setState(State.HAND);
			c.setOwner(this);
		}
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
		//call drawCard from adventure deck
		AdventureCard card = d.drawCard();
		card.setOwner(this);
		card.setState(State.HAND);
		playerHandCards.add(card);
		log.info(name + " drew " + card.getName() + " from adventure deck");
		return true;
	}
	
	public void pickCard(String card, AdventureDeck d) {
		AdventureCard target = null;
		for(AdventureCard c : d.getDeck()) {
			if(c.getName() == card) {
				target = c;
				break;
			}
		}
		if(target != null) {
			d.getDeck().remove(target);
			target.setOwner(this);
			target.setState(State.HAND);
			playerHandCards.add(target);
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
		if(playerActiveCards.size() > 0) {
			for(int i = 0; i < playerActiveCards.size(); i++) {
				if(playerActiveCards.get(i).getName().equalsIgnoreCase(cardName))
					return true;
			}
		}
		return false;
	}
	
	//play card functionality based on current state of the player
	public boolean playCard(AdventureCard c) {
		return state_.playCard(c, this);
	}

	public boolean playCard(AdventureCard c, int stageNum) {
		if(state_ instanceof SponsorState)
			return ((SponsorState)state_).playCard(c, this, stageNum);
		else
			return state_.playCard(c, this);
	}
	
	//discard a card from hand or play
	public boolean discardCard(AdventureCard c, AdventureDeck d) {
		return state_.discardCard(c, d, this);
	}
	
	//do something based on user input (Yes/No/Done)
	public boolean userInput(int input) {
		return state_.userInput(input, this);
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
	
	
	
	//discards all the players active weapons
	public void discardWeaponsActive(AdventureDeck d) {
		int size = playerActiveCards.size() - 1;
		for(int i = size; i >= 0; i--) {
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
	
	public int getFreeBids() {
		int num = 0;
		for(AdventureCard card : playerActiveCards) {
			if(card instanceof AllyCard)
				num += ((AllyCard) card).getBids();
			if(card instanceof AmourCard)
				num += ((AmourCard) card).getBids();
		}
		return num;
	}
	public int numBidsAllowed() {
		int num = 0;
		num += getFreeBids();
		num += playerHandCards.size();	
		return num;
	}
	
	public void printHand() {
		log.info(name+"'s Hand: ");
		log.info(String.format("%-15s%-15s%s", "Name", "Battle Points", "ID"));
		log.info("==================================");
		for(AdventureCard a : this.playerHandCards) {
			log.info(a.printCard());
		}
		log.info("Number of cards: "+this.playerHandCards.size());
	}
	
	public void printActive() {
		log.info(name+"'s Active: ");
		log.info(String.format("%-15s%-15s%s", "Name", "Battle Points", "ID"));
		log.info("==================================");
		for(AdventureCard a : this.playerActiveCards) {
			log.info(a.printCard());
		}
		log.info("Number of cards: "+this.playerActiveCards.size());
	}
	
	public void printStage() {
		log.info("Stage: ");
		log.info(String.format("%-15s%-15s%s", "Name", "Battle Points", "ID"));
		log.info("==================================");
		for(AdventureCard a : this.playerStageCards) {
			log.info(a.printCard());
		}
		log.info(String.format("Number of cards: %s\n", this.playerStageCards.size()));
	}
}