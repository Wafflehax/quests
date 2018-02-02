package com.comp_3004.quest_cards.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.log4j.Logger;

public class GameController{
	GameModel model;
	GameView view;
	Thread modelthr;
	static Logger log = Logger.getLogger(GameController.class); //log4j logger
	
	public GameController(GameModel m, GameView v) {
		System.out.println("Game controller Ctor");
		this.model = m;
		this.view = v;
		
		//... Add listeners to the view.
        view.addSomeListener(new SomeListener());
	}
	
	//Listener Template
	class SomeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userInput = "";
            try {
                //userInput = view.getUserInput();
               // model.doSomethingWith(userInput);
               // view.doSomethingToUpdateView(m.model.getSomeValue());
                
            } catch (NumberFormatException nfex) {
                //view.showError("Bad input: '" + userInput + "'");
            }
        }
	}
	
	//user requests
	public void startGame(int numPlayers) {
		model.startGame(numPlayers);
	}
}