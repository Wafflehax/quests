package core;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;

import junit.framework.TestCase;

public class MordredTest extends TestCase {

	static Logger log = Logger.getLogger(MordredTest.class); //log4j logger
	
	//p1 uses mordred as foe in quest set up
	public void testMordred1() {
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck 
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		GamePresenter pres = new GamePresenter(game);
		assertEquals(125, advDeck.getDeck().size());
		
		//set up hands
		game.getPlayerAtIndex(0).pickCard("Mordred", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Boar", advDeck);
		game.getPlayerAtIndex(0).pickCard("Sword", advDeck);

		game.getPlayerAtIndex(1).pickCard("Dagger", advDeck);
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Giant", advDeck);
		game.getPlayerAtIndex(1).pickCard("Merlin", advDeck);
		game.getPlayerAtIndex(1).pickCard("Sir Tristan", advDeck);	

		game.getPlayerAtIndex(2).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(2).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);
		game.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(2).pickCard("Green Knight", advDeck);	

		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(3).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(3).pickCard("Lance", advDeck);
		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck);
		game.getPlayerAtIndex(3).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(3).pickCard("Dagger", advDeck);
		
		game.getAdvDeck().shuffle();
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(1);
		
		//set up
		pres.playCard(63, 0);
		pres.playCard(64, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.playCard(109, -1);
		pres.userInput(1);
		pres.playCard(138, -1);
		pres.userInput(1);
		pres.playCard(121, -1);
		pres.userInput(1);
		
		//stage 1
		pres.playCard(106, -1);
		pres.userInput(1);
		pres.playCard(108, -1);
		pres.userInput(1);
		
		assertEquals("Player 2", game.getcurrentTurn().getName());
		assertEquals(7, game.getAdvDeck().getDiscard().size());
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(2, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
	}
	
	//p1 uses mordred to kill p2's tristan during second stage preventing p2 from completing quest
	public void testMordred2() {
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck 
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		GamePresenter pres = new GamePresenter(game);
		assertEquals(125, advDeck.getDeck().size());
		
		//set up hands
		game.getPlayerAtIndex(0).pickCard("Mordred", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Boar", advDeck);
		game.getPlayerAtIndex(0).pickCard("Sword", advDeck);

		game.getPlayerAtIndex(1).pickCard("Dagger", advDeck);
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Giant", advDeck);
		game.getPlayerAtIndex(1).pickCard("Queen Iseult", advDeck);
		game.getPlayerAtIndex(1).pickCard("Sir Tristan", advDeck);	

		game.getPlayerAtIndex(2).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(2).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);
		game.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(2).pickCard("Green Knight", advDeck);	

		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(3).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(3).pickCard("Lance", advDeck);
		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck);
		game.getPlayerAtIndex(3).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(3).pickCard("Dagger", advDeck);
		
		game.getAdvDeck().shuffle();
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(1);
		
		//set up
		pres.playCard(184, 0);
		pres.playCard(228, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.playCard(274, -1);
		pres.userInput(1);
		pres.playCard(243, -1);
		pres.userInput(1);
		pres.playCard(284, -1);
		pres.playCard(288, -1);
		pres.userInput(1);
		
		//stage 1
		pres.playCard(261, -1);
		pres.userInput(1);
		pres.playCard(217, -1);
		pres.userInput(284);
		assertEquals("Sir Tristan", game.getAdvDeck().getDiscard().get(2).getName());
		assertEquals("Mordred", game.getAdvDeck().getDiscard().get(3).getName());
		assertEquals(1, game.getPlayerAtIndex(1).getActive().size());
		pres.userInput(1);
		pres.playCard(262, -1);
		pres.userInput(1);
		
		assertEquals("Player 2", game.getcurrentTurn().getName());
		assertEquals(8, game.getAdvDeck().getDiscard().size());
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(2, game.getPlayerAtIndex(3).getShields());
		
		
	}
	
	//mordred kills ally providing free bids during test to disqualify a player
	public void testMordred3() {
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck 
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		GamePresenter pres = new GamePresenter(game);
		assertEquals(125, advDeck.getDeck().size());
		
		//set up hands
		game.getPlayerAtIndex(0).pickCard("Mordred", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Boar", advDeck);
		game.getPlayerAtIndex(0).pickCard("Sword", advDeck);

		game.getPlayerAtIndex(1).pickCard("Dagger", advDeck);
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Giant", advDeck);
		game.getPlayerAtIndex(1).pickCard("Queen Iseult", advDeck);
		game.getPlayerAtIndex(1).pickCard("Sir Tristan", advDeck);	

		game.getPlayerAtIndex(2).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(2).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);
		game.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(2).pickCard("Test of Valor", advDeck);	

		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(3).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(3).pickCard("Lance", advDeck);
		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck);
		game.getPlayerAtIndex(3).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(3).pickCard("Dagger", advDeck);
		
		game.getAdvDeck().shuffle();
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(1);
		
		//set up
		pres.playCard(337, 0);
		pres.playCard(454, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.userInput(1);
		pres.userInput(1);
		pres.playCard(437, -1);
		pres.playCard(441, -1);
		pres.userInput(1);
		
		//stage 1
		pres.userInput(1);
		pres.userInput(2);
		pres.userInput(7); 	//p2 bids 7 (5 + 2 free)
		pres.userInput(-1);	//p4 drops out
		pres.playCard(370, -1);
		pres.userInput(441);
		
		System.out.println(game.getcurrentTurn().getFreeBids());
		System.out.println(game.getcurrentTurn().getState());
		game.getcurrentTurn().printHand();
		
		
	}

}
