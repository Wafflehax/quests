package com.comp_3004.quest_cards.core.states;

import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.core.GameController;
import com.comp_3004.quest_cards.core.GameModel;

public class TourAskParticipation extends State{

	public TourAskParticipation(GameController c) {
		super(c);
	}

	@Override
	public void msg() {
		c.m.nextPlayer();
		String msg = "Asking " + c.m.getPlayers().current().getName() +  " if they want "
				+ "to participate in " + ((TournamentCard)c.m.getStory()).getName();
		log.info(msg);
		// yes/no popup   
		//c.view popup 
	}

	@Override
	public void no() {
		Ask_T_Part_Response(false);
	}

	@Override
	public void yes() {
		Ask_T_Part_Response(true);
	}

	private void Ask_T_Part_Response(boolean b) {
		c.m.state.pop(); // pop off askTour 
		if(b) {
			c.m.setParticipationTour(b);
			c.m.forceAdventureDraw();
			if(c.m.getPlayers().current().tooManyHandCards()) {
				// push new TOur too many hands
				State tooM = new TourTooManyCardsNotPlayerTurn(c);
				c.m.state.push(tooM);
				c.m.getState().msg();//top state now tooM,
			}
		}
		else {
			log.info("Player => " + c.m.getPlayers().current().getName() 
					+ " decided not to play");
			c.m.setParticipationTour(b);
			log.debug("Moving onto next state");
			c.m.StateMsg();
		}	
	}
	
	@Override
	public boolean handPress(Card c) {
		return false;
	}

	@Override
	public
	boolean disCardPress(Card c) {
		return false;
	}

	@Override
	public
	boolean doneTurn() {
		return false;
	}
	
}