package com.comp_3004.quest_cards.core;

import java.awt.event.ActionListener;

public class GameView {

	private GameModel model;
    
    /** Constructor */
	GameView(GameModel m) {
		System.out.println("Game view Ctor");
        model = m;
        
    }	
	
	//Listener Template
	public void addSomeListener(ActionListener l) {
		//doSomethingButton.addActionListener(l);
	}
}
