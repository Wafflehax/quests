package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AnnouncementDialog extends Dialog {

  Button button;

  public AnnouncementDialog(Skin skin) {
    super("", skin);

    button = new TextButton(Assets.Strings.Buttons.ACKNOWLEDGE, skin);
    button(button);

  }

  public void setActionListener(ClickListener listener) {

    button.clearListeners();
    button.addListener(listener);

  }
}
