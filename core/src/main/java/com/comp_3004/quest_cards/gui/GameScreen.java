package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.core.QuestCards;

public class GameScreen extends Table implements Disposable {

  private final QuestCards parent;


  //Subsystems

  private PlayerView playerView;
  private DrawingDeckView storyDeck;

  public GameScreen(QuestCards parent) {

    this.parent = parent;
  }

  public void init() {
    //Load assets


    AssetManager manager = parent.getAssetManager();

    manager.load(Assets.GAME_BACKGROUNDS, TextureAtlas.class);
    manager.load(Assets.GAME_SPRITES, TextureAtlas.class);
    manager.finishLoading();

    //Set up background

    setLayoutEnabled(false);
    setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);
    TextureAtlas sprites = manager.get(Config.Assets.SPRITE_ATLAS);
    TextureAtlas bgAtlas = manager.get("sprites/backgrounds.atlas", TextureAtlas.class);
    setBackground(new Image(bgAtlas.findRegion("game-board")).getDrawable());

    //Init player view

    playerView = new PlayerView(this);
    playerView.setBounds(
        getWidth() / 2 - Config.PlayerView.WIDTH / 2,
        Config.GameView.PADDDING_VERTICAL,
        Config.PlayerView.WIDTH,
        Config.PlayerView.HEIGHT);

    addActor(playerView);

    //Init story deck

    storyDeck = new DrawingDeckView(manager);
    storyDeck.setBounds(
        Config.GameView.PADDING_HORIZONTAL,
        getHeight() - Config.CardView.CARD_HEIGHT - Config.PlayerView.PADDING_VERTICAL,
        Config.GameView.DRAWING_DECKS_WIDTH,
        Config.CardView.CARD_HEIGHT);

    CardView cards = new CardView(sprites.findRegion("E_Plague"));

    storyDeck.setDiscardPile(cards);

    addActor(storyDeck);
  }

  @Override
  public void dispose() {
    manager.unload(Assets.GAME_SPRITES);
    manager.unload(Assets.GAME_BACKGROUNDS);
  }

  public TextureAtlas getBackgrounds() {

    return manager.get(Assets.GAME_BACKGROUNDS);
  }

  public TextureAtlas getSprites() {
    return manager.get(Assets.GAME_SPRITES);
  }

  public static class TestGameView{

    public static PlayerView debug(QuestCards parent){

      GameScreen gameScreen = new GameScreen();
    }

  }
}
