package com.comp_3004.quest_cards.core;

import com.comp_3004.quest_cards.cards.TestCard;

import junit.framework.TestCase;

public class TesterTest extends TestCase{

	public void case1Tourstest() {
		
		Controller controller = new Controller();
		controller.onCreate();
		//stimulate user 1 wants to play
		assertEquals(true, true);
		
	}
	
	
	
	
	
}