package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.comp_3004.quest_cards.gui.Config.PlayerView.*;

public class PlayerView extends Group{

  private Map<String, Class> dependencies;
  private PlayerView instance;
  private DeckView playerAdventureCards;
  private CardView hero;
  private Image shields;
  private Image background;
  private AssetManager manager;

  private PlayerView(){
    dependencies = new LinkedHashMap<String, Class>();
    dependencies.put(Config.Assets.BACKGROUND_ATLAS, TextureAtlas.class);
    dependencies.put(Config.Assets.SPRITE_ATLAS, TextureAtlas.class);
  }

  private void init(AssetManager manager){

    this.manager = manager;
    TextureAtlas backgrounds = manager.get(Config.Assets.BACKGROUND_ATLAS, TextureAtlas.class);
    TextureAtlas sprites = manager.get(Config.Assets.SPRITE_ATLAS, TextureAtlas.class);

    //Init
    setSize(WIDTH, HEIGHT);
    setPosition(5,5);

    //Init background

    initBackground(backgrounds);

    //Init widgets

    initAdventureDeck();
    initShields(sprites);
    debugCards();
  }

  private void initBackground(TextureAtlas atlas){

    background = new Image(atlas.findRegion("player-area"));
    background.setBounds(getX(), getY(), getWidth(), getHeight());
    addActor(background);
  }

  private void initAdventureDeck(){

    DeckView.DisplayStrategy deckDisplay = new SpillingDeckStrategy(
            ADVENTURE_CARDS_MIN_OVERLAP,
            ADVENTURE_CARDS_MAX_OVERLAP);

    playerAdventureCards = new DeckView(deckDisplay);
    playerAdventureCards.setSize(ADVENTURE_SPILLDECK_WIDTH, Config.CardView.CARD_HEIGHT);
    playerAdventureCards.setPosition(getX() + PADDING_HORIZONTAL, getY() + getHeight() / 2 - Config.CardView.CARD_HEIGHT / 2);
    playerAdventureCards.setColor(Color.GREEN);
    addActor(playerAdventureCards);
  }

  private void initShields(TextureAtlas atlas){

    shields = new Image(atlas.findRegion("shield"));
    shields.setSize(Config.PlayerView.SHIELD_WIDTH, Config.PlayerView.SHIELD_HEIGHT);

    float x = getWidth()  - Config.PlayerView.PADDING_HORIZONTAL - shields.getWidth();
    float y = getHeight() + Config.PlayerView.PADDING_VERTICAL;

    shields.setPosition(x,y);

    System.out.printf("Shields x: %f\n", x);
    System.out.printf("Shields y: %f\n", y);
    System.out.printf("Shields width: %f\n", shields.getWidth());
    System.out.printf("Shields height: %f\n", shields.getHeight());
    addActor(shields);
  }

  public PlayerView setHero(CardView hero){

    //Replace old hero

    removeActor(this.hero);
    this.hero = hero;

    //Add to group

    addActor(hero);
    hero.setPosition(getX() + getWidth() - hero.getWidth() - Config.PlayerView.PADDING_HORIZONTAL, getY() + Config.PlayerView.PADDING_VERTICAL);
    return this;
  }

  public void debugCards(){

    TextureAtlas atlas = manager.get(Config.Assets.SPRITE_ATLAS, TextureAtlas.class);

    CardView[] cards = new CardView[12];

    for(int i = 0; i < cards.length; i++){
      cards[i] =  new CardView(atlas.findRegion("A_King_Arthur"));
    }

    setCards(cards);
    setHero(new CardView(atlas.findRegion("R_Champion_Knight")));
  }

  public PlayerView setCards(CardView[] cards) {
    playerAdventureCards.setCards(cards);
    return this;
  }

  public static class Builder implements Loadable {

    private Loader loader;
    private AssetManager manager;
    private boolean isBuilt = false;
    private static final PlayerView INSTANCE = new PlayerView();

    public Builder(AssetManager manager){
      this.manager = manager;
      loader = new Loader(manager, INSTANCE.dependencies.entrySet());
    }

    public PlayerView build(){

      if(!loader.isLoaded(manager)){
        throw new RuntimeException("Required assets have not been loaded");
      }

      if(isBuilt){
        return INSTANCE;
      }

      INSTANCE.init(manager);
      isBuilt = true;
      return INSTANCE;
    }

    @Override
    public void load() {
      loader.load(manager);
    }

    @Override
    public void dispose() {
      loader.unload(manager);
    }
  }

  public static class TestPlayerView{

    public static boolean testBackgroundBounds(PlayerView playerView){

      System.out.printf("Background width: %f\n", playerView.background.getWidth());
      System.out.printf("Background height: %f\n", playerView.background.getHeight());
      System.out.printf("Background z-index: %d\n", playerView.background.getZIndex());
      System.out.printf("Background X: %f\n", playerView.background.getX());
      System.out.printf("Background y: %f\n", playerView.background.getY());
      return false;
    }

    public static boolean testSelfBounds(PlayerView playerView){

      System.out.printf("Player view width: %f\n", playerView.getWidth());
      System.out.printf("Player view height: %f\n", playerView.getHeight());
      System.out.printf("Player view z-index: %d\n", playerView.getZIndex());
      System.out.printf("Player view X: %f\n", playerView.getX());
      System.out.printf("Player view y: %f\n", playerView.getY());
      return false;
    }

    public static boolean testDeckViewBounds(PlayerView playerView){

      System.out.printf("Adventure card area width: %f\n", playerView.playerAdventureCards.getWidth());
      System.out.printf("Adventure card area height: %f\n", playerView.playerAdventureCards.getHeight());
      System.out.printf("Adventure card area z-index: %d\n", playerView.playerAdventureCards.getZIndex());
      System.out.printf("Adventure card area X: %f\n", playerView.playerAdventureCards.getX());
      System.out.printf("Adventure card area y: %f\n", playerView.playerAdventureCards.getY());

      return false;
    }

    public static boolean runTests(){

      TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sprites/backgrounds.atlas"));



      atlas.dispose();

      return false;
    }

  }
}

