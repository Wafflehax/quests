package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PlayerView extends Table {

  private PlayerView instance;
  private DeckView playerAdventureCards;
  private CardView hero;
  private Image shields;
  private Image background;
  private GameScreen parent;

  public PlayerView(GameScreen parent) {

    //setBounds(Config.PlayerView.X, Config.PlayerView.Y, Config.PlayerView.WIDTH, Config.PlayerView.HEIGHT);

    this.parent = parent;
    //setBounds(0, 0, getWidth(), getHeight());

    System.out.printf("Player view width: %f\n", getWidth());
    System.out.printf("Player view height: %f\n", getHeight());
    System.out.printf("Player view z-index: %d\n", getZIndex());
    System.out.printf("Player view X: %f\n", getX());
    System.out.printf("Player view y: %f\n", getY());

    TextureAtlas backgrounds = parent.getBackgrounds();
    TextureAtlas sprites = parent.getSprites();

    //Init background

    setBackground(new Image(backgrounds.findRegion("player-area")).getDrawable());

    //Init widgets

    initAdventureDeck();
    initShields(sprites);
    debugCards(parent);
  }

  private void initAdventureDeck() {

    DeckView.DisplayStrategy deckDisplay = new SpillingDeckStrategy(
        Config.PlayerView.ADVENTURE_CARDS_MIN_OVERLAP,
        Config.PlayerView.ADVENTURE_CARDS_MAX_OVERLAP);

    playerAdventureCards = new DeckView(deckDisplay);
    playerAdventureCards.setBounds(
        Config.PlayerView.PADDING_HORIZONTAL,
        Config.PlayerView.PADDING_VERTICAL,
        Config.PlayerView.ADVENTURE_SPILLDECK_WIDTH,
        Config.CardView.CARD_HEIGHT);

    addActor(playerAdventureCards);
  }

  private void initShields(TextureAtlas atlas) {

    shields = new Image(atlas.findRegion("shield"));
    shields.setSize(Config.PlayerView.SHIELD_WIDTH, Config.PlayerView.SHIELD_HEIGHT);

    float x = getWidth() - Config.PlayerView.PADDING_HORIZONTAL - shields.getWidth();
    float y = getHeight() + Config.PlayerView.PADDING_VERTICAL;

    shields.setPosition(x, y);

    System.out.printf("Shields x: %f\n", x);
    System.out.printf("Shields y: %f\n", y);
    System.out.printf("Shields width: %f\n", shields.getWidth());
    System.out.printf("Shields height: %f\n", shields.getHeight());
    addActor(shields);
  }

  public PlayerView setHero(CardView hero) {

    //Replace old hero

    removeActor(this.hero);
    this.hero = hero;

    //Add to group

    addActor(hero);
    hero.setPosition(getWidth() - hero.getWidth() - Config.PlayerView.PADDING_HORIZONTAL, Config.PlayerView.PADDING_VERTICAL);
    return this;
  }

  public void debugCards(GameScreen parent) {

    TextureAtlas atlas = parent.getSprites();
    CardView[] cards = new CardView[12];

    for (int i = 0; i < cards.length; i++) {
      cards[i] = new CardView(atlas.findRegion("A_King_Arthur"));
    }

    setCards(cards);
    setHero(new CardView(atlas.findRegion("R_Champion_Knight")));
  }

  public PlayerView setCards(CardView[] cards) {
    playerAdventureCards.setCards(cards);
    return this;
  }

  public static class PlayerViewTester {

    public static boolean testBackgroundBounds(PlayerView playerView) {

      System.out.printf("Background width: %f\n", playerView.background.getWidth());
      System.out.printf("Background height: %f\n", playerView.background.getHeight());
      System.out.printf("Background z-index: %d\n", playerView.background.getZIndex());
      System.out.printf("Background X: %f\n", playerView.background.getX());
      System.out.printf("Background y: %f\n", playerView.background.getY());
      return false;
    }

    public static boolean testSelfBounds(PlayerView playerView) {

      System.out.printf("Player view width: %f\n", playerView.getWidth());
      System.out.printf("Player view height: %f\n", playerView.getHeight());
      System.out.printf("Player view z-index: %d\n", playerView.getZIndex());
      System.out.printf("Player view X: %f\n", playerView.getX());
      System.out.printf("Player view y: %f\n", playerView.getY());
      return false;
    }

    public static boolean testDeckViewBounds(PlayerView playerView) {

      System.out.printf("Adventure card area width: %f\n", playerView.playerAdventureCards.getWidth());
      System.out.printf("Adventure card area height: %f\n", playerView.playerAdventureCards.getHeight());
      System.out.printf("Adventure card area z-index: %d\n", playerView.playerAdventureCards.getZIndex());
      System.out.printf("Adventure card area X: %f\n", playerView.playerAdventureCards.getX());
      System.out.printf("Adventure card area y: %f\n", playerView.playerAdventureCards.getY());

      return false;
    }

    public static PlayerView Debug(GameScreen parent) {

      PlayerView playerView = new PlayerView(parent);

      testSelfBounds(playerView);
      testBackgroundBounds(playerView);
      testDeckViewBounds(playerView);

      return playerView;
    }
  }
}

