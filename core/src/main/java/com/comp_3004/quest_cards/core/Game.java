package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class Game implements ApplicationListener {
	Texture texture;
	SpriteBatch batch;
	float elapsed;

	@Override
	public void create () {
		texture = new Texture(Gdx.files.internal("bg-castle.jpg"));
		batch = new SpriteBatch();
		
		MainGameLogic gamelogic;
		
		AdventureDeck advDeck = new AdventureDeck();
		StoryDeck storyDeck = new StoryDeck();
		advDeck.printDeck();
		storyDeck.printDeck();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		batch.begin();
		

		//Draw stuff

		batch.draw(texture, 0, 0);
		batch.end();
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
