package com.comp_3004.quest_cards.core;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class SplashPresenter extends Group {
    private QuestCards parent;
    private Texture splashTexture;
    private Sprite splash;
    private TweenManager tweenManager;

    public SplashPresenter(QuestCards parent)
    {this.parent = parent;

        splashTexture = new Texture("splashScreen.png");
        splash = new Sprite(splashTexture);
        splash.setSize(Game.WIDTH,Game.HEIGHT);

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class,new SpriteAccessor());
        Tween.set(splash,SpriteAccessor.Alpha).target(0).start(tweenManager);
        Tween.to(splash,SpriteAccessor.Alpha, 4).target(1).repeatYoyo(1,2).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                dispose();
                ((QuestCards)Gdx.app.getApplicationListener()).SetMenuMusic(true);
                ((QuestCards)Gdx.app.getApplicationListener()).ScreenAssign("mainMenu");
            }
        }).start(tweenManager);
        //Tween.to(splash,SpriteAccessor.Alpha, 4).target(0).delay(4).start(tweenManager);


        Gdx.app.log("SplashPresenter(gsm)","Splash constructor runs");

    }


    public void draw(Batch batch, float alpha) {
        this.tweenManager.update(Gdx.graphics.getDeltaTime());
        splash.draw(batch);

    }

    public void dispose() {
        splashTexture.dispose();

    }
}
