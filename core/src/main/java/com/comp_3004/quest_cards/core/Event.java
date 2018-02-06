package com.comp_3004.quest_cards.core;

import java.util.ArrayList;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.StoryCard;

public class Event {
	
	private StoryCard evnt;
	private Players players;
	private AdventureDeck advDeck;

	public Event(StoryCard e, Players p, AdventureDeck d) {
		this.evnt = e;
		this.players = p;
		this.advDeck = d;
	}
	
	
	public void runEvent() {
		//can't use switch bc project is on javaSE-1.6, change to switch if we ever switch versions
		if(evnt.getName() ==  "Chivalrous Deed") {
			System.out.printf("Running event %s\n", evnt.getName());
			for(int i=0; i<players.getNumPlayers(); i++) {
				System.out.printf("%s's Rank: %s\n", players.getPlayerAtIndex(i).getName(), players.getPlayerAtIndex(i).getRank());
				System.out.printf("%s's shields: %s\n", players.getPlayerAtIndex(i).getName(), players.getPlayerAtIndex(i).getShields());
			}
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
			for(int i=0; i<players.getNumPlayers(); i++) {
				System.out.printf("%s's updated Rank: %s\n", players.getPlayerAtIndex(i).getName(), players.getPlayerAtIndex(i).getRank());
				System.out.printf("%s's updated shields: %s\n", players.getPlayerAtIndex(i).getName(), players.getPlayerAtIndex(i).getShields());
			}
			
		}
		else if(evnt.getName() ==  "Pox") {
			System.out.printf("Running event %s\n", evnt.getName());
			for(int i=0; i<players.getNumPlayers(); i++) {
				if (players.getPlayers().get(i) != players.current()) {
					System.out.printf("%s's shields: %s\n", players.getPlayerAtIndex(i).getName(), players.getPlayerAtIndex(i).getShields());
					players.getPlayerAtIndex(i).loseShields(1);
					System.out.printf("%s's updted shields: %s\n", players.getPlayerAtIndex(i).getName(), players.getPlayerAtIndex(i).getShields());
				}
			}
			
		}
		else if(evnt.getName() ==  "Plague") {
			System.out.printf("Running event %s\n", evnt.getName());
			System.out.printf("%s's shields: %s\n", players.current().getName(), players.current().getShields());
			players.current().loseShields(2);
			System.out.printf("%s's updted shields: %s\n", players.current().getName(), players.current().getShields());
			
		}
		else if(evnt.getName() ==  "King's Recognition") {
			System.out.printf("The event %s is not yet implemented\n", evnt.getName());
			//TODO: implement this event later
		}
		else if(evnt.getName() ==  "Queen's Favor") {
			System.out.printf("Running event %s\n", evnt.getName());
			for(int i=0; i<players.getNumPlayers(); i++)
				System.out.printf("%s's Rank: %s\n", players.getPlayerAtIndex(i).getName(), players.getPlayerAtIndex(i).getRank());

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
				System.out.printf("%s\n ============\n", p.getName());
				p.printHand();
				p.drawCard(advDeck);
				p.drawCard(advDeck);
				p.printHand();
			}
			
			
		}
		else if(evnt.getName() ==  "Court Called to Camelot") {
			System.out.printf("Running event %s\n", evnt.getName());
			for(Player p : players.getPlayers()) {
				System.out.printf("%s\n ============\n", p.getName());
				p.printActive();
				ArrayList<AdventureCard> discard = new ArrayList<AdventureCard>();
				for(AdventureCard card : p.getActive()) {
					if(card instanceof AllyCard)
						discard.add(card);
				}
				for(AdventureCard card : discard)
					p.discardCard(card, advDeck);
				p.printActive();
					
			}
			
			
		}
		else if(evnt.getName() ==  "King's Call to Arms") {
			System.out.printf("The event %s is not yet implemented\n", evnt.getName());
			//TODO: implement this event later
		}
		else if(evnt.getName() ==  "Prosperity Throughout the Realms") {
			System.out.printf("Running event %s\n", evnt.getName());
			for(int i=0; i<players.getNumPlayers(); i++) {
				System.out.printf("%s\n ============\n", players.current().getName());
				players.getPlayerAtIndex(i).printHand();
				players.getPlayerAtIndex(i).drawCard(advDeck);
				players.getPlayerAtIndex(i).drawCard(advDeck);
				players.getPlayerAtIndex(i).printHand();
			}
			
		}
	}
}