package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.assets.AssetManager;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractBuilder<T> implements Loadable {

  private Map<String, Class> dependencies;
  private Loader loader;
  private AssetManager manager;

  public AbstractBuilder(AssetManager manager) {
    dependencies = new LinkedHashMap<String, Class>();
    this.manager = manager;
    loader = new Loader(manager, dependencies.entrySet());
  }

  public void addDependency(String name, Class type) {
    dependencies.put(name, type);
  }

  public void removeDependecy(String name, Class type) {
    dependencies.remove(name);
  }

  public abstract T build();

  public RuntimeException newResourceNotLoadedException(){
    return new RuntimeException("Attempting to build an object with dependencies without first loading the required assets");
  }

  @Override
  public boolean isLoaded() {
    return loader.isLoaded(manager);
  }

  @Override
  public void load() {
    loader.load(manager);
  }

  @Override
  public void dispose() {
    loader.unload(manager);
  }

  public AssetManager getAssetManager(){
    return manager;
  }
}
