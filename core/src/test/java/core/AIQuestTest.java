package core;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;

import Ai.AI;
import Ai.AbstractAI;
import Ai.Quests2;
import junit.framework.TestCase;

public class AIQuestTest extends TestCase {

	//3 ai's 1 human
	//ai's do not have valid sponsorship cards
	//ai's do not have valid participation cards
	public void test1() {
		StoryDeck storyDeck = new StoryDeck();		//set up story deck		
		storyDeck.setTopCard("Journey Through the Enchanted Forest");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		GameModel game = new GameModel(0, 0, advDeck, storyDeck);
		
		AbstractAI ai = new AI();
		Player aiplayer = new Player("P1-ai", ai);
		ai.setPlayer(aiplayer);
		Quests2 q2 = new Quests2();
		ai.setQuest(q2);
		
		
		// p1 second ai player
		AbstractAI ai2 = new AI();
		Player aiplayer2 = new Player("P2-ai", ai2);
		ai2.setPlayer(aiplayer2);
		Quests2 q2p2 = new Quests2();
		ai2.setQuest(q2p2);
		
		// p1 second ai player
		AbstractAI ai3 = new AI();
		Player aiplayer3 = new Player("P3-ai", ai3);
		ai3.setPlayer(aiplayer3);
		Quests2 q2p3 = new Quests2();
		ai3.setQuest(q2p3);
		
		//p2 human
		Player p4 = new Player("P4-human");

		game.addPlayer(aiplayer);
		game.addPlayer(aiplayer2);
		game.addPlayer(aiplayer3);
		game.addPlayer(p4);
		
		//set up hands
		game.getPlayerAtIndex(0).pickCard("Mordred", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Boar", advDeck);
		game.getPlayerAtIndex(0).pickCard("Sword", advDeck);
		game.getPlayerAtIndex(0).pickCard("Evil Knight", advDeck);

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
		game.getPlayerAtIndex(3).pickCard("Green Knight", advDeck);
		game.getPlayerAtIndex(3).pickCard("Saxons", advDeck);
		game.getPlayerAtIndex(3).pickCard("Evil Knight", advDeck);
		game.getPlayerAtIndex(3).pickCard("Test of Morgan Le Fey", advDeck);
		
		GamePresenter pres = new GamePresenter(game);
		game.getAdvDeck().shuffle();
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(1);
		
		//set up
		pres.playCard(33, 0);
		pres.playCard(53, 1);
		pres.playCard(149, 2);
		pres.userInput(1);
		
		//no one participates
		assertEquals("P2-ai", game.getcurrentTurn().getName());
		assertEquals(10, game.getPlayerAtIndex(3).getHand().size());
		assertEquals(3, game.getAdvDeck().getDiscard().size());
		
	}
	
	//3 ai's 1 human
	//p3-ai has valid sponsorship cards
	public void test2() {
		StoryDeck storyDeck = new StoryDeck();		//set up story deck		
		storyDeck.setTopCard("Slay the Dragon");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		GameModel game = new GameModel(0, 0, advDeck, storyDeck);
		
		//p0 ai
		AbstractAI ai = new AI();
		Player aiplayer = new Player("P1-ai", ai);
		ai.setPlayer(aiplayer);
		Quests2 q2 = new Quests2();
		ai.setQuest(q2);
		
		// p1 second ai player
		AbstractAI ai2 = new AI();
		Player aiplayer2 = new Player("P2-ai", ai2);
		ai2.setPlayer(aiplayer2);
		Quests2 q2p2 = new Quests2();
		ai2.setQuest(q2p2);
		
		// p1 second ai player
		AbstractAI ai3 = new AI();
		Player aiplayer3 = new Player("P3-ai", ai3);
		ai3.setPlayer(aiplayer3);
		Quests2 q2p3 = new Quests2();
		ai3.setQuest(q2p3);	
		
		//p2 human
		Player p4 = new Player("P4-human");

		game.addPlayer(aiplayer);
		game.addPlayer(aiplayer2);
		game.addPlayer(aiplayer3);
		game.addPlayer(p4);
		
		//set up hands
		game.getPlayerAtIndex(0).pickCard("Mordred", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(0).pickCard("Sword", advDeck);
		game.getPlayerAtIndex(0).pickCard("King Arthur", advDeck);

		game.getPlayerAtIndex(1).pickCard("Sword", advDeck);
		game.getPlayerAtIndex(1).pickCard("Saxons", advDeck);
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck);
		game.getPlayerAtIndex(1).pickCard("Boar", advDeck);
		game.getPlayerAtIndex(1).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(1).pickCard("Sir Tristan", advDeck);
		game.getPlayerAtIndex(1).pickCard("Sword", advDeck);

		game.getPlayerAtIndex(2).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(2).pickCard("Test of Morgan Le Fey", advDeck);
		game.getPlayerAtIndex(2).pickCard("Giant", advDeck);
		game.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);
		game.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);	

		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(3).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(3).pickCard("Lance", advDeck);
		game.getPlayerAtIndex(3).pickCard("Green Knight", advDeck);
		game.getPlayerAtIndex(3).pickCard("Saxons", advDeck);
		game.getPlayerAtIndex(3).pickCard("Evil Knight", advDeck);
		
		GamePresenter pres = new GamePresenter(game);
		game.getAdvDeck().shuffle();
		pres.getModel().beginTurn();
		
		//sponsorship
		//p3-ai sponsors/sets up quest
		
		//participation
		pres.userInput(1);
		
		//stage 1
		pres.playCard(274, -1);
		pres.userInput(1);
		
		//stage 2
		pres.userInput(3);
		pres.userInput(-1);
		game.getcurrentTurn().printHand();
		
	}
}
