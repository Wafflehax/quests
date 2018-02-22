package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CardDropZone extends Image {
    private Rectangle bounds;
    public static int SponsorX = 50;
    public static int SponsorY = Config.CardView.CARD_HEIGHT+100;
    public static int SponsorWIDTH = 1500;
    public static int SponsorHEIGHT = Config.CardView.CARD_HEIGHT;
    //STATICS ARE DEFAULT FOR SPONSOR-CDZ


    public CardDropZone(Sprite sprite)
    {super(sprite);
    setBounds(SponsorX,SponsorY,SponsorWIDTH,SponsorHEIGHT);
        bounds=new Rectangle((int)getX(), (int)getY()-30, (int)getWidth(), (int)getHeight());
        this.setColor(1,1,1,0.2f);
    }

    public Rectangle getBounds(){return bounds;}

    public void setDropZoneBounds(int x, int y, int w, int h){bounds.set(x,y,w,h); this.setBounds(x,y,w,h);}
}
