package com.comp_3004.quest_cards.core;

import java.util.Scanner;

import com.comp_3004.quest_cards.core.Game.states;

public class Controller{

	Game model;
	Thread modelthr;
	public void onCreate() {
	
		modelthr = new Thread(new Runnable() {
			@Override
			public void run() {
				
				model = new Game();
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
		while(run && model.runGameLoop) {
			
			if(model.state == states.ASKINGPARTICIPATION) {
				System.out.println("Would " + model.getcurrentTurn().getName() + " like to play ");
				model.currStory.printCard();
				System.out.print("?");
				model.state = states.WAITING;
			}
			
			
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	public void participateYes() {
		model.getcurrentTurn().participateTournament = true;
		model.lock.wake();
	}
	
	public void participateNo() {
		model.getcurrentTurn().participateTournament = false;
		model.lock.wake();
	}
	
	public void pressedHand0() {
		
	}
	public void pressedHand1() {
		
	}
	public void pressedHand2() {
		
	}
	public void pressedHand3() {
		
	}
	public void pressedHand4() {
		
	}
	public void pressedHand5() {
		
	}
	public void pressedHand6() {
		
	}
	public void pressedHand7() {
		
	}
	public void pressedHand8() {
		
	}
	public void pressedHand9() {
		
	}
	public void pressedHand10() {
		
	}
	public void pressedHand11() {
		
	}
	
	
	private void startTextToButton() {
		Scanner scanner = new Scanner(System.in);
		String action;
		boolean run = true;
		while(run) {
			System.out.println("Please write an action to emulate, b1 is to press button 1. exit to exit. buttons 0-11");
			action = scanner.nextLine();
			if(action.equals("b0"))
				pressedHand0();
			else if(action.equals("b1"))
				pressedHand1();
			else if(action.equals("b2"))
				pressedHand1();
			else if(action.equals("b3"))
				pressedHand1();
			else if(action.equals("b4"))
				pressedHand1();
			else if(action.equals("b5"))
				pressedHand1();
			else if(action.equals("b6"))
				pressedHand1();
			else if(action.equals("b7"))
				pressedHand1();
			else if(action.equals("b8"))
				pressedHand1();
			else if(action.equals("b9"))
				pressedHand1();
			else if(action.equals("b10"))
				pressedHand1();
			else if(action.equals("b11"))
				pressedHand1();
			else if(action.equalsIgnoreCase("yes") || action.equalsIgnoreCase("y"))
				participateYes();
			else if(action.equalsIgnoreCase("no") || action.equalsIgnoreCase("n"))
				participateNo();
			else if(action.equals("b11"))
				pressedHand1();
			else if(action.equals("exit")) {
				run = false;
			}
				
			
		}
		
		
		
		
	}
	
	
	
}