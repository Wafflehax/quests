package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.scenes.scene2d.Group;

public class DeckView extends Group {

  private DisplayStrategy displayStrategy;

  public DeckView(DisplayStrategy displayStrategy) {

    this.displayStrategy = displayStrategy;
  }

  public void setCards(CardView[] cards) {

    displayStrategy.setCards(this, cards);
  }

  public interface DisplayStrategy {

    void setCards(DeckView client, CardView[] cards);
  }
}
