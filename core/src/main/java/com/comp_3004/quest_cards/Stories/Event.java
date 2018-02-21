package com.comp_3004.quest_cards.Stories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Players;

public class Event {
	
	//attributes
	static Logger log = Logger.getLogger(Event.class); //log4j logger
	private StoryCard evnt;
	private Players players;
	private AdventureDeck advDeck;
	private HashMap<Player, Integer> cardsToDiscard;
	private ArrayList<Player> highestRank;
	private Player drewEvent;

	//constructor
	public Event(StoryCard e, Players pls, AdventureDeck d, Player p) {
		this.evnt = e;
		this.players = pls;
		this.advDeck = d;
		this.highestRank = new ArrayList<Player>();
		this.cardsToDiscard = new HashMap<Player, Integer>();
		this.drewEvent = p;
		for(Player pl : players.getPlayers())
			pl.setEvent(this);
	}
	
	//getters/setters
	public ArrayList<Player> getHighestRank() { return this.highestRank; }
	public HashMap<Player, Integer> getCardsToDiscard() { return this.cardsToDiscard; }
	public Players getPlayers() { return this.players; }
	public Player getDrewEvent() { return this.drewEvent; }
	
	public boolean runEvent() {
		log.info(players.current().getName() + " drew event " + evnt.getName());
		for(Player p : players.getPlayers())
			p.setState("event");
		
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
			for(Player p : highestRank) {
				log.info(p.getName() +" must discard cards");
				int numFoes = 0;
				for(AdventureCard card : p.getHand()) {
					if(card instanceof FoeCard)
						numFoes++;
					if(numFoes == 2)
						break;
				}
				cardsToDiscard.put(p, numFoes);
			}
			for(int i=0; i<players.size(); i++) {
				boolean hasWeapon = false;
				for(AdventureCard card : players.getPlayerAtIndex(i).getHand()) {
					if(card instanceof WeaponCard)
						hasWeapon = true;
				}
				if(!hasWeapon) {
					if(cardsToDiscard.get(players.getPlayerAtIndex(i)) == 0)
						highestRank.remove(players.getPlayerAtIndex(i));
				}
			}
			
			players.setCurrent(highestRank.get(0));
			return false;
		}
		else if(evnt.getName() ==  "Prosperity Throughout the Realms") {
			for(int i=0; i<players.getNumPlayers(); i++) {
				players.getPlayerAtIndex(i).drawCard(advDeck);
				players.getPlayerAtIndex(i).drawCard(advDeck);
			}
			
		}
		log.info("Event Finished");
		for(int i=0; i<players.getNumPlayers(); i++) {
			if(players.getPlayerAtIndex(i).getHand().size() > 12) {
				players.getPlayerAtIndex(i).setState("tooManyCards");
			}
		}
		
		return true;
	}
	
	public boolean checkForTooManyCards() {
		for(int i=0; i<players.getNumPlayers(); i++) {
			if(players.current().getState() == "tooManyCards") {
				return true;
			}
			players.next();
		}
		players.setCurrent(drewEvent);
		players.next();
		return false;
	}
	
	public boolean discardCard(Player p, AdventureCard c) {
		boolean hasWeapon = false;
		for(AdventureCard card : p.getHand()) {
			if(card instanceof WeaponCard)
				hasWeapon = true;
		}
		if(hasWeapon) 
			return discardWeapon(p, c);
		else {
			log.info(p.getName()+" must discard "+cardsToDiscard.get(p)+" foes");
			return discardFoe(p, c);
		}
	}
	
	private boolean discardWeapon(Player p, AdventureCard c) {
		if(!(c instanceof WeaponCard)) {
			log.info(p.getName()+" error: hand contains a weapon, must discard a weapon");
			return true;
		}
		else if(c instanceof WeaponCard) {
			highestRank.remove(p);
			if(highestRank.size() > 0) {
				players.setCurrent(highestRank.get(0));
			}
			else {
				players.setCurrent(drewEvent);
				players.next();
			}
			
		}
		return true;
	}
		
	public boolean discardFoe(Player p, AdventureCard c) {
		if(!(c instanceof FoeCard)) {
			log.info(p.getName()+" error: must discard a foe");
			return true;
		}
		else {
			cardsToDiscard.put(p, cardsToDiscard.get(p)-1);
			if(cardsToDiscard.get(p) == 0) {
				highestRank.remove(p);
				if(highestRank.size() > 0) {
					players.setCurrent(highestRank.get(0));
				}
				else {
					players.setCurrent(drewEvent);
					players.next();
				}
			}
			return true;
		}
	}
}