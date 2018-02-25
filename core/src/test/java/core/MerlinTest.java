package core;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;

import junit.framework.TestCase;

public class MerlinTest extends TestCase {

	static Logger log = Logger.getLogger(MerlinTest.class); //log4j logger
	
	/* player2 declines using merlins ability in stage 0 when played
	 * player2 uses merlins ability in stage 1
	 * player2 sponsor next quest
	 */
	public void testMerlin1() {
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck 
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		GamePresenter pres = new GamePresenter(game);
		assertEquals(125, advDeck.getDeck().size());
		
		//set up hands
		game.getPlayerAtIndex(0).pickCard("Saxons", advDeck); 
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
		
		//quest 1
		//sponsorship
		pres.userInput(1);
		
		//set up
		pres.playCard(58, 0);
		pres.playCard(63, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.playCard(134, -1);
		pres.userInput(-1);			//declines using merlins ability
		pres.playCard(109, -1);
		pres.userInput(1);
		pres.userInput(0);
		pres.userInput(0);
		
		//stage 1
		pres.userInput(1);			//uses merlins ability to see stage 1
		pres.playCard(131, -1);
		pres.userInput(1);
		
		//quest 2
		//sponsorship
		pres.userInput(1);
		
		//set up
		pres.playCard(29, 0);
		pres.playCard(74, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 1
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		assertEquals("Player 3", game.getcurrentTurn().getName());
		assertEquals(2, game.getPlayerAtIndex(1).getActive().size());
		assertEquals(5, game.getAdvDeck().getDiscard().size());
		assertEquals(2, game.getPlayerAtIndex(1).getShields());
	}
	
	/* player3 uses merlins ability in stage 0 when played (tries to see non existant stage, then sees stage 1)
	 * merlins ability will no longer be available in stage 1
	 * player3 and 4 complete quest
	 * player3 uses merlins ability in stage 1
	 * player2 and 3 complete quest
	 */
	public void testMerlin2() {
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck 
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		GamePresenter pres = new GamePresenter(game);
		assertEquals(125, advDeck.getDeck().size());
		
		//set up hands
		game.getPlayerAtIndex(0).pickCard("Saxons", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Boar", advDeck);
		game.getPlayerAtIndex(0).pickCard("Sword", advDeck);

		game.getPlayerAtIndex(1).pickCard("Dagger", advDeck);
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Giant", advDeck);
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck);
		game.getPlayerAtIndex(1).pickCard("Horse", advDeck);	

		game.getPlayerAtIndex(2).pickCard("Sir Tristan", advDeck); 
		game.getPlayerAtIndex(2).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);
		game.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(2).pickCard("Merlin", advDeck);	

		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(3).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(3).pickCard("Lance", advDeck);
		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck);
		game.getPlayerAtIndex(3).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(3).pickCard("Robber Knight", advDeck);
		
		game.getAdvDeck().shuffle();
		pres.getModel().beginTurn();
		
		//quest 1
		//sponsorship
		pres.userInput(1);
		
		//set up
		pres.playCard(211, 0);
		pres.playCard(216, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.userInput(1);
		pres.playCard(287, -1);
		pres.userInput(2); //player 3 tries to see stage 3, but only 2 stages in quest
		pres.userInput(1); //player3 uses merlin to see stage 2
		pres.playCard(284, -1);
		pres.userInput(1);
		pres.playCard(274, -1);
		pres.userInput(1);
		
		//stage 1
		pres.playCard(233, -1);
		pres.userInput(1);
		pres.playCard(261, -1);
		pres.userInput(1);
		
		//quest2
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(1);
		
		//set up
		pres.playCard(186, 0);
		pres.playCard(198, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.playCard(243, -1);
		pres.userInput(1);
		pres.playCard(262, -1);
		pres.userInput(1);
		pres.userInput(-1); //p3 declines using merlin
		pres.userInput(1);
		
		//stage 1
		pres.playCard(258, -1);
		pres.userInput(1);
		pres.playCard(232, -1);
		pres.userInput(1);
		pres.userInput(1); //p3 uses merlins ability to see stage 2
		pres.userInput(1);
		
		assertEquals("Player 3", game.getcurrentTurn().getName());
		assertEquals(2, game.getPlayerAtIndex(2).getActive().size());
		assertEquals(10, game.getAdvDeck().getDiscard().size());
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(2, game.getPlayerAtIndex(1).getShields());
		assertEquals(4, game.getPlayerAtIndex(2).getShields());
		assertEquals(2, game.getPlayerAtIndex(3).getShields());
	}

}
