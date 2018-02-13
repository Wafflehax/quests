package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameView extends Group {


  //Widgets

  public PlayerView playerView;
  public Image background;
  public Image storyDeck;
  public Image storyDeckDiscardPile;
  public Image adventureDeckDiscardPile;
  public Image adventureDeck;


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

    //Add widgets to table

    addActor(background);
    addActor(storyDeck);
    addActor(storyDeckDiscardPile);
    addActor(adventureDeck);
    addActor(adventureDeckDiscardPile);
    addActor(playerView);
    addActor(new Dialog("Hello", skin));

  }

  @Override
  public void setBounds(float x, float y, float width, float height) {

    super.setBounds(x, y, width, height);

    //Resize widgets
    background.setBounds(x, y, width, height);

    playerView.setBounds(
        width / 2 - Config.PlayerView.WIDTH / 2,
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
        Config.CardView.CARD_HEIGHT);
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

  public GameView displayStoryDeck(TextureRegion storyDeck) {

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

  public GameView setBackground(TextureRegion background) {

    this.background.setDrawable(new TextureRegionDrawable(background));
    return this;
  }

  public GameView setPlayerViewBackground(TextureRegion background) {

    playerView.setBackground(background);
    return this;
  }

  public GameView setShieldsTexture(TextureRegion texture) {
    playerView.setShieldsTexture(texture);
    return this;
  }

}
