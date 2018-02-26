package core;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;

import Ai.AI;
import Ai.AbstractAI;
import Ai.DoSponsor1;
import Ai.NextBid1;
import Ai.PlayInQuest1;
import Ai.QuestParticipation1;
import junit.framework.TestCase;

public class AI1QuestTest extends TestCase {

	//test sponsorship with no duplicate weapons
	public void test1() {
		StoryDeck storyDeck = new StoryDeck();		//set up story deck		
		storyDeck.setTopCard("Slay the Dragon");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		GameModel game = new GameModel(0, 0, advDeck, storyDeck);
		
		// p1 first ai player
		AbstractAI ai = new AI();
		Player aiplayer = new Player("P1-ai", ai);
		ai.setPlayer(aiplayer);
		ai.setSponsor(new DoSponsor1());
		ai.setQuestParticipation(new QuestParticipation1());
		ai.setQuestPlay(new PlayInQuest1());
		ai.setNextBid(new NextBid1());
		
		// p2 second ai player
		AbstractAI ai2 = new AI();
		Player aiplayer2 = new Player("P2-ai", ai2);
		ai2.setPlayer(aiplayer2);
		ai2.setSponsor(new DoSponsor1());
		ai2.setQuestParticipation(new QuestParticipation1());
		
		// p3 second ai player
		AbstractAI ai3 = new AI();
		Player aiplayer3 = new Player("P3-ai", ai3);
		ai3.setPlayer(aiplayer3);
		ai3.setSponsor(new DoSponsor1());
		ai3.setQuestParticipation(new QuestParticipation1());
		
		//p4 human
		Player p4 = new Player("P4-human");
		
		game.addPlayer(aiplayer);
		game.addPlayer(aiplayer2);
		game.addPlayer(aiplayer3);
		game.addPlayer(p4);
		
		//set up hands
		game.getPlayerAtIndex(0).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Saxons", advDeck);
		game.getPlayerAtIndex(0).pickCard("Sword", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Sword", advDeck);
		game.getPlayerAtIndex(0).pickCard("Dagger", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Queen Iseult", advDeck);
		game.getPlayerAtIndex(0).pickCard("Lance", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Saxons", advDeck);
		game.getPlayerAtIndex(1).pickCard("Sword", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(1).pickCard("King Arthur", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Battle-Ax", advDeck);
		
		game.getPlayerAtIndex(2).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(2).pickCard("Test of Temptation", advDeck);
		game.getPlayerAtIndex(2).pickCard("Giant", advDeck);
		game.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);
		game.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(2).pickCard("Sword", advDeck);
		
		GamePresenter pres = new GamePresenter(game);
		game.getAdvDeck().shuffle();
		pres.getModel().beginTurn();
		
		assertEquals(5, game.getQuest().getStage(0).getBattlePts());
		assertEquals(0, game.getQuest().getStage(1).getBattlePts());
		assertEquals(70, game.getQuest().getStage(2).getBattlePts());
		
		//participation
		pres.userInput(1);
		assertEquals(2, game.getQuest().getParticipants().size());
		
		//stage 1
		pres.userInput(1);
		
		//stage 2
		pres.userInput(1);
		pres.userInput(-1);
		game.getcurrentTurn().printHand();
	}
	
	/*
	//test sponsorship with duplicate weapons to add
	public void test2() {
		StoryDeck storyDeck = new StoryDeck();		//set up story deck		
		storyDeck.setTopCard("Slay the Dragon");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		GameModel game = new GameModel(0, 0, advDeck, storyDeck);
		
		// p1 first ai player
		AbstractAI ai = new AI();
		Player aiplayer = new Player("P1-ai", ai);
		ai.setPlayer(aiplayer);
		ai.setSponsor(new DoSponsor1());
		//ai.setQuestParticipation(new QuestParticipation1());
		//ai.setQuestPlay(new PlayInQuest1());
		//ai.setDiscardAfterTest(new DiscardAfterTest1());
		//ai.setNextBid(new NextBid2());
		
		// p2 second ai player
		AbstractAI ai2 = new AI();
		Player aiplayer2 = new Player("P2-ai", ai2);
		ai2.setPlayer(aiplayer2);
		ai2.setSponsor(new DoSponsor1());
		//ai2.setQuestParticipation(new QuestParticipation1());
		//ai2.setQuestPlay(new PlayInQuest1());
		//ai2.setDiscardAfterTest(new DiscardAfterTest1());
		//ai2.setNextBid(new NextBid1());
		
		// p3 second ai player
		AbstractAI ai3 = new AI();
		Player aiplayer3 = new Player("P3-ai", ai3);
		ai3.setPlayer(aiplayer3);
		ai3.setSponsor(new DoSponsor1());
		//ai3.setQuestParticipation(new QuestParticipation1());
		//ai3.setQuestPlay(new PlayInQuest1());
		//ai3.setDiscardAfterTest(new DiscardAfterTest1());
		//ai3.setNextBid(new NextBid1());
		
		//p4 human
		Player p4 = new Player("P4-human");
		
		game.addPlayer(aiplayer);
		game.addPlayer(aiplayer2);
		game.addPlayer(aiplayer3);
		game.addPlayer(p4);
		
		//set up hands
		game.getPlayerAtIndex(2).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(2).pickCard("Test of Morgan Le Fey", advDeck);
		game.getPlayerAtIndex(2).pickCard("Giant", advDeck);
		game.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);
		game.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(2).pickCard("Sword", advDeck);
		game.getPlayerAtIndex(2).pickCard("Sword", advDeck);
		
		GamePresenter pres = new GamePresenter(game);
		game.getAdvDeck().shuffle();
		pres.getModel().beginTurn();
		
		assertEquals(15, game.getQuest().getStage(0).getBattlePts());
		assertEquals(0, game.getQuest().getStage(1).getBattlePts());
		assertEquals(70, game.getQuest().getStage(2).getBattlePts());
	}*/

}
