package com.comp_3004.quest_cards.core;

import java.util.ArrayList;
import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.TournamentCard;

public class Game{
	
	protected AdventureDeck advDeck;
	protected StoryDeck storyDeck;
	
	protected int numPlayers;
	private boolean runGameLoop;
	protected Players players;
	private final int MAX_HAND_SIZE = 12;
	protected volatile Object lock = new Object(); // lock for current thread
	private Player currentTurn;
	
	
	
	// constructor
	public Game() {	}
	
	public void startGame(int numPlayers) {
		this.numPlayers = numPlayers;
		advDeck = new AdventureDeck();
		storyDeck = new StoryDeck();
		//init players
		ArrayList<Player> plyrs = new ArrayList<Player>(numPlayers);
		for(int i = 0; i < numPlayers; i++)
			plyrs.add(new Player("Player " + i));
		players = new Players(0, numPlayers-1, plyrs);
		
		runGameLoop = true;
		LogicLoop();
		
	}
	
	private void LogicLoop() {
		while(runGameLoop) {
			
			
			
			
			//all four tournament types
			TournamentCard camelot = new TournamentCard("Tournament at Camelot", 3);
			TournamentCard orkney = new TournamentCard("Tournament at Orkney", 2);
			TournamentCard tintagel = new TournamentCard("Tournament at Tintagel", 1);
			TournamentCard york = new TournamentCard("Tournament at York", 0);
			
			Card drawn = york;
			
			
			
			if(drawn instanceof TournamentCard) {
				System.out.println("Tournament card");
				TournamentMatch tour = new TournamentMatch(this);
				tour.play(york);
			}
			//add other story cards
			else {
				
			}
			
						
			runGameLoop = false; // just testing 1 tour currently
		}
	}	

	protected void wake() {
		synchronized(lock) {
			lock.notify();
		}
	}
	
	private void sleepGame() {
		synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	
}