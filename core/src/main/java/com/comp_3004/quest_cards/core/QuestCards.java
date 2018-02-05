package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.comp_3004.quest_cards.gui.GameScreen;

import java.util.HashMap;
import java.util.Map;

public class QuestCards implements ApplicationListener {

  //"World" coordinates

  public static final int VIRTUAL_WIDTH = 1920;
  public static final int VIRTUAL_HEIGHT = 1080;

  //Assets

  private AssetManager manager;
  private SpriteBatch batch;
  private Skin uiSkin;


  //Stage

  private Camera camera;
  private Viewport viewport;
  private Stage stage;


  //Game Screens

  private Map<String, Group> gameScreens;

  public static AssetManager getAssetManager() {

    return ((QuestCards) Gdx.app.getApplicationListener()).manager;
  }

  @Override
  public void create() {

    //Init basic modules. These need to be disposed

    manager = new AssetManager();
    batch = new SpriteBatch();
    stage = new Stage();

    //Load UI skin

    manager.load("skins/uiskin.json", Skin.class);
    manager.finishLoading();
    uiSkin = manager.get("skins/uiskin.json", Skin.class);

    //Stage & camera set up

    camera = new OrthographicCamera();
    viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
    stage.setViewport(viewport);
    camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
    camera.update();

    //Init game screen & set as current screen

    gameScreens = new HashMap<String, Group>();
    gameScreens.put("mainGame", new GameScreen());
    stage.addActor(gameScreens.get("mainGame"));
    
    GameModel model = new GameModel();   
    GameController gameController = new GameController(model);
    model.eventTest();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height);
  }

  @Override
  public void render() {

    //Clear screen

    Gdx.gl.glClearColor(0, 0, 0, 0);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    //Update actors

    stage.act(Gdx.graphics.getDeltaTime());

    //Draw

    stage.draw();
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void dispose() {
    stage.dispose();
    batch.dispose();
    manager.dispose();
  }
}