package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/*For the time being, this coding setup is a bit clunky, which is why the GameStateManager class
* is currently being implemented.  The game logic is going to deal with a stack of states, to decrease
* code complexity and increase management efficiency.  If you run the application from the GameDesktop.java
* class, you should be able to see a simple introduction.  The exit-button and new-game-button are able to make
* proper transitions between states currently.  As stated above, this functionality will be transitioned over to the
* GameStateManager to increase efficiency and readability.
* */

public class Game implements ApplicationListener {
	private MainMenuScreen mainScreen;
	private MainGameScreen gameScreen;
	private boolean forceExit = false;


	
	@Override 
	public void create () {mainScreen = new MainMenuScreen();
	gameScreen = new MainGameScreen();
	mainScreen.create();

	}
	
	@Override
	public void resize (int width, int height) {
		mainScreen.resize(width,height);
	}

	@Override
	public void render () {
		mainScreen.render();
		if (mainScreen.getExitStatus())
			Gdx.app.exit();
		if (mainScreen.getGameStatus())
			{gameScreen.create();
				mainScreen.setGameStatus(false);
				mainScreen.dispose();
			}
		if(gameScreen.active)
		{gameScreen.render();}
	}

	@Override
	public void dispose() {
		//mainScreen.dispose();
	}
	
	

	@Override
	public void pause() {
		mainScreen.pause();
		
	}

	@Override
	public void resume() {
		mainScreen.resume();
		
	}


	//Extra Methods
	public boolean getExitStatus(){return forceExit;}

	public void setExitStatus(boolean status){forceExit=status;}
	
	public static void main(String[] args) {
        System.out.println("Test"); // Display the string.
       
    }
	
	
}
