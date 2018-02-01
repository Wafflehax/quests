package com.comp_3004.quest_cards.core;

import com.comp_3004.quest_cards.cards.AdventureDeck;
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
			
		}
		else if(evnt.getName() ==  "Pox") {
			System.out.printf("Running event %s\n", evnt.getName());
			for(int i=0; i<players.size(); i++) {
				if (players.getPlayers().get(i) != players.current()) {
					System.out.printf("%s's shields: %s\n", players.getPlayerAtIndex(i).getName(), players.getPlayerAtIndex(i).getShields());
					players.getPlayers().get(i).loseShields(1);
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
		}
		else if(evnt.getName() ==  "Queen's Favor") {
			System.out.printf("Running event %s\n", evnt.getName());
			
		}
		else if(evnt.getName() ==  "Court Called to Camelot") {
			System.out.printf("Running event %s\n", evnt.getName());
			
		}
		else if(evnt.getName() ==  "King's Call to Arms") {
			System.out.printf("The event %s is not yet implemented\n", evnt.getName());
		}
		else if(evnt.getName() ==  "Prosperity Throughout the Realms") {
			System.out.printf("Running event %s\n", evnt.getName());
			for(int i=0; i<players.size(); i++) {
				System.out.printf("%s\n ============\n", players.current().getName());
				players.current().printHand();
				players.current().drawCard(advDeck);
				players.current().drawCard(advDeck);
				players.current().printHand();
				players.next();
			}
			
		}
	}
}