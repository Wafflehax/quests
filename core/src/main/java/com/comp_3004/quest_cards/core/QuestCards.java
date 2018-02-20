package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.comp_3004.quest_cards.gui.Config;

import java.util.HashMap;
import java.util.Map;

public class QuestCards implements ApplicationListener {

  //Assets
  private AssetManager manager;
  private SpriteBatch batch;
  private Skin uiSkin;
  private Music _menuSong; //Asyncronous Menu Music

  //Stage

  private Camera camera;
  private Viewport viewport;
  private Stage stage;


  //Game Screens

  private Map<String, Group> gameStates;






  @Override
  public void create() {

    //Init basic modules. These need to be disposed

    manager = new AssetManager();
    batch = new SpriteBatch();
    stage = new Stage();


    //Load UI skin: Todo: Make this useful. It does nothing at the moment

    manager.load("skins/uiskin.json", Skin.class);
    manager.load("music/MainMenu.wav",Music.class); //Asyncronous MenuMusic
    //manager.load("skins/buttonSkin.json", Skin.class);
    manager.finishLoading();
    uiSkin = manager.get("skins/uiskin.json", Skin.class);
    _menuSong = manager.get("music/MainMenu.wav",Music.class);


    //Stage & camera set up

    camera = new OrthographicCamera();
    viewport = new FitViewport(Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT, camera);
    stage.setViewport(viewport);
    camera.position.set(Config.VIRTUAL_WIDTH / 2, Config.VIRTUAL_HEIGHT / 2, 0);
    camera.update();

    Gdx.input.setInputProcessor(stage);

    //Init game screen & set as current screen


    gameStates = new HashMap<String, Group>();
    gameStates.put("mainMenu", new MenuPresenter(this));
    gameStates.put("preMenu", new SplashPresenter(this));
    gameStates.put("rulesDisplay", new RulesPresenter(this));
    gameStates.put("mainGame", new GamePresenter(this));

    //Switch screen

    //stage.addActor(gameStates.get("mainGame"));
    ScreenAssign("mainGame");

    // Temp Tour testing
    //TourTest test = new TourTest();
    //test.test();
    
    //Create game MVC
    //GameModel model = new GameModel();
    //GameView view = new GameView(model);
    //GameController controller = new GameController(model, view);

    //testing
    //in reality, this method would be triggered from an ActionListener in view when user clicks "New Game"
    //controller.startGame(4);
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

  public AssetManager getAssetManager() {

    return manager;
  }

  public void ScreenAssign(String State)
  {stage.clear();
    stage.addActor(gameStates.get(State));

    System.out.println(stage.getActors() + " " + State + " " + Gdx.input.getInputProcessor());

    if (State.compareTo("mainGame")==0)
    { Group temp = gameStates.get("rulesDisplay");
      ((RulesPresenter) temp).dispose();
      Gdx.input.setInputProcessor(stage);
    }

  }

  public void SetMenuMusic(boolean ON)
  {if (ON)
  {_menuSong.setLooping(ON);
  _menuSong.setVolume(0.5f);
  _menuSong.play();}

  else
  {_menuSong.stop();
  _menuSong.dispose();}
  }


}