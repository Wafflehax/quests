package com.comp_3004.quest_cards.gui;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.comp_3004.quest_cards.core.CardViewAccessor;
import com.comp_3004.quest_cards.core.ImageAccessor;

import java.util.LinkedList;
import java.util.function.Consumer;

import static com.comp_3004.quest_cards.gui.Assets.Strings.Buttons.NEXT_TURN;

public class GameView extends Group {


  public PlayerView playerView;

  //Widgets
  public TweenManager AnimationManager;
  public Image background;
  public Image storyDeck;
  public Image storyDeckDiscardPile;
  public Image adventureDeckDiscardPile;
  public Image adventureDeck;
  public Image hoverDraw;
  public CardDropZone SponsorCDZ;
  public CardDropZone DiscardCDZ;
  public CardDropZone InPlayCDZ;
  public TextButton nextTurnButton;
  public PlayerStatView[] players;
  public LinkedList<CardView> InPlay;
  public LinkedList<CardView> QuestStages;
  private AnnouncementDialog announcementDialog;
  private BooleanDialog questionDialog;
  private JoinEventDialog joinEventDialog;
  private EventAnnouncementDialog eventAnnouncementDialog;
  private Skin skin;
  private TextureAtlas sprites;

  public GameView(AssetManager manager) {

    AnimationManager = new TweenManager(); //NOT YET IN USE
    this.skin = manager.get(Assets.SKIN);
    this.sprites = manager.get(Assets.GAME_SPRITES, TextureAtlas.class);

    //Set up layout
    background = new Image();
    storyDeck = new Image();
    storyDeckDiscardPile = new Image();
    adventureDeck = new Image();
    adventureDeckDiscardPile = new Image();
    hoverDraw = new Image();
    playerView = new PlayerView();
    announcementDialog = new AnnouncementDialog(skin);
    questionDialog = new BooleanDialog(skin);
    eventAnnouncementDialog = new EventAnnouncementDialog(skin);
    joinEventDialog = new JoinEventDialog(skin);
    nextTurnButton = new TextButton(NEXT_TURN, skin);
    players = new PlayerStatView[4];

    //Init and Orient the CDZs
    SponsorCDZ = new CardDropZone(new Sprite(new Texture("DropZones/SponsorCDZ.png")));
    DiscardCDZ = new CardDropZone(new Sprite(new Texture("DropZones/SponsorCDZ.png")));
    InPlayCDZ = new CardDropZone(new Sprite(new Texture("DropZones/InPlayCDZ.png")));

    InPlay = new LinkedList<>();
    QuestStages = new LinkedList<>();

    //Add widgets to table

    addActor(background);
    addActor(SponsorCDZ);
    addActor(DiscardCDZ);
    addActor(storyDeck);
    addActor(storyDeckDiscardPile);
    addActor(adventureDeck);
    addActor(adventureDeckDiscardPile);
    addActor(hoverDraw);
    addActor(playerView);


    playerView.addActorAt(1, InPlayCDZ);
  }

