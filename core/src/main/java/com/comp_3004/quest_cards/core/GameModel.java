package com.comp_3004.quest_cards.core;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.TournamentCard;	//used for testing
import com.comp_3004.quest_cards.cards.EventCard;			//used for testing
import com.comp_3004.quest_cards.cards.StoryCard;


public class GameModel{
	
	static Logger log = Logger.getLogger(GameModel.class); //log4j logger
	public static final byte MAX_HAND_SIZE = 12;
	
	private Event event;
	private Quest quest;
	private AdventureDeck advDeck;
	private StoryDeck storyDeck;
	protected volatile GameMatch match; //calling quests and tours matches
	private int numPlayers;
	private Players players = new Players(0, numPlayers, new ArrayList<Player>());
	private volatile Object lockObj = new Object(); // lock for current thread
	protected volatile ThreadLock lock = new ThreadLock(lockObj);
	public static enum cardModes { PLAY, DISCARD, NONE }; // determine when card was pressed what action it was. ex.going to discard, or activating(playing),none(nothing)	
	
	// constructor
		public GameModel() {
			advDeck = new AdventureDeck();
			advDeck.shuffle();
			//advDeck.printDeck();
			storyDeck = new StoryDeck();
			storyDeck.shuffle();
			//storyDeck.printDeck();
			initPlayersStart(4, MAX_HAND_SIZE);
		}
		
		//sandbox constructor: pass in premade decks to control/set up a scenario
		public GameModel(int n, int c, AdventureDeck a, StoryDeck s) {
			this.numPlayers = n;
			this.advDeck = a;
			this.storyDeck = s;
			initPlayersStart(numPlayers, c); //c is the number of cards the player will start with
		}
	
	//getters setters
	public Players getPlayers() { return players; }
	public void setPlayers(Players p) { players = p; }
	public Player getPlayerAtIndex(int i) { return this.players.getPlayers().get(i); }
	public int getNumPlayers() { return this.players.getNumPlayers(); }
	public Player getcurrentTurn() { return players.current();}
	public AdventureDeck getAdvDeck() { return this.advDeck; }
	public StoryDeck getStoryDeck() { return this.storyDeck; }
	public cardModes getCardMode() { 
		if(match == null)
			return cardModes.NONE; //no match no playing cards
		return match.getcardMode(); 
	}
	public GameMatch getMatch() { return match; }

	
	public void startGame() {
		//testing
		//Player 0 draws a tournament card
		TournamentCard york = new TournamentCard("Tournament at York", 0);
		match = new Tournament(players, advDeck, york, lock, log);
		match.run();
	}
	
	public void addPlayer(Player p) {
		players.addPlayer(p);
	}
	
	public void cardPressed(int pos) {
			if(match == null) {
				log.info("Pressed card on null match error");
			}else {
				if(pos < 0 || pos > match.getPlayers().current().playerHandCards.size()-1) {
					log.info("invalid card, does not match hand");
				}else {
				AdventureCard c = match.getPlayers().current().getHandCard(pos);
				if(getCardMode() == cardModes.DISCARD) {
					log.info("Press mode: DISCARD");
					players.current().discardCard(c, advDeck); 
				}else if(getCardMode() == cardModes.PLAY) {
					match.playCard(c);
					log.info("Press mode: PLAY");
				}else if(getCardMode() == cardModes.NONE) {
					log.info("Press mode: NONE");
				}else {
					log.info("UNKNOWN Press mode");
				}	
			}
		}
	}
	
	// playCard, discardCard to replace cardPressed(int pos)
	public boolean playCard(Player p, AdventureCard c) {
		if(match == null) {
			log.info("Attempted to play a card on a null match(no game). Allow this in future?");
			return false;
		}else {
			match.playCard(c);	//TODO: pass the player that played the card into the method
			return true;			//TODO: have the match.playCard return a boolean (success or fail)
		}
	}
	
	public void discardCard(AdventureCard c) {
		if(match == null) {
			log.info("Attempted to discard card on null game(no game). Allow this in future?");
		}else {
			match.discardCard(c);
		}
	}
	
	public boolean setParticipation(boolean b) {
		if(match == null)
			return false;
		else {
			match.setParticipation(b);
			return true;
		}
	}
	
	public void initPlayersStart(int numPlayers, int numCards) {
		this.numPlayers = numPlayers;
		//TODO: Add players choosing their own name
		ArrayList<Player> plyrs = new ArrayList<Player>(numPlayers);
		for(int i = 0; i < numPlayers; i++) {
			Player newPlayer = new Player("Player " + i);
			for(int q = 0; q < numCards; q++)
				newPlayer.drawCard(advDeck);
			plyrs.add(newPlayer);
		}
		players = new Players(0, numPlayers, plyrs);
	}
	
	public void done() {
		lock.wake();
	}
	
	
	//          Tester functions 
	//Used for simulating a tournament for testing
	public void startGameTournamentTest() {
		//testing
		//Player 0 draws a tournament card
		TournamentCard york = new TournamentCard("Tournament at York", 0);
		match = new Tournament(players, advDeck, york, lock, log);
		match.run();
	}
	
	//Event testing
	public void eventTest() {
			StoryCard cardDrawn = storyDeck.drawCard();
			event = new Event(cardDrawn, players, advDeck);
			event.runEvent();
			storyDeck.discardCard(cardDrawn);
	}
	
	//Quest testing
	public void questTest() {
		StoryCard cardDrawn = storyDeck.drawCard();
		quest = new Quest(cardDrawn, players, advDeck);
		quest.runQuest();
		storyDeck.discardCard(cardDrawn);
	}
	
}