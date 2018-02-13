package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Dialog extends Group {

  TextButton button;
  Image background;

  public Dialog(String message, Skin skin) {

    setBounds(
        Config.VIRTUAL_WIDTH / 2 - Config.GameView.Modal.WIDTH / 2,
        Config.VIRTUAL_HEIGHT / 2 - Config.GameView.Modal.HEIGHT,
        Config.GameView.Modal.WIDTH,
        Config.GameView.Modal.HEIGHT);

    background = new Image(skin, "modal");
    background.setSize(getWidth(), getHeight());

    button = new TextButton(message, skin);
    button.setSize((float) (getWidth() * 0.2), 100);
    button.setPosition(getWidth() / 2 - button.getWidth() / 2, 10);

    addActor(background);
    addActor(button);
  }
}