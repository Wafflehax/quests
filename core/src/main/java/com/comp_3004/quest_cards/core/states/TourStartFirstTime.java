package com.comp_3004.quest_cards.core.states;

import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.core.GameController;

public class TourStartFirstTime extends State {

	public TourStartFirstTime(GameController c) {
		super(c);
	}

	@Override
	public void msg() {
		String msg = "Tournamant Starting..... calulating if enough players";
		log.info(msg);
		//view.popup(msg)	
		if(c.m.enoughTournamentParticipants()) {
			msg = "Starting Tour";
			log.info(msg);
			this.c.m.sPop();
			//put current story turn in tempPlayers. do Players = tempPlayers at end of tournament after winners
			c.m.playersTemp = c.m.getPlayers();
			c.m.setPlayers(c.m.getPlayers().getTournamentParticipants());
			//push TourRoundEndEvaluation before player turns
			this.c.m.pushSt(new TourRoundEndEvaluation(this.c));
			//push amount of player turns
			for(int i = 0; i < c.m.getNumPlayers(); i++) {
				this.c.m.pushSt(new TourPlayerTurn(this.c));
			}
			c.m.StateMsg();
		}else {
			msg = "Can't start Tournament Need atleast 2 players";
			log.info(msg);
			//pop off and go to next state currently no next after tour.
			this.c.m.sPop();
			this.c.m.StateMsg();
		}
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
		return false;
	}

	@Override
	public boolean disCardPress(Card c) {
		return false;
	}

	@Override
	public boolean doneTurn() {
		// TODO Auto-generated method stub
		return false;
	}
	
}