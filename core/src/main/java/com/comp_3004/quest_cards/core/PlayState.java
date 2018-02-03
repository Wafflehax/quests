package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State {

    private Texture playTexture;

    public PlayState(GameStateManager gsm)
    {super(gsm);
    playTexture = new Texture("temp_play.png");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(playTexture,0,0,Game.WIDTH,Game.HEIGHT);
        sb.end();
    }

    @Override
    public void dispose() {
        //newGame.dispose();
        //background.dispose();
    }

}
