package com.comp_3004.quest_cards.core;

import java.util.ArrayList;

public class Player{
	
	private String name;
	
	private final int NOT_FOUND = -1;
	
	protected ArrayList<Card> playerHandCards;
	protected ArrayList<Card> playerActiveCards;
	
	protected boolean participateQuest;
	protected boolean participateTournament;
	
	// constructor
	public Player(String name) {
		this.name = name;
	}
	
	// getters/setters
	public String getName() {
		return name;
	}

	public boolean addToHand(Card c) {
		// can't have more than 12 cards
		if(playerHandCards.size() >= 12) {
			return false;
		}
		else {
			playerHandCards.add(c);
			return true;
		}
	}
	
	public void addActiveCard(Card c) {
		// can only add cards to table from your hand
		if(findCardIndex(c, playerHandCards) != NOT_FOUND) {
			playerActiveCards.add(c);
			removeHandCard(c);
		}
	}
	
	public void removeHandCard(Card c) {
		int index = findCardIndex(c, playerHandCards);
		if(index >= 0)
			playerHandCards.remove(index);
	}
	
	public void removeActiveCard(Card c) {
		int index = findCardIndex(c, playerActiveCards);
		if(index >= 0)
			playerHandCards.remove(index);
	}
	
	private int findCardIndex(Card c, ArrayList<Card> deck) {
		for(int i = 0; i < deck.size(); i++) {
			if(c == deck.get(i)) {
				return i;
			}
		}
		return NOT_FOUND;
	}
	
}