package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PlayerCards extends Table {

  //All of these constants should go on a definitions file

  private static final float CARD_OVERLAP = 0.60f;
  private static final int BORDER_SIZE = 5; //Pixels
  private static final int PADDING_VERTICAL = 10;
  private static final int PADDING_HORIZONTAL = 25;

  private Group adventureCards;

  public PlayerCards(TextureAtlas atlas) {

    //Init
    setBackground(new Image(atlas.findRegion("player-area")).getDrawable());
    adventureCards = new Group();
    add(adventureCards);

    //Set up layout

    setSize(getPrefWidth(), getPrefHeight());
    left().bottom();
  }

  public static PlayerCards debugPlayerHand(AssetManager manager) {

    manager.load("sprites/backgrounds.atlas", TextureAtlas.class);
    manager.load("sprites/cards.atlas", TextureAtlas.class);
    manager.finishLoading();


    TextureAtlas cardSprites = manager.get("sprites/cards.atlas", TextureAtlas.class);
    TextureAtlas backgroundSprites = manager.get("sprites/backgrounds.atlas", TextureAtlas.class);

    PlayerCards hand = new PlayerCards(backgroundSprites);
    CardView[] cards = new CardView[12];
    for (int i = 0; i < cards.length; i++) {
      cards[i] = new CardView(cardSprites.findRegion("A_King_Arthur"));
    }

    hand.setCards(cards);

    return hand;
  }

  public PlayerCards setCards(CardView[] cards) {

    //Remove previous cards
    adventureCards.clearChildren();

    for (int i = 0; i < cards.length; i++) {

      CardView currentCardView = cards[i];

      //Compute absolute position

      float x = PADDING_HORIZONTAL + i * currentCardView.getWidth() * (1 - CARD_OVERLAP);
      float y = BORDER_SIZE + PADDING_VERTICAL;

      currentCardView.setPosition(x, y);
      currentCardView.setZIndex(i);

      adventureCards.addActor(currentCardView);
    }

    setSize(getPrefWidth(), getPrefHeight());

    return this;
  }

}
