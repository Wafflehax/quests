package com.comp_3004.quest_cards.core;


import java.util.LinkedList;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.core.Player.Rank;
import com.comp_3004.quest_cards.core.states.TourAskParticipation;
import com.comp_3004.quest_cards.core.states.State;
import com.comp_3004.quest_cards.core.states.TourStartFirstTime;
import com.comp_3004.quest_cards.gui.GameScreen;

public class GameController extends Actor{
	public GameModel m;
	Group view;
	static Logger log = Logger.getLogger(GameController.class); //log4j logger
	
	public GameController(GameModel m) {
		this.m = m;
		//this.view = new GameScreen(); commented had null pointer error
		
	}
	
	public void tourTest() {
	}
	
	public void yes() {
		m.getState().yes();
	}
	
	public void no() {
		m.getState().no();
	}
	
	
	//When player pulls from hand to table (playing card)
	// returns true if card played to table
	public boolean handPress(Card c) {
		return m.getState().handPress(c);
	}
	// Card dragged to discard pile
	// returns true if successful
	public boolean disCardPress(Card c) {
		return m.getState().disCardPress(c);
	}
	//when user finishes their turn
	// move to next player draw their view
	public boolean doneTurn() {
		return m.getState().doneTurn();
	}
	
	
	private Rank getRank(int i) { return m.getPlayerAtIndex(i).getRank();	}
	private int getShields(int i) { return m.getPlayerAtIndex(i).getShields(); }
	private LinkedList<AdventureCard> getHand(int i) { return m.getPlayerAtIndex(i).getHand(); }
	private LinkedList<AdventureCard> getActive(int i) { return m.getPlayerAtIndex(i).getActive(); }
	private Stack<AdventureCard> getAdvDeck() { return m.getAdvDeck().getDeck(); }
	private Stack<AdventureCard> getAdvDiscard() { return m.getAdvDeck().getDiscard(); }
	private Stack<StoryCard> getStoryDeck() { return m.getStoryDeck().getDeck(); }
	private Stack<StoryCard> getStoryDiscard() { return m.getStoryDeck().getDiscard(); }
	
	
	public void draw(Batch batch, float parentAlpha) {
		for(int i=0; i<m.getNumPlayers(); i++) {
			getRank(i);
			getShields(i);
			getHand(i);
			getActive(i);
		}
		getAdvDeck();
		getAdvDiscard();
		getStoryDeck();
		getStoryDiscard();
		
		/*if(heroChanged)
			setRank(); //pass in player(int) and rank
		if(shieldsChanged)
			setShields();*/
		
		view.draw(batch, parentAlpha);
	}
	//overwrite
	public void act() {
		
	}
	
	
	
	
	
}