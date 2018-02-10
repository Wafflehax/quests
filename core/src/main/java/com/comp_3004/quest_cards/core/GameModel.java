package com.comp_3004.quest_cards.core;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.StoryDeck;	//used for testing
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.states.State;			//used for testing
import com.comp_3004.quest_cards.cards.StoryCard;


public class GameModel{
	
	static Logger log = Logger.getLogger(GameModel.class); //log4j logger
	public static final byte MAX_HAND_SIZE = 12;
	public Stack<State> state = new Stack<State>();
	
	private Event event;
	private AdventureDeck advDeck;
	private StoryDeck storyDeck;
	private int numPlayers;
	private Players players = new Players(0, numPlayers, new ArrayList<Player>());
	private Card StoryEv; //hold current Story card, Event
	public Players playersTemp;
	     
	private int joiners; // holds the amount of players that initally joined a tournament

	
	public void resetJoiners() { joiners = 0; }
	public int getJoiners() { return joiners; }
	
	
	//getters 
	public Players getPlayers() { return players; }
	public Player getPlayerAtIndex(int i) { return this.players.getPlayers().get(i); }
	public int getNumPlayers() { return this.players.getNumPlayers(); }
	public Player getcurrentTurn() { return players.current();}
	public AdventureDeck getAdvDeck() { return this.advDeck; }
	public StoryDeck getStoryDeck() { return this.storyDeck; }
	public Card getStory() { return StoryEv; }
	
	public State getState() { 
		if(state == null || state.isEmpty()) {
			log.info("getState: Error no state");
			return null;
		}
		else
			return state.peek();
	}
	public void StateMsg() {
		if(getState() != null)
			getState().msg();
	}
	public State sPop() { return state.pop(); }
	// Setters
	public void setPlayers(Players p) { players = p; }
	public void setStory(Card c) {		this.StoryEv = c;	}
	public void pushSt(State m) { state.push(m); } 
	
	//reset position to start
	public void SetPlayerArrayResetPos(ArrayList<Player> p) {
		players.players = p;
		players.setSize(p.size());
		players.setPos(0);
	}
	
	// constructor
	public GameModel() {
		advDeck = new AdventureDeck();
		advDeck.shuffle();
		storyDeck = new StoryDeck();
		storyDeck.shuffle();
		initPlayersStart(4, MAX_HAND_SIZE);
	}

	public GameModel(int num) {
		advDeck = new AdventureDeck();
		advDeck.shuffle();
		storyDeck = new StoryDeck();
		storyDeck.shuffle();
		initPlayersStart(num, MAX_HAND_SIZE);
	}
	
	//testing constructor
	public GameModel(int n, int c, AdventureDeck a, StoryDeck s) {
		this.numPlayers = n;
		this.advDeck = a;
		this.storyDeck = s;
		initPlayersStart(numPlayers, c); //c is the number of cards the player will start with

	}
	
	public void addPlayer(Player p) {
		players.addPlayer(p);
	}
	
	public boolean playCard(Card c) {
		if(c instanceof WeaponCard) {
			return players.current().playCard((WeaponCard)c);
		}else if(c instanceof AmourCard) {
			return players.current().playCard((AmourCard)c);
		}else if(c instanceof AllyCard) {
			return players.current().playCard((AllyCard)c);
		}else {
			log.info("playCard(Card c):Error could not cast to proper card to play");
			return false;
		}
	}
	
	public boolean disCard(Card c) {
		if(c instanceof AdventureCard) {
			AdventureCard cw = (AdventureCard)c;
			return players.current().discardCard(cw, advDeck);		
		}
		else
			return false;
	}
	
	
	
	public boolean inPlay(String name) {
		return getPlayers().current().existsActive(name);
	}
	
	public Player nextPlayer() {
		return players.next();
	}
	
	public Player prevPlayer() {
		return players.prev();
	}
	
	public void forceAdventureDraw() {
		players.current().forceDrawAdventure(advDeck);
		log.info("Forced " + players.current().getName() + " to draw adventure card");
	}
	
	public void setParticipationTour(boolean b) {
		if(b) {
			players.current().participateTournament = true;
			joiners++;	
		}else {
			players.current().participateTournament = false;
		}		
	}
	
	public boolean enoughTournamentParticipants() {
		if(players.getTournamentParticipants().size() >= 2) {
			return true;
		}
		else
			return false;
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
	
	//          Tester functions 
	//Event testing
	public void eventTest() {
		
			StoryCard cardDrawn = storyDeck.drawCard();
			event = new Event(cardDrawn, players, advDeck);
			event.runEvent();
			storyDeck.discardCard(cardDrawn);
	}
	
}