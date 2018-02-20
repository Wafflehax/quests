package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.function.Consumer;

public class GameView extends Group {


  //Widgets

  public PlayerView playerView;
  public Image background;
  public Image storyDeck;
  public Image storyDeckDiscardPile;
  public Image adventureDeckDiscardPile;
  public Image adventureDeck;
  public CardDropZone SponsorCDZ;
  public CardDropZone DiscardCDZ;
  public CardDropZone InPlayCDZ;

  public AnnouncementDialog announcementDialog;
  public BooleanDialog questionDialog;


  private Skin skin;

  public GameView(Skin skin) {

    this.skin = skin;

    //Set up layout
    background = new Image();
    storyDeck = new Image();
    storyDeckDiscardPile = new Image();
    adventureDeck = new Image();
    adventureDeckDiscardPile = new Image();
    playerView = new PlayerView();
    announcementDialog = new AnnouncementDialog(skin);
    questionDialog = new BooleanDialog(skin);

    //Init and Orient the CDZs
    SponsorCDZ = new CardDropZone(new Sprite(new Texture("DropZones/SponsorCDZ.png")));
    DiscardCDZ = new CardDropZone(new Sprite(new Texture("DropZones/SponsorCDZ.png")));
    InPlayCDZ = new CardDropZone(new Sprite (new Texture("DropZones/InPlayCDZ.png")));





    //Add widgets to table

    addActor(background);
    addActor(SponsorCDZ);
    addActor(DiscardCDZ);
    addActor(storyDeck);
    addActor(storyDeckDiscardPile);
    addActor(adventureDeck);
    addActor(adventureDeckDiscardPile);
    addActor(playerView);
    playerView.addActorAt(1,InPlayCDZ);
  }

  public void pack(){

  }

  @Override
  public void setBounds(float x, float y, float width, float height) {

    super.setBounds(x, y, width, height);

    //Resize widgets
    background.setBounds(x, y, width, height);

    playerView.setBounds(
        getWidth() / 2 - Config.PlayerView.WIDTH / 2,
        Config.GameView.PADDING_VERTICAL,
        Config.PlayerView.WIDTH,
        Config.PlayerView.HEIGHT);

    storyDeck.setBounds(
        Config.GameView.PADDING_HORIZONTAL,
        Config.VIRTUAL_HEIGHT - Config.GameView.PADDING_VERTICAL - Config.CardView.CARD_HEIGHT,
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    storyDeckDiscardPile.setBounds(
        storyDeck.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL,
        storyDeck.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    adventureDeck.setBounds(
        storyDeckDiscardPile.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL,
        storyDeckDiscardPile.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    adventureDeckDiscardPile.setBounds(
        adventureDeck.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL,
        storyDeckDiscardPile.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT
    );

    DiscardCDZ.setDropZoneBounds((int)adventureDeck.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL+70,
            (int)storyDeckDiscardPile.getY()+150,
            10,
            50);

    InPlayCDZ.setDropZoneBounds(Config.VIRTUAL_WIDTH/2+40,20,Config.CardView.CARD_WIDTH*3-20,Config.CardView.CARD_HEIGHT);


    //Add widgets to table

//    addActor(storyDeck);
//    addActor(storyDeckDiscardPile);
//    addActor(adventureDeck);
//    addActor(adventureDeckDiscardPile);
//    addActor(SponsorCDZ); //CDZ = CARDDROPZONE
//    addActor(playerView);
    //addActor(cardDropZone);
  }



  public GameView displayHero(TextureRegion hero) {
    playerView.displayHero(hero);
    announcementDialog.setSize(Config.GameView.Modal.WIDTH, Config.GameView.Modal.HEIGHT);
    announcementDialog.setCenterPosition(getWidth() / 2, getHeight() / 2);
    questionDialog.setSize(Config.GameView.Modal.WIDTH, Config.GameView.Modal.HEIGHT);
    questionDialog.setCenterPosition(getWidth() / 2, getHeight() / 2);
return this;
  }

  public void displayPlayerHand(CardView[] cards) {
    playerView.displayPlayerHand(cards);
  }

  public void displayShieldNumber(int n) {

    return;
  }

  public void displayStoryDeck(TextureRegion storyDeck) {

    this.storyDeck.setDrawable(new TextureRegionDrawable(storyDeck));
  }

  public void displayAdventureDiscardPile(TextureRegion card) {

    adventureDeckDiscardPile.setDrawable(new TextureRegionDrawable(card));
  }

  public void displayStoryDiscardPile(TextureRegion card) {

    storyDeckDiscardPile.setDrawable(new TextureRegionDrawable(card));
  }

  public void displayDrawCardAnimation(CardView card) {


    return;
  }

  public void displayAdventureDeck(TextureRegion adventureDeck) {
    this.adventureDeck.setDrawable(new TextureRegionDrawable(adventureDeck));
  }

  public void setBackground(TextureRegion background) {

    this.background.setDrawable(new TextureRegionDrawable(background));
  }

  public void setPlayerViewBackground(TextureRegion background) {

    playerView.setBackground(background);
  }

  public void setShieldsTexture(TextureRegion texture) {
    playerView.setShieldsTexture(texture);
  }

  public void displayAnnouncementDialog(String title, String message, final Consumer<Boolean> action) {

    announcementDialog.setTitle(title);
    announcementDialog.setMessage(message);
    announcementDialog.setActionListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        action.accept(true);
        announcementDialog.remove();
      }
    });

    addActor(announcementDialog);

  }

  public void displayQuestionDialog(String title, String message, final Consumer<Boolean> action) {

    questionDialog.setTitle(title);
    questionDialog.setMessage(message);

    questionDialog.setActionTrue(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        questionDialog.remove();
        action.accept(true);
      }
    });

    questionDialog.setActionTrue(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        questionDialog.remove();
        action.accept(false);
      }
    });

    addActor(questionDialog);
  }

}
