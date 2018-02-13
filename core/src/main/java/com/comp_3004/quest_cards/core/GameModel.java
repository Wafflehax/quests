package com.comp_3004.quest_cards.core;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.Stories.Event;
import com.comp_3004.quest_cards.Stories.Quest;
import com.comp_3004.quest_cards.Stories.Tour;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Players;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.cards.StoryCard;


public class GameModel{
	
	static Logger log = Logger.getLogger(GameModel.class); //log4j logger
	public static final byte MAX_HAND_SIZE = 12;
	
	private Event event;
	private Quest quest;
	private Tour tour;
	private AdventureDeck advDeck;
	private StoryDeck storyDeck;
	private int numPlayers;
	private Players players = new Players(0, numPlayers, new ArrayList<Player>());
	private Card StoryEv; //hold current Story card, Event
	public Players playersTemp;
	     
		
	
	//getters 
	public Players getPlayers() { return players; }
	public Player getPlayerAtIndex(int i) { return this.players.getPlayers().get(i); }
	public int getNumPlayers() { return this.players.getNumPlayers(); }
	public Player getcurrentTurn() { return players.current();}
	public AdventureDeck getAdvDeck() { return this.advDeck; }
	public StoryDeck getStoryDeck() { return this.storyDeck; }
	public Card getStory() { return StoryEv; }
	public Quest getQuest() { return this.quest; }
	
	
	// Setters
	public void setPlayers(Players p) { players = p; }
	public void setStory(Card c) {		this.StoryEv = c;	}
	
	// constructor
	public GameModel() {
		advDeck = new AdventureDeck();
		advDeck.shuffle();
		storyDeck = new StoryDeck();
		storyDeck.shuffle();
		StoryEv = null;
		initPlayersStart(4, MAX_HAND_SIZE);
	}

	public GameModel(int num) {
		advDeck = new AdventureDeck();
		advDeck.shuffle();
		storyDeck = new StoryDeck();
		storyDeck.shuffle();
		StoryEv = null;
		initPlayersStart(num, MAX_HAND_SIZE);
	}
	
	//testing constructor
	public GameModel(int n, int c, AdventureDeck a, StoryDeck s) {
		this.numPlayers = n;
		this.advDeck = a;
		this.storyDeck = s;
		StoryEv = null;
		initPlayersStart(numPlayers, c); //c is the number of cards the player will start with

	}
	
	public void beginTurn() {
		StoryCard cardDrawn = storyDeck.drawCard();
		StoryEv = cardDrawn;
		if(cardDrawn instanceof QuestCard) {
			quest = new Quest((QuestCard)cardDrawn, players, advDeck);
		}
		else if(cardDrawn instanceof TournamentCard) {
			tour = new Tour(players, (TournamentCard)cardDrawn, advDeck);
		}
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
	
	public boolean inPlay(String name) {
		return getPlayers().current().existsActive(name);
	}
	
	public Player nextPlayer() {
		return players.next();
	}
	
	public Player prevPlayer() {
		return players.prev();
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