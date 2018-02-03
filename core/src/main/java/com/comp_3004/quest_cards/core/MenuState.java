package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;


public class MenuState extends State {
    private Texture background;
    private Texture play;




    public MenuState(GameStateManager gsm)
    {super(gsm);
    background = new Texture("bg-castle.jpg");
    play = new Texture("NewGame.png");
    }


    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()) //If NewGame is touched
        {
            Vector2 tmp=new Vector2(Gdx.input.getX(),Gdx.input.getY());
            //camera.unproject(tmp);
            Rectangle textureBounds=new Rectangle((Game.WIDTH/2-500/2),(Game.HEIGHT/2-300/2),500,300);

            if(textureBounds.contains(tmp.x,tmp.y))
            {
                gsm.set(new PlayState(gsm));
                dispose();
            }
        }

        
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0,Game.WIDTH,Game.HEIGHT);
        sb.draw(play,(Game.WIDTH/2-500/2),(Game.HEIGHT/2-300/2),500,300);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        play.dispose();
    }


}
