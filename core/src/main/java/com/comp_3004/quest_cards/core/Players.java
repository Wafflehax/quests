package com.comp_3004.quest_cards.core;

import java.util.ArrayList;

import com.comp_3004.quest_cards.cards.AdventureDeck;



public class Players{
	
	protected ArrayList<Player> players; //protected for testing
	private int position;
	private int endIndex;
	
	// class stores int position from 0 to endPos. Once end Position reached loops to start
	// and players

	public Players(int position, int size, ArrayList<Player> players){
		this.players = players;
		this.position = position;
		this.endIndex = size-1;
	}
	public Players(Players p) {
		this.position = p.position;
		this.endIndex = p.endIndex;
		this.players = p.players;
	}

	// getter/setter
	protected boolean isEmpty() {		return players.isEmpty();		}
	public int getNumPlayers() { return this.players.size(); }
	public ArrayList<Player> getPlayers() { return this.players; }
	
	public void addPlayer(String name) {
		Player p = new Player(name);
		players.add(p);
		endIndex++;
	}
	
	public void addPlayer(Player p) {
		this.players.add(p);
		endIndex++;
	}
	
	// moves to next position and returns it
	private int nextIndex() {
		if(position == endIndex)
			position = 0;
		else
			position++;
		return position;
	}
	
	public Player next() {
		return players.get(nextIndex());
	}
	
	public Player current() {
		return players.get(position);
	}
	
	public void discardAllWeapons(AdventureDeck d) {
		for(int i = 0; i < players.size(); i++) {
			players.get(i).discardWeaponsActive(d);
		}
	}
	
	public void discardAllAmour(AdventureDeck d) {
		for(int i = 0; i < players.size(); i++) {
			players.get(i).discardAmoursActive(d);
		}
	}
	
	public Players getTournamentParticipants() {
		//TODO: TESTING FUNCTIONALITY
		ArrayList<Player> playing = new ArrayList<Player>();
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).participateTournament) {
				playing.add(players.get(i));
			}
		}
		Players partic = new Players(0, playing.size(), playing);
		// can return with no participants 
		return partic;
	}
	
	
}