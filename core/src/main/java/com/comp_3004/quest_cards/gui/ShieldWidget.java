package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ShieldWidget extends Container {

  Label label;

  private int shields;

  public ShieldWidget(BitmapFont font) {
    shields = 0;
  }

  public void setShields(int n) {
    this.shields = n;
  }

  @Override
  public void draw(Batch batch, float alpha) {

  }

}
