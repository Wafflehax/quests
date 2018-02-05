package com.comp_3004.quest_cards.core;


import java.util.LinkedList;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.Player.Rank;
import com.comp_3004.quest_cards.gui.GameScreen;



public class GameController extends Actor{
	GameModel model;
	Group view;
	static Logger log = Logger.getLogger(GameController.class); //log4j logger
	
	public GameController(GameModel m) {
		this.model = m;
		this.view = new GameScreen();
		
	}
	
	private Rank getRank(int i) { return model.getPlayerAtIndex(i).getRank();	}
	private int getShields(int i) { return model.getPlayerAtIndex(i).getShields(); }
	private LinkedList<AdventureCard> getHand(int i) { return model.getPlayerAtIndex(i).getHand(); }
	private LinkedList<AdventureCard> getActive(int i) { return model.getPlayerAtIndex(i).getActive(); }
	private Stack<AdventureCard> getAdvDeck() { return model.getAdvDeck().getDeck(); }
	private Stack<AdventureCard> getAdvDiscard() { return model.getAdvDeck().getDiscard(); }
	private Stack<StoryCard> getStoryDeck() { return model.getStoryDeck().getDeck(); }
	private Stack<StoryCard> getStoryDiscard() { return model.getStoryDeck().getDiscard(); }
	
	
	public void draw(Batch batch, float parentAlpha) {
		for(int i=0; i<model.getNumPlayers(); i++) {
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