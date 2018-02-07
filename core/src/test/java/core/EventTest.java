package core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.EventCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.Player.Rank;

import junit.framework.TestCase;

public class EventTest extends TestCase{
	CardSpawner spawner = new CardSpawner();

	
	public void testChivalrousDeed() {
		//set up story deck
		String[] sd = {"chivalrousDeed"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		String[] ad = { };
		AdventureDeck advDeck = new AdventureDeck(ad);
		
		GameModel game;
		
		//test1 - 4 way tie
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.eventTest();
		assertEquals(3, game.getPlayerAtIndex(0).getShields());
		assertEquals(3, game.getPlayerAtIndex(1).getShields());
		assertEquals(3, game.getPlayerAtIndex(2).getShields());
		assertEquals(3, game.getPlayerAtIndex(3).getShields());
		
		//test 2 - 2 way rank tie, 2 way shields tie
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(5);
		game.getPlayerAtIndex(1).addShields(5);
		
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(3, game.getPlayerAtIndex(2).getShields());
		assertEquals(3, game.getPlayerAtIndex(3).getShields());
		
		//test3 - 4 way rank tie, 1 player with lowest shields
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(4);
		game.getPlayerAtIndex(1).addShields(4);
		game.getPlayerAtIndex(2).addShields(4);
		game.getPlayerAtIndex(3).addShields(2);
		
		game.eventTest();
		assertEquals(4, game.getPlayerAtIndex(0).getShields());
		assertEquals(4, game.getPlayerAtIndex(1).getShields());
		assertEquals(4, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(Rank.KNIGHT, game.getPlayerAtIndex(3).getRank());
		
	}
	
	public void testPox() {
		//set up story deck
		String[] sd= {"pox"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		String[] ad= {};
		AdventureDeck advDeck = new AdventureDeck(ad);
		
		GameModel game;
		
		//test 1 - players unable to lose shields
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(1).addShields(0);
		game.getPlayerAtIndex(2).addShields(5);
		game.getPlayerAtIndex(3).addShields(12);
		
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		
		//test 2 - normal case
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(2);
		game.getPlayerAtIndex(1).addShields(2);
		game.getPlayerAtIndex(2).addShields(3);
		game.getPlayerAtIndex(3).addShields(13);
		
		game.eventTest();
		assertEquals(2, game.getPlayerAtIndex(0).getShields());
		assertEquals(1, game.getPlayerAtIndex(1).getShields());
		assertEquals(2, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());	
	}
	
	public void testPlague() {
		//set up story deck
		String[] sd = {"plauge"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		String[] ad = {};
		AdventureDeck advDeck = new AdventureDeck(ad);
		
		GameModel game;
		
		//test 1 - players unable to lose shields
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(5);
		
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		
		//test 2 - edge case
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(1);
		
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		
		//test 2 - normal case
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(4);
		
		game.eventTest();
		assertEquals(2, game.getPlayerAtIndex(0).getShields());
	}
	
	public void testQueensFavor() {
		//set up story deck
		String[] sd = {"queensFavor"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		String[] ad = {"sword", "sword", "sword", "sword", "sword", "sword", "sword", "sword"};
		AdventureDeck advDeck = new AdventureDeck(ad);
		
		GameModel game;
		
		//test 1 - 4 way tie
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(2);
		game.getPlayerAtIndex(1).addShields(1);
		game.getPlayerAtIndex(2).addShields(3);
		game.getPlayerAtIndex(3).addShields(4);
		
		game.eventTest();
		for(int i=0; i<4; i++)
			assertEquals(2, game.getPlayerAtIndex(i).getHand().size());
		
		//test 2 - 2 way tie
		advDeck = new AdventureDeck(ad);
		game = new GameModel(4, 0, advDeck, storyDeck);

		game.getPlayerAtIndex(0).addShields(2);
		game.getPlayerAtIndex(1).addShields(1);
		game.getPlayerAtIndex(2).addShields(8);
		game.getPlayerAtIndex(3).addShields(10);
		
		game.eventTest();
		assertEquals(2, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(2, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(3).getHand().size());
		
		//test 2 - normal case
		advDeck = new AdventureDeck(ad);
		game = new GameModel(4, 0, advDeck, storyDeck);

		game.getPlayerAtIndex(0).addShields(2);
		game.getPlayerAtIndex(1).addShields(9);
		game.getPlayerAtIndex(2).addShields(8);
		game.getPlayerAtIndex(3).addShields(10);
		
		game.eventTest();
		assertEquals(2, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(3).getHand().size());
	}
	
	public void testCourtCalledToCamelot() {
		//set up story deck
		String[] sd= {"courtCalledToCamelot"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		String[] ad= {};
		AdventureDeck advDeck = new AdventureDeck(ad);
		
		String[] active = {"merlin", "gawain", "pellinore"};
		
		//start game
		GameModel game = new GameModel(2, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(1).setActiveHand(active);
		assertEquals(3, game.getPlayerAtIndex(1).getActive().size());
		
		//starts turn, draws card from story deck
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(0).getActive().size());
		assertEquals(0, game.getPlayerAtIndex(1).getActive().size());
		assertEquals(3, game.getAdvDeck().getDiscard().size());
	}
	
	public void testProsperityThroughoutTheRealms() {
		//set up story deck
		String[] sd = {"prosperityThroughoutTheRealms"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		String[] ad = {"sword", "sword", "sword", "sword", "sword", "sword", "sword", "sword", }; 
		AdventureDeck advDeck = new AdventureDeck(ad);
		
		GameModel game;
		
		//test 1
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		game.eventTest();
		for(int i=0; i<4; i++)
			assertEquals(2, game.getPlayerAtIndex(i).getHand().size());
	}

}
