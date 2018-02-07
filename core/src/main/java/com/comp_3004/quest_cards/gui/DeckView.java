package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.comp_3004.quest_cards.core.QuestCards;

public class DeckView extends Table {

  private DisplayStrategy displayStrategy;

  public DeckView(DisplayStrategy displayStrategy) {
    setLayoutEnabled(false);
    this.displayStrategy = displayStrategy;
  }

  public void setCards(CardView[] cards) {

    displayStrategy.setCards(this, cards);
  }

  public interface DisplayStrategy {

    void setCards(DeckView client, CardView[] cards);
  }
}
