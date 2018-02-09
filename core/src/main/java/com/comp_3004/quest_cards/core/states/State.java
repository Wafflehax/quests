package com.comp_3004.quest_cards.core.states;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.core.GameController;

public abstract class State{
	//extension of controller
	static Logger log = Logger.getLogger(State.class); //log4j logger
	protected GameController c;
	
	public State(GameController c) {
		this.c = c;
	}
	
	
	abstract public void msg();
	abstract public void no();
	abstract public void yes();
	abstract public boolean handPress(Card c); //boolean for testing
	abstract public boolean disCardPress(Card c);
	abstract public boolean doneTurn();
	
}