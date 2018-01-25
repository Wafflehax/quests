package com.comp_3004.quest_cards.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.comp_3004.quest_cards.core.QuestsOfTheRoundTable;

public class QuestsOfTheRoundtableDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new QuestsOfTheRoundTable(), config);
	}
}
