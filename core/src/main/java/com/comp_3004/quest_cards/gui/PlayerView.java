package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.comp_3004.quest_cards.core.Player;

public class PlayerView extends Table {

  //Todo: Make these private after unit testing

  public DeckView playerAdventureCards;
  public Image hero;
  public Image shields;

  public PlayerView() {

    setLayoutEnabled(false);

    System.out.printf("Player view width: %f\n", getWidth());
    System.out.printf("Player view height: %f\n", getHeight());
    System.out.printf("Player view z-index: %d\n", getZIndex());
    System.out.printf("Player view X: %f\n", getX());
    System.out.printf("Player view y: %f\n", getY());

    //Init widgets

    shields = configureShields(new Image());
    hero = configureHero(new Image());
    playerAdventureCards = configureAdventureDeck(initAdventureDeck());

    //Set up widgets

    add(playerAdventureCards);
    add(shields);
    add(hero);
  }

  private DeckView initAdventureDeck() {

    DeckView.DisplayStrategy deckDisplay = new SpillingDeckStrategy(
        Config.PlayerView.ADVENTURE_CARDS_MIN_OVERLAP,
        Config.PlayerView.ADVENTURE_CARDS_MAX_OVERLAP);

    playerAdventureCards = new DeckView(deckDisplay);
    return playerAdventureCards;
  }

  private DeckView configureAdventureDeck(DeckView playerAdventureCards) {

    playerAdventureCards.setBounds(
        Config.PlayerView.PADDING_HORIZONTAL,
        Config.PlayerView.PADDING_VERTICAL,
        Config.PlayerView.ADVENTURE_SPILLDECK_WIDTH,
        Config.CardView.CARD_HEIGHT);

    return playerAdventureCards;
  }

  private Image configureShields(Image shields) {

    shields.setSize(Config.PlayerView.SHIELD_WIDTH, Config.PlayerView.SHIELD_HEIGHT);

    float x = getWidth() - Config.PlayerView.PADDING_HORIZONTAL - shields.getWidth();
    float y = getHeight() + Config.PlayerView.PADDING_VERTICAL;

    shields.setPosition(x, y);

    System.out.printf("Shields x: %f\n", x);
    System.out.printf("Shields y: %f\n", y);
    System.out.printf("Shields width: %f\n", shields.getWidth());
    System.out.printf("Shields height: %f\n", shields.getHeight());

    return shields;
  }

  private Image configureHero(Image hero) {

    hero.setBounds(
        getWidth() - hero.getWidth() - Config.PlayerView.PADDING_HORIZONTAL,
        Config.PlayerView.PADDING_VERTICAL,
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    return hero;
  }

  public PlayerView setShieldsTexture(TextureRegion shieldTexture) {

    removeActor(this.shields);
    shields = configureShields(new Image(shieldTexture));
    addActor(shields);

    return this;
  }

  /*
  public void debugCards(GameView parent) {

    TextureAtlas atlas = parent.getSprites();
    CardView[] cards = new CardView[12];

    for (int i = 0; i < cards.length; i++) {
      cards[i] = new CardView(atlas.findRegion("A_King_Arthur"));
    }

    setCards(cards);
    setHero(new CardView(atlas.findRegion("R_Champion_Knight")));
  }
   */

  public PlayerView setHero(CardView hero) {

    removeActor(this.hero);
    this.hero = hero;
    addActor(hero);

    return this;
  }

  public PlayerView setCards(CardView[] cards) {
    playerAdventureCards.setCards(cards);
    return this;
  }


  public static class PlayerViewTester {

  }
}

