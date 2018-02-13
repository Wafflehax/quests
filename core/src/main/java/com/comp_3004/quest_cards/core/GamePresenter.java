package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comp_3004.quest_cards.gui.Assets;
import com.comp_3004.quest_cards.gui.CardView;
import com.comp_3004.quest_cards.gui.Config;
import com.comp_3004.quest_cards.gui.GameView;
import com.sun.corba.se.pept.transport.EventHandler;
import org.apache.log4j.Logger;

public class GamePresenter extends Group {

  private QuestCards parent;
  private GameModel model;
  private GameView view;

  TextureAtlas sprites;
  TextureAtlas backgrounds;

  private AssetManager manager;
  private Skin skin;


  public GamePresenter(QuestCards parent) {

    this.parent = parent;
    manager = parent.getAssetManager();
    loadAssets();

    view = initGameView();
    addActor(view);

    model = new GameModel();
  }

  public void loadAssets() {

    AssetManager manager = parent.getAssetManager();
    manager.load(Assets.GAME_BACKGROUNDS, TextureAtlas.class);
    manager.load(Assets.GAME_SPRITES, TextureAtlas.class);
    manager.load(Assets.SKIN, Skin.class);
    manager.finishLoading();

    skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
    sprites = manager.get(Assets.GAME_SPRITES, TextureAtlas.class);
    backgrounds = manager.get(Assets.GAME_BACKGROUNDS, TextureAtlas.class);
  }


  public GameView initGameView() {

    GameView view = new GameView(skin);
    view.setShieldsTexture(sprites.findRegion("shield"));
    view.setBackground(backgrounds.findRegion("game_board"));
    view.setPlayerViewBackground(backgrounds.findRegion("player_area"));
    view.setBounds(0,0 , Config.VIRTUAL_WIDTH, Config.VIRTUAL_HEIGHT);

    CardView[] cards = new CardView[12];
    for (int i = 0; i < cards.length; i++) {

      cards[i] = new CardView(sprites.findRegion("A_King_Arthur"));
    }

    //Hero area

    view.displayPlayerHand(cards);
    view.displayHero(sprites.findRegion("R_Champion_Knight"));

    //Drawing decks

    view.displayStoryDeck(sprites.findRegion(Assets.Cards.CARD_BACK));
    view.displayStoryDiscardPile(sprites.findRegion(Assets.Cards.Story.KINGS_RECOGNITION));
    view.displayAdventureDeck(sprites.findRegion(Assets.Cards.CARD_BACK));
    view.displayAdventureDiscardPile(sprites.findRegion(Assets.Cards.Allies.KING_PELLINORE));

    return view;
  }
}