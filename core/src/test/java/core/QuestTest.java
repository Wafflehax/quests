package core;


import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.Stories.Quest;
import com.comp_3004.quest_cards.cards.AdventureCard;
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

	/* testing quests:
	 *  all players decline to sponsor quest,
	 *  proper turn sequence,
	 *  players attempting to sponsor without required cards,
	 *  sponsor adding improper cards to stage,
	 *  sponsor setting up quest without increasing battle points per stage,
	 *  sponsor setting up quest without cards in each stage,
	 *  3-way tie,
	 *  turn sequence
	 */
	public void testQuest1() {
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Slay the Dragon");
		
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
		pres.playCard(155, 0);	//try to add weapon to stage with no foe
		pres.playCard(154, 1);	//play thieves in stage 1
		pres.playCard(156, 1);	//try to play a second foe to the stage
		pres.playCard(156, 0);	//play boar in stage 0
		pres.playCard(157, 1);	//try to add a test to a stage with a foe
		pres.playCard(155, 0);	//add dagger to stage 0
		
		pres.userInput(1);	//fails quest set up check, must set up again
		
		pres.userInput(1); 	//tries to start setup with no cards added
		
		//sets up quest correctly
		pres.playCard(154, 0);	//play thieves in stage 0
		pres.playCard(156, 1);	//play boar in stage 1
		pres.playCard(155, 1);	//add dagger to stage 1
		
		pres.userInput(1);	//passes quest set up check
		
		//users decide if they want to participate in quest
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		for(Player p : game.getQuest().getPlayers()) {
			if(p != game.getQuest().getSponsor())
				assert(game.getQuest().getParticipants().contains(p));
		}
		
		//stage 0
		pres.playCard(158, -1);		//player 1 plays dagger 
		pres.userInput(1);		//player 1 done playing cards
		pres.playCard(160, -1);		//player 2 plays dagger
		pres.userInput(1);		//player 2 done playing cards
		pres.playCard(162, -1);		//player 3 plays dagger
		pres.userInput(1);		//player 3 done playing cards
		
		//stage 1
		pres.playCard(159, -1);		//player 1 plays lance
		pres.userInput(1);		//player 1 done playing cards
		pres.playCard(161, -1);		//player 2 plays lance
		pres.userInput(1);		//player 2 done playing cards
		pres.playCard(163, -1);		//player 3 plays lance
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
	
	/* testing quests:
	 *  named foe,
	 *  declining participation,
	 *  declining to play cards to a stage
	 */
	public void testQuest2() {
		//set up story deck
		log.info("QUEST TEST 2");
		log.info("===================================");
		
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		
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
		
		//turn 1
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(1);		//player 2 sponsors quest
		
		//player 2 sets up quest
		pres.playCard(321, 0);
		pres.playCard(323, 1);
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
		pres.playCard(325, -1);
		pres.userInput(1);
		assertEquals("Dagger", game.getPlayerAtIndex(3).getStage().get(0).getName());
		assertEquals(325, game.getPlayerAtIndex(3).getStage().get(0).getID());
		
		//player 0 and 1 dont play any cards
		pres.userInput(1);
		pres.userInput(1);
		
		//player 3 plays lance
		pres.playCard(326, -1);
		pres.userInput(1);
		
		pres.userInput(1);
		pres.userInput(1);
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(2, game.getPlayerAtIndex(3).getShields());
		assertEquals(4, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(4, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(6, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(2, game.getPlayerAtIndex(3).getHand().size());
		assertEquals(4, game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());	
	}
	
	/* testing bids: 
	 * returning to next stage after bidding war, 
	 * trying to bid less than current bid
	 */
	public void testQuest3() {
		//set up story deck
		log.info("QUEST TEST 3");
		log.info("===================================");
		
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		String[] hand0 = {"dagger", "lance"};
		String[] hand1 = {"dagger", "lance", "excalibur", "arthur", "giant", "saxonKnight"};
		String[] hand2 = {"thieves", "dagger", "boar", "valor"};
		String[] hand3 = {"dagger", "lance"};
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(3).setHand(hand3);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		//turn 1
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(1);		//player 2 sponsors quest
		
		//player 2 sets up quest
		pres.playCard(488, 1);
		pres.playCard(491, 0);
		pres.userInput(1);
		
		//players confirm participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		for(Player p : game.getQuest().getPlayers()) {
			if(p != game.getQuest().getSponsor())
				assert(game.getQuest().getParticipants().contains(p));
		}
				
		//stage 0 : test
		pres.userInput(3);
		pres.userInput(4);
		pres.userInput(-1);
		pres.userInput(6);
		pres.userInput(-1);
		//player 1 wins the bidding war
		
		//player 1 discards cards
		pres.discardCard(482);
		pres.discardCard(483);

		game.getcurrentTurn().printHand();
		pres.discardCard(484);
		pres.discardCard(485);
		pres.discardCard(486);
		pres.discardCard(487);
		
		//player 1 doesnt play any cards
		pres.userInput(0);
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(2, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(3, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(2, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(6, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(3, game.getPlayerAtIndex(3).getHand().size());
		assertEquals(8, game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());	
	}
	
	/* testing bids: 
	 * free bids, 
	 * players trying to bid more than they are allowed, 
	 * players trying to bid under minimum bid
	 */
	public void testQuest4() {
		//set up story deck
		log.info("QUEST TEST 4");
		log.info("===================================");
		
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		String[] hand0 = {"amour", "lance"};
		String[] hand1 = {"thieves", "dagger", "boar", "morganLeFey"};
		String[] hand2 = {"guinevere", "lance", "tristan", "giant", "saxonKnight"};
		String[] hand3 = {"arthur", "lance"};
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(3).setHand(hand3);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(0);
		pres.userInput(1);
		
		//quest set up
		pres.playCard(651, 0);
		pres.playCard(652, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0: participants play cards
		pres.playCard(653, -1);
		pres.playCard(654, -1);
		pres.userInput(0);
		pres.playCard(658, -1);
		pres.userInput(0);
		pres.playCard(647, -1);
		pres.userInput(0);
		
		assertEquals(4, game.getPlayerAtIndex(0).numBidsAllowed());
		assertEquals(2, game.getPlayerAtIndex(1).numBidsAllowed());
		assertEquals(8, game.getPlayerAtIndex(2).numBidsAllowed());
		assertEquals(5, game.getPlayerAtIndex(3).numBidsAllowed());
		
		//stage 1: participants bid
		pres.userInput(1);
		pres.userInput(3);
		pres.userInput(3);
		pres.userInput(4);
		pres.userInput(5);
		pres.userInput(-1);
		assertEquals(2, game.getQuest().getParticipants().size());
		pres.userInput(6);
		pres.userInput(-1);
		assertEquals(1, game.getQuest().getParticipants().size());
		
		pres.discardCard(655);
		pres.discardCard(656);
		pres.discardCard(657);
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(2, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(3, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(6, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(2, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(3, game.getPlayerAtIndex(3).getHand().size());
		assertEquals(7, game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	/* testing quests: 
	 * testing opting out of a quest as sponsor if already accepted sponsorship
	 */
	public void testQuest5() {
		//set up story deck
		log.info("QUEST TEST 5");
		log.info("===================================");
		
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Search for the Questing Beast");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		String[] hand0 = {"dagger", "lance"};
		String[] hand1 = {"dagger", "lance", "tristan", "arthur", "giant", "saxonKnight"};
		String[] hand2 = {"giant", "dagger", "boar", "questingBeast", "thieves"};
		String[] hand3 = {"dagger", "lance"};
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(3).setHand(hand3);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(1);
		
		//quest set up
		pres.playCard(825, 0);
		pres.playCard(823, 1);
		pres.playCard(824, 2);
		pres.playCard(821, 3);
		pres.userInput(1);
		
		pres.userInput(0); //player 2 declines sponsoring quest after accepting 
		pres.userInput(0);
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(2, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(6, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(5, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(2, game.getPlayerAtIndex(3).getHand().size());
		assertEquals(0, game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
		
	}
	
	/* testing bids: 
	 * test of the questing beast on search for the questing beast
	 * testing quests:
	 * no winner
	 * single participant in last stage
	 */
	public void testQuest6() {
		//set up story deck
		log.info("QUEST TEST 6");
		log.info("===================================");
		
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Search for the Questing Beast");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		String[] hand0 = {"dagger", "lance"};
		String[] hand1 = {"dagger", "lance", "tristan", "arthur", "giant", "saxonKnight"};
		String[] hand2 = {"giant", "dagger", "boar", "questingBeast", "thieves"};
		String[] hand3 = {"dagger", "lance"};
		game.getPlayerAtIndex(0).setHand(hand0);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(3).setHand(hand3);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(1);
		
		//quest set up
		pres.playCard(993, 0);		
		pres.playCard(991, 1);
		pres.playCard(990, 1);
		pres.playCard(992, 2);
		pres.playCard(989, 3);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 1
		pres.playCard(995, -1);
		pres.userInput(1);
		pres.playCard(982, -1);
		pres.userInput(1);
		pres.playCard(984, -1);
		pres.userInput(1);
		
		//stage 2
		pres.userInput(1);
		pres.userInput(4);
		pres.userInput(-1);
		pres.userInput(-1);
		
		//player 1 discards cards
		for(int i =0; i<4; i++)
			pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());
		
		//stage 3
		pres.userInput(0);
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(4, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(8, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(9, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(1, game.getPlayerAtIndex(3).getHand().size());
		assertEquals(12, game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
	
	/* testing quests:
	 * no quest participation
	 * sponsor draws over hand limit
	 * sponsor can now discard or play cards to go back down to 12
	 */
	public void testQuest7() {
		//set up story deck
		log.info("QUEST TEST 7");
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
		pres.playCard(1155, 0);
		pres.playCard(1154, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(0);
		
		game.getcurrentTurn().printHand();
		pres.discardCard(1150);
		pres.playCard(1151, -1);
		pres.playCard(1152, -1);
		
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(1, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(12, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(1, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(1, game.getPlayerAtIndex(3).getHand().size());
		assertEquals(3, game.getAdvDeck().getDiscard().size());
		assertEquals("Player 1", game.getcurrentTurn().getName());
	}
}
