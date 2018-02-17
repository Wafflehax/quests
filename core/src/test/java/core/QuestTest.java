package core;


import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.Stories.Quest;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;

import junit.framework.TestCase;

public class QuestTest extends TestCase{
	CardSpawner spawner = new CardSpawner();
	static Logger log = Logger.getLogger(QuestTest.class); //log4j logger

	//no one sponsors quest
	public void testQuest1() {
		//set up story deck
		String[] sd = {"boarHunt", "boarHunt", "slayTheDragon"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		String[] hand0 = {"thieves", "dagger", "boar", "valor"};
		String[] hand1 = {"dagger", "lance"};
		String[] hand2 = {"dagger", "lance"};
		String[] hand3 = {"dagger", "lance"};
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(3).setHand(hand3);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		for(Player p : game.getPlayers().getPlayers()) {
			System.out.println(p.getName());
			p.printHand();
		}
		
		//turn 1
		assertEquals("Player 0", game.getcurrentTurn().getName());
		for(int i=0; i<game.getNumPlayers(); i++) {
			pres.userInput(0);
		}//no one sponsored quest, turn over
		
		//turn 2
		assertEquals("Player 1", game.getcurrentTurn().getName());
		for(int i=0; i<game.getNumPlayers(); i++) 
			pres.userInput(1); //all players attempt to sponsor, only player 0 has cards to sponsored quest
		
		//player 0 set up quest incorrectly
		pres.playCard(130, 0);	//try to add weapon to stage with no foe
		pres.playCard(129, 1);	//play thieves in stage 1
		pres.playCard(131, 1);	//try to play a second foe to the stage
		pres.playCard(131, 0);	//play boar in stage 0
		pres.playCard(132, 1);	//try to add a test to a stage with a foe
		pres.playCard(130, 0);	//add dagger to stage 0
		
		pres.userInput(1);	//fails quest set up check, must set up again
		
		//sets up quest correctly
		pres.playCard(129, 0);	//play thieves in stage 0
		pres.playCard(131, 1);	//play boar in stage 1
		pres.playCard(130, 1);	//add dagger to stage 1
		
		pres.userInput(1);	//passes quest set up check
		
		//users decide if they want to participate in quest
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		for(Player p : game.getQuest().getPlayers()) {
			if(p != game.getQuest().getSponsor())
				assert(game.getQuest().getParticipants().contains(p));
		}
		
		//stage 1
		pres.playCard(133, -1);		//player 1 plays dagger 
		pres.userInput(1);		//player 1 done playing cards
		pres.playCard(135, -1);		//player 2 plays dagger
		pres.userInput(1);		//player 2 done playing cards
		pres.playCard(137, -1);		//player 3 plays dagger
		pres.userInput(1);		//player 3 done playing cards
		
		//stage 2
		pres.playCard(134, -1);		//player 1 plays lance
		pres.userInput(1);		//player 1 done playing cards
		pres.playCard(136, -1);		//player 2 plays lance
		pres.userInput(1);		//player 2 done playing cards
		pres.playCard(138, -1);		//player 3 plays lance
		pres.userInput(1);		//player 3 done playing cards
		
		assertEquals(9, game.getAdvDeck().getDiscard().size());
		assertEquals(6, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(2, game.getPlayerAtIndex(1).getShields());
		assertEquals(2, game.getPlayerAtIndex(2).getShields());
		assertEquals(2, game.getPlayerAtIndex(3).getShields());
		assertEquals(114, game.getAdvDeck().getDeck().size());
		for(int i=0; i<game.getNumPlayers(); i++) {
			assertEquals(0, game.getPlayerAtIndex(i).getActive().size());
		}
		assertEquals("Player 2", game.getcurrentTurn().getName());
		
		
		
	}
	
	public void testQuest2() {
		//set up story deck
		log.info("QUEST TEST 2");
		log.info("===================================");
		
		String[] sd = {"boarHunt", "boarHunt", "boarHunt"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		String[] hand0 = {"dagger", "lance"};
		String[] hand1 = {"dagger", "lance"};
		String[] hand2 = {"thieves", "dagger", "boar", "valor"};
		String[] hand3 = {"dagger", "lance"};
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(3).setHand(hand3);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		for(Player p : game.getPlayers().getPlayers()) {
			System.out.println(p.getName());
			p.printHand();
		}
		
		//turn 1
		System.out.println("Sponsor Quest?");
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(1);		//player 2 sponsors quest
		
		//player 2 sets up quest
		game.getPlayers().current().printHand();
		pres.playCard(271, 0);
		pres.playCard(273, 1);
		pres.userInput(1);
		
		//players confirm participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		for(Player p : game.getQuest().getPlayers()) {
			if(p != game.getQuest().getSponsor())
				assert(game.getQuest().getParticipants().contains(p));
		}
			
		
		//players play cards for stage 0
		pres.playCard(275, -1);
		pres.userInput(1);
		assertEquals("Dagger", game.getPlayerAtIndex(3).getStage().get(0).getName());
		assertEquals(275, game.getPlayerAtIndex(3).getStage().get(0).getID());
		
		//player 0 and 1 dont play any cards
		pres.userInput(1);
		pres.userInput(1);
		
		//player 3 plays lance
		pres.playCard(276, -1);
		pres.userInput(1);
		
		pres.userInput(1);
		pres.userInput(1);
		
		assertEquals("Player 1", game.getcurrentTurn().getName());	
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
