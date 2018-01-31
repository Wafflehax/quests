package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

public class GameScreen extends Table implements Disposable {

  //Parent

  private QuestCards parent;

  //Assets

  private TextureAtlas cardsAtlas;
  private TextureAtlas bgAtlas;// Backgrounds atlas

  public GameScreen(QuestCards parent) {
    this.parent = parent;
    //Load assets. These need to be unloaded at end of screen life cycle

    AssetManager manager = parent.getAssetManager();
    manager.load("sprites/cards.atlas", TextureAtlas.class);
    manager.load("sprites/backgrounds.atlas", TextureAtlas.class);
    manager.finishLoading();

    cardsAtlas = manager.get("sprites/cards.atlas", TextureAtlas.class);
    bgAtlas = manager.get("sprites/backgrounds.atlas", TextureAtlas.class);

    //Set background

    setBackground(new Image(bgAtlas.findRegion("game-board")).getDrawable());
    setSize(getPrefWidth(), getPrefHeight());

    //This code needs to go to children classes

    bottom().padBottom(10).add(PlayerCards.debugPlayerHand(manager));
  }

  @Override
  public void dispose() {
    AssetManager manager = parent.getAssetManager();
    manager.unload("sprites/gameSprites.atlas");
    manager.unload("sprites/backgrounds.atlas");
  }
}
