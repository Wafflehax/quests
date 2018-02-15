package com.comp_3004.quest_cards.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comp_3004.quest_cards.core.QuestCards;
import com.comp_3004.quest_cards.gui.Config;

public class GameDesktop {
  public static void main(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.forceExit = true;

    config.width = Config.VIRTUAL_WIDTH;
    config.height = Config.VIRTUAL_HEIGHT;
    config.title = "Quests of the Round Table";
    new LwjglApplication(new QuestCards(), config);

  }
}
