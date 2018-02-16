package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.gui.Assets;
import com.comp_3004.quest_cards.gui.CardView;
import com.comp_3004.quest_cards.gui.Config;
import com.comp_3004.quest_cards.gui.GameView;
import com.sun.corba.se.pept.transport.EventHandler;
import org.apache.log4j.Logger;

public class GamePresenter extends Group {

  private QuestCards parent;
  private GameModel model;
  private GameView view;

  TextureAtlas sprites;
  TextureAtlas backgrounds;

  private AssetManager manager;
  private Skin skin;

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

  public void loadAssets() {

    AssetManager manager = parent.getAssetManager();
    manager.load(Assets.GAME_BACKGROUNDS, TextureAtlas.class);
    manager.load(Assets.GAME_SPRITES, TextureAtlas.class);
    manager.load(Assets.SKIN, Skin.class);
    manager.finishLoading();

    skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
    sprites = manager.get(Assets.GAME_SPRITES, TextureAtlas.class);
    backgrounds = manager.get(Assets.GAME_BACKGROUNDS, TextureAtlas.class);
  }


  public GameView initGameView() {

    GameView view = new GameView(skin);
    view.setShieldsTexture(sprites.findRegion("shield"));
    view.setBackground(backgrounds.findRegion("game_board"));
    view.setPlayerViewBackground(backgrounds.findRegion("player_area"));
    view.setBounds(0,0 , Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);

    CardView[] cards = new CardView[12];
    for (int i = 0; i < cards.length; i++) {

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
}
