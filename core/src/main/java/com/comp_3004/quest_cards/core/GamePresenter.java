package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comp_3004.quest_cards.Stories.Quest;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.gui.Assets;
import com.comp_3004.quest_cards.gui.CardView;
import com.comp_3004.quest_cards.gui.Config;
import com.comp_3004.quest_cards.gui.GameView;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.gui.*;
import com.comp_3004.quest_cards.player.Players;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

public class GamePresenter extends Group {

  static Logger log = Logger.getLogger(GamePresenter.class); //log4j logger
  private final GameView view;
  public Map<String, String> CardAssetMap;
  public CardView[] handCards;
  public CardView[] activeCards;

  public TextureAtlas sprites;
  public TextureAtlas backgrounds;
  public int StageNum;
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

    //model = new GameModel();
    setUpScen1();

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
    view.players = new PlayerStatView[model.getPlayers().size()-1];

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
      handCards[i].setSponsorCDZ(view.zeroBounds);
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
    view.SponsorCDZ.setVisible(false);

    //SPONSOR CHECK
    if(model.getQuest() != null && model.getQuest().getSponsor() != null)
    {if(model.getcurrentTurn().getName().compareTo(model.getQuest().getSponsor().getName())==0)
    {view.SponsorCDZ.setVisible(true);
      for(int i=0; i<handCards.length;i++)handCards[i].setSponsorCDZ(view.SponsorCDZ.getBounds());}
    }

