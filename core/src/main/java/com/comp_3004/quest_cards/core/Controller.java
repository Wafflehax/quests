package com.comp_3004.quest_cards.core;

public class Controller{
	
	//add view later //view contains model
	Game model;
	Thread modelthr;
	public void onCreate() {
	
		modelthr = new Thread(new Runnable() {
			@Override
			public void run() {
				//default starting with four players
				model = new Game();
				model.startGame(4);
			}
		});
		
		modelthr.start();
		try {
			Thread.sleep(1000);
			model.wake();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
	
	
	
	
}