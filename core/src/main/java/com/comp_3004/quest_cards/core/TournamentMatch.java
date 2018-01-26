package com.comp_3004.quest_cards.core;

import java.util.Scanner;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.core.Game.states;

public class TournamentMatch{
	
	private AdventureDeck advDeck;
	private int numPlayers;
	private Players players;
	private volatile ThreadLock lock;
	protected states state;
	
	private TournamentCard tourCard;
	private int roundNumber = 1;  // game starts at first round can go up to 3(double tie)
	private boolean runningTour = true;
	
	public TournamentMatch(Game logic) {
		this.advDeck = logic.advDeck;
		this.players = logic.players;
		this.numPlayers = logic.numPlayers;
		this.players = logic.players;
		this.lock = logic.lock;
		this.state = logic.state;
	}	
}