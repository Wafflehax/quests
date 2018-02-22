package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.gui.Assets;
import com.comp_3004.quest_cards.gui.CardView;
import com.comp_3004.quest_cards.gui.Config;
import com.comp_3004.quest_cards.gui.GameView;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GamePresenter extends Group {

  static Logger log = Logger.getLogger(GamePresenter.class); //log4j logger
  private final GameView view;
  public Map<String, String> CardAssetMap;
  public CardView [] cards;
  TextureAtlas sprites;
  TextureAtlas backgrounds;
  private QuestCards parent;
  private GameModel model;
  private AssetManager manager;
  private Skin skin;

  //used in JUnit tests
  public GamePresenter(GameModel m) {

    this.parent = null;
    manager = null;
    view = null;
    model = m;
    DragAndDrop dnd = new DragAndDrop();

  }

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

  public GameModel getModel() {
    return this.model;
  }

  public GameView getView() {
    return this.view;
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

    final GameView view = new GameView(manager);
    view.setBackground(backgrounds.findRegion("game_board"));
    view.setPlayerViewBackground(backgrounds.findRegion("player_area"));

    LinkedList<AdventureCard> temp = model.getcurrentTurn().getHand();

    //THIS PORTION WILL BE A METHOD EVENTUALLY ie. dealHand()
    cards = new CardView[12];
    for (int i = 0; i < temp.size(); i++) {
      String spriteGet = temp.get(i).getName();

      System.out.println("spriteGet = " + spriteGet + "\nCardAssetMap.get(spriteGet) = " + CardAssetMap.get(spriteGet));
      cards[i] = new CardView(sprites.findRegion(CardAssetMap.get(spriteGet)), temp.get(i).getID());
      cards[i].setDiscardCDZ(view.DiscardCDZ.getBounds());
      cards[i].setInPlayCDZ(view.InPlayCDZ.getBounds());
      cards[i].setSponsorCDZ(view.SponsorCDZ.getBounds());
      cards[i].setGamePresenter(this);
      cards[i].setColor(1,1,1,0);

    }

    view.setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);


    //DragConfig(cards);

    //Hero area
    view.displayPlayerHand(cards); //CHECK IT OUT
    view.displayHero(sprites.findRegion("R_Champion_Knight"));

    //Drawing decks

    view.displayStoryDeck(sprites.findRegion(Assets.Cards.CARD_BACK));
    view.displayStoryDiscardPile(sprites.findRegion(Assets.Cards.Story.KINGS_RECOGNITION));
    view.displayAdventureDeck(sprites.findRegion(Assets.Cards.CARD_BACK));
    view.displayAdventureDiscardPile(sprites.findRegion(Assets.Cards.Allies.KING_PELLINORE));


    //Dialog example

    /*view.displayQuestionDialog("Question title", "Question message", result -> {
      if (result) {
        //Player says yes
      } else {
        //Player says no
      }*/

      view.displayAnnouncementDialog("Begin Turn", ""+model.getcurrentTurn().getName()+"... begin!", result_2 -> {
        drawCards();
      });
   // });

    return view;
  }

  @Override
  public void draw(Batch batch, float alpha) {
    //view.AnimationManager.update(Gdx.graphics.getDeltaTime());
    drawChildren(batch, alpha);
  }


  @Override
  public void act(float delta) {
    super.act(delta);
    view.AnimationManager.update(delta);
  }


  /* if player is not a sponsor adding a card to a stage during set up, the value passed in
   * to stageNum can be anything. The player class will determine which playCard method to use
   * based on player state. IF the player is a sponsor, pass in the stage number they are playing
   * the card to.
   */
  public void playCard(int cardID, int stageNum) {
    AdventureCard cardToPlay = null;
    for (AdventureCard card : model.getPlayers().current().getHand())
      if (card.getID() == cardID)
        cardToPlay = card;
    if (cardToPlay == null)
      System.out.println("Card ID not found");
    if (cardToPlay != null)
      if (model.getPlayers().current().playCard(cardToPlay, stageNum)) {
        //then update view with what changed in the model
      } else {
        log.info(model.getcurrentTurn().getName() + "'s turn begins.");
        model.beginTurn();
      }
  }

  //takes cardID as input from view, finds corresponding card in model
  public void discardCard(int cardID) {
    AdventureCard cardToDiscard = null;
    for (AdventureCard card : model.getPlayers().current().getHand())
      if (card.getID() == cardID)
        cardToDiscard = card;
    if (cardToDiscard != null) {
      if (model.getPlayers().current().discardCard(cardToDiscard, model.getAdvDeck())) {
        int temp;
        //then update view with what changed in the model
      } else {
        log.info(model.getcurrentTurn().getName() + "'s turn begins.");
        model.beginTurn();
      }
    } else
      log.info(cardID + "  not found in " + model.getPlayers().current().getName() + "'s hand");
  }

  /* takes user input from clicks on dialog box
   * result of input depends on player state
   * if in BidState, input is the number the player is bidding
   * if in sponsor/participate state, 1:yes 0: no
   * if in QuestPlay state, 1: Done playing cards
   * if in TourPlayState, any input attempts to end turn
   */
  public void userInput(int input) {
    if (!model.getPlayers().current().userInput(input)) {
      if (model.getcurrentTurn().getState().equalsIgnoreCase("playtour")) {
        //player couldn't leave turn too many cards
      }
      else
        model.beginTurn();
    }

  }

  //temporary methods to use for model testing
  //takes cardID as input from view, finds corresponding card in model
  public void playCard(int cardID) {
    AdventureCard cardToPlay = null;
    for (AdventureCard card : model.getPlayers().current().getHand())
      if (card.getID() == cardID)
        cardToPlay = card;
    if (cardToPlay == null)
      System.out.println("Card not found");
    if (cardToPlay != null)
      if (model.getPlayers().current().playCard(cardToPlay)) {
        //then update view with what changed in the model
      }
  }

  private void PopulateCardAssetMap() {
    //WEAPONS
    CardAssetMap.put("Horse", "W_Horse");
    CardAssetMap.put("Sword", "W_Sword");
    CardAssetMap.put("Excalibur", "W_Excalibur");
    CardAssetMap.put("Lance", "W_Lance");
    CardAssetMap.put("Dagger", "W_Dagger");
    CardAssetMap.put("Battle-Ax", "W_Battle_ax");
    //ALLIES
    CardAssetMap.put("Sir Gawain", "A_Sir_Gawain");
    CardAssetMap.put("King Pellinore", "A_King_Pellinore");
    CardAssetMap.put("Sir Percival", "A_Sir_Percival");
    CardAssetMap.put("Sir Tristan", "A_Sir_Tristan");
    CardAssetMap.put("King Arthur", "A_King_Arthur");
    CardAssetMap.put("Queen Guinevere", "A_Queen_Guinevere");
    CardAssetMap.put("Merlin", "A_Merlin");
    CardAssetMap.put("Queen Iseult", "A_Queen_Iseult");
    CardAssetMap.put("Sir Lancelot", "A_Sir_Lancelot");
    CardAssetMap.put("Sir Galahad", "A_Sir_Galahad");
    //TESTS
    CardAssetMap.put("Test of the Questing Beast", "T_Test_of_the_Questing_Beast");
    CardAssetMap.put("Test of Temptation", "T_Test_of_Temptation");
    CardAssetMap.put("Test of Valor", "T_Test_of_Valor");
    CardAssetMap.put("Test of Morgan Le Fey", "T_Test_of_Morgan_Le_Fey");
    //FOES
    CardAssetMap.put("Thieves", "F_Thieves");
    CardAssetMap.put("Saxon Knight", "F_Saxon_Knight");
    CardAssetMap.put("Robber Knight", "F_Robber_Knight");
    CardAssetMap.put("Evil Knight", "F_Evil_Knight");
    CardAssetMap.put("Saxons", "F_Saxons");
    CardAssetMap.put("Boar", "F_Boar");
    CardAssetMap.put("Mordred", "F_Mordred");
    CardAssetMap.put("Black Knight", "F_Black_Knight");
    CardAssetMap.put("Giant", "F_Giant");
    CardAssetMap.put("Green Knight", "F_Green_Knight");
    CardAssetMap.put("Dragon", "F_Dragon");
    //TOURNEYS
        /*CardAssetMap.put("Tournament at Camelot","");
        CardAssetMap.put("Tournament at Orkney","");
        CardAssetMap.put("Tournament at Tintagel","");
        CardAssetMap.put("Tournament at York","");*/
    //EVENTS
    CardAssetMap.put("King's Recognition", "E_Kings_Recognition");
    CardAssetMap.put("Queen's Favor", "E_Queens_Favor");
    CardAssetMap.put("Court Called to Camelot", "E_Court_Called_Camelot");
    CardAssetMap.put("Pox", "E_Pox");
    CardAssetMap.put("Plague", "E_Plague");
    CardAssetMap.put("Chivalrous Deed", "E_Chivalrous_Deed");
    CardAssetMap.put("Prosperity Throughout the Realms", "E_Prosperity_Throughout_the_Realm");
    CardAssetMap.put("King's Call to Arms", "E_Kings_Call_to_Arms");
    CardAssetMap.put("Amour", "Amour");
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

  public void flipDown(CardView card){card.setDrawable(new TextureRegionDrawable(new TextureRegion(sprites.findRegion(Assets.Cards.CARD_BACK))));}
  public void flipUp(CardView card){card.setDrawable(new TextureRegionDrawable(card.getPicDisplay()));}

  public void drawCards(){  for (int i = 0; i < cards.length; i++){view.displayDrawCardAnimation(cards[i]);
  }}

}
