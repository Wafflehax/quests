package com.comp_3004.quest_cards.core;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.*;
import com.comp_3004.quest_cards.cards.AdventureCard.State;

public class Player{
	static Logger log = Logger.getLogger(Player.class); //log4j logger
	private String name;
	public enum Rank { SQUIRE, KNIGHT, CHAMPION_KNIGHT, KNIGHT_OF_THE_ROUND_TABLE };
	private Rank rank;
	private int shields;
	
	protected LinkedList<AdventureCard> playerHandCards;
	protected LinkedList<AdventureCard> playerActiveCards;

	
	protected boolean participateQuest;
	protected volatile boolean participateTournament;
	
	// constructor
	public Player(String name) {
		this.name = name;
		this.rank = Rank.SQUIRE;
		this.shields = 0;
		this.playerHandCards = new LinkedList<AdventureCard>();
		this.playerActiveCards = new LinkedList<AdventureCard>();
	}
	
	// getters/setters
	public String getName() { return this.name; }
	public Rank getRank() { return this.rank; }
	public int getShields() { return this.shields; }
	public boolean participantInTournament() { return participateTournament; }
	public void participateTour(boolean b) { participateTournament = b; }
	public int numberOfHandCards() { return playerHandCards.size(); }
	public int numberOfActiveCards() { return playerActiveCards.size(); }
	public LinkedList<AdventureCard> getHand() { return this.playerHandCards; }
	public LinkedList<AdventureCard> getActive() { return this.playerActiveCards; }

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
	
	protected boolean exists(String cardName) {
		for(int i = 0; i < playerActiveCards.size(); i++) {
			if(playerActiveCards.get(i).getName().equalsIgnoreCase(cardName))
				return true;
		}
		return false;
	}
	
	public boolean playCard(AdventureCard c) {
		// can only add cards to table from your hand
		if(playerHandCards.contains(c)) {
			playerActiveCards.add(c);
			playerHandCards.remove(c);
			c.setState(State.PLAY);
			log.info("played card " + c.getName());
			return true;
		}
		//TODO: conditions where player cannot play card
		log.info("Failed you do now have this card " + c.getName());
		return false;
	}
	
	public boolean discardCard(AdventureCard c, AdventureDeck d) {
		// can only discard cards from table or from your hand
		if(c.getOwner() == this && (c.getState() == State.PLAY || c.getState() == State.HAND)) {
			if(playerActiveCards.contains(c)){
				playerActiveCards.remove(c);
			}
			else if(playerHandCards.contains(c)) {
				playerHandCards.remove(c);
			}
			d.discardCard(c);
			c.setState(State.DISCARD);
			c.setOwner(null);
		}
		return false;
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
	
	public void addShields(int sh) {
		shields += sh;
		if(shields >= 5 && rank == Rank.SQUIRE) {
			rank = Rank.KNIGHT;
			shields -= 5;
		}
		if(shields >= 7 && rank == Rank.KNIGHT) {
			rank = Rank.CHAMPION_KNIGHT;
			shields -= 7;
		}
		if(shields >= 10 && rank == Rank.CHAMPION_KNIGHT) {
			rank = Rank.KNIGHT_OF_THE_ROUND_TABLE;
			//triggers winning condition
		}
	}
	
	public void loseShields(int sh) {
		if(shields < sh)
			shields = 0;
		else
			shields -= sh;
	}
}