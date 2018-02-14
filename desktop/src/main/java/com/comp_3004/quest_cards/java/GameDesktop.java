package com.comp_3004.quest_cards.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comp_3004.quest_cards.core.Game;
import com.comp_3004.quest_cards.core.QuestCards;
//import com.comp_3004.quest_cards.gui.DragAndDropTest;

public class GameDesktop {
  public static void main(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.forceExit = true;
    config.width = 1920;
    config.height = 1080;
    config.title = "Quests of the Round Table";
    new LwjglApplication(new QuestCards(), config);
    //new LwjglApplication(new Game(), config);
  }
}
