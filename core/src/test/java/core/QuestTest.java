package core;

import java.io.ByteArrayInputStream;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;

import junit.framework.TestCase;

public class QuestTest extends TestCase{
	CardSpawner spawner = new CardSpawner();

	//no one sponsors quest
	public void testQuest1() throws InterruptedException {
		//set up story deck
		String[] sd = {"boarHunt", "boarHunt"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		String[] hand0 = {"thieves", "dagger", "boar", "valor"};
		String[] hand1 = {"dagger", "sword"};
		String[] hand2 = {"dagger", "sword"};
		String[] hand3 = {"dagger", "sword"};
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(3).setHand(hand3);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		//turn 1
		System.out.println("Sponsor Quest?");
		for(int i=0; i<game.getNumPlayers(); i++) {
			//Thread.sleep(1000);
			pres.userInput(0);
		}//no one sponsored quest, turn over
		
		//turn 2
		System.out.println("Sponsor Quest?");
		for(int i=0; i<game.getNumPlayers(); i++) {
			//Thread.sleep(1000);
			pres.userInput(1);
		}//player 0 sponsored quest
		//player 0 set up quest incorrectly
		pres.playCard(129, 0);	//try to add weapon to stage with no foe
		pres.playCard(128, 1);	//play thieves in stage 1
		pres.playCard(130, 0);	//try to play a second foe to the stage
		pres.playCard(130, 0);	//play boar in stage 0
		pres.playCard(131, 1);	//try to add a test to a stage with a foe
		pres.playCard(129, 0);	//add dagger to stage 0
		
		pres.userInput(1);	//fails quest set up check, must set up again
		
		//sets up quest correctly
		pres.playCard(128, 0);	//play thieves in stage 0
		pres.playCard(130, 1);	//play boar in stage 1
		pres.playCard(129, 1);	//add dagger to stage 1
		
		pres.userInput(1);	//passes quest set up check
		
		
		//game.questTest();
		//System.setIn(System.in);
		
	}
	
	//named foe case - 3 way tie
	/*public void testQuest2() {
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
	}*/

}
