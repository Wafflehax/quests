package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.comp_3004.quest_cards.gui.Config.PlayerView.*;

public class PlayerView extends Group {


  private DeckView playerAdventureCards;

  public PlayerView(TextureAtlas atlas) {

    //Init
    setBounds(X, Y, WIDTH, HEIGHT);

    //Init background

    Image background = new Image(atlas.findRegion("player-area"));
    background.setBounds(getX(), getY(), getWidth(), getHeight());
    addActor(background);

    System.out.printf("Player view width: %f\n", getWidth());
    System.out.printf("Player view height: %f\n", getHeight());
    System.out.printf("Player view z-index: %d\n", getZIndex());
    System.out.printf("Player view X: %f\n", getX());
    System.out.printf("Player view y: %f\n", getY());

    //Init adventure cards view (adventure cards held by player)

    DeckView.DisplayStrategy deckDisplay = new SpillingDeckStrategy(
        ADVENTURE_CARDS_MIN_OVERLAP,
        ADVENTURE_CARDS_MAX_OVERLAP);

    playerAdventureCards = new DeckView(deckDisplay);
    playerAdventureCards.setSize(ADVENTURE_SPILLDECK_WIDTH, Config.CardView.CARD_HEIGHT);
    playerAdventureCards.setPosition(getX() + PADDING_HORIZONTAL,
        getY() + PADDING_VERTICAL);
    playerAdventureCards.setColor(Color.GREEN);
    addActor(playerAdventureCards);


    System.out.printf("Adventure card area width: %f\n", playerAdventureCards.getWidth());
    System.out.printf("Adventure card area height: %f\n", playerAdventureCards.getHeight());
    System.out.printf("Adventure card area z-index: %d\n", playerAdventureCards.getZIndex());
    System.out.printf("Adventure card area X: %f\n", playerAdventureCards.getX());
    System.out.printf("Adventure card area y: %f\n", playerAdventureCards.getY());


  }

  public static PlayerView debugPlayerHand(AssetManager manager) {

    manager.load("sprites/backgrounds.atlas", TextureAtlas.class);
    manager.load("sprites/cards.atlas", TextureAtlas.class);
    manager.finishLoading();


    TextureAtlas cardSprites = manager.get("sprites/cards.atlas", TextureAtlas.class);
    TextureAtlas backgroundSprites = manager.get("sprites/backgrounds.atlas", TextureAtlas.class);

    PlayerView hand = new PlayerView(backgroundSprites);
    CardView[] cards = new CardView[12];
    for (int i = 0; i < cards.length; i++) {
      cards[i] = new CardView(cardSprites.findRegion("A_King_Arthur"));
    }

    hand.setCards(cards);

    return hand;
  }

  public PlayerView setCards(CardView[] cards) {
    playerAdventureCards.setCards(cards);
    return this;
  }


}
