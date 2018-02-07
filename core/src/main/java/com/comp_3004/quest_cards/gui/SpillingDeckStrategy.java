package com.comp_3004.quest_cards.gui;

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

    System.out.printf("Discard pile x: %f width: %f\n", client.getX(), client.getWidth());

    float computedOverlap = computeOverlap(cards[0].getWidth(), cards.length, client.getWidth());
    System.out.printf("Computed overlap: %f\n", computedOverlap);
    computedOverlap = computedOverlap < minOverlap ? minOverlap : computedOverlap;
    computedOverlap = computedOverlap > maxOverlap ? maxOverlap : computedOverlap;

    for (int i = 0; i < cards.length; i++) {

      CardView card = cards[i];

      //Compute absolute position

      float x = i * card.getWidth() * (1 - computedOverlap);
      float y = 0;

      card.setPosition(x, y);
      card.setZIndex(i);

      System.out.printf("Placing adveture card at (%f, %f)\n", card.getX(), card.getY());

      client.addActor(card);
    }
  }

  private float computeOverlap(float cardWidth, int n, float maxWidth) {

    System.out.printf("CardWidth: %f. Card #: %d. MaxWidth: %f\n", cardWidth, n, maxWidth);

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
}