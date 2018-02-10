package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameView extends Table {


  //Widgets

  public PlayerView playerView;
  public Image storyDeck;
  public Image storyDeckDiscardPile;
  public Image adventureDeckDiscardPile;
  public Image adventureDeck;

  public GameView() {

    //Set up layout

    setLayoutEnabled(false);
    setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);

    //Init widgets

    playerView = new PlayerView();
    playerView.setBounds(
        Config.VIRTUAL_WIDTH / 2 - Config.PlayerView.WIDTH / 2,
        Config.GameView.PADDING_VERTICAL,
        Config.PlayerView.WIDTH,
        Config.PlayerView.HEIGHT);

    storyDeck = new Image();
    storyDeck.setBounds(
        Config.GameView.PADDING_HORIZONTAL,
        Config.VIRTUAL_HEIGHT - Config.GameView.PADDING_VERTICAL - Config.CardView.CARD_HEIGHT,
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    storyDeckDiscardPile = new Image();
    storyDeckDiscardPile.setBounds(
        storyDeck.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL,
        storyDeck.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    adventureDeck = new Image();
    adventureDeck.setBounds(
        storyDeckDiscardPile.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL,
        storyDeckDiscardPile.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    adventureDeckDiscardPile = new Image();
    adventureDeckDiscardPile.setBounds(
        adventureDeck.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL,
        storyDeckDiscardPile.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT
    );

    //Add widgets to table

    addActor(storyDeck);
    addActor(storyDeckDiscardPile);
    addActor(adventureDeck);
    addActor(adventureDeckDiscardPile);
    addActor(playerView);
  }

  public GameView displayHero(TextureRegion hero) {
    playerView.displayHero(hero);
    return this;
  }

  public GameView displayPlayerHand(CardView[] cards) {
    playerView.displayPlayerHand(cards);
    return this;
  }

  public GameView displayShieldNumber(int n) {

    return null;
  }

  public GameView displayStoryDeck(TextureRegion storyDeck){

    this.storyDeck.setDrawable(new TextureRegionDrawable(storyDeck));
    return this;
  }

  public GameView displayAdventureDiscardPile(TextureRegion card) {

    adventureDeckDiscardPile.setDrawable(new TextureRegionDrawable(card));
    return this;
  }

  public GameView displayStoryDiscardPile(TextureRegion card) {

    storyDeckDiscardPile.setDrawable(new TextureRegionDrawable(card));
    return this;
  }

  public GameView displayDrawCardAnimation(CardView card) {


    return this;
  }

  public void displayAdventureDeck(TextureRegion adventureDeck) {
    this.adventureDeck.setDrawable(new TextureRegionDrawable(adventureDeck));
  }

  public GameView setBackground(TextureRegion background){

    setBackground(new TextureRegionDrawable(background));
    return this;
  }

  public GameView setPlayerViewBackground(TextureRegion background) {

    playerView.setBackground(new TextureRegionDrawable(background));
    return this;
  }

  public GameView setShieldsTexture(TextureRegion texture) {
    playerView.setShieldsTexture(texture);
    return this;
  }

}
