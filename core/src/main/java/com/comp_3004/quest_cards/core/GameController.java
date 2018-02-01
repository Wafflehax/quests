package com.comp_3004.quest_cards.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.core.GameModel.gamestates;



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
	
	public void onCreate() {
	
		modelthr = new Thread(new Runnable() {
			@Override
			public void run() {
				
				//model = new GameModel();
				model.startGame(4);
			}
		});
		modelthr.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //wait for model to init
		Thread ui = new Thread(new Runnable() {
			
			@Override
			public void run() {
				startTextToButton();
			}
		});
		ui.start();
		
		fakeViewRender();
		
		
	}
	
	public void  fakeViewRender() {
		boolean run = true;
		while(run && model.getTournament().getRunGameLoop()) {
			
			if(model.state == gamestates.ASKING_PARTICIPATION) {
				System.out.println("Would " + model.getcurrentTurn().getName() + " like to play "
						+ " " + model.getTournament().getCurrentTour().getName() + "?\n");
				model.state = gamestates.WAITING;
			}else if(model.state == gamestates.PLAYER_TURN) {
				log.info("Player : " + model.getcurrentTurn().getName() + "Pick your cards which will be face down and press done\n");
				model.state = gamestates.WAITING;
			}else if(model.state == gamestates.DISCARD_HAND_CARD) {
				//TODO: enable buttons?, display message to discard a card
				model.state = gamestates.WAITING;
				log.info("Please enter a card to discard then press done");
			}
			
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void participateYes() {
		model.getTournament().setTournamentParticupation(true);
	}
	
	public void participateNo() {
		model.getTournament().setTournamentParticupation(false);
	}
	
	
	// For now assuming button numbers correspond to card in player.playerHandCards
	public void pressedHand0() {
		model.getTournament().cardPressed(0);
	}
	public void pressedHand1() {
		model.getTournament().cardPressed(1);
	}
	public void pressedHand2() {
		model.getTournament().cardPressed(2);
	}
	public void pressedHand3() {
		model.getTournament().cardPressed(3);
	}
	public void pressedHand4() {
		model.getTournament().cardPressed(4);
	}
	public void pressedHand5() {
		model.getTournament().cardPressed(5);
	}
	public void pressedHand6() {
		model.getTournament().cardPressed(6);
	}
	public void pressedHand7() {
		model.getTournament().cardPressed(7);
	}
	public void pressedHand8() {
		model.getTournament().cardPressed(8);
	}
	public void pressedHand9() {
		model.getTournament().cardPressed(9);
	}
	public void pressedHand10() {
		model.getTournament().cardPressed(10);
	}
	public void pressedHand11() {
		model.getTournament().cardPressed(11);
	}
	public void pressedHand12() {
		model.getTournament().cardPressed(12);
	}
	
	public void doneTurn() {
		model.lock.wake();
	}
	
	
	private void startTextToButton() {
		Scanner scanner = new Scanner(System.in);
		String action;
		boolean run = true;
		String info = "Please write an action to emulate, " 
				+ "b1 is to press button 1. exit to exit. buttons 0-11\n Type print to see your cards" 
							+ " and done to finish turn. \ninfo for help";
		while(run) {
			action = scanner.nextLine();
			if(action.equals("b0")) {
				pressedHand0();log.info("b0");
			}
			else if(action.equals("b1")) {
				pressedHand1();log.info("b1");
			}
			else if(action.equals("b2")) {
				pressedHand2();log.info("b2");
			}
			else if(action.equals("b3")) {
				pressedHand3();log.info("b3");
			}
			else if(action.equals("b4")) {
				pressedHand4();log.info("b4");
			}
			else if(action.equals("b5")) {
				pressedHand5();log.info("b5");
			}
			else if(action.equals("b6")) {
				pressedHand6();log.info("b6");
			}
			else if(action.equals("b7")) {
				pressedHand7();log.info("b7");
			}
			else if(action.equals("b8")) {
				pressedHand8();log.info("b8");
			}
			else if(action.equals("b9")) {
				pressedHand9();log.info("b9");
			}
			else if(action.equals("b10")) {
				pressedHand10();log.info("b10");
			}
			else if(action.equals("b11")) {
				pressedHand11();log.info("b11");
			}else if(action.equals("b12")) {
				pressedHand12();log.info("b12");
			}
			else if(action.equalsIgnoreCase("yes") || action.equalsIgnoreCase("y")) {
				participateYes();log.info("Participation: Yes");
			}
			else if(action.equalsIgnoreCase("no") || action.equalsIgnoreCase("n")) {
				participateNo();log.info("Participation: No");
			}
			else if(action.equals("exit")) {
				run = false; model.getTournament().setRunGameLoop(false);
			}
			else if(action.equalsIgnoreCase("done")) {
				doneTurn();
			}
			else if(action.equalsIgnoreCase("print")) {
				printHand();
			}else if(action.equalsIgnoreCase("info")) {
				System.out.println(info);
			}
		}
		scanner.close();
	}
	
	public void printHand() {
		Player player = model.getcurrentTurn();
		LinkedList<AdventureCard> hand = player.playerHandCards;
		LinkedList<AdventureCard> active = player.playerActiveCards;
		String output = "\nPlayer: " + player.getName() + "\n";
		output += "Current playable cards:\n-------------------------\n";
		for(int i = 0; i < hand.size(); i++) {
			output += "Card(" + i + ") :" + hand.get(i).getName() + "\n";
		}
		output += "-------------------------\n";
		output += "Current Players Active cards:\n-------------------------\n";
		for(int i = 0; i < active.size(); i++) {
			output += "Card(" + i + ") :" + active.get(i).getName() + "\n";
		}
		output += "-------------------------\n";
		log.info(output);
	}
	
	
}