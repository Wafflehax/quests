package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;

import static com.comp_3004.quest_cards.gui.Config.PlayerStatView.FLAG_ARROW_WIDTH;

public class PlayerStatView extends Group {

  private Image flag_arrow;
  private Image flag_body;
  private Image deck;
  private Image shield;
  private Label shield_label;
  private Label deck_label;
  private CardView [] cards;

  private static final int IMG_WIDTH = 50;
  private static final int IMG_HEIGHT = 50;
  private static final int LABEL_WIDTH = 40;
  private static final int BODY_WIDTH = 2*IMG_WIDTH + 2*IMG_HEIGHT;
  private Player player;
  private GamePresenter presenter;

  PlayerStatView(TextureAtlas atlas, Skin skin) {

    deck = new Image(atlas.findRegion(Assets.MiscSprites.DECK_ICON));
    deck_label = new Label("x0", skin);
    shield = new Image(atlas.findRegion(Assets.MiscSprites.SHIELD));
    shield_label = new Label("x0", skin);
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
  }

  private void pack() {

    setSize(FLAG_ARROW_WIDTH + BODY_WIDTH, IMG_HEIGHT);
    flag_arrow.setBounds(0, 0, FLAG_ARROW_WIDTH, IMG_HEIGHT);
    flag_body.setBounds(FLAG_ARROW_WIDTH, 0, BODY_WIDTH, IMG_HEIGHT);

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
  public void setPlayer(Player player_in){this.player = player_in;}
  public void setPresenter(GamePresenter pres_in){this.presenter = pres_in;}
  public void setCards(int cards) {
    deck_label.setText(" x " + cards);
  }

  public void playerConfig(){
    cards = new CardView[player.getActive().size()];

    for(int i=0;i<cards.length;i++)
    {cards[i] = new CardView(presenter.sprites.findRegion(presenter.CardAssetMap.get(player.getActive().get(i).getName())),0);
    cards[i].HoverDrawConfig(cards[i]);
    }

    flag_arrow.addListener(new ClickListener(){
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        presenter.getView().displayStatViewCards(cards);
        presenter.getView().statViewBG.setVisible(true);
      }

      @Override
      public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        presenter.getView().hideStatViewCards(cards);
        presenter.getView().statViewBG.setVisible(false);

      }
    });








  }
}

