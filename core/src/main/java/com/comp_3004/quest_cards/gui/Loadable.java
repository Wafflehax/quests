package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.utils.Disposable;

public interface Loadable extends Disposable{

  void load();
  void dispose();
  boolean isLoaded();
}
