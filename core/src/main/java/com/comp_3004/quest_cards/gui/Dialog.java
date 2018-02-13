package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Dialog extends Group {

  TextButton button;

  private Dialog(){
  }

  public Dialog(String message, TextButton.TextButtonStyle buttonStyle) {

    button = new TextButton(message, buttonStyle);
    button.
  }


  public static class Builder{

    public Dialog build(){

      TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
      style.
    }

  }

}