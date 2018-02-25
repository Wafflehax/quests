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
  public CardView[] cards;
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

    cards = new CardView[temp.size()];
    for (int i = 0; i < temp.size(); i++) {
      String spriteGet = temp.get(i).getName();

      System.out.println("spriteGet = " + spriteGet + "\nCardAssetMap.get(spriteGet) = " + CardAssetMap.get(spriteGet));
      cards[i] = new CardView(sprites.findRegion(CardAssetMap.get(spriteGet)), temp.get(i).getID());
      cards[i].setDiscardCDZ(view.DiscardCDZ.getBounds());
      cards[i].setInPlayCDZ(view.InPlayCDZ.getBounds());
      cards[i].setSponsorCDZ(view.SponsorCDZ.getBounds());
      cards[i].setGamePresenter(this);
      cards[i].setColor(1, 1, 1, 0);

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

    view.displayAnnouncementDialog("Begin Turn", "" + model.getcurrentTurn().getName() + "... begin!", result_2 -> {
      drawCards();
    });
    view.displayNextTurnButton(() -> {

      view.displayEventAnnouncement(new CardView(sprites.findRegion(Assets.Cards.Story.KINGS_RECOGNITION), 0), res_2 -> {
      });

      view.displayJoinEventDialog("Event",
          "Event body",
          new CardView(sprites.findRegion(Assets.Cards.Tournament.CAMELOT), 0), result -> {

          });





      model.nextPlayer();
      assignHand();
    }, false);
    // });


    return view;
  }

  private void assignHand() {
    LinkedList<AdventureCard> temp = model.getcurrentTurn().getHand();
    view.playerView.wipePlayerHand(cards);

    cards = new CardView[temp.size()];
    for (int i = 0; i < temp.size(); i++) {
      String spriteGet = temp.get(i).getName();

      System.out.println("spriteGet = " + spriteGet + "\nCardAssetMap.get(spriteGet) = " + CardAssetMap.get(spriteGet));
      cards[i] = new CardView(sprites.findRegion(CardAssetMap.get(spriteGet)), temp.get(i).getID());
      cards[i].setDiscardCDZ(view.DiscardCDZ.getBounds());
      cards[i].setInPlayCDZ(view.InPlayCDZ.getBounds());
      cards[i].setSponsorCDZ(view.SponsorCDZ.getBounds());
      cards[i].setGamePresenter(this);
      cards[i].setColor(1, 1, 1, 0);

    }
    view.displayPlayerHand(cards); //CHECK IT OUT

    view.displayAnnouncementDialog("Begin Turn", "" + model.getcurrentTurn().getName() + "... begin!", result_2 -> {
      drawCards();
    });
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
      } else
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
    CardAssetMap.put("Horse", Assets.Cards.Weapon.HORSE);
    CardAssetMap.put("Sword", Assets.Cards.Weapon.SWORD);
    CardAssetMap.put("Excalibur", Assets.Cards.Weapon.EXCALIBUR);
    CardAssetMap.put("Lance", Assets.Cards.Weapon.LANCE);
    CardAssetMap.put("Dagger", Assets.Cards.Weapon.DAGGER);
    CardAssetMap.put("Battle-Ax", Assets.Cards.Weapon.BATTLE_AX);
    //ALLIES
    CardAssetMap.put("Sir Gawain", Assets.Cards.Allies.SIR_GAWAIN);
    CardAssetMap.put("King Pellinore", Assets.Cards.Allies.KING_PELLINORE);
    CardAssetMap.put("Sir Percival", Assets.Cards.Allies.SIR_PERCIVAL);
    CardAssetMap.put("Sir Tristan", Assets.Cards.Allies.SIR_TRISTAN);
    CardAssetMap.put("King Arthur", Assets.Cards.Allies.KING_ARTHUR);
    CardAssetMap.put("Queen Guinevere", Assets.Cards.Allies.QUEEN_GUINEVERE);
    CardAssetMap.put("Merlin", Assets.Cards.Allies.MERLIN);
    CardAssetMap.put("Queen Iseult", Assets.Cards.Allies.QUEEN_ISEULT);
    CardAssetMap.put("Sir Lancelot", Assets.Cards.Allies.SIR_LANCELOT);
    CardAssetMap.put("Sir Galahad", Assets.Cards.Allies.SIR_GALAHAD);
    //TESTS
    CardAssetMap.put("Test of the Questing Beast", Assets.Cards.Test.TEST_OF_THE_QUESTING_BEAST);
    CardAssetMap.put("Test of Temptation", Assets.Cards.Test.TEST_OF_TEMPTATION);
    CardAssetMap.put("Test of Valor", Assets.Cards.Test.TEST_OF_VALOR);
    CardAssetMap.put("Test of Morgan Le Fey", Assets.Cards.Test.TEST_OF_MORGAN_LE_FEY);
    //FOES
    CardAssetMap.put("Thieves", Assets.Cards.Foe.THIEVES);
    CardAssetMap.put("Saxon Knight", Assets.Cards.Foe.SAXON_KNIGHT);
    CardAssetMap.put("Robber Knight", Assets.Cards.Foe.ROBBER_KNIGHT);
    CardAssetMap.put("Evil Knight", Assets.Cards.Foe.EVIL_KNIGHT);
    CardAssetMap.put("Saxons", Assets.Cards.Foe.SAXONS);
    CardAssetMap.put("Boar", Assets.Cards.Foe.BOAR);
    CardAssetMap.put("Mordred", Assets.Cards.Foe.MORDRED);
    CardAssetMap.put("Black Knight", Assets.Cards.Foe.BLACK_KNIGHT);
    CardAssetMap.put("Giant", Assets.Cards.Foe.GIANT);
    CardAssetMap.put("Green Knight", Assets.Cards.Foe.GREEN_KNIGHT);
    CardAssetMap.put("Dragon", Assets.Cards.Foe.DRAGON);
    //TOURNEYS
    CardAssetMap.put("Tournament at Camelot", "");
    CardAssetMap.put("Tournament at Orkney", "");
    CardAssetMap.put("Tournament at Tintagel", "");
    CardAssetMap.put("Tournament at York", "");
    //EVENTS
    CardAssetMap.put("King's Recognition", Assets.Cards.Story.KINGS_RECOGNITION);
    CardAssetMap.put("Queen's Favor", Assets.Cards.Story.QUEENS_FAVOR);
    CardAssetMap.put("Court Called to Camelot", Assets.Cards.Story.COURT_CALLED_CAMELOT);
    CardAssetMap.put("Pox", Assets.Cards.Story.POX);
    CardAssetMap.put("Plague", Assets.Cards.Story.PLAGUE);
    CardAssetMap.put("Chivalrous Deed", Assets.Cards.Story.CHIVALROUS_DEED);
    CardAssetMap.put("Prosperity Throughout the Realms", Assets.Cards.Story.PROSPERITY_THROUGHOUT_THE_REALM);
    CardAssetMap.put("King's Call to Arms", Assets.Cards.Story.KINGS_CALL_TO_ARMS);
    CardAssetMap.put("Amour", Assets.Cards.Allies.AMOUR);
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

  public void flipDown(CardView card) {
    card.setDrawable(new TextureRegionDrawable(new TextureRegion(sprites.findRegion(Assets.Cards.CARD_BACK))));
  }

  public void flipUp(CardView card) {
    card.setDrawable(new TextureRegionDrawable(card.getPicDisplay()));
  }

  public void drawCards() {
    for (int i = 0; i < cards.length; i++) {
      view.displayDrawCardAnimation(cards[i]);
    }
  }

}