  @Override
  public void setBounds(float x, float y, float width, float height) {

    super.setBounds(x, y, width, height);

    //Resize widgets
    background.setBounds(x, y, width, height);

    playerView.setBounds(
        getWidth() / 2 - Config.PlayerView.WIDTH / 2,
        Config.GameView.PADDING_VERTICAL,
        Config.PlayerView.WIDTH,
        Config.PlayerView.HEIGHT);

    storyDeck.setBounds(
        Config.GameView.PADDING_HORIZONTAL,
        Config.VIRTUAL_HEIGHT - Config.GameView.PADDING_VERTICAL - Config.CardView.CARD_HEIGHT,
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    storyDeckDiscardPile.setBounds(
        storyDeck.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL,
        storyDeck.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    adventureDeck.setBounds(
        storyDeckDiscardPile.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL,
        storyDeckDiscardPile.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT);

    adventureDeckDiscardPile.setBounds(
        adventureDeck.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL,
        storyDeckDiscardPile.getY(),
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT
    );

    InPlayCDZ.setDropZoneBounds(Config.VIRTUAL_WIDTH / 2 + 40, 20, Config.CardView.CARD_WIDTH * 3 - 20, Config.CardView.CARD_HEIGHT);

    hoverDraw.setBounds(
        Config.VIRTUAL_WIDTH - Config.CardView.CARD_WIDTH - 150,
        storyDeckDiscardPile.getY() - 50,
        Config.CardView.CARD_WIDTH,
        Config.CardView.CARD_HEIGHT
    );

    DiscardCDZ.setDropZoneBounds((int) adventureDeck.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL + 70,
        (int) storyDeckDiscardPile.getY() + 150,
        10,
        50); DiscardCDZ.setVisible(false);

    InPlayCDZ.setDropZoneBounds(Config.VIRTUAL_WIDTH / 2 + 40, 20, Config.CardView.CARD_WIDTH * 3 - 20, Config.CardView.CARD_HEIGHT);

    nextTurnButton.setBounds(Config.VIRTUAL_WIDTH - 200, 360 + 10, 200, 50);

    PlayerColor[] colors = PlayerColor.values();
    for (int i = 0; i < players.length; i++) {

      PlayerStatView currentPlayer = players[i] = new PlayerStatView(sprites, skin);
      addActor(currentPlayer);
      currentPlayer.setColor(colors[i].color());
      currentPlayer.setPosition(Config.VIRTUAL_WIDTH - currentPlayer.getWidth(), Config.PlayerStatView.Y + i * (currentPlayer.getHeight() + Config.GameView.PADDING_VERTICAL));
    }
  }

  public GameView displayHero(TextureRegion hero) {

    playerView.displayHero(hero);
    announcementDialog.setSize(Config.GameView.Modal.WIDTH, Config.GameView.Modal.HEIGHT);
    announcementDialog.setCenterPosition(getWidth() / 2, getHeight() / 2);
    questionDialog.setSize(Config.GameView.Modal.WIDTH, Config.GameView.Modal.HEIGHT);
    questionDialog.setCenterPosition(getWidth() / 2, getHeight() / 2);
    return this;
  }

  public void displayPlayerHand(CardView[] cards) {
    playerView.displayPlayerHand(cards);
  }

  public void displayShieldNumber(int n) {

    return;
  }

  public void displayStoryDeck(TextureRegion storyDeck) {

    this.storyDeck.setDrawable(new TextureRegionDrawable(storyDeck));
  }

  public void displayAdventureDiscardPile(TextureRegion card) {

    adventureDeckDiscardPile.setDrawable(new TextureRegionDrawable(card));
  }

  public void displayStoryDiscardPile(TextureRegion card) {

    storyDeckDiscardPile.setDrawable(new TextureRegionDrawable(card));
  }

  public void displayDrawCardAnimation(CardView card) {
    Tween.registerAccessor(CardView.class, new CardViewAccessor());

    Timeline.createSequence()
        .push(Tween.set(card, CardViewAccessor.FADE).target(0))
        //.push(Tween.set(card,CardViewAccessor.TRANSLATE).target((storyDeckDiscardPile.getX() + Config.CardView.CARD_WIDTH + Config.GameView.PADDING_HORIZONTAL),
        //        storyDeckDiscardPile.getY())) TODO: Figure out why Y is being set to 0 after update
        .push(Tween.set(card, CardViewAccessor.TRANSLATE).target(0, 0))

        .pushPause(0.5f)

        .push(Tween.to(card, CardViewAccessor.FADE, 0.5f).target(1))
        .pushPause(0.5f)
        .push(Tween.to(card, CardViewAccessor.TRANSLATE, 0.75f).target(card.getDeckX(), card.getDeckY()).delay(0.2f))
        .start(AnimationManager);


    // Tween.to(card, CardViewAccessor.FLIP,0.2f).target(Config.CardView.CARD_WIDTH).start(AnimationManager);

    //AnimationManager.update(Gdx.graphics.getDeltaTime());

    return;
  }

  public void displayAdventureDeck(TextureRegion adventureDeck) {
    this.adventureDeck.setDrawable(new TextureRegionDrawable(adventureDeck));
  }

  public void discardCard(CardView card) {
    displayAdventureDiscardPile(card.getPicDisplay());
    card.remove(); //Removes CardView from its parent
  }


  //ADDED METHODS
  //There might be a cleaner way to do this, but this works fairly well
  //The following methods work in conjunction with the CardView class in order to allow the user
  //To represent their different card placements with appropriate visual reactions.

  public void addToPlay(CardView card) {
    card.clear();//Kill listeners
    card.scaleBy(-0.5f);
    InPlay.add(card);
    card.HoverDrawConfig(card);
    displayCardsInPlay(InPlay);
  }

  public void addToQuestStages(CardView card) {
    card.clear();
    card.scaleBy(-0.5f);
    QuestStages.add(card);
    card.HoverDrawConfig(card);
    displayCardsQuestStages(QuestStages);
    //
  }

  public void displayCardsInPlay(LinkedList<CardView> InPlay) {
    float x0 = InPlayCDZ.getX();
    float y0 = InPlayCDZ.getY() + InPlayCDZ.getHeight() / 2 - 30;

    for (int i = 0; i < InPlay.size(); i++) {
      CardView setThis = InPlay.get(i);
      setThis.setY(y0);
      setThis.setDeckY(y0);
      setThis.setDeckZ(i);
      setThis.setZIndex(i);
      setThis.setX(x0);
      setThis.setDeckX(x0);
      x0 = (x0 + 50);

      if (i == 11) {
        y0 = y0 - InPlayCDZ.getHeight() / 2;
        x0 = InPlayCDZ.getX();
        setThis.setY(y0);
        setThis.setDeckY(y0);
        setThis.setDeckZ(i);
        setThis.setZIndex(i);
        setThis.setX(x0);
        setThis.setDeckX(x0);
      }
    }
  }

  public void cardWipe(){InPlay.clear(); QuestStages.clear();}

  public void displayNextTurnButton(final Runnable action, boolean hideAfter) {

    if (nextTurnButton.getListeners().size > 1) {
      nextTurnButton.getListeners().pop();
    }

    nextTurnButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        action.run();
        if (hideAfter) {
          nextTurnButton.remove();
        }
      }
    });


