package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.comp_3004.quest_cards.core.QuestCards;

public class GameScreen extends Group implements Disposable {

  //Parent

  private AssetManager manager;

  //Assets

  private TextureAtlas cardsAtlas;
  private TextureAtlas bgAtlas;// Backgrounds atlas

  public GameScreen() {
    setBounds(0,0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);
    //Load assets. These need to be unloaded at end of screen life cycle

    AssetManager manager = QuestCards.getAssetManager();
    manager.load("sprites/cards.atlas", TextureAtlas.class);
    manager.load("sprites/backgrounds.atlas", TextureAtlas.class);
    manager.finishLoading();

    cardsAtlas = manager.get("sprites/cards.atlas", TextureAtlas.class);
    bgAtlas = manager.get("sprites/backgrounds.atlas", TextureAtlas.class);

    //Set up

    Image background = new Image(bgAtlas.findRegion("game-board"));
    background.setBounds(0,0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);
    setSize(Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);
    addActor(background);
    //This code needs to go to children classes

    addActor(PlayerView.debugPlayerHand(manager));
  }

  public void dispose() {
    manager.unload("sprites/gameSprites.atlas");
    manager.unload("sprites/backgrounds.atlas");
  }
}
