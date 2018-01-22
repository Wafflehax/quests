package com.comp_3004.quest_cards.core;

import java.util.Stack;

public class MainGameLoop{
	
	private AdventureDeck advDeck;
	private StoryDeck storyDeck;
	
	private int numPlayers;
	private Player[] players; // does not grow during game
	private boolean runGameLoop;
	
	// add storage of player sitting positions
	private Player currentTurn;
	
	
	
	// constructor
	public MainGameLoop() {	}
	
	public void startGame(int numPlayers) {
		this.numPlayers = numPlayers;
		advDeck = new AdventureDeck();
		storyDeck = new StoryDeck();
		players = new Player[numPlayers];
		runGameLoop = true;
		
	}

	
}