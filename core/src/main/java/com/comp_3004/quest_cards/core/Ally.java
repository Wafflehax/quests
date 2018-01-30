package com.comp_3004.quest_cards.core;

public class Ally extends Adventure {
  private int battlePoints;
  private int bids;

  //constructors
  public Ally(String n, int bp, int bd) {
    this.name = n;
    this.battlePoints = bp;
    this.bids = bd;
  }

  public void printCard() {
    System.out.printf("%-15s", this.name);
    System.out.printf("%-15s", this.battlePoints + "/" + this.bids);
    System.out.printf("%s\n", this.getClass().getSimpleName());
  }
}
