package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class JoinEventDialog extends BooleanDialog {

  private static final int WIDTH = 400;
  private static final int HEIGHT = 600;

  private CardView cardView;

  public JoinEventDialog(Skin skin) {
    super(skin);
    super.setBounds(1920 / 2 - WIDTH / 2, 1080 / 2 - HEIGHT / 2, WIDTH, HEIGHT);
    button_true.setText("Join");
    button_false.setText("Pass");
  }

  @Override
  public void setTitle(String title) {

  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
  }

  public void setCardView(CardView cardView) {
    this.cardView = cardView;
  }

  @Override
  public void draw(Batch batch, float alpha) {

    super.draw(batch, alpha);

    if (cardView != null) {
      cardView.getDrawable().draw(batch, 1920 / 2 - 366 / 2, 1080 / 2 - 500 / 2 + 25, 366, 500);
    }
  }
}
