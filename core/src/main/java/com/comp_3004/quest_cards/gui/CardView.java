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
  private Rectangle DropZoneBounds;
  private Rectangle CardBounds;
  private float deckX, deckY;
  private int deckZ;
  private GamePresenter gamePresenter;
  private int cardID;

  //ADD AN ID PARAMETER SO THE VIEW CARRIES THE ID WITH IT
  public CardView(TextureRegion sprite, int ID) {
    super(sprite);
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

  public void dragConfig(final CardView card)
  {card.addListener(new ClickListener() {
                      @Override
                      public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        if (y > card.getY() + (card.getHeight() / 3)) {
                          card.moveBy(0, card.getHeight() / 3);
                          card.setZIndex(12);

                        }
                      }

                      @Override
                      public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        //RESETS THE CARD'S POSITIONING
                        if(CardBounds.overlaps(DropZoneBounds))
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
      card.moveBy(x - card.getWidth() / 2, y - card.getHeight() / 2);
      CardBounds.set(card.getX(),card.getY(),card.getWidth(),card.getHeight());

      if(CardBounds.overlaps(DropZoneBounds))
        {setColor(0.6f,1,0.6f,1);}

      else
        {setColor(1f,0.5f,0.5f,1);}

    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer) {
      //card.setX(card.getDeckX());
      //card.setY(card.getDeckY());


      setColor(1,1,1,1);
      if(CardBounds.overlaps(DropZoneBounds))
      {card.setX(getX());
       card.setY(getY());
       System.out.println("PlayCard = " + gamePresenter.playCard(card.cardID) +"; CardID = "+card.cardID);
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
public Rectangle getCardBounds() {return CardBounds;}
public Rectangle getDropZoneBounds() {return DropZoneBounds;}

//SETTERS
public void setDeckX(float x){deckX=x;}
public void setDeckY(float y){deckY=y;}
public void setDeckZ(int z){deckZ = z;}
public void setDropZoneBounds(Rectangle CDZ){this.DropZoneBounds = CDZ;}
public void setGamePresenter(GamePresenter gamePresenter_in){gamePresenter=gamePresenter_in;}



}
