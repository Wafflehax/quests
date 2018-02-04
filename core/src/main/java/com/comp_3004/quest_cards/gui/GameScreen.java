package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.comp_3004.quest_cards.core.QuestCards;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameScreen extends Group implements Disposable {

  //Parent

  private AssetManager manager;
  private Map<String, Class> dependencies;
  private PlayerView playerView;

  //Assets

  private List<Loadable> loadableActors;

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

    //Load assets

    loadAssets(QuestCards.getAssetManager());

    //Set up background

    setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);
    TextureAtlas bgAtlas = manager.get("sprites/backgrounds.atlas", TextureAtlas.class);
    initBackground(bgAtlas);

    //Instantiate children & add to group

    playerView = playerViewBuilder.build();
    addActor(playerView);
  }

  private void initBackground(TextureAtlas atlas){

    Image background = new Image(atlas.findRegion("game-board"));
    background.setBounds(0, 0, Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);
    setSize(Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);
    addActor(background);
  }

  private void loadAssets(AssetManager manager){

    //Load own dependecies

    for(Map.Entry<String, Class> dependecy : dependencies.entrySet()){
      manager.load(dependecy.getKey(), dependecy.getValue());
    }

    //Load children dependecies

    for(Loadable loadable : loadableActors){
      loadable.load();
    }

    manager.finishLoading();
  }

  @Override
  public void dispose() {
    for(Loadable loadable : loadableActors){
      loadable.dispose();
    }
  }
}
