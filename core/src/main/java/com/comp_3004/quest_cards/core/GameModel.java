package com.comp_3004.quest_cards.core;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.TournamentCard;


public class GameModel{
	
	static Logger log = Logger.getLogger(GameModel.class); //log4j logger
	public static final byte MAX_HAND_SIZE = 12;
	
	public static enum gamestates{ WAITING, ASKING_PARTICIPATION, PLAYER_TURN, START,
		DISCARD_HAND_CARD }; // used for communicating with view
	
	protected String message; //displaying messg on view, ex. battle points of everyone at end of tour
	
	protected AdventureDeck advDeck;
	protected StoryDeck storyDeck;
	
	private Tournament tour;
	
	private int numPlayers;
	private volatile Players players;
	private volatile Object lockObj = new Object(); // lock for current thread
	protected volatile ThreadLock lock = new ThreadLock(lockObj);
	protected gamestates state = gamestates.START;
	
	
	
	//getters setters
	protected Player getcurrentTurn() { return players.current();}
	
	// constructor
	public GameModel() {
		System.out.println("Game model Ctor");
	}
	
	public void startGame(int numPlayers) {
		
		advDeck = new AdventureDeck();
		advDeck.shuffle();
		storyDeck = new StoryDeck();
		storyDeck.shuffle();
		initPlayersStart();
		
		//testing
		//Player 0 draws a tournament card
		TournamentCard york = new TournamentCard("Tournament at York", 0);
		tour = new Tournament(players, advDeck, york, state, lock, log);
		tour.runTournament();
	}
	
	private void initPlayersStart() {
		//TODO: Add players choosing their own name
		ArrayList<Player> plyrs = new ArrayList<Player>(numPlayers);
		for(int i = 0; i < numPlayers; i++) {
			Player newPlayer = new Player("Player " + i);
			for(int q = 0; q < MAX_HAND_SIZE; q++)
				newPlayer.drawCard(advDeck);
			plyrs.add(newPlayer);
		}
		players = new Players(0, numPlayers-1, plyrs);
	}
	
	public Tournament getTournament() { return this.tour; }
}