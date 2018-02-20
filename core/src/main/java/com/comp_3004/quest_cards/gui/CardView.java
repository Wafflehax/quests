package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.comp_3004.quest_cards.core.GamePresenter;

public class CardView extends Image {

  String spriteId;
  //Bounds to check card-drop positioning
  private Rectangle CardBounds;
  private Rectangle SponsorCDZ;
  private Rectangle DiscardCDZ;
  private Rectangle InPlayCDZ;

  private float deckX, deckY;
  private int deckZ;
  private GamePresenter gamePresenter;
  private int cardID;
  private boolean inPlay;
  private TextureRegion picDisplay;

  //ADD AN ID PARAMETER SO THE VIEW CARRIES THE ID WITH IT
  public CardView(TextureRegion sprite, int ID) {
    super(sprite);
    picDisplay = sprite;
    cardID=ID;
    dragConfig(this);
    /*this.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Gdx.app.log("CARD:","Clicked!");
      }
    });*/
    setSize(Config.CardView.CARD_WIDTH, Config.CardView.CARD_HEIGHT);
    CardBounds=new Rectangle((int)getDeckX(), (int)getDeckY(), (int)getWidth(), (int)getHeight());
  }

  public void InPlayConfig(final CardView Card){}

  public void StoryConfig(final CardView card){}

  public void dragConfig(final CardView card)
  {card.addListener(new ClickListener() {
                      @Override
                      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        //if(active)
                        if (y > card.getY() + (card.getHeight() / 3)) {
                          card.moveBy(0, card.getHeight() / 3);
                          card.setZIndex(12);

                        }
                      }

                      @Override
                      public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        //RESETS THE CARD'S POSITIONING
                        if(CardBounds.overlaps(SponsorCDZ))
                        {
                          //DO NOTHING
                        }
                        else{
                          card.setX(card.getDeckX());
                          card.setY(card.getDeckY());
                          card.setZIndex(card.getDeckZ());
                        }

                      }

                    }



  );

    card.addListener(new DragListener() {
    @Override
    public void dragStart(InputEvent event, float x, float y, int pointer) {

    }

    public void drag(InputEvent event, float x, float y, int pointer) {
      //if(!active) DO NOTHING
      card.moveBy(x - card.getWidth() / 2, y - card.getHeight() / 2);
      CardBounds.set(card.getX()+card.getWidth()/3,card.getY()+card.getHeight()/3,card.getWidth()/3,card.getHeight()/3);

      if(CardBounds.overlaps(DiscardCDZ))
      {setColor(1,1,0,1);
      }

      else if(CardBounds.overlaps(InPlayCDZ))
      {setColor(0.6f,0.6f,1,1);}

      else if(CardBounds.overlaps(SponsorCDZ))
        {setColor(0.6f,1,0.6f,1);}


      else
        {setColor(1f,0.5f,0.5f,1);}

    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer) {
      //card.setX(card.getDeckX());
      //card.setY(card.getDeckY());
      //Gdx.app.log("DiscardCDZ? ",""+CardBounds.overlaps(DiscardCDZ));

      setColor(1,1,1,1);

      if(CardBounds.overlaps(InPlayCDZ))
      {//TODO: gamePresenter.getView().displayInPlay(card)
        //TODO: gamePresenter.getModel().playCard(int CardID)
        card.clear();
        Gdx.app.log("PlayCard CardID ",""+card.cardID);
      }

      else if(CardBounds.overlaps(SponsorCDZ))
      {//TODO: Implement Sponsor-PlayCard
        //TODO: Also Implement Bounds Based on Conditions... Omit SponsorCDZ if model.getcurrentTurn() != Sponsor
        card.setX(getX());
       card.setY(getY());
       //card.scaleBy(-0.5f);
       System.out.println("CardID = "+card.cardID);
      }

      else if(CardBounds.overlaps(DiscardCDZ))
      {gamePresenter.getView().displayAdventureDiscardPile(card.picDisplay);
        Gdx.app.log("Discard CardID ",""+card.cardID);
      card.clear();//Kill all Card-Listeners
      card.scaleBy(-1); //Hides Card: There Must be a better way of doing this...
      }

      else
      {card.setX(card.getDeckX());
        card.setY(card.getDeckY());}

    }




  });



  }

  //GETTERS
public float getDeckX(){return deckX;}
public float getDeckY(){return deckY;}
public int getDeckZ(){return deckZ;}
public int getCardID() {return cardID;}
public boolean getInPlay(){return inPlay;}
public Rectangle getCardBounds() {return CardBounds;}
public Rectangle getSponsorCDZ() {return SponsorCDZ;}

//SETTERS
public void setDeckX(float x){deckX=x;}
public void setDeckY(float y){deckY=y;}
public void setDeckZ(int z){deckZ = z;}
public void setInPlay(boolean inPlaySet){inPlay=inPlaySet;}

public void setGamePresenter(GamePresenter gamePresenter_in){gamePresenter=gamePresenter_in;}
public void setSponsorCDZ(Rectangle CDZ){SponsorCDZ = CDZ;}
public void setDiscardCDZ(Rectangle CDZ){DiscardCDZ = CDZ;}
public void setInPlayCDZ(Rectangle CDZ){InPlayCDZ = CDZ;}



}
