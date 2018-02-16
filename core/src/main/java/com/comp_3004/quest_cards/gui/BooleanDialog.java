package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BooleanDialog extends Dialog {
  Button button_true;
  Button button_false;

  public BooleanDialog(Skin skin) {
    super("", skin);

    button_true = new TextButton(Assets.Strings.Buttons.TRUE, skin);
    button_false = new TextButton(Assets.Strings.Buttons.FALSE, skin);
    button(button_true);
    button(button_false);

  }

  public void setActionTrue(ClickListener listener) {

    button_true.clearListeners();
    button_true.addListener(listener);

  }

  public void setActionFalse(ClickListener listener){
    button_false.clearListeners();
    button_false.addListener(listener);
  }
}
