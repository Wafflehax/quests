package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.comp_3004.quest_cards.core.QuestCards;

public class GameView extends Table implements Disposable {

  private QuestCards parent;

  //Widgets

  private PlayerView playerView;
  private Image storyDeck;

  private GameView() {

    setLayoutEnabled(false);
    setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);

    storyDeck = new Image();
    storyDeck.setBounds(
        getHeight() - Config.CardView.CARD_HEIGHT,
        Config.GameView.PADDING_HORIZONTAL,
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);
  }

  public GameView(QuestCards parent) {
    this.parent = parent;

    //Load assets

    loadAssets();

    //Set up layout

    setLayoutEnabled(false);
    setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);

    //Init widgets

    playerView = configurePlayerView(new PlayerView());
    storyDeck = initStoryDeck();

    //Add widgets to table

    addActor(playerView);
    addActor(storyDeck);
  }

  //Todo: move this to controller class

  private void loadAssets() {

    AssetManager manager = parent.getAssetManager();
    manager.load(Assets.GAME_BACKGROUNDS, TextureAtlas.class);
    manager.load(Assets.GAME_SPRITES, TextureAtlas.class);
    manager.finishLoading();
  }

  private PlayerView configurePlayerView(PlayerView playerView) {
    playerView.setBounds(
        getWidth() / 2 - Config.PlayerView.WIDTH / 2,
        Config.GameView.PADDDING_VERTICAL,
        Config.PlayerView.WIDTH,
        Config.PlayerView.HEIGHT);

    return playerView;
  }

  private Image initStoryDeck() {

    Image storyDeck = new Image();
    storyDeck.setBounds(
        getHeight() - Config.CardView.CARD_HEIGHT,
        Config.GameView.PADDING_HORIZONTAL,
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    return storyDeck;
  }

  @Override
  public void dispose() {

    AssetManager manager = parent.getAssetManager();
    manager.unload(Assets.GAME_SPRITES);
    manager.unload(Assets.GAME_BACKGROUNDS);
  }

  public GameView setStoryDeckView(CardView storyDeckView) {
    storyDeck.setDrawable(storyDeckView.getDrawable());
    return this;
  }

  public GameView setPlayerHand(CardView[] cards) {
    playerView.setCards(cards);
    return this;
  }

  public GameView setShieldsTexture(TextureRegion texture) {
    playerView.setShieldsTexture(texture);
    return this;
  }

  public GameView setHero(CardView hero) {
    playerView.setHero(hero);
    return this;
  }

  public static class TestGameView {

    public static GameView debug(QuestCards parent) {

      GameView gameView = new GameView();
      gameView.parent = parent;
      PlayerView gameScreen = PlayerView.PlayerViewTester.createTestableInstance(gameView);

      return null;
    }

  }
}
