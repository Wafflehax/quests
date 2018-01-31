package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CardView extends Image {

  String spriteId;

  public CardView(TextureRegion sprite) {
    super(sprite);
    pack();
  }
}
