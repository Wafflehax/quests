package com.comp_3004.quest_cards.core;

import com.badlogic.gdx.ApplicationAdapter;
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

public class Game extends ApplicationAdapter {
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	private SpriteBatch batch;
	private Texture img;
	private GameStateManager gsm;

	public static final String TITLE = "Quests of the Round Table";
	
	@Override 
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1,0,0,1);
		gsm.push(new MenuState(gsm));

	}


	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	

	@Override
	public void pause() {
		//TODO
	}

	@Override
	public void resume() {
		//TODO
		
	}



	
	
}
