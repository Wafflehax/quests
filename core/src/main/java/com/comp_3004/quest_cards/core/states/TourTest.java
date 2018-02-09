package com.comp_3004.quest_cards.core.states;

import com.comp_3004.quest_cards.core.GameController;
import com.comp_3004.quest_cards.core.GameModel;

public class TourTest{
	GameModel model = new GameModel();   
    GameController c = new GameController(model);
    
	public void test() {
		//testing tour
		c.tourTest();
	}
	
	
}