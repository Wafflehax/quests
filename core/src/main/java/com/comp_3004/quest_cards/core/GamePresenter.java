package com.comp_3004.quest_cards.core;

import org.apache.log4j.Logger;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.comp_3004.quest_cards.gui.GameScreen;



public class GamePresenter extends Group{
	GameModel model;
	GameScreen view;
	static Logger log = Logger.getLogger(GamePresenter.class); //log4j logger
	
	public GamePresenter(GameScreen v, GameModel m) {
		this.view = v;
		addActor(view);
		this.model = m;
		
		
		//this is just a rough idea of what a listener may look like...
		//play card from hand listener
		view.addListener(new DragListener() {
			//user clicks on a card and drags it
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				/*if x,y are inside a cards tap square
					get card ID
					return true;
				else x,y are not inside a cards tap square*/
				return false;
			}
			//user drags card to play and releases it
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				/*if x,y are inside the play area's tap square
					if (model.getcurrentTurn().playCard(card)) {
						card played successfully (card was allowed to be played by model)
						these next two methods are essentially setters for view attributes
						view.updateHand(model.getcurrentTurn().getHand());
						view.updateActive(model.getcurrentTurn().getActive());
					}*/
			}
		});
	}
	
	//won't need these, as the calls to model.getSomething can be placed directly in the listeners
	/*update view with model data - called when a change in model occurs
	private Rank updateRank() { return model.getcurrentTurn().getRank(); }
	private int updateShields() { return model.getcurrentTurn().getShields(); }
	private LinkedList<AdventureCard> updateHand() { return model.getcurrentTurn().getHand(); }
	private LinkedList<AdventureCard> updateActive() { return model.getcurrentTurn().getActive(); }
	private Stack<AdventureCard> updateAdvDeck() { return model.getAdvDeck().getDeck(); }
	private Stack<AdventureCard> updateAdvDiscard() { return model.getAdvDeck().getDiscard(); }
	private Stack<StoryCard> updateStoryDeck() { return model.getStoryDeck().getDeck(); }
	private Stack<StoryCard> updateStoryDiscard() { return model.getStoryDeck().getDiscard(); }*/
}