    view.displayAnnouncementDialog("","Let the Games BEGIN!\n\n"+model.getcurrentTurn().getName()+"...begin!",res->{

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
    	  assignHand(false,false);
    	  view.displayAnnouncementDialog("BEWARE!","You have too many cards!\nYou still need to discard "+(model.getcurrentTurn().getHand().size()-12)+" cards...",res->{});}

      else {
    	  		//if no event do nothing
    	  		if(model.getStory() instanceof TournamentCard) {
    	  			if(model.getTour().Complete()) {
    	  				//move on to next story
    	  				beginTurn();
    	  			}
    	  			model.getTour().doneTurn();
    	  		}
    	  		if(model.getEvent() == null) {
    	  			log.info("there is no event");
    	  			nextPlayer();
    	  		}
    	  		else if(model.getPlayers().peekNext() == model.getEvent().getDrewEvent()) {
        			model.getPlayers().setCurrent(model.getEvent().getDrewEvent());
        			nextPlayer();
        			beginTurn();
        		}
    	  		
        		else
        			nextPlayer();
        }


    }, false);
    // });


    return view;
  }

  private void questStageDisplay(){
    Quest quest = model.getQuest();

    for(int i = 0; i < quest.getQuest().getStages(); i++)
    { ArrayList<AdventureCard> tStage = quest.getStage(i).getSponsorCards();
     CardView [] stageCards = new CardView[tStage.size()];

      for(int j=0; j<stageCards.length;j++)
        {if(model.getcurrentTurn().getState().compareTo("sponsor") == 0) stageCards[j] = new CardView(sprites.findRegion(CardAssetMap.get(tStage.get(j).getName())),i);
          else if(i < quest.getCurrentStageNum()) stageCards[j] = new CardView(sprites.findRegion(CardAssetMap.get(tStage.get(j).getName())),i);
        else  stageCards[j] = new CardView(sprites.findRegion(Assets.Cards.CARD_BACK),tStage.get(j).getID());

        stageCards[j].setCardStage(i);
        view.playerView.playerAdventureCards.addActor(stageCards[j]);
        view.addToQuestStages(stageCards[j]);
        stageCards[j].setGamePresenter(this);


        }
      view.displayExtraCards(stageCards);



    }



  }



  //ASSIGN HAND
  private void assignHand(boolean doAnnounce, boolean doUpdate) {
    LinkedList<AdventureCard> tempHand = model.getcurrentTurn().getHand();
    LinkedList<AdventureCard> tempActive = model.getcurrentTurn().getActive();


    Players tempPlayers = model.getPlayers();
    //System.out.println("player = "+model.getcurrentTurn().getName()+": assignHand IDS:");

    //CLEAN CARDS FROM VIEW
    view.cardWipe();

    view.InPlayCDZ.setVisible(true);
    System.out.println("assignHand(): "+model.getcurrentTurn().getName()+": IDS");
    

    handCards = new CardView[tempHand.size()];
    activeCards = new CardView[tempActive.size()];


    for (int i = 0; i < tempHand.size(); i++) {
      String spriteGet = tempHand.get(i).getName();

      //System.out.println("spriteGet = " + spriteGet + "\nCardAssetMap.get(spriteGet) = " + CardAssetMap.get(spriteGet));
      handCards[i] = new CardView(sprites.findRegion(CardAssetMap.get(spriteGet)), tempHand.get(i).getID());
      handCards[i].setDiscardCDZ(view.DiscardCDZ.getBounds());
      handCards[i].setInPlayCDZ(view.InPlayCDZ.getBounds());
      handCards[i].setSponsorCDZ(view.zeroBounds);
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


    for(int i=0,j=0; i<tempPlayers.size();i++)
    {
      if(tempPlayers.getPlayerAtIndex(i).getName().compareTo(model.getcurrentTurn().getName()) != 0)
    {view.players[j].setPlayer(tempPlayers.getPlayerAtIndex(i));
    view.players[j].setPresenter(this);
    view.players[j].playerConfig();
    j++;}
    }


    view.displayPlayerHand(handCards); //CHECK IT OUT
    view.displayExtraCards(activeCards);
    if(model.getQuest() != null) questStageDisplay();
    view.displayHero(sprites.findRegion(CardAssetMap.get(model.getcurrentTurn().getRankS())));
    view.SponsorCDZ.setVisible(false);


    if(model.getQuest() != null && model.getQuest().getSponsor() != null)
    {
      if(model.getcurrentTurn().getName().compareTo(model.getQuest().getSponsor().getName())==0)
        {view.SponsorCDZ.setVisible(true);
        view.InPlayCDZ.setVisible(false);
          for(int i=0; i<handCards.length;i++)
          {handCards[i].setSponsorCDZ(view.SponsorCDZ.getBounds());
           handCards[i].setInPlayCDZ(view.zeroBounds);}
        }
    }
        if(doAnnounce) {
        		if(model.getcurrentTurn().getState() == "questParticipant") {
        			view.displayParticipateQuestDialog("Participation", "Participate in quest?", participate->{
        				if(participate) {
        					userInput(1);
        					nextPlayer();
        				}
        				else {
        					userInput(1);
        					nextPlayer();
        				}
        			});
      	  	}


			else {
				view.displayAnnouncementDialog("Begin Turn", "" + model.getcurrentTurn().getName() + "... begin!", result_2 -> {
		            drawCards();
		            storyDisplay();
		          });
			}

        }

        else if(doUpdate)
        {drawCards(); storyDisplay();}

        else
          {drawCards();}
  }



  //TURN METHODS
  public void beginTurn(){
	  model.beginTurn();
	  view.displayStoryDiscardPile(sprites.findRegion(CardAssetMap.get(model.getStory().getName())));
  }

  public void nextPlayer(){
	  model.nextPlayer();
	  assignHand(true,false);

	  }

  public void storyDisplay(){

    CardView StoryEv = new CardView(sprites.findRegion(CardAssetMap.get(model.getStory().getName())),model.getStory().getID());
    String storyType = CardAssetMap.get(model.getStory().getName()).substring(0,1);//E,T, or Q
    switch(storyType){

      case "E": //EVENT HANDLING
        //Gdx.app.log("displayEventAnnouncement","storyType -> EVENT");
        StageNum = model.getNumPlayers();
        StageNum--;
       if(model.getEvent().getDrewEvent() == model.getcurrentTurn() && StageNum == 0)
        {
          break;}

        view.displayEventAnnouncement(StoryEv, res_2 -> {
          model.getEvent().runEvent();
          assignHand(false,false);
          if(!model.getcurrentTurn().tooManyHandCards())
          {if(model.getPlayers().peekNext() == model.getEvent().getDrewEvent()) {
	  			model.getPlayers().setCurrent(model.getEvent().getDrewEvent());
	  			nextPlayer();
	  			beginTurn();
	  		}}
	  		else
          {view.displayAnnouncementDialog("You drew too many cards!","Play or discard at least "+(model.getcurrentTurn().getHand().size()-12)+" cards before you end your turn.",res->{});}
        });
        //TODO: IMPLEMENT KING'S CALL TO ARMS
        break;

        //TOURNEY HANDLING
      case "T":

        if(model.getTour().getLeftAsk()<1)
          {
        	//view.displayAnnouncementDialog("","Nobody Left to Ask",res->{});
        	if(model!=null && model.getTour()!=null) {
        		if(model.getTour().displaytourstartmessage() 
            			&& model.getTour().getJoiners() >=2 && model.getTour().getRound() == 1
            			&& model.getTour().getleftToPlayCard() > 0) {
            		String currentP = model.getcurrentTurn().getName();
            		TournamentCard t = (TournamentCard)model.getStory();
            		int aT = 0;
            		if(t != null)
            			aT = t.getBonusSh();
            		int avil = model.getTour().getJoiners();
            		avil += aT;
            		
            		view.displayAnnouncementDialog("Tournament Starting",currentP +"'s turn,"
            				+ " shields available are " + avil,res->{});	
            	}
        		else if(model.getTour().getJoiners() < 2
            			&& model.getTour().displaytourstartmessage() && model.getTour().getRound() == 1
            			&& model.getTour().getleftToPlayCard() > 0) {
            		String currentP = model.getcurrentTurn().getName();
            		TournamentCard t = (TournamentCard)model.getStory();
            		int aT = 0;
            		if(t != null)
            			aT = t.getBonusSh();
            		int avil = model.getTour().getJoiners();
            		avil += aT;
            		view.displayAnnouncementDialog("Tournament Not Starting",currentP +"'s turn,"
            				+ " shields available are " + avil,res->{});
            	}
        		//case of getting tour ouput
        		else if(model.getTour().Complete()) {
        			//display results
        			view.displayAnnouncementDialog("Round Results",model.getTour().tourresult + model.getcurrentTurn().getName(),res->{});
        			//start next story && check win condition before
        			beginTurn();
        		}
            	else {
            		view.displayAnnouncementDialog("","Begin Turn " + model.getcurrentTurn().getName(),res->{});
                }
        	}
        	else
        		view.displayAnnouncementDialog("","Model is null",res->{});
        	
          break;
          }

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
        if(model.getQuest().getSponsor() != null){
	        	if(model.getcurrentTurn().getState() == "questParticipant") {
		    		determineParticipation();
		    		if(model.getcurrentTurn() == model.getQuest().getDrewQuest())
		    			System.out.println("DING");
        		}
	        	break;
	    	}
    	  view.displaySponsorQuestDialog("Sponsor?",""+model.getStory().getName(), StoryEv, sponsorQuest->{

              if(sponsorQuest)
              { userInput(1); //Tells model currentPlayer wants to sponsor quest
                sponsor(false);

              }

              else
              {userInput(0);
              if(model.getPlayers().peekNext() == model.getQuest().getDrewQuest()) {
          		model.nextPlayer();
          		nextPlayer();
          		beginTurn();
              }
              else {
            	  	nextPlayer();
            	  	System.out.println(model.getcurrentTurn().getName());
              }
                }
            });

        break;

      default:
        System.err.print("Invalid input when Accessing storyType");
        break;

    }

  }

  public void sponsor(boolean error) {
	  System.out.println(error);
	  StageNum = 0;
      if(error) {
    	  	System.out.println("DING");
    	  	view.displayAnnouncementDialog("Set Up Error","Battle Points do not increase for each stage",res->{
    	  		log.info("Quest set up incorrectly");
    	  		questSetUp();
    	  		});
      }
      else {
    	  	questSetUp();
      }
  }

  public void questSetUp() {
	  StageNum = 0;
	  cardUpdate(StageNum);
      assignHand(false, false);
      view.displayNextStageButton(()->{
    	  	StageNum++;
    	  	assignHand(false,false);
    	      cardUpdate(StageNum);
    	      if(StageNum == (model.getQuest().getQuest().getStages()-1))
    	      {view.hideNextStageButton();

    	      //clearCards(handCards); //Kills Listeners on cards so sponsor can't commit OOB error
    	      view.displayFinishQuestSetupButton(()->{
    	        //TODO: Implement a check if QuestSetup is good, and resolve accordingly
    	      		if(model.getQuest().checkQuestSetup()) {
    	      			log.info("Quest set up correctly");
    	      			view.hideFinishSetupButton();
    	      			nextPlayer();
    	      		}
    	      		else {
    	      			assignHand(false,false);
    	      			sponsor(true);

    	      		}
    	      },false);}
      },false);
  }

  public void determineParticipation() {
		view.displayParticipateQuestDialog("Participation", "Participate in quest?", participate->{
			if(participate) {
				userInput(1);
				nextPlayer();
			}
			else  {
				userInput(0);
				nextPlayer();
			}
		});
  }

  public void clearCards(CardView [] cards){for(int i = 0; i<cards.length; i++) cards[i].clear();}

  public void cardUpdate(int stage){
    for(int i = 0; i<handCards.length;i++)
    {handCards[i].setCardStage(stage);}

  }

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
  public boolean playCard(int cardID, int stageNum) {
    AdventureCard cardToPlay = null;
    for (AdventureCard card : model.getPlayers().current().getHand())
      if (card.getID() == cardID)
        cardToPlay = card;
    if (cardToPlay == null)
      System.out.println("Card ID not found");
    if (cardToPlay != null)
      return model.getPlayers().current().playCard(cardToPlay, stageNum);


      return false;
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
        //log.info(model.getcurrentTurn().getName() + "'s turn begins.");
        //model.beginTurn();
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
      } //else
        //model.beginTurn();
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
    {}

    if (cardToPlay != null)
      if (model.getPlayers().current().playCard(cardToPlay)) {
        System.out.println("CARD FOUND!!"); return true;
      }
  }
  System.out.println("Card not played");
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
  
  	private void setUpScen1() {
		//set up story deck

		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Tournament at York");
		storyDeck.setTopCard("Chivalrous Deed");
		storyDeck.setTopCard("Prosperity Throughout the Realms");
		storyDeck.setTopCard("Boar Hunt");
        //storyDeck.setTopCard("Prosperity Throughout the Realms");
		
		//set up adventure deck 
		AdventureDeck advDeck = new AdventureDeck();
		
		model = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		//player1 hand: saxons, boar, sword, test of valor, dagger, thieves + 6
		model.getPlayerAtIndex(0).pickCard("Saxons", advDeck); 
		model.getPlayerAtIndex(0).pickCard("Boar", advDeck);
		model.getPlayerAtIndex(0).pickCard("Sword", advDeck);
		model.getPlayerAtIndex(0).pickCard("Test of Valor", advDeck);
		model.getPlayerAtIndex(0).pickCard("Dagger", advDeck);	
		model.getPlayerAtIndex(0).pickCard("Thieves", advDeck);	
		//player2 hand: thieves, dagger, giant, king arthur, lance, sir tristan + 6
		model.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		model.getPlayerAtIndex(1).pickCard("Dagger", advDeck);
		model.getPlayerAtIndex(1).pickCard("Giant", advDeck);
		model.getPlayerAtIndex(1).pickCard("King Arthur", advDeck);
		model.getPlayerAtIndex(1).pickCard("Lance", advDeck);	
		model.getPlayerAtIndex(1).pickCard("Sir Tristan", advDeck);	
		//player3 hand: thieves, horse, excalibur, amour, battle-ax, green knight + 6
		model.getPlayerAtIndex(2).pickCard("Thieves", advDeck); 
		model.getPlayerAtIndex(2).pickCard("Horse", advDeck);
		model.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);
		model.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		model.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);
		model.getPlayerAtIndex(2).pickCard("Green Knight", advDeck);	
		//player4 hand: thieves, battle-ax, lance, thieves, horse, dagger + 6
		model.getPlayerAtIndex(3).pickCard("Thieves", advDeck); 
		model.getPlayerAtIndex(3).pickCard("Battle-Ax", advDeck);
		model.getPlayerAtIndex(3).pickCard("Lance", advDeck);
		model.getPlayerAtIndex(3).pickCard("Thieves", advDeck);
		model.getPlayerAtIndex(3).pickCard("Horse", advDeck);
		model.getPlayerAtIndex(3).pickCard("Dagger", advDeck);
		

		model.getAdvDeck().shuffle();
		for(Player p : model.getPlayers().getPlayers()) {
			for(int i=0; i<6; i++)
				p.drawCard(advDeck);
		}
	}

}
