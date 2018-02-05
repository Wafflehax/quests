package com.comp_3004.quest_cards.core;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.Tween;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashState extends State {
    private Texture splashTexture;
    private Sprite splash;
    private TweenManager tweenManager;

    public SplashState(GameStateManager gsm)
    {super(gsm);
        splashTexture = new Texture("splashScreen.png");
        splash = new Sprite(splashTexture);
        splash.setSize(Game.WIDTH,Game.HEIGHT);

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class,new SpriteAccessor());
        Tween.set(splash,SpriteAccessor.Alpha).target(0).start(tweenManager);
        Tween.to(splash,SpriteAccessor.Alpha, 4).target(1).repeatYoyo(1,2).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game)Gdx.app.getApplicationListener()).toMenu();
            }
        }).start(tweenManager);
        //Tween.to(splash,SpriteAccessor.Alpha, 4).target(0).delay(4).start(tweenManager);


        Gdx.app.log("SplashState(gsm)","Splash constructor runs");

    }

    @Override
    protected void handleInput() {


    }

    @Override
    public void update(float dt) {
        handleInput();
        this.tweenManager.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        this.tweenManager.update(Gdx.graphics.getDeltaTime());
        sb.begin();
        splash.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        splashTexture.dispose();

    }
}
