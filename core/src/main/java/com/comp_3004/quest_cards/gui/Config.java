package com.comp_3004.quest_cards.gui;

public class Config {

  public static final int VIRTUAL_WIDTH = 1920;
  public static final int VIRTUAL_HEIGHT = 1080;

  public static class GameView {
    public static final int DRAWING_DECKS_WIDTH = 600;
    public static final int PADDING_HORIZONTAL = 25;
    public static final int PADDING_VERTICAL = 10;
    public static final int DISCARD_PILE_WIDTH = 278;

    public static class Modal {
      public static final int WIDTH = 1000;
      public static final int HEIGHT = 600;
    }
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

    //Shield display properties

    public static final int SHIELD_WIDTH = 159;
    public static final int SHIELD_HEIGHT = 200;

  }

  public static class Assets {
    public static final String BACKGROUND_ATLAS = "sprites/backgrounds.atlas";
    public static final String SPRITE_ATLAS = "sprites/cards.atlas";
  }
}
