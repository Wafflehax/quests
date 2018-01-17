package com.comp_3004.quest_cards.html;

import com.comp_3004.quest_cards.core.Game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GameHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new Game();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
