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
		
		pres.userInput(1); 	//tries to start setup with no cards added
		
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
	
	/* testing quests:
	 *  named foe,
	 *  declining participation,
	 *  declining to play cards to a stage
	 */
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
		
		//turn 1
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
		
		String[] sd = {"boarHunt", "boarHunt", "boarHunt"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		//set up hands
		String[] hand0 = {"dagger", "lance"};
		String[] hand1 = {"dagger", "lance", "tristan", "arthur", "giant", "saxonKnight"};
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
		pres.playCard(413, 1);
		pres.playCard(416, 0);
		pres.userInput(1);
		
		//players confirm participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		for(Player p : game.getQuest().getPlayers()) {
			if(p != game.getQuest().getSponsor())
				assert(game.getQuest().getParticipants().contains(p));
		}
				
		//stage 1 : test
		pres.userInput(3);
		pres.userInput(4);
		pres.userInput(-1);
		pres.userInput(6);
		pres.userInput(-1);
		//player 1 wins the bidding war
		
		//player 1 discards cards
		pres.discardCard(407);
		pres.discardCard(408);
		pres.discardCard(409);
		pres.discardCard(410);
		pres.discardCard(411);
		pres.discardCard(412);
		
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
		
		String[] sd = {"boarHunt", "boarHunt", "boarHunt"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
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
		
		for(Player p : game.getPlayers().getPlayers())
			p.printHand();
		
		//sponsorship
		pres.userInput(0);
		pres.userInput(1);
		
		//quest set up
		pres.playCard(551, 0);
		pres.playCard(552, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0: participants play cards
		pres.playCard(553, -1);
		pres.playCard(554, -1);
		pres.userInput(0);
		pres.playCard(558, -1);
		pres.userInput(0);
		pres.playCard(547, -1);
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
		
		pres.discardCard(555);
		pres.discardCard(556);
		pres.discardCard(557);
		
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
		
		String[] sd = {"boarHunt", "searchForTheQuestingBeast"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
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
		pres.playCard(699, 0);
		pres.playCard(697, 1);
		pres.playCard(698, 2);
		pres.playCard(695, 3);
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
		
		String[] sd = {"boarHunt", "searchForTheQuestingBeast"};
		StoryDeck storyDeck = new StoryDeck(sd);
		
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
		pres.playCard(841, 0);		
		pres.playCard(839, 1);
		pres.playCard(838, 1);
		pres.playCard(840, 2);
		pres.playCard(837, 3);
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
		pres.playCard(842, -1);
		pres.userInput(1);
		pres.playCard(829, -1);
		pres.userInput(1);
		pres.playCard(831, -1);
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
	}
}
