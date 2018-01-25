package com.comp_3004.quest_cards.core;

import java.util.Scanner;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.TournamentCard;

public class TournamentMatch{
	
	private AdventureDeck advDeck;
	private int numPlayers;
	private Players players;
	
	private TournamentCard tourCard;
	private int roundNumber = 1;  // game starts at first round can go up to 3(double tie)
	private boolean runningTour = true;
	
	public TournamentMatch(Game logic) {
		this.advDeck = logic.advDeck;
		this.players = logic.players;
		this.numPlayers = logic.numPlayers;
		this.players = logic.players;
	}
	
	protected void play(TournamentCard tcard) {
		tourCard = tcard;
		Player currentPlyr = players.current();
		System.out.println("Player: " + currentPlyr.getName() + " has drawn :");
		tourCard.printCard();
		//determineParticipants();
		
		while(runningTour) {
			
			
			
			
		}
		
		//hot seat through participating players let them pick cards
		
		
	}
	
	
}