package com.comp_3004.quest_cards.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comp_3004.quest_cards.core.Game;
import com.comp_3004.quest_cards.core.QuestCards;
//import com.comp_3004.quest_cards.gui.DragAndDropTest;   compile error

public class GameDesktop {
  public static void main(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.forceExit = true;
    //new LwjglApplication(new QuestCards(), config);
    //new LwjglApplication(new DragAndDropTest(), config);  compile error replaced with line under
    new LwjglApplication(new QuestCards(), config); 
  }
}
