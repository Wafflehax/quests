package com.comp_3004.quest_cards.core;

import java.util.ArrayList;



public class Players{
	
	protected ArrayList<Player> players; //protected for testing
	private int position;
	private int endPos;
	
	// class stores int position from 0 to endPos. Once end Position reached loops to start
	// and players
	
	public Players(int position, int endPos, ArrayList<Player> players){
		this.players = players;
		this.position = position;
		this.endPos = endPos;
	}
	public Players(Players p) {
		this.position = p.position;
		this.endPos = p.endPos;
		this.players = p.players;
	}
	
	public int getNumPlayers() { return this.players.size(); }
	
	// moves to next position and returns it
	private int nextIndex() {
		if(position == endPos)
			position = 0;
		else
			position++;
		return position;
	}
	
	protected Player next() {
		return players.get(nextIndex());
	}
	
	protected Player current() {
		return players.get(position);
	}
	
	protected boolean isEmpty() {		return players.isEmpty();		}
	protected int size() { return players.size(); }
	
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