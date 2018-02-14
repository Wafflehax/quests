package com.comp_3004.quest_cards.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.comp_3004.quest_cards.core.Game;
import com.comp_3004.quest_cards.core.QuestCards;
<<<<<<< HEAD
//import com.comp_3004.quest_cards.gui.DragAndDropTest;   compile error
=======
//import com.comp_3004.quest_cards.gui.DragAndDropTest;
>>>>>>> GamePresenter

public class GameDesktop {
  public static void main(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.forceExit = true;
<<<<<<< HEAD
    config.width = Game.WIDTH;
    config.height = Game.HEIGHT;
    config.title = Game.TITLE;
    //new LwjglApplication(new QuestCards(), config);
    //new LwjglApplication(new DragAndDropTest(), config);  compile error replaced with line under
    new LwjglApplication(new QuestCards(), config); 
=======
    config.width = 1920;
    config.height = 1080;
    config.title = "Quests of the Round Table";
    new LwjglApplication(new QuestCards(), config);
    //new LwjglApplication(new Game(), config);
>>>>>>> GamePresenter
  }
}
