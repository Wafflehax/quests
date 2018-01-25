package com.comp_3004.quest_cards.core;

import java.util.ArrayList;



public class Players{
	
	protected ArrayList<Player> players;
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
	
	
}