package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comp_3004.quest_cards.player.Player;

public class PlayerView extends Group {

  //Todo: Make these private after unit testing

  public DeckView playerAdventureCards;
  public Image hero;
  public Image background;

  public PlayerView() {

    background = new Image();
    background.setBounds(0,0,getWidth(), getHeight());

    System.out.printf("Player view width: %f\n", getWidth());
    System.out.printf("Player view height: %f\n", getHeight());
    System.out.printf("Player view z-index: %d\n", getZIndex());
    System.out.printf("Player view X: %f\n", getX());
    System.out.printf("Player view y: %f\n", getY());

    //Init widgets

    //Init hero

    hero = new Image();
    hero.setBounds(
        Config.PlayerView.WIDTH - Config.PlayerView.PADDING_HORIZONTAL - Config.CardView.CARD_WIDTH,
        Config.PlayerView.PADDING_VERTICAL,
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    //Init player hand

    DeckView.DisplayStrategy deckDisplay = new SpillingDeckStrategy(
        Config.PlayerView.ADVENTURE_CARDS_MIN_OVERLAP,
        Config.PlayerView.ADVENTURE_CARDS_MAX_OVERLAP);

    playerAdventureCards = new DeckView(deckDisplay);
    playerAdventureCards.setBounds(
        Config.PlayerView.PADDING_HORIZONTAL,
        Config.PlayerView.PADDING_VERTICAL,
        Config.PlayerView.ADVENTURE_SPILLDECK_WIDTH,
        Config.CardView.CARD_HEIGHT);

    //Add widgets to table

    addActor(background);
    addActor(playerAdventureCards);
    addActor(hero);
  }

  @Override
  public void setBounds(float x, float y, float width, float height){
    super.setBounds(x,y,width, height);
    background.setBounds(0,0,width, height);
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

  public PlayerView displayHero(TextureRegion heroDrawable) {

    this.hero.setDrawable(new TextureRegionDrawable(heroDrawable));
    return this;
  }

  public PlayerView displayPlayerHand(CardView[] cards) {
    playerAdventureCards.setCards(cards);
    return this;
  }

  public PlayerView wipePlayerHand(CardView[] cards){
    for(int i=0; i<cards.length;i++)
      cards[i].remove();

    return this;
  }

  public PlayerView setBackground(TextureRegion textureRegion){
    this.background.setDrawable(new TextureRegionDrawable(textureRegion));
    return this;
  }

}

