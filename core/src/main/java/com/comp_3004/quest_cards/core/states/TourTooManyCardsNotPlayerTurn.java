package com.comp_3004.quest_cards.core.states;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.core.GameController;

public class TourTooManyCardsNotPlayerTurn extends State{

	//Too many cards after drawing from adventure deck and not your turn to play
	
	public TourTooManyCardsNotPlayerTurn(GameController c) {
		super(c);
	}

	@Override
	public void msg() {
		String msg = "PLayer " + c.m.getPlayers().current().getName() + " has too many cards";
		log.info(msg);
		// alert/pop up on msg
		//player must choose to discard cards
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
		//not your turn
		return false;
	}

	@Override
	public boolean disCardPress(Card c) {
		boolean disCarded = false;
			AdventureCard card = (AdventureCard)c;
			this.c.m.getPlayers().current().discardCard(card, this.c.m.getAdvDeck());
			if(this.c.m.getPlayers().current().tooManyHandCards()) {
				//do not pop off still too many cards
				this.c.m.getState().msg(); // tooMany
			}else {
				log.info("Enough cards, press doneTurn when done, or discard more");
			}
		return disCarded;
	}

	@Override public boolean doneTurn() {
		boolean done = false;
		if(this.c.m.getPlayers().current().tooManyHandCards()) {
			log.info("You can't end your turn you have over 12 cards");
		}else {
			log.info("Ended turn from TooManyHandCards");
			this.c.m.state.pop(); //Pop too many Cards
			this.c.m.StateMsg(); //states next msg
		}
		return done;
	}
}