package com.comp_3004.quest_cards.core;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.StoryCard;

public class Event {
	
	static Logger log = Logger.getLogger(Event.class); //log4j logger
	private StoryCard evnt;
	private Players players;
	private AdventureDeck advDeck;

	public Event(StoryCard e, Players p, AdventureDeck d) {
		this.evnt = e;
		this.players = p;
		this.advDeck = d;
	}
	
	
	public void runEvent() {
		log.info(players.current().getName() + " drew event " + evnt.getName());
		
		if(evnt.getName() ==  "Chivalrous Deed") {
			ArrayList<Player> lowestRank = new ArrayList<Player>();
			lowestRank.add(0, players.getPlayerAtIndex(0));
			for(int i=1; i<players.getNumPlayers(); i++) {
				if(lowestRank.get(0).getRank().compareTo(players.getPlayerAtIndex(i).getRank()) == 1) {
					lowestRank.clear();
					lowestRank.add(0, players.getPlayerAtIndex(i));
				}
				else if(lowestRank.get(0).getRank().compareTo(players.getPlayerAtIndex(i).getRank()) == 0) {
					lowestRank.add(lowestRank.size(), players.getPlayerAtIndex(i));
				}
			}
			
			ArrayList <Player> lowestShields = new ArrayList<Player>();
			lowestShields.add(0, lowestRank.get(0));
			for(int i=1; i<lowestRank.size(); i++) {
				if(players.getPlayerAtIndex(i).getShields() < lowestShields.get(0).getShields()) {
					lowestShields.clear();
					lowestShields.add(0, players.getPlayerAtIndex(i));
				}
				else if(players.getPlayerAtIndex(i).getShields() == lowestShields.get(0).getShields()) {
					lowestShields.add(lowestShields.size(), lowestRank.get(i));
				}
			}
			for(Player p : lowestShields)
				p.addShields(3);
		}
		
		else if(evnt.getName() ==  "Pox") {
			for(int i=0; i<players.getNumPlayers(); i++) {
				if (players.getPlayers().get(i) != players.current()) {
					players.getPlayerAtIndex(i).loseShields(1);
					}
			}
			
		}
		
		else if(evnt.getName() ==  "Plague") {
			players.current().loseShields(2);
			
		}
		
		else if(evnt.getName() ==  "King's Recognition") {
			System.out.printf("The event %s is not yet implemented\n", evnt.getName());
			//TODO: implement this event later
		}
		
		else if(evnt.getName() ==  "Queen's Favor") {
			ArrayList<Player> lowestRank = new ArrayList<Player>();
			lowestRank.add(0, players.getPlayerAtIndex(0));
			for(int i=1; i<players.getNumPlayers(); i++) {
				if(lowestRank.get(0).getRank().compareTo(players.getPlayerAtIndex(i).getRank()) == 1) {
					lowestRank.clear();
					lowestRank.add(0, players.getPlayerAtIndex(i));
				}
				else if(lowestRank.get(0).getRank().compareTo(players.getPlayerAtIndex(i).getRank()) == 0) {
					lowestRank.add(lowestRank.size(), players.getPlayerAtIndex(i));
				}
			}
			for(Player p : lowestRank) {
				p.drawCard(advDeck);
				p.drawCard(advDeck);
			}
		}
			
		else if(evnt.getName() ==  "Court Called to Camelot") {
			for(Player p : players.getPlayers()) {
				ArrayList<AdventureCard> discard = new ArrayList<AdventureCard>();
				for(AdventureCard card : p.getActive()) {
					if(card instanceof AllyCard)
						discard.add(card);
				}
				for(AdventureCard card : discard)
					p.discardCard(card, advDeck);
			}
			
			
		}
		else if(evnt.getName() ==  "King's Call to Arms") {
			System.out.printf("The event %s is not yet implemented\n", evnt.getName());
			//TODO: implement this event later
		}
		else if(evnt.getName() ==  "Prosperity Throughout the Realms") {
			for(int i=0; i<players.getNumPlayers(); i++) {
				players.getPlayerAtIndex(i).drawCard(advDeck);
				players.getPlayerAtIndex(i).drawCard(advDeck);
			}
			
		}
	}
}