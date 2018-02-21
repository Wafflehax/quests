package core;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;

import junit.framework.TestCase;

public class TooManyCardsTest extends TestCase{
	CardSpawner spawner = new CardSpawner();
	static Logger log = Logger.getLogger(TooManyCardsTest.class); //log4j logger

	//test drawing over hand limit in events
	public void testProsperityThroughoutTheRealms() {
		log.info("Prosperity Throughout the Realms: Test 1");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Prosperity Throughout the Realms");
		
		//set up adventure deck 
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Dagger", advDeck);
		game.getPlayerAtIndex(1).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(3).pickCard("Test of Valor", advDeck);
		game.getPlayerAtIndex(3).pickCard("Sir Tristan", advDeck);
		game.getPlayerAtIndex(3).pickCard("Amour", advDeck);
		for(int i=0; i<4; i++) {
			for(int j=0; j<9; j++)
				game.getPlayerAtIndex(i).drawCard(advDeck);
		}
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		pres.playCard(197, -1);
		pres.playCard(277, -1);
		pres.discardCard(247);
		pres.playCard(316, -1);
		pres.discardCard(299);
		pres.playCard(306, -1);
		
		
		assertEquals(11, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(12, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(11, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(12, game.getPlayerAtIndex(3).getHand().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
		
	/* testing quests:
	 * no quest participation
	 * sponsor draws over hand limit
	 * sponsor can now discard or play cards to go back down to 12
	 */
	public void testQuest() {
		//set up story deck
		log.info("QUEST TEST 1");
		log.info("===================================");
		
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		String[] hand0 = {"dagger"};
		String[] hand1 = {"dagger", "lance", "tristan", "arthur", "giant", "saxonKnight", 
							"horse", "excalibur", "lance", "sword", "battleAx", "iseult"};
		String[] hand2 = {"giant"};
		String[] hand3 = {"dagger"};
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(3).setHand(hand3);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(0);
		pres.userInput(1);
		
		//set up
		game.getcurrentTurn().printHand();
		pres.playCard(160, 0);
		pres.playCard(159, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(0);
		
		game.getcurrentTurn().printHand();
		pres.discardCard(161);
		pres.playCard(166, -1);
		
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(1, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(12, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(1, game.getPlayerAtIndex(1).getActive().size());
		assertEquals(1, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(1, game.getPlayerAtIndex(3).getHand().size());
		assertEquals(3, game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}

}
