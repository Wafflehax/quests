package com.comp_3004.quest_cards.core;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.core.match.GameMatch;


public class GameModel{
	
	static Logger log = Logger.getLogger(GameModel.class); //log4j logger
	public static final byte MAX_HAND_SIZE = 12;
	
	protected AdventureDeck advDeck;
	protected StoryDeck storyDeck;
	private GameMatch match; //calling quests and tours matches
	private int numPlayers;
	private volatile Players players;
	private volatile Object lockObj = new Object(); // lock for current thread
	protected volatile ThreadLock lock = new ThreadLock(lockObj);
	public static enum cardModes { PLAY, DISCARD, NONE}; // determine when card was pressed what action it was. ex.going to discard, or activating(playing),none(nothing)
	protected cardModes cardMode = cardModes.NONE;
	
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
		initPlayersStart(numPlayers);
		
		//testing
		//Player 0 draws a tournament card
		TournamentCard york = new TournamentCard("Tournament at York", 0);
		match = new Tournament(players, advDeck, york, lock, log);
		match.run();
	}
	
	public void cardPressed(int pos) {
		if(pos < 0 || pos > players.current().playerHandCards.size()-1) {
			log.info("invalid card, does not match hand");
		}else {
			AdventureCard c = players.current().getHandCard(pos);
			if(cardMode == cardModes.DISCARD) {
				log.info("Press mode: DISCARD");
				players.current().discardCard(c, advDeck);
			}else if(cardMode == cardModes.PLAY) {
				match.playCard(c);
				log.debug("Press mode: PLAY");
			}else if(cardMode == cardModes.NONE) {
				log.debug("Press mode: NONE");
			}else {
				log.debug("UNKNOWN Press mode");
			}	
		}
	}
	
	
	private void initPlayersStart(int numPlayers) {
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
}