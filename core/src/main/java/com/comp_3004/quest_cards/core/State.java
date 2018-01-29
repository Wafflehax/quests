package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
protected OrthographicCamera cam;
protected Vector3 mouse;
protected GameStateManager gsm;

protected State(GameStateManager gsm_in){
    this.gsm = gsm_in;
    cam = new OrthographicCamera();
    mouse = new Vector3();
    }

protected abstract void handleInput();
protected abstract void update(float dt);
protected abstract void render(SpriteBatch sb);
}
