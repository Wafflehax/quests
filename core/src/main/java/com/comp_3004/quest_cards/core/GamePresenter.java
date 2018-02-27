package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.EventCard;
import com.comp_3004.quest_cards.cards.TournamentCard;
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
  public CardView[] handCards;
  public CardView[] activeCards;
  public CardView[] stageCards;

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

    LinkedList<AdventureCard> tempHand = model.getcurrentTurn().getHand();

    activeCards = new CardView[1];
    activeCards[0] = new CardView(sprites.findRegion("R_Champion_Knight"),0);
    activeCards[0].setVisible(false);

    handCards = new CardView[tempHand.size()];
    for (int i = 0; i < tempHand.size(); i++) {
      String spriteGet = tempHand.get(i).getName();


      handCards[i] = new CardView(sprites.findRegion(CardAssetMap.get(spriteGet)), tempHand.get(i).getID());
      handCards[i].setDiscardCDZ(view.DiscardCDZ.getBounds());
      handCards[i].setInPlayCDZ(view.InPlayCDZ.getBounds());
      handCards[i].setSponsorCDZ(view.SponsorCDZ.getBounds());
      handCards[i].setGamePresenter(this);
      handCards[i].setColor(1, 1, 1, 0);

    }

    view.setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);


    //DragConfig(cards);

    //Hero area
    view.displayPlayerHand(handCards); //CHECK IT OUT
    view.displayPlayerHand(activeCards);
    view.displayHero(sprites.findRegion(CardAssetMap.get(model.getcurrentTurn().getRankS())));

    //Drawing decks

    view.displayStoryDeck(sprites.findRegion(Assets.Cards.CARD_BACK));
    view.displayStoryDiscardPile(new TextureRegion(new Texture("sprites/boundary.png")));
    view.displayAdventureDeck(sprites.findRegion(Assets.Cards.CARD_BACK));
    view.displayAdventureDiscardPile(new TextureRegion(new Texture("sprites/boundary.png"))); //TODO: initialize empty white border

    view.displayAnnouncementDialog("","Let the Games BEGIN!\n\n"+model.getcurrentTurn().getName()+"...begin!",res->{
      model.getStoryDeck().setTopCard("Tournament at Camelot");//RIGGING!
      beginTurn();
      drawCards();
      storyDisplay();
  });

    //Dialog example

    /*view.displayQuestionDialog("Question title", "Question message", result -> {
      if (result) {
        //Player says yes
      } else {
        //Player says no
      }*/

    view.displayNextTurnButton(() -> {
      System.out.println(model.getcurrentTurn().getState());
      if(model.getcurrentTurn().tooManyHandCards()){
    	  assignHand(false);
    	  view.displayAnnouncementDialog("BEWARE!","YOU HAVE TOO MANY CARDS!!\nPLEASE MAKE SURE YOU HAVE LESS THAN 12 CARDS!",res->{});}
      else
      {
    	  nextPlayer();
      }

    }, false);
    // });


    return view;
  }



  //ASSIGN HAND
  private void assignHand(boolean doAnnounce) {
    LinkedList<AdventureCard> tempHand = model.getcurrentTurn().getHand();
    LinkedList<AdventureCard> tempActive = model.getcurrentTurn().getActive();
    //System.out.println("player = "+model.getcurrentTurn().getName()+": assignHand IDS:");

    view.playerView.wipePlayerHand(handCards);
    view.playerView.wipePlayerHand(activeCards);
    view.cardWipe();
    System.out.println("assignHand(): "+model.getcurrentTurn().getName()+": IDS");

    handCards = new CardView[tempHand.size()];
    activeCards = new CardView[tempActive.size()];
    for (int i = 0; i < tempHand.size(); i++) {
      String spriteGet = tempHand.get(i).getName();

      //System.out.println("spriteGet = " + spriteGet + "\nCardAssetMap.get(spriteGet) = " + CardAssetMap.get(spriteGet));
      handCards[i] = new CardView(sprites.findRegion(CardAssetMap.get(spriteGet)), tempHand.get(i).getID());
      handCards[i].setDiscardCDZ(view.DiscardCDZ.getBounds());
      handCards[i].setInPlayCDZ(view.InPlayCDZ.getBounds());
      handCards[i].setSponsorCDZ(view.SponsorCDZ.getBounds());
      handCards[i].setGamePresenter(this);
      handCards[i].setColor(1, 1, 1, 0);
      //System.out.println(handCards[i].getCardID());

    }

    for(int i=0; i<tempActive.size(); i++) {
      String spriteGet = tempActive.get(i).getName();
      activeCards[i] = new CardView(sprites.findRegion(CardAssetMap.get(spriteGet)), tempActive.get(i).getID());
      view.playerView.playerAdventureCards.addActor(activeCards[i]);
      view.addToPlay(activeCards[i]);
      activeCards[i].setGamePresenter(this);
    }


    view.displayPlayerHand(handCards); //CHECK IT OUT
    view.displayHero(sprites.findRegion(CardAssetMap.get(model.getcurrentTurn().getRankS())));


        if(doAnnounce) {
          		
			if(model.getTour() != null && model.getTour().displaytourstartmessage() == true 
					&& model.getTour().getJoiners() >= 2) {
				view.displayAnnouncementDialog("Tournament Starting", model.getTour().getJoiners() + " have joined\n" +
          " with shield winnings of " + (((TournamentCard)model.getStory()).getBonusSh()+model.getTour().getJoiners()),res->{});
				drawCards();
	            storyDisplay();
	          
			}
			else if(model.getTour().getleftToPlayCard() == 0 && model.getTour().getJoiners() < 2
					&& model.getTour().displaytourstartmessage()) {
				view.displayAnnouncementDialog("Tournament NOT Starting", model.getTour().getJoiners() + " have joined\n" +
          " with shield winnings of " + (((TournamentCard)model.getStory()).getBonusSh()+model.getTour().getJoiners()) +
          " but not enought players!",res->{});
        	  model.getTour().displaytourstartmessage(false);
        	  drawCards();
	            storyDisplay();
	          
			}
			else {
				view.displayAnnouncementDialog("Begin Turn", "" + model.getcurrentTurn().getName() + "... begin!", result_2 -> {
		            drawCards();
		            storyDisplay();
		          });
			}
          
        }

        else
        {drawCards();}
  }



  //TURN METHODS
  public void beginTurn(){model.beginTurn();
    view.displayStoryDiscardPile(sprites.findRegion(CardAssetMap.get(model.getStory().getName())));
  }

  public void nextPlayer(){model.nextPlayer(); assignHand(true);}

  public void storyDisplay(){

    CardView StoryEv = new CardView(sprites.findRegion(CardAssetMap.get(model.getStory().getName())),model.getStory().getID());
    String storyType = CardAssetMap.get(model.getStory().getName()).substring(0,1);//E,T, or Q
    switch(storyType){

      case "E": //EVENT HANDLING
        //Gdx.app.log("displayEventAnnouncement","storyType -> EVENT");

        view.displayEventAnnouncement(StoryEv, res_2 -> {
          if(model.getPlayers().peekNext().getName().compareTo(model.getEvent().getDrewEvent().getName()) == 0)
          {beginTurn();
          nextPlayer();}
          else
          {nextPlayer();}

        });
        //TODO: IMPLEMENT KING'S CALL TO ARMS
        break;

        //TOURNEY HANDLING
      case "T":
        if(model.getTour().getLeftAsk()==0)
          {
           // if(model.getTour().isOver())
           // {}
        	//if player turn start then display start tour message
        
        	
        	

            break;}

        view.displayJoinEventDialog("Join Tourney?",""+model.getStory().getName(), StoryEv, joinTourney->{

          if(joinTourney)
          { userInput(1); //Tells model currentPlayer wants to Tourney
            nextPlayer();
          }

          else
          {userInput(0);
            nextPlayer();}

        });
        break;

      case "Q":
        view.displaySponsorQuestDialog("I'm Gay","whatever",StoryEv,res->{});
       //Gdx.app.log("displayEventAnnouncement","storyType -> QUEST");
       // model.nextPlayer();
        break;

      default:
        System.err.print("Invalid input when Accessing storyType");
        break;

    }

  }

  public void handleCardOverflow(){
   System.out.println("handleCardOverflow()");

    model.getcurrentTurn().setState(model.getcurrentTurn().getPrevState());

  } //TODO: Infinite loop until state != "tooManyCards"

  public void playerUpdate(){
    //model.getcurrentTurn().getRankS();
  }

  @Override
  public void draw(Batch batch, float alpha) {
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
  public boolean playCard(int cardID) {
    System.out.println("playCard() "+model.getcurrentTurn().getName());
    AdventureCard cardToPlay = null;
    for (AdventureCard card : model.getcurrentTurn().getHand())
    {if (card.getID() == cardID)
        cardToPlay = card;

    if (cardToPlay == null)
    {System.out.println("Card not found");}

    if (cardToPlay != null)
      if (model.getPlayers().current().playCard(cardToPlay)) {
        System.out.println("CARD FOUND!!"); return true;
      }
  }
  return false;
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
    CardAssetMap.put("Tournament at Camelot", Assets.Cards.Tournament.CAMELOT);
    CardAssetMap.put("Tournament at Orkney", Assets.Cards.Tournament.ORKNEY);
    CardAssetMap.put("Tournament at Tintagel", Assets.Cards.Tournament.TINTAGEL);
    CardAssetMap.put("Tournament at York", Assets.Cards.Tournament.YORK);
    //EVENTS
    CardAssetMap.put("King's Recognition", Assets.Cards.Story.KINGS_RECOGNITION);
    CardAssetMap.put("Queen's Favor", Assets.Cards.Story.QUEENS_FAVOR);
    CardAssetMap.put("Court Called to Camelot", Assets.Cards.Story.COURT_CALLED_CAMELOT);
    CardAssetMap.put("Pox", Assets.Cards.Story.POX);
    CardAssetMap.put("Plague", Assets.Cards.Story.PLAGUE);
    CardAssetMap.put("Chivalrous Deed", Assets.Cards.Story.CHIVALROUS_DEED);
    CardAssetMap.put("Prosperity Throughout the Realms", Assets.Cards.Story.PROSPERITY_THROUGHOUT_THE_REALM);
    CardAssetMap.put("King's Call to Arms", Assets.Cards.Story.KINGS_CALL_TO_ARMS);
    //AMOUR
    CardAssetMap.put("Amour", Assets.Cards.Allies.AMOUR);
    //QUESTS
    CardAssetMap.put("Search for the Holy Grail",Assets.Cards.Quest.SEARCH_FOR_THE_HOLY_GRAIL);
    CardAssetMap.put("Test of the Green Knight",Assets.Cards.Quest.TEST_OF_THE_GREEN_KNIGHT);
    CardAssetMap.put("Search for the Questing Beast",Assets.Cards.Quest.SEARCH_FOR_THE_QUESTING_BEAST);
    CardAssetMap.put("Defend the Queen's Honor",Assets.Cards.Quest.DEFEND_THE_QUEENS_HONOR);
    CardAssetMap.put("Rescue the Fair Maiden",Assets.Cards.Quest.RESCUE_THE_FAIR_MAIDEN);
    CardAssetMap.put("Journey Through the Enchanted Forest",Assets.Cards.Quest.JOURNEY_THROUGH_THE_ENCHANTED_FOREST);
    CardAssetMap.put("Slay the Dragon",Assets.Cards.Quest.SLAY_THE_DRAGON);
    CardAssetMap.put("Vanquish King Arthur's Enemies",Assets.Cards.Quest.VANQUISH_KING_ARTHURS_ENEMIES);
    CardAssetMap.put("Boar Hunt",Assets.Cards.Quest.BOAR_HUNT);
    CardAssetMap.put("Repel the Saxon Invaders",Assets.Cards.Quest.REPEL_THE_SAXON_RAIDERS);
    //RANKS
    CardAssetMap.put("Champion Knight","R_Champion_Knight");
    CardAssetMap.put("Squire","R_Squire");
    CardAssetMap.put("Knight","R_Knight");

  }

  public void flipDown(CardView card) {
    card.setDrawable(new TextureRegionDrawable(new TextureRegion(sprites.findRegion(Assets.Cards.CARD_BACK))));
  }

  public void flipUp(CardView card) {
    card.setDrawable(new TextureRegionDrawable(card.getPicDisplay()));
  }

  public void drawCards() {
    for (int i = 0; i < handCards.length; i++) {
      view.displayDrawCardAnimation(handCards[i]);
    }
  }

}
