package com.comp_3004.quest_cards.core;

import java.util.ArrayList;



public class Players{
	
	protected ArrayList<Player> players; //protected for testing
	private int position;
	private int size;
	
	// class stores int position from 0 to endPos. Once end Position reached loops to start
	// and players
	
	public Players(int position, int size, ArrayList<Player> players){
		this.players = players;
		this.position = position;
		this.size = size-1;
	}
	public Players(Players p) {
		this.position = p.position;
		this.size = p.size;
		this.players = p.players;
	}

	// getter/setter
	protected boolean isEmpty() {		return players.isEmpty();		}
	public int size() { return players.size(); }
	public int getNumPlayers() { return this.players.size(); }
	public ArrayList<Player> getPlayers() { return this.players; }
	
	public void addPlayer(String name) {
		Player p = new Player(name);
		players.add(p);
	}
	
	public void addPlayer(Player p) {
		this.players.add(p);
	}
	
	// moves to next position and returns it
	private int nextIndex() {
		if(position == size)
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
	
	protected Players getTournamentParticipants() {
		//TODO: TESTING FUNCTIONALITY
		ArrayList<Player> playing = new ArrayList<Player>();
		for(int i = 0; i < players.size(); i++) {
			if(players.get(i).participateTournament) {
				playing.add(players.get(i));
			}
		}
		Players partic = new Players(0, playing.size()-1, playing);
		// can return with no participants 
		return partic;
	}
	
	
}