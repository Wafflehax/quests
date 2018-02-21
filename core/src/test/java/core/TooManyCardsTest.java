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

	//test drawing over hand limit
			public void testProsperityThroughoutTheRealms2() {
				log.info("Prosperity Throughout the Realms: Test 2");
				log.info("=======================");
				//set up story deck
				StoryDeck storyDeck = new StoryDeck();
				storyDeck.setTopCard("Prosperity Throughout the Realms");
				
				//set up adventure deck 
				AdventureDeck advDeck = new AdventureDeck();
				
				GameModel game;
				game = new GameModel(4, 0, advDeck, storyDeck);
				game.getAdvDeck().printDeck();
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
				
				game.getcurrentTurn().printHand();
				pres.playCard(29, -1);
				pres.playCard(109, -1);
				pres.discardCard(79);
				game.getPlayerAtIndex(3).printHand();
				pres.playCard(148, -1);
				pres.discardCard(131);
				pres.playCard(138, -1);
				
				
				assertEquals(11, game.getPlayerAtIndex(0).getHand().size());
				assertEquals(12, game.getPlayerAtIndex(1).getHand().size());
				assertEquals(11, game.getPlayerAtIndex(2).getHand().size());
				assertEquals(12, game.getPlayerAtIndex(3).getHand().size());
				assertEquals("Player 1", game.getcurrentTurn().getName());
			}

}
