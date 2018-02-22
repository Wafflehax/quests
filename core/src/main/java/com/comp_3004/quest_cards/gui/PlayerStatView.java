package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PlayerStatView extends Group {

  private TextureRegion flag_arrow;
  private TextureRegion flag_body;
  private TextureRegion deck;
  private TextureRegion shield;
  private Label shield_label;
  private Label deck_label;

  PlayerStatView(TextureAtlas atlas, Skin skin) {

    deck = atlas.findRegion(Assets.MiscSprites.DECK_ICON);
    shield = atlas.findRegion(Assets.MiscSprites.DECK_ICON);
    deck_label = new Label("0x", skin);
    shield_label = new Label("0x", skin);
    flag_arrow = atlas.findRegion(Assets.MiscSprites.FLAG_ARROW);
    flag_body = atlas.findRegion(Assets.MiscSprites.FLAG_BODY);
  }


  @Override
  public void draw(Batch batch, float parentAlpha) {

    batch.setColor(Color.GREEN);
    batch.draw(
        flag_arrow,
        0,
        0,
        flag_arrow.getRegionWidth(),
        flag_arrow.getRegionHeight());

    batch.draw(
        flag_body,
        flag_arrow.getRegionWidth(),
        0,
        flag_body.getRegionWidth(),
        flag_body.getRegionHeight());

    batch.setColor(Color.WHITE);

    float x_pos = flag_arrow.getRegionWidth();
    batch.draw(shield, x_pos, 0, 100,100);
    x_pos += shield.getRegionWidth();

    shield_label.setCenterPosition(x_pos + shield_label.getWidth() / 2, flag_arrow.getRegionHeight() / 2);
    shield_label.draw(batch, parentAlpha);
    deck_label.draw(batch, parentAlpha);

  }

  @Override
  public void setBounds(float x, float y, float width, float height) {
    super.setBounds(x, y, width, height);
  }
}
