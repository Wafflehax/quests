package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SpillingDeckStrategy implements DeckView.DisplayStrategy {


  private float minOverlap;
  private float maxOverlap;

  public SpillingDeckStrategy() {
    this(0, 1);
  }

  public SpillingDeckStrategy(float minOverlap, float maxOverlap) {

    this.minOverlap = minOverlap;
    this.maxOverlap = maxOverlap;
  }

  @Override
  public void setCards(DeckView client, CardView[] cards) {

    //Remove previous cards
    client.clearChildren();


    float computedOverlap = computeOverlap(cards[0].getWidth(), cards.length, client.getWidth());
    computedOverlap = computedOverlap < minOverlap ? minOverlap : computedOverlap;
    computedOverlap = computedOverlap > maxOverlap ? maxOverlap : computedOverlap;

    for (int i = 0; i < cards.length; i++) {

      CardView card = cards[i];

      //Compute absolute position

      float x = i * card.getWidth() * (1 - computedOverlap);
      float y = 0;

      // card.setPosition(x, y);
      card.setDeckY(y);
      card.setDeckX(x);
      card.setDeckZ(i);
      card.setZIndex(i);


      client.addActor(card);
    }
  }

  public void addCards(DeckView client, CardView[] cards){
    for (int i = 0; i < cards.length; i++)
    {client.addActor(cards[i]);}
  }

  private float computeOverlap(float cardWidth, int n, float maxWidth) {


    return maxWidth / (cardWidth * n);
  }

  public float getMinOverlap() {
    return minOverlap;
  }

  public void setMinOverlap(float minOverlap) {
    this.minOverlap = minOverlap;
  }

  public float getMaxOverlap() {
    return maxOverlap;
  }

  public void setMaxOverlap(float maxOverlap) {
    this.maxOverlap = maxOverlap;
  }

  private static class CardBehavior extends ClickListener {

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor actor) {
      //actor.;
    }
  }
}