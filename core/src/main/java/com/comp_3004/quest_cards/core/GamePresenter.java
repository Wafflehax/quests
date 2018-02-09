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