package core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.EventCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player.Rank;

import junit.framework.TestCase;

public class EventTest extends TestCase{
	CardSpawner spawner = new CardSpawner();
	static Logger log = Logger.getLogger(EventTest.class); //log4j logger
	
	
	//test1 - 4 way tie
	public void testChivalrousDeed1() {
		log.info("Chivalrous Deed: Test 1");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Chivalrous Deed");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		assertEquals(3, game.getPlayerAtIndex(0).getShields());
		assertEquals(3, game.getPlayerAtIndex(1).getShields());
		assertEquals(3, game.getPlayerAtIndex(2).getShields());
		assertEquals(3, game.getPlayerAtIndex(3).getShields());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
		
	//test 2 - 2 way rank tie, 2 way shields tie
	public void testChivalrousDeed2() {
		log.info("Chivalrous Deed: Test 2");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Chivalrous Deed");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(5);
		game.getPlayerAtIndex(1).addShields(5);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(3, game.getPlayerAtIndex(2).getShields());
		assertEquals(3, game.getPlayerAtIndex(3).getShields());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
		
	//test3 - 4 way rank tie, 1 player with lowest shields
	public void testChivalrousDeed3() {
		log.info("Chivalrous Deed: Test 3");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Chivalrous Deed");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(4);
		game.getPlayerAtIndex(1).addShields(4);
		game.getPlayerAtIndex(2).addShields(4);
		game.getPlayerAtIndex(3).addShields(2);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		assertEquals(4, game.getPlayerAtIndex(0).getShields());
		assertEquals(4, game.getPlayerAtIndex(1).getShields());
		assertEquals(4, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(Rank.KNIGHT, game.getPlayerAtIndex(3).getRank());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	//test 1 - players unable to lose shields
	public void testPox1() {
		log.info("Pox: Test 1");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Pox");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(1).addShields(0);
		game.getPlayerAtIndex(2).addShields(5);
		game.getPlayerAtIndex(3).addShields(12);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals("Player 1", game.getcurrentTurn().getName());	
	}
	
	//test 2 - normal case
	public void testPox2() {
		log.info("Pox: Test 2");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Pox");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(2);
		game.getPlayerAtIndex(1).addShields(2);
		game.getPlayerAtIndex(2).addShields(3);
		game.getPlayerAtIndex(3).addShields(13);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		assertEquals(2, game.getPlayerAtIndex(0).getShields());
		assertEquals(1, game.getPlayerAtIndex(1).getShields());
		assertEquals(2, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	//test 1 - players unable to lose shields
	public void testPlague1() {
		log.info("Plague: Test 1");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Plague");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(5);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	//test 2 - edge case
	public void testPlague2() {
		log.info("Plague: Test 2");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Plague");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(1);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
		
	//test 3 - normal case
	public void testPlague3() {
		log.info("Plague: Test 3");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Plague");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(4);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();

		assertEquals(2, game.getPlayerAtIndex(0).getShields());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	//test 1 - 4 way tie
	public void testQueensFavor1() {
		//set up story deck
		log.info("Queens Favor: Test 1");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Queen's Favor");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(2);
		game.getPlayerAtIndex(1).addShields(1);
		game.getPlayerAtIndex(2).addShields(3);
		game.getPlayerAtIndex(3).addShields(4);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		for(int i=0; i<4; i++)
			assertEquals(2, game.getPlayerAtIndex(i).getHand().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}

	//test 2 - 2 way tie
	public void testQueensFavor2() {
		//set up story deck
		log.info("Queens Favor: Test 2");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Queen's Favor");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(2);
		game.getPlayerAtIndex(1).addShields(1);
		game.getPlayerAtIndex(2).addShields(8);
		game.getPlayerAtIndex(3).addShields(10);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		assertEquals(2, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(2, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(3).getHand().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	//test 2 - normal case
	public void testQueensFavor3() {
		//set up story deck
		log.info("Queens Favor: Test 3");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Queen's Favor");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(2);
		game.getPlayerAtIndex(1).addShields(9);
		game.getPlayerAtIndex(2).addShields(8);
		game.getPlayerAtIndex(3).addShields(10);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();

		assertEquals(2, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(3).getHand().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	
	public void testCourtCalledToCamelot() {
		//set up story deck
		log.info("Court Called to Camelot: Test 1");
		log.info("=======================");
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Court Called to Camelot");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		String[] active = {"merlin", "gawain", "pellinore"};
		
		//start game
		GameModel game = new GameModel(2, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(1).setActiveHand(active);
		assertEquals(3, game.getPlayerAtIndex(1).getActive().size());
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		assertEquals(0, game.getPlayerAtIndex(0).getActive().size());
		assertEquals(0, game.getPlayerAtIndex(1).getActive().size());
		assertEquals(3, game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
		
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
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		for(int i=0; i<4; i++)
			assertEquals(2, game.getPlayerAtIndex(i).getHand().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	//test 1 normal case
	public void testKingsRecognition1() {
		log.info("King's Recognition: Test 1");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("King's Recognition");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(2, 0, advDeck, storyDeck);
		String[] hand0 = {"sword", "lance"};
		String[] hand1 = {"thieves", "boar", "thieves", "boar"};
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		
		assert(!game.getPlayerAtIndex(0).getKingsRecognitionBonus());
		assert(!game.getPlayerAtIndex(1).getKingsRecognitionBonus());
		
		GamePresenter pres = new GamePresenter(game);
		//turn 1
		pres.getModel().beginTurn();
		
		assert(game.getPlayerAtIndex(0).getKingsRecognitionBonus());
		assert(!game.getPlayerAtIndex(1).getKingsRecognitionBonus());
		
		//turn 2
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(1);
		
		//set up
		pres.playCard(156, 0);
		pres.playCard(157, 1);
		pres.userInput(1);
		
		//participation
		game.getcurrentTurn().printHand();
		pres.userInput(1);
		
		//stage 0
		pres.userInput(1);
		
		//stage 1
		pres.playCard(154, -1);
		pres.userInput(1);
		
		assertEquals(4, game.getPlayerAtIndex(0).getShields());
		
		//turn 3
		//sponsorship
		pres.userInput(0);
		pres.userInput(1);

		//set up
		pres.playCard(158, 0);
		pres.playCard(159, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		
		//stage 0
		pres.userInput(1);
		
		//stage 1
		pres.playCard(155, -1);
		pres.userInput(1);
		
		assertEquals(1, game.getPlayerAtIndex(0).getShields());
		assertEquals(Rank.KNIGHT, game.getPlayerAtIndex(0).getRank());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
		
	//test already has kings recognition
	public void testKingsRecognition2() {
		log.info("King's Recognition: Test 2");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("King's Recognition");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(2, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).setKingsRecognitionBonus(true);
		
		assert(game.getPlayerAtIndex(0).getKingsRecognitionBonus());
		assert(!game.getPlayerAtIndex(1).getKingsRecognitionBonus());
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		assert(game.getPlayerAtIndex(0).getKingsRecognitionBonus());
		assert(!game.getPlayerAtIndex(1).getKingsRecognitionBonus());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	//test 1 players have weapons to discard
	public void testKingsCallToArms1() {
		log.info("King's Call to Arms: Test 1");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("King's Call to Arms");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		//set up hands
		String[] hand0 = {"dagger", "lance", "sword"};
		String[] hand1 = {"dagger"};
		game = new GameModel(2, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).setKingsRecognitionBonus(true);
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		//player 0 discards a weapon
		pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());
		//player 1 discards a weapon
		pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());

		assertEquals(2,game.getPlayerAtIndex(0).getHand().size());
		assertEquals(0,game.getPlayerAtIndex(1).getHand().size());
		assertEquals(2,game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
		
	}
	
	//test 2 players have no weapons to discard
	public void testKingsCallToArms2() {
		log.info("King's Call to Arms: Test 2");
		log.info("=======================");
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("King's Call to Arms");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		//set up hands
		String[] hand0 = {"boar", "saxons", "dragon"};
		String[] hand1 = {"boar", "saxons"};
		String[] hand2 = {"boar"};
		
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		game.getcurrentTurn().printHand();
		pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());
		pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());
		game.getcurrentTurn().printHand();
		pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());
		pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());
		game.getcurrentTurn().printHand();
		pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());
		
		assertEquals(1,game.getPlayerAtIndex(0).getHand().size());
		assertEquals(0,game.getPlayerAtIndex(1).getHand().size());
		assertEquals(0,game.getPlayerAtIndex(2).getHand().size());
		assertEquals(0,game.getPlayerAtIndex(3).getHand().size());
		assertEquals(5,game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	} 
	
}
