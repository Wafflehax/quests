package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class MainMenu implements InputProcessor, Screen {

  MainMenuState menuState;
  MainMenuView menuView;

  @Override
  public boolean keyDown(int i) {
    return false;
  }

  @Override
  public boolean keyUp(int i) {
    return false;
  }

  @Override
  public boolean keyTyped(char c) {
    return false;
  }

  @Override
  public boolean touchDown(int i, int i1, int i2, int i3) {
    return false;
  }

  @Override
  public boolean touchUp(int i, int i1, int i2, int i3) {
    return false;
  }

  @Override
  public boolean touchDragged(int i, int i1, int i2) {
    return false;
  }

  @Override
  public boolean mouseMoved(int i, int i1) {
    return false;
  }

  @Override
  public boolean scrolled(int i) {
    return false;
  }

  @Override
  public void render(float v) {

  }

  @Override
  public void resize(int i, int i1) {

  }

  @Override
  public void show() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {

  }
}
