package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CardDropZone extends Image {
    private Rectangle bounds;
    public static int X = 50;
    public static int Y = Config.CardView.CARD_HEIGHT+150;
    public static int WIDTH = 1500;
    public static int HEIGHT = Config.CardView.CARD_HEIGHT;


    public CardDropZone(Sprite sprite)
    {super(sprite);
    setBounds(X,Y,WIDTH,HEIGHT);
        bounds=new Rectangle((int)getX(), (int)getY()-30, (int)getWidth(), (int)getHeight());
        this.setColor(1,1,1,0.2f);
    }

    public Rectangle getBounds(){return bounds;}
}
