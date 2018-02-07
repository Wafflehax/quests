package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.assets.AssetManager;

import java.util.Map;
import java.util.Set;

public class Loader {

  private Set<Map.Entry<String, Class>> dependencies;

  public Loader(AssetManager manager, Set<Map.Entry<String, Class>> dependencies){
    this.dependencies = dependencies;
  }

  public void load(AssetManager manager){

    for(Map.Entry<String, Class> dependency : dependencies){
      manager.load(dependency.getKey(), dependency.getValue());
    }
  }
  public void unload(AssetManager manager){
    for(Map.Entry<String, Class> dependency : dependencies){
      manager.unload(dependency.getKey());
    }
  }

  public boolean isLoaded(AssetManager manager) {

    if(manager == null)
      return false;

    boolean result = true;
    for(Map.Entry<String, Class> dependency : dependencies){
      result = result && manager.isLoaded(dependency.getKey());
    }

    return result;
  }
}
