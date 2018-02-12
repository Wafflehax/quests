package com.comp_3004.quest_cards.core;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.WeaponCard;

public class Event {
	
	//attributes
	static Logger log = Logger.getLogger(Event.class); //log4j logger
	private StoryCard evnt;
	private Players players;
	private AdventureDeck advDeck;

	//constructor
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
			players.current().setKingsRecognitionBonus(true);
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
				for(AdventureCard card : discard) {
					p.discardCard(card, advDeck);
				}
			}
			
			
		}
		else if(evnt.getName() ==  "King's Call to Arms") {
			//get highest rank players
			ArrayList<Player> highestRank = new ArrayList<Player>();
			highestRank.add(0, players.getPlayerAtIndex(0));
			for(int i=1; i<players.getNumPlayers(); i++) {
				if(highestRank.get(0).getRank().compareTo(players.getPlayerAtIndex(i).getRank()) == -1) {
					highestRank.clear();
					highestRank.add(0, players.getPlayerAtIndex(i));
				}
				else if(highestRank.get(0).getRank().compareTo(players.getPlayerAtIndex(i).getRank()) == 0) {
					highestRank.add(highestRank.size(), players.getPlayerAtIndex(i));
				}
			}
			
			//for each player, get a list of weapon cards they have
			ArrayList<AdventureCard> discard;
			for(Player p : highestRank) {
				discard = new ArrayList<AdventureCard>();
				for(AdventureCard card : p.getHand())
					if(card instanceof WeaponCard)
						discard.add(card);
				//player has 1 weapon to discard
				if(discard.size() == 1) {
					p.discardCard(discard.get(0), advDeck);
				}
				//player has to choose which weapon to discard
				else if(discard.size() > 1) {
					Scanner sc = new Scanner(System.in);		//using scanner for user input for now
					int index;								//when switching to ui control, use cardID
					System.out.printf("%s please select index of card to discard\n", p.getName());
					index = sc.nextInt();
					p.discardCard(discard.get(index), advDeck);
				}
				//player has no weapon, must discard 2 foes
				else {
					//for each player, get a list of foe cards they have
					for(AdventureCard card : p.getHand())
						if(card instanceof FoeCard)
							discard.add(card);
					//player has 2 or less foes
					if(discard.size() <= 2) {
						for(AdventureCard c : discard)
							p.discardCard(c, advDeck);
					}
					//player has to choose which foes to discard
					else if(discard.size() > 2) {
						for(int i=0; i<2; i++) {
							Scanner sc = new Scanner(System.in);		//using scanner for user input for now
							int index;								//when switching to ui control, use cardID
							System.out.printf("%s please select index of card to discard\n", p.getName());
							index = sc.nextInt();
							p.discardCard(discard.get(index), advDeck);
						}
					}
				}
			}
		}
		else if(evnt.getName() ==  "Prosperity Throughout the Realms") {
			for(int i=0; i<players.getNumPlayers(); i++) {
				players.getPlayerAtIndex(i).drawCard(advDeck);
				players.getPlayerAtIndex(i).drawCard(advDeck);
			}
			
		}
	}
}