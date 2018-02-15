package com.comp_3004.quest_cards.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CardDropZone extends Image {
    private Rectangle bounds;

    public CardDropZone(Sprite sprite)
    {super(sprite);
    setBounds(50,Config.CardView.CARD_HEIGHT+150,1000,Config.CardView.CARD_HEIGHT+10);
        bounds=new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
        this.setColor(1,1,1,0.2f);
    }

    public Rectangle getBounds(){return bounds;}
}
