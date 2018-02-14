package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.gui.Assets;
import com.comp_3004.quest_cards.gui.CardView;
import com.comp_3004.quest_cards.gui.GameView;
import com.sun.corba.se.pept.transport.EventHandler;
import org.apache.log4j.Logger;

public class GamePresenter extends Group{

  private QuestCards parent;
  private GameModel model;
  private GameView view;

  TextureAtlas sprites;
  TextureAtlas backgrounds;

  private AssetManager manager;

  //used in JUnit tests
  public GamePresenter(GameModel m) {

	    this.parent = null;
	    manager = null;
	    view = null;
	    model = m;
	  }
  public GameModel getModel() { return this.model; }
  
  public GamePresenter(QuestCards parent) {

    this.parent = parent;
    manager = parent.getAssetManager();
    loadAssets();

    view = initGameView();
    addActor(view);

    model = new GameModel();
  }

  public void loadAssets(){

    AssetManager manager = parent.getAssetManager();
    manager.load(Assets.GAME_BACKGROUNDS, TextureAtlas.class);
    manager.load(Assets.GAME_SPRITES, TextureAtlas.class);
    manager.finishLoading();

    sprites = manager.get(Assets.GAME_SPRITES, TextureAtlas.class);
    backgrounds = manager.get(Assets.GAME_BACKGROUNDS, TextureAtlas.class);
  }


  public GameView initGameView(){

    GameView view = new GameView();
    view.setShieldsTexture(sprites.findRegion("shield"));
    view.setBackground(backgrounds.findRegion("game_board"));
    view.setPlayerViewBackground(backgrounds.findRegion("player_area"));


    CardView[] cards = new CardView[12];
    for(int i = 0; i < cards.length; i++){

      cards[i] = new CardView(sprites.findRegion("A_King_Arthur"));
    }

    //Hero area

    view.displayPlayerHand(cards);
    view.displayHero(sprites.findRegion("R_Champion_Knight"));

    //Drawing decks

    view.displayStoryDeck(sprites.findRegion(Assets.Cards.CARD_BACK));
    view.displayStoryDiscardPile(sprites.findRegion(Assets.Cards.Story.KINGS_RECOGNITION));
    view.displayAdventureDeck(sprites.findRegion(Assets.Cards.CARD_BACK));
    view.displayAdventureDiscardPile(sprites.findRegion(Assets.Cards.Allies.KING_PELLINORE));

    return view;
  }

  @Override
  public void draw(Batch batch, float alpha) {
    drawChildren(batch, alpha);
  }


  @Override
  public void act(float delta) {
    super.act(delta);
  }
  
  	//temporary methods to use for model testing
  	//takes cardID as input from view, finds corresponding card in model
  	public void playCard(int cardID) {
	  	AdventureCard cardToPlay = null;
	  	for(AdventureCard card : model.getPlayers().current().getHand())
	  		if(card.getID() == cardID)
	  			cardToPlay = card;
	  	if(cardToPlay == null)
	  		System.out.println("Card not found");
	  	if(cardToPlay != null)
	  		if(model.getPlayers().current().playCard(cardToPlay)) {
	  			//then update view with what changed in the model
	  		}
  	}
  //had to overload for sponsoring a quest as you can add cards to different stages :(
  	public void playCard(int cardID, int stageNum) {
	  	AdventureCard cardToPlay = null;
	  	System.out.println(model.getPlayers().current().getName());
	  	for(AdventureCard card : model.getPlayers().current().getHand())
	  		if(card.getID() == cardID)
	  			cardToPlay = card;
	  	if(cardToPlay == null)
	  		System.out.println("Card ID not found");
	  	if(cardToPlay != null)
	  		if(model.getPlayers().current().playCard(cardToPlay, stageNum)) {
	  			//then update view with what changed in the model
	  		}
  	}
  	
  	//takes cardID as input from view, finds corresponding card in model
  	public void discardCard(int cardID) {
  		AdventureCard cardToDiscard = null;
	  	for(AdventureCard card : model.getPlayers().current().getHand())
	  		if(card.getID() == cardID)
	  			cardToDiscard = card;
	  	if(cardToDiscard != null)
	  		if(model.getPlayers().current().discardCard(cardToDiscard, model.getAdvDeck())) {
	  			int temp;
	  			//then update view with what changed in the model
	  		}
  	}
  
  	public void userInput(int b) {
  		if(b == 1) {
  			if(!model.getPlayers().current().userInput(true))
  				model.beginTurn();
  		}
  		else if(b == 0) {
  			if(!model.getPlayers().current().userInput(false))
  				model.beginTurn();
  		}
  	}
  	
  	
  	/*public void userInput(int b) {
  		if(b == 1) {
  			//sponsor clicks done while setting up quest
  			if(model.getQuest().getSponsor() == model.getPlayers().current()) {
  				if(model.getPlayers().current().userInput(true))
  					model.getPlayers().next();
  				return;
  			}
  			//player hits yes when asked if sponsoring
  			else if(model.getPlayers().current().userInput(true)) {
  				//user input while in sponsor state
  				if(model.getPlayers().current().getState() == "sponsor")
  					if(model.getPlayers().peekNext().getState() == "sponsor")
  						return;
  			}
  		}
  		else if(b == 0)
  			//user hits no when asked if participating
  			model.getPlayers().current().userInput(false);
  		model.getPlayers().next();
  		
  		//checks if no one wants to sponsor quest
  		if(model.getStory() instanceof QuestCard) {
			if(model.getQuest().getNumDeclines() == model.getNumPlayers())
				model.noQuestSponsor();
  		}
  		
  		//move to next player if current player is sponsor in quest
  		//if(model.getPlayers().current() == model.getQuest().getSponsor() )
  	}*/
}


/*
    //this is just a rough idea of what a listener may look like...
    //play card from hand listener
    view.addListener(new DragListener() {
      //user clicks on a card and drags it
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				/*if x,y are inside a cards tap square
					get card ID
					return true;
				else x,y are not inside a cards tap square
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
					}
  }
});
    }
    public playCard(AdventureCard card) {
    		model.getPlayers().current().playCard(card);
    }
    public userInput(boolean b) {
    		model.getPlayers().current().userInput(b);
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
	private Stack<StoryCard> updateStoryDiscard() { return model.getStoryDeck().getDiscard(); }
    }
 */