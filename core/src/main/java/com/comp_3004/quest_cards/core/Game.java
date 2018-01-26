package com.comp_3004.quest_cards.core;

import java.util.ArrayList;
import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.core.Game.states;


public class Game{
	
	protected AdventureDeck advDeck;
	protected StoryDeck storyDeck;
	
	protected int numPlayers;
	protected boolean runGameLoop;
	protected volatile Players players;
	private volatile Object lockObj = new Object(); // lock for current thread
	protected volatile ThreadLock lock = new ThreadLock(lockObj);
	public static enum states{ WAITING, ASKINGPARTICIPATION, START};
	protected states state = states.START;
	
	protected StoryCard currStory;
	
	//getters setters
	protected Player getcurrentTurn() { return players.current();}
	
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
		
		
		LogicLoopTourTesting();
		
	}
	
	private void MainGameLoop() {
		
	}
	
	
	// separate loop from main to test just tournaments
	private void LogicLoopTourTesting() {
		while(runGameLoop) {
			
			
			
			
			//all four tournament types
			TournamentCard camelot = new TournamentCard("Tournament at Camelot", 3);
			TournamentCard orkney = new TournamentCard("Tournament at Orkney", 2);
			TournamentCard tintagel = new TournamentCard("Tournament at Tintagel", 1);
			TournamentCard york = new TournamentCard("Tournament at York", 0);
			currStory = york;
			
			if(currStory instanceof TournamentCard) {
				System.out.println("Tournament card");
				playTournament();
			}
			//add other story cards
			else {
				
			}		
			runGameLoop = false; // just testing 1 tour currently
		}
	}	
	
	
	private void playTournament() {
		Player currentPlyr = players.current();
		System.out.println("Player: " + currentPlyr.getName() + " has drawn :");
		currStory.printCard();
		determineParticipants();
	}
	
	private void determineParticipants() {
		for(int i = 0; i < numPlayers && runGameLoop; i++) {
			state = states.ASKINGPARTICIPATION;
			lock.sleepGame();
			players.next();
		}
		System.out.println("Done gathering participation data");
		
		
		
		
		
	}
	
	
	
}