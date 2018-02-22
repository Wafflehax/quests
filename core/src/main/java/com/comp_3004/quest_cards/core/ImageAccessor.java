package com.comp_3004.quest_cards.core;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.comp_3004.quest_cards.gui.Config;


public class ImageAccessor implements TweenAccessor<Image> {

    public static final int FLIP = 0;
    public static final int FADE = 1;



    @Override
    public int getValues(Image target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case FLIP:
                returnValues[0] = target.getWidth();
                return 1;
            case FADE:
                returnValues[0] = target.getColor().a;
                return 1;
            default:
                assert false; return -1;
        }
    }

    @Override
    public void setValues(Image target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case FLIP:
                target.setWidth(newValues[0]);
                break;
            case FADE:
                target.setColor(1,1,1,newValues[0]);
                break;
            default:
                assert false;
                break;


        }
    }


}
