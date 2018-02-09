package com.comp_3004.quest_cards.core.states;

import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.core.GameController;

public class TourEnded extends State {

	public TourEnded(GameController c) {
		super(c);
	}

	@Override
	public void msg() {

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