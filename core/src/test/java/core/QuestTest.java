package core;

import java.io.ByteArrayInputStream;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;

import junit.framework.TestCase;

public class QuestTest extends TestCase{
	CardSpawner spawner = new CardSpawner();

	//no one sponsors quest
	public void testQuest1() {
		//set up story deck
		String[] sd = {"boarHunt"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		ByteArrayInputStream in = new ByteArrayInputStream("0\n0\n0\n0\n".getBytes());
		System.setIn(in);
		game.questTest();
		//System.setIn(System.in);
		
	}
	
	//named foe case - 3 way tie
	public void testQuest2() {
		//set up story deck
		String[] sd = {"boarHunt"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		//set up hands
		String[] hand0 = {"dagger", "sword"};
		String[] hand1 = {"dagger", "sword"};
		String[] hand2 = {"thieves", "valor", "boar", "valor"};
		String[] hand3 = {"dagger", "sword"};
		
		GameModel game;
		
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(3).setHand(hand3);
		
		ByteArrayInputStream in = new ByteArrayInputStream("0\n1\n1\n0\n0\n1\n1\n1\n0\n1\n1\n1\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0".getBytes());
		System.setIn(in);
		game.questTest();
	}

}
