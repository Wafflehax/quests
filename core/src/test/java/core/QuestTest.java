package core;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;

import junit.framework.TestCase;

public class QuestTest extends TestCase{
	CardSpawner spawner = new CardSpawner();

	public void testQuest() {
		//set up story deck
		String[] sd = {"defendTheQueensHonor"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		String[] ad = { };
		AdventureDeck advDeck = new AdventureDeck(ad);
		
		//set up hands
		String[] hand0 = {"boar", "valor", "thieves", "dragon", "sword", "sword", "dagger"};
		String[] hand2 = {"boar", "valor", "dragon"};
		
		GameModel game;
		
		//test1 - 4 way tie
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(2).setHand(hand2);
		
		game.questTest();
	}

}
