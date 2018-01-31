package com.comp_3004.quest_cards.core;

public abstract class Card {
  protected String name;
  protected CardView view;

  abstract protected void printCard();

  public CardView getView() {
    return view;
  }
}