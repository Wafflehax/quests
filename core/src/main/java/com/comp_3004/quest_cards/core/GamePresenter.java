package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.gui.Assets;
import com.comp_3004.quest_cards.gui.CardDropZone;
import com.comp_3004.quest_cards.gui.CardView;
import com.comp_3004.quest_cards.gui.GameView;
import com.sun.corba.se.pept.transport.EventHandler;
import org.apache.log4j.Logger;

import javax.smartcardio.Card;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GamePresenter extends Group{

  private QuestCards parent;
  private GameModel model;
  private GameView view;
  private DragAndDrop dnd;
  public Map<String,String> CardAssetMap;

  TextureAtlas sprites;
  TextureAtlas backgrounds;

  private AssetManager manager;

  //used in JUnit tests
  public GamePresenter(GameModel m) {

	    this.parent = null;
	    manager = null;
	    view = null;
	    model = m;
        DragAndDrop dnd = new DragAndDrop();

	  }
  public GameModel getModel() { return this.model; }

  
  public GamePresenter(QuestCards parent) {

    this.parent = parent;
    manager = parent.getAssetManager();
    CardAssetMap = new HashMap<String, String>();
    PopulateCardAssetMap();
    loadAssets();

    model = new GameModel();
    view = initGameView();
    addActor(view);



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

   LinkedList<AdventureCard> temp = model.getcurrentTurn().getHand();

    CardView [] cards = new CardView[12];
    for(int i = 0; i < temp.size(); i++){
        String spriteGet = temp.get(i).getName();

        if(spriteGet.compareTo("Amour")==0) spriteGet = "Thieves"; //TODO: HAVE THIS PLACEDHOLDER RECTIFIED!!

        System.out.println("spriteGet = "+spriteGet+"\nCardAssetMap.get(spriteGet) = "+CardAssetMap.get(spriteGet));
      cards[i] = new CardView(sprites.findRegion(CardAssetMap.get(spriteGet)),temp.get(i).getID());
      cards[i].setDropZoneBounds(view.CDZ.getBounds());
      cards[i].setGamePresenter(this);

    }
    //DragConfig(cards);

    //Hero area
    view.displayPlayerHand(cards); //CHECK IT OUT
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
  	public boolean playCard(int cardID) {
	  	AdventureCard cardToPlay = null;
	  	for(AdventureCard card : model.getPlayers().current().getHand())
	  		if(card.getID() == cardID)
	  			cardToPlay = card;
	  	if(cardToPlay == null)
        {System.out.println("Card not found");
        return false;}
	  	if(cardToPlay != null)
	  		if(model.getPlayers().current().playCard(cardToPlay)) {
	  			//then update view with what changed in the model
                return true;
	  		}
	  		return false;
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
                //card.goto(playPlace)
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

    private void PopulateCardAssetMap() {
      //WEAPONS
      CardAssetMap.put("Horse","W_Horse");
        CardAssetMap.put("Sword","W_Sword");
        CardAssetMap.put("Excalibur","W_Excalibur");
        CardAssetMap.put("Lance","W_Lance");
        CardAssetMap.put("Dagger","W_Dagger");
        CardAssetMap.put("Battle-Ax","W_Battle_ax");
        //ALLIES
        CardAssetMap.put("Sir Gawain","A_Sir_Gawain");
        CardAssetMap.put("King Pellinore","A_King_Pellinore");
        CardAssetMap.put("Sir Percival","A_Sir_Percival");
        CardAssetMap.put("Sir Tristan","A_Sir_Tristan");
        CardAssetMap.put("King Arthur","A_King_Arthur");
        CardAssetMap.put("Queen Guinevere","A_Queen_Guinevere");
        CardAssetMap.put("Merlin","A_Merlin");
        CardAssetMap.put("Queen Iseult","A_Queen_Iseult");
        CardAssetMap.put("Sir Lancelot","A_Sir_Lancelot");
        CardAssetMap.put("Sir Galahad","A_Sir_Galahad");
        //TESTS
        CardAssetMap.put("Test of the Questing Beast","T_Test_of_the_Questing_Beast");
        CardAssetMap.put("Test of Temptation","T_Test_of_Temptation");
        CardAssetMap.put("Test of Valor","T_Test_of_Valor");
        CardAssetMap.put("Test of Morgan Le Fey","T_Test_of_Morgan_Le_Fey");
        //FOES
        CardAssetMap.put("Thieves","F_Thieves");
        CardAssetMap.put("Saxon Knight","F_Saxon_Knight");
        CardAssetMap.put("Robber Knight","F_Robber_Knight");
        CardAssetMap.put("Evil Knight","F_Evil_Knight");
        CardAssetMap.put("Saxons","F_Saxons");
        CardAssetMap.put("Boar","F_Boar");
        CardAssetMap.put("Mordred","F_Mordred");
        CardAssetMap.put("Black Knight","F_Black_Knight");
        CardAssetMap.put("Giant","F_Giant");
        CardAssetMap.put("Green Knight","F_Green_Knight");
        CardAssetMap.put("Dragon","F_Dragon");
        //TOURNEYS
        /*CardAssetMap.put("Tournament at Camelot","");
        CardAssetMap.put("Tournament at Orkney","");
        CardAssetMap.put("Tournament at Tintagel","");
        CardAssetMap.put("Tournament at York","");*/
        //EVENTS
        CardAssetMap.put("King's Recognition","E_Kings_Recognition");
        CardAssetMap.put("Queen's Favor","E_Queens_Favor");
        CardAssetMap.put("Court Called to Camelot","E_Court_Called_Camelot");
        CardAssetMap.put("Pox","E_Pox");
        CardAssetMap.put("Plague","E_Plague");
        CardAssetMap.put("Chivalrous Deed","E_Chivalrous_Deed");
        CardAssetMap.put("Prosperity Throughout the Realms","E_Prosperity_Throughout_the_Realm");
        CardAssetMap.put("King's Call to Arms","E_Kings_Call_to_Arms");
        //QUESTS
        /*CardAssetMap.put("Search for the Holy Grail","");
        CardAssetMap.put("Test of the Green Knight","");
        CardAssetMap.put("Search for the Questing Beast","");
        CardAssetMap.put("Defend the Queen's Honor","");
        CardAssetMap.put("Rescue the Fair Maiden","");
        CardAssetMap.put("Journey Through the Enchanted Forest","");
        CardAssetMap.put("Slay the Dragon","");
        CardAssetMap.put("Vanquish King Arthur's Enemies","");
        CardAssetMap.put("Boar Hunt","");
        CardAssetMap.put("Repel the Saxon Invaders","");*/





    }
  	/*public LinkedList<AdventureCard> getCurrentHand(){
      return model.getcurrentTurn().getHand();
    }*/

  	
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