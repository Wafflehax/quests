package com.comp_3004.quest_cards.core;

import java.util.LinkedList;

import com.comp_3004.quest_cards.core.AdventureCard.State;

public class Player{
	
	private String name;
	private enum Rank { SQUIRE, KNIGHT, CHAMPION_KNIGHT, KNIGHT_OF_THE_ROUND_TABLE };
	private Rank rank;
	private int shields;
	
	protected LinkedList<AdventureCard> playerHandCards;
	protected LinkedList<AdventureCard> playerActiveCards;
	
	protected boolean participateQuest;
	protected boolean participateTournament;
	
	// constructor
	public Player(String name) {
		this.name = name;
		this.rank = Rank.SQUIRE;
		this.shields = 0;
		this.playerHandCards = new LinkedList<AdventureCard>();
		this.playerActiveCards = new LinkedList<AdventureCard>();
	}
	
	// getters/setters
	public String getName() {
		return name;
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
			card.owner = this;
			card.state = State.HAND;
			return true;
		}
	}
	
	public boolean playCard(AdventureCard c) {
		// can only add cards to table from your hand
		if(playerHandCards.contains(c)) {
			playerActiveCards.add(c);
			playerHandCards.remove(c);
			c.state = State.PLAY;
			return true;
		}
		//TODO: conditions where player cannot play card
		return false;
	}
	
	public boolean discardCard(AdventureCard c, AdventureDeck d) {
		// can only discard cards from table or from your hand
		if(c.owner == this && (c.state == State.PLAY || c.state == State.HAND)) {
			if(playerActiveCards.contains(c)){
				playerActiveCards.remove(c);
			}
			else if(playerHandCards.contains(c)) {
				playerHandCards.remove(c);
			}
			d.discardCard(c);
			c.state = State.DISCARD;
			c.owner = null;
		}
		return false;
	}

	
}