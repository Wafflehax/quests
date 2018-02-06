package com.comp_3004.quest_cards.core;


import java.util.LinkedList;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.Player.Rank;
import com.comp_3004.quest_cards.gui.GameScreen;



public class GamePresenter extends Group{
	GameModel model;
	GameScreen view;
	static Logger log = Logger.getLogger(GamePresenter.class); //log4j logger
	
	public GamePresenter(GameScreen v, GameModel m) {
		this.view = v;
		addActor(view);
		this.model = m;
	}
	
	//send model data to the view
	private Rank setViewRank(int i) { 
		return model.getPlayerAtIndex(i).getRank();
		//GameScreen.methodUpdateRelvantInformation().
	}
	private int setViewShields(int i) { return model.getPlayerAtIndex(i).getShields(); }
	private LinkedList<AdventureCard> setViewHand(int i) { return model.getPlayerAtIndex(i).getHand(); }
	private LinkedList<AdventureCard> setViewActive(int i) { return model.getPlayerAtIndex(i).getActive(); }
	private Stack<AdventureCard> setViewAdvDeck() { return model.getAdvDeck().getDeck(); }
	private Stack<AdventureCard> setViewAdvDiscard() { return model.getAdvDeck().getDiscard(); }
	private Stack<StoryCard> setViewStoryDeck() { return model.getStoryDeck().getDeck(); }
	private Stack<StoryCard> setViewStoryDiscard() { return model.getStoryDeck().getDiscard(); }
	
	//perform actions from view on model
	public void startGame() {	//user presses new game on main menu
		model = new GameModel();
	}
	public void playerPlaysAdventureCard(int i, AdventureCard card) {		//user drags card from hand to play
		model.getPlayerAtIndex(i).playCard(card);
	}
	public void playerDiscardsAdventureCard(int i, AdventureCard card) {	//user drags card from hand to discard
		model.getPlayerAtIndex(i).discardCard(card, model.getAdvDeck());
	}
	
	public void draw(Batch batch, float parentAlpha) {
		for(int i=0; i<model.getNumPlayers(); i++) {
			setViewRank(i);
			setViewShields(i);
			setViewHand(i);
			setViewActive(i);
		}
		setViewAdvDeck();
		setViewAdvDiscard();
		setViewStoryDeck();
		setViewStoryDiscard();
		
		view.draw(batch, parentAlpha);
	}
	
	//overwrite
	public void act() {
		
	}
	
	
	
	
	
}