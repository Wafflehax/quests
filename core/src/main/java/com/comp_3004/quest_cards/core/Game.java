package com.comp_3004.quest_cards.core;

import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;

public class Game{
	
	private AdventureDeck advDeck;
	private StoryDeck storyDeck;
	
	private int numPlayers;
	private Player[] players; // does not grow during game
	private boolean runGameLoop;
	
	// add storage of player sitting positions
	private Player currentTurn;
	
	
	
	// constructor
	public Game() {	}
	
	public void startGame(int numPlayers) {
		this.numPlayers = numPlayers;
		advDeck = new AdventureDeck();
		storyDeck = new StoryDeck();
		players = new Player[numPlayers];
		runGameLoop = true;
		
	}

	
}