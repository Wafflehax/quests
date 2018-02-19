package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AnnouncementDialog extends Dialog {

  Button button;
  Label text;

  public AnnouncementDialog(Skin skin) {
    super("", skin);

    //Init message label and action button, and keep references to both

    text = new Label("", skin);
    text(text);

    button = new TextButton(Assets.Strings.Buttons.ACKNOWLEDGE, skin);
    button(button);

  }

  public void setActionListener(ClickListener listener) {

    button.clearListeners();
    button.addListener(listener);

  }

  public void setMessage(String message) {
    text.setText(message);
  }
}
