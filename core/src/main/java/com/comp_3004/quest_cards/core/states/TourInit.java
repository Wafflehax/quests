package com.comp_3004.quest_cards.core.states;

import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.core.GameController;

public class TourInit extends State{

	public TourInit(GameController c) {
		super(c);
	}

	@Override
	public void msg() {
		this.c.m.sPop(); //pop self
		this.c.m.pushSt(new TourStartFirstTime(this.c));
		this.c.m.resetJoiners();
		int np = this.c.m.getNumPlayers();
		for(int i = 0; i < np; i++) {
			State st = new TourAskParticipation(this.c);
			this.c.m.pushSt(st);
		}
		this.c.m.prevPlayer(); // state-> ask-tour-participation advances next player at start
		//start up state stack 
		this.c.m.getState().msg();
	}

	@Override
	public void no() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void yes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handPress(Card c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disCardPress(Card c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doneTurn() {
		// TODO Auto-generated method stub
		return false;
	}
	
}