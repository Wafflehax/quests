package com.comp_3004.quest_cards.core;

import aurelienribon.tweenengine.TweenAccessor;
import com.comp_3004.quest_cards.gui.CardView;
import com.comp_3004.quest_cards.gui.Config;


public class CardViewAccessor implements TweenAccessor<CardView> {

    public static final int FLIP = 0;
    public static final int FADE = 1;
    public static final int TRANSLATE = 2;



    @Override
    public int getValues(CardView target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case FLIP:
                returnValues[0] = target.getWidth();
                return 1;
            case FADE:
                returnValues[0] = target.getColor().a;
                return 1;
            case TRANSLATE:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 1;
            default:
                assert false; return -1;
        }
    }

    @Override
    public void setValues(CardView target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case FLIP:
                target.setWidth(newValues[0]);
                break;
            case FADE:
                target.setColor(1,1,1,newValues[0]);
                break;
            case TRANSLATE:
                target.setPosition(newValues[0], newValues[1]);
                System.out.println("(x,y) = ("+newValues[0]+","+newValues[1]+")");
            default:
                assert false;
                break;


        }
    }


}