    addActor(nextTurnButton);
  }

  public void hideNextTurnButton(){nextTurnButton.remove();}

  public void displayCardsQuestStages(LinkedList<CardView> QuestStages) {
    float x0 = SponsorCDZ.getX();
    float y0 = SponsorCDZ.getY() + SponsorCDZ.getHeight() / 2 - 40;

    for (int i = 0; i < QuestStages.size(); i++) {
      CardView setThis = QuestStages.get(i);
      setThis.setY(y0);
      setThis.setDeckY(y0);
      setThis.setDeckZ(i);
      setThis.setZIndex(i);
      setThis.setX(x0);
      setThis.setDeckX(x0);
      x0 = (x0 + 100);

      if (i == 10) {
        y0 = y0 - SponsorCDZ.getHeight() / 2;
        x0 = SponsorCDZ.getX();
      }
    }
  }

  public void displayHoverDraw(CardView card) {
    hoverDraw.setDrawable(new TextureRegionDrawable(card.getPicDisplay()));
    Tween.registerAccessor(Image.class, new ImageAccessor());
    Tween.set(hoverDraw, ImageAccessor.FADE).target(0).start(this.AnimationManager);
    Tween.to(hoverDraw, ImageAccessor.FADE, 0.2f).target(1).start(this.AnimationManager);


  }

  public void hideHoverDraw() {
    Tween.registerAccessor(Image.class, new ImageAccessor());
    Tween.to(hoverDraw, ImageAccessor.FADE, 0.2f).target(0).start(this.AnimationManager);
    //hoverDraw.setVisible(false);
  }

  public void showSponsorDropZone() {
    SponsorCDZ.setDropZoneBounds(CardDropZone.SponsorX, CardDropZone.SponsorY, CardDropZone.SponsorWIDTH, CardDropZone.SponsorHEIGHT);
  }

  public void hideSponsorDropZone() {
    SponsorCDZ.setDropZoneBounds(0, 0, 0, 0);
  }

  public void setBackground(TextureRegion background) {

    this.background.setDrawable(new TextureRegionDrawable(background));
  }


  //ENDOF ADDED

  public void setPlayerViewBackground(TextureRegion background) {

    playerView.setBackground(background);
  }

  public void displayAnnouncementDialog(String title, String message, final Consumer<Boolean> action) {

    announcementDialog.setTitle(title);
    announcementDialog.setMessage(message);
    announcementDialog.setActionListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
    	  	announcementDialog.remove();
    	  	action.accept(true);
      }
    });

    addActor(announcementDialog);

  }

  public void displayQuestionDialog(String title, String message, final Consumer<Boolean> action) {

    questionDialog.setTitle(title);
    questionDialog.setMessage(message);

    questionDialog.setActionTrue(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        questionDialog.remove();
        action.accept(true);
      }
    });

    questionDialog.setActionTrue(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        questionDialog.remove();
        action.accept(false);
      }
    });

    addActor(questionDialog);
  }

  public void displayEventAnnouncement(CardView event, Consumer<Boolean> action) {

    eventAnnouncementDialog.setCardView(event);
    eventAnnouncementDialog.setActionListener(new ClickListener() {

      @Override
      public void clicked(InputEvent event, float x, float y) {
        action.accept(true);
        eventAnnouncementDialog.remove();
      }
    });

    addActor(eventAnnouncementDialog);

  }

  public void displayJoinEventDialog(String title, String message, CardView cardView, Consumer<Boolean> action) {

    joinEventDialog.setTitle(title);
    joinEventDialog.setMessage(message);
    joinEventDialog.setCardView(cardView);
    joinEventDialog.setActionTrue(new ClickListener() {

      @Override
      public void clicked(InputEvent e, float x, float y) {
        action.accept(true);
        joinEventDialog.remove();
      }
    });

    joinEventDialog.setActionFalse(new ClickListener() {
      @Override
      public void clicked(InputEvent e, float x, float y) {
        action.accept(false);
        joinEventDialog.remove();
      }
    });

    addActor(joinEventDialog);
  }

  public void setPlayerShields(int playerNumber, int shields) {
    players[playerNumber].setShields(shields);
  }

  public void setPlayerCards(int playerNumber, int cards) {
    players[playerNumber].setCards(cards);
  }

  public enum PlayerColor {
    player1(Color.YELLOW),
    Player2(Color.BLUE),
    player3(Color.RED),
    player4(Color.GREEN);

    Color color;

    PlayerColor(Color color) {
      this.color = color;
    }

    public Color color() {
      return color;
    }
  }
}
