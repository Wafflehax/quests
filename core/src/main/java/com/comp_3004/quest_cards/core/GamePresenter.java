package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.gui.*;
import org.apache.log4j.Logger;

import java.util.function.Consumer;

public class GamePresenter extends Group {

  private QuestCards parent;
  private GameModel model;
  private final GameView view;

  TextureAtlas sprites;
  TextureAtlas backgrounds;

  private AssetManager manager;
  private Skin skin;


  static Logger log = Logger.getLogger(GamePresenter.class); //log4j logger

  //used in JUnit tests
  public GamePresenter(GameModel m) {

    this.parent = null;
    manager = null;
    view = null;
    model = m;
  }

  public GameModel getModel() {
    return this.model;
  }

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

    final GameView view = new GameView(skin);
    view.setShieldsTexture(sprites.findRegion("shield"));
    view.setBackground(backgrounds.findRegion("game_board"));
    view.setPlayerViewBackground(backgrounds.findRegion("player_area"));
    view.setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);

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


    //Dialog example

    view.displayQuestionDialog("Question title", "Question message", result -> {
      if (result) {
        //Player says yes
      } else {
        //Player says no
      }

      view.displayAnnouncementDialog("Announcement title", "Announcement body", result_2 -> {
        //Do stuff after announcement
      });
    });

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
   */
  public void userInput(int input) {
    if (!model.getPlayers().current().userInput(input))
      model.beginTurn();
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

}
