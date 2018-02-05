package com.comp_3004.quest_cards.gui;

public class Config {

  public static final int VIRTUAL_WIDTH = 1920;
  public static final int VIRTUAL_HEIGHT = 1080;

  public static class GameView {

  }

  public static class CardView {

    public static final int CARD_WIDTH = 218;
    public static final int CARD_HEIGHT = 300;
  }

  public static class PlayerView {

    //Container properties

    public static final int WIDTH = 1900;
    public static final int HEIGHT = 340;
    public static final int X = 10;
    public static final int Y = 10;
    public static final int PADDING_HORIZONTAL = 15;
    public static final int PADDING_VERTICAL = 20;

    //Adveture card display properties

    public static final int ADVENTURE_SPILLDECK_WIDTH = 1870;
    public static final float ADVENTURE_CARDS_MIN_OVERLAP = 0.10f;
    public static final float ADVENTURE_CARDS_MAX_OVERLAP = 0.90f;
  }
}
