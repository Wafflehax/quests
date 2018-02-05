package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.comp_3004.quest_cards.core.QuestCards;
import sun.management.counter.perf.PerfLongArrayCounter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameScreen extends Table implements Disposable {

  //Assets & Dependencies

  private AssetManager manager;
  private Map<String, Class> dependencies;
  private List<Loadable> loadableActors;

  //Subsystems

  private PlayerView playerView;
  private DrawingDeckView storyDeck;

  public GameScreen() {
    loadableActors = new ArrayList<Loadable>();
    manager = QuestCards.getAssetManager();

    //Register dependencies

    dependencies = new LinkedHashMap<String, Class>();
    dependencies.put(Config.Assets.BACKGROUND_ATLAS, TextureAtlas.class);
    dependencies.put(Config.Assets.SPRITE_ATLAS, TextureAtlas.class);

    //Init builders

    PlayerView.Builder playerViewBuilder = new PlayerView.Builder(manager);
    loadableActors.add(playerViewBuilder);
    DrawingDeckView.Factory deckFactory = new DrawingDeckView.Factory(manager);
    loadableActors.add(deckFactory);

    //Load assets

    loadAssets(QuestCards.getAssetManager());

    //Set up background

    setLayoutEnabled(false);
    setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);
    TextureAtlas sprites = manager.get(Config.Assets.SPRITE_ATLAS);
    TextureAtlas bgAtlas = manager.get("sprites/backgrounds.atlas", TextureAtlas.class);
    setBackground(new Image(bgAtlas.findRegion("game-board")).getDrawable());

    //Init player view

    playerViewBuilder.setBounds(
        getWidth() / 2 - Config.PlayerView.WIDTH / 2,
        Config.GameView.PADDDING_VERTICAL,
        Config.PlayerView.WIDTH,
        Config.PlayerView.HEIGHT);

    playerView = playerViewBuilder.build();
    addActor(playerView);

    //Init story deck

    storyDeck = deckFactory.build();
    storyDeck.setBounds(
        Config.GameView.PADDING_HORIZONTAL,
        getHeight() - Config.CardView.CARD_HEIGHT - Config.PlayerView.PADDING_VERTICAL,
        Config.GameView.DRAWING_DECKS_WIDTH,
        Config.CardView.CARD_HEIGHT);

    CardView cards = new CardView(sprites.findRegion("E_Plague"));

    storyDeck.setDiscardPile(cards);

    addActor(storyDeck);
  }

  private void loadAssets(AssetManager manager) {

    //Load own dependencies

    for (Map.Entry<String, Class> dependency : dependencies.entrySet()) {
      manager.load(dependency.getKey(), dependency.getValue());
    }

    //Load children dependencies

    for (Loadable loadable : loadableActors) {
      loadable.load();
    }

    manager.finishLoading();
  }

  @Override
  public void dispose() {
    for (Loadable loadable : loadableActors) {
      loadable.dispose();
    }
  }
}
