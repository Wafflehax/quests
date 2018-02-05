package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DrawingDeckView extends Group {

  private AssetManager manager;
  private Image discardPile;
  private Image drawingDeck;

  private DrawingDeckView() {
  }

  private void init(AssetManager manager) {
    TextureAtlas atlas = manager.get(Config.Assets.SPRITE_ATLAS, TextureAtlas.class);

    //Init "drawing" deck, i.e., the deck where players will draw cards from

    drawingDeck = new Image(atlas.findRegion("card_back"));
    drawingDeck.setBounds(0, 0, Config.CardView.CARD_WIDTH, Config.CardView.CARD_HEIGHT);
    addActor(drawingDeck);

    discardPile = new Image();

  }

  public void setDiscardPile(CardView card) {
    discardPile = card;
    discardPile.setBounds(
        drawingDeck.getY() + drawingDeck.getWidth() + Config.GameView.PADDING_HORIZONTAL,
        drawingDeck.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);
    addActor(discardPile);
  }

  static class Factory extends AbstractBuilder<DrawingDeckView> {

    public Factory(AssetManager manager) {
      super(manager);
      addDependency(Config.Assets.SPRITE_ATLAS, TextureAtlas.class);
    }

    @Override
    public DrawingDeckView build() {
      if (!isLoaded()) {
        throw newResourceNotLoadedException();
      }

      DrawingDeckView drawingDeckView = new DrawingDeckView();
      drawingDeckView.init(getAssetManager());
      return drawingDeckView;
    }
  }
}
