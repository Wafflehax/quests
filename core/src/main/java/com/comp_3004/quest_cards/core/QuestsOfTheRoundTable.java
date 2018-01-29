package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;

import org.apache.log4j.Logger;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class QuestsOfTheRoundTable implements ApplicationListener {
	Texture texture;
	SpriteBatch batch;
	float elapsed;
	Thread gameThr;
	public static Logger log = Logger.getLogger(QuestsOfTheRoundTable.class); //log4j logger

	@Override
	public void create () {
		texture = new Texture(Gdx.files.internal("bg-castle.jpg"));
		batch = new SpriteBatch();
		
		//some initial card/deck testing
		/*
		//adventure deck testing
		AdventureDeck advDeck = new AdventureDeck();
		//advDeck.printDeck();
		//advDeck.shuffle();
		//advDeck.printDeck();
		
		System.out.printf("Deck empty?: %s \n", advDeck.deckEmpty());
		System.out.printf("Discard empty?: %s \n",advDeck.discardEmpty());
		while (!advDeck.deckEmpty()) {
			//System.out.print("Deck top card: ");
			//storyDeck.deck.peek().printCard();	//need to make deck public to test
			advDeck.discardCard(advDeck.drawCard());
			//System.out.print("Discard top card: ");
			//storyDeck.discard.peek().printCard();	//need to make discard public to test
		}
		advDeck.shuffleDiscardIntoDeck();
		advDeck.printDeck();
		advDeck.printDiscard();
		*/
		
		
		//story deck testing
		/*
		StoryDeck storyDeck = new StoryDeck();
		//storyDeck.printDeck();
		//storyDeck.shuffle();
		//storyDeck.printDeck();
		System.out.printf("Deck empty?: %s \n", storyDeck.deckEmpty());
		System.out.printf("Discard empty?: %s \n",storyDeck.discardEmpty());
		while (!storyDeck.deckEmpty()) {
			//System.out.print("Deck top card: ");
			//storyDeck.deck.peek().printCard();	//need to make deck public to test
			storyDeck.discardCard(storyDeck.drawCard());
			//System.out.print("Discard top card: ");
			//storyDeck.discard.peek().printCard();	//need to make discard public to test
		}
		storyDeck.shuffleDiscardIntoDeck();
		storyDeck.printDeck();
		storyDeck.printDiscard();
		*/
		
		
		Controller cont = new Controller();
		// uncomment for tournament implementation
		cont.onCreate();
		
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
