package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PlayerStatView extends Group {

  private Image flag_arrow;
  private Image flag_body;
  private Image deck;
  private Image shield;
  private Label shield_label;
  private Label deck_label;

  PlayerStatView(TextureAtlas atlas, Skin skin) {

    deck = new Image(atlas.findRegion(Assets.MiscSprites.DECK_ICON));
    deck_label = new Label("0x", skin);
    shield = new Image(atlas.findRegion(Assets.MiscSprites.SHIELD));
    shield_label = new Label("0x", skin);
    flag_arrow = new Image(atlas.findRegion(Assets.MiscSprites.FLAG_ARROW));
    flag_body = new Image(atlas.findRegion(Assets.MiscSprites.FLAG_BODY));

    addActor(flag_arrow);
    addActor(flag_body);
    addActor(deck);
    addActor(deck_label);
    addActor(shield);
    addActor(shield_label);

    pack();
  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
    setPosition(x, y);
    pack();
  }

  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x, y);
    Gdx.app.log("PlayerStatView position", "x: " + getX() + " y: " + getY());
  }

  private void pack() {

    final int IMG_WIDTH = 100;
    final int IMG_HEIGHT = 100;
    final int LABEL_WIDTH = 50;
    final int BODY_WIDTH = 300;

    setSize(500, 100);
    flag_arrow.setBounds(0, 0, 50, IMG_HEIGHT);
    flag_body.setBounds(50, 0, BODY_WIDTH, IMG_HEIGHT);

    deck.setSize(IMG_WIDTH, IMG_HEIGHT);
    deck.setPosition(flag_arrow.getWidth(), getHeight() / 2 - deck.getHeight() / 2);
    deck_label.pack();
    deck_label.setWidth(LABEL_WIDTH);
    deck_label.setPosition(deck.getX() + deck.getWidth(), getHeight() / 2 - deck_label.getHeight() / 2);


    shield.setSize(IMG_WIDTH, IMG_HEIGHT);
    shield.setPosition(deck_label.getX() + deck_label.getWidth(), 0);
    shield_label.pack();
    shield_label.setWidth(LABEL_WIDTH);
    shield_label.setBounds(shield.getX() + shield.getWidth(), 0, IMG_WIDTH, IMG_HEIGHT);

  }

  public void setColor(Color tint) {
    flag_arrow.setColor(tint);
    flag_body.setColor(tint);
  }

  public void setShields(int shields) {
    shield_label.setText(" x " + shields);
  }

  public void setCards(int cards) {
    deck_label.setText(" x " + cards);
  }
}
