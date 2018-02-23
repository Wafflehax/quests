package core;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;

import junit.framework.TestCase;

public class Scenario1Test extends TestCase {
	static Logger log = Logger.getLogger(Scenario1Test.class); //log4j logger

	/*
	 * Scenario 1
player1 gets 12 cards including saxons, boar and sword
players 2, 3, and 4 get 12 cards (with some specific ones as seen below)
first story card is Boar Hunt
player1 sponsors
player1 sets up stage 1: saxons (worth 10 not 20) stage 2: boar + dagger + sword (worth 15+5+10) other 3 players accept and get an A card: must discard to stay at 12 cards in their hands
player 2 plays nothing, player 3 plays a horse; player 4 plays an axe (ie battle-ax)
player 2 is eliminated, players 3 and 4 continue to stage 2
players 3 and 4 get an A card (their 12th), player3 plays excalibur; Player4 plays a lance
player 3 wins and gets 2 Shields, player 4 does not get shields
player 1 discards all 4 cards of the quest, gets 6 new cards,then discards to get back to 12.
second story card is Prosperity
all players draw 2 cards and must discard correctly. In particular:
player2 discards a weapon, player 3 plays amour, player 4 discards a foe third story card is Chivalrous deed
all players BUT p3 get 3 shields
	 */
	
	public void testScenario1() {
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Chivalrous Deed");
		storyDeck.setTopCard("Prosperity Throughout the Realms");
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck 
		AdventureDeck advDeck = new AdventureDeck();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		GamePresenter pres = new GamePresenter(game);
		assertEquals(125, advDeck.getDeck().size());
		
		//set up hands
		//player1 hand: saxons, boar, sword, test of valor, dagger, thieves + 6
		game.getPlayerAtIndex(0).pickCard("Saxons", advDeck); 
		game.getPlayerAtIndex(0).pickCard("Boar", advDeck);
		game.getPlayerAtIndex(0).pickCard("Sword", advDeck);
		game.getPlayerAtIndex(0).pickCard("Test of Valor", advDeck);
		game.getPlayerAtIndex(0).pickCard("Dagger", advDeck);	
		game.getPlayerAtIndex(0).pickCard("Thieves", advDeck);	
		//player2 hand: thieves, dagger, giant, king arthur, lance, sir tristan + 6
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Dagger", advDeck);
		game.getPlayerAtIndex(1).pickCard("Giant", advDeck);
		game.getPlayerAtIndex(1).pickCard("King Arthur", advDeck);
		game.getPlayerAtIndex(1).pickCard("Lance", advDeck);	
		game.getPlayerAtIndex(1).pickCard("Sir Tristan", advDeck);	
		//player3 hand: thieves, horse, excalibur, amour, battle-ax, green knight + 6
		game.getPlayerAtIndex(2).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(2).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);
		game.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(2).pickCard("Green Knight", advDeck);	
		//player4 hand: thieves, battle-ax, lance, thieves, horse, dagger + 6
		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(3).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(3).pickCard("Lance", advDeck);
		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck);
		game.getPlayerAtIndex(3).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(3).pickCard("Dagger", advDeck);
		

		game.getAdvDeck().shuffle();
		for(Player p : game.getPlayers().getPlayers()) {
			for(int i=0; i<6; i++)
				p.drawCard(advDeck);
			assertEquals(12,p.getHand().size());
		}
		assertEquals(125-(4*12), game.getAdvDeck().getDeck().size());

		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(1);
		
		//quest set up
		pres.playCard(58, 0);
		pres.playCard(63, 1);
		pres.playCard(109, 1);
		pres.playCard(90, 1);
		pres.userInput(1);
		assertEquals(10, game.getQuest().getStage(0).getBattlePts());
		assertEquals(30, game.getQuest().getStage(1).getBattlePts());
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//players discard to go down to 12 cards
		pres.discardCard(31);
		pres.discardCard(33);
		pres.discardCard(35);
		
		//stage 0
		pres.userInput(0);
		pres.playCard(79, -1);
		pres.userInput(0);
		pres.playCard(121, -1);
		pres.userInput(0);
		assertEquals(0, game.getPlayerAtIndex(1).getActive().size());
		assertEquals(0, game.getPlayerAtIndex(2).getActive().size());
		assertEquals(0, game.getPlayerAtIndex(3).getActive().size());
		assertEquals(12, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(12, game.getPlayerAtIndex(3).getHand().size());
		
		//stage 1
		pres.playCard(106, -1);
		pres.userInput(0);
		pres.playCard(110, -1);
		pres.userInput(0);
		assertEquals(2, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getActive().size());
		assertEquals(0, game.getPlayerAtIndex(3).getActive().size());
		
		pres.discardCard(148);
		pres.discardCard(29);
		
		//player1's discards
		pres.discardCard(111);
		pres.discardCard(74);
		
		//player2's play
		pres.playCard(138, -1);
		
		//player3's discard
		pres.discardCard(37);
		
		//player0's play/discard
		pres.discardCard(game.getPlayerAtIndex(0).getHand().get(0).getID());
		pres.discardCard(game.getPlayerAtIndex(0).getHand().get(0).getID());
		
		assertEquals(12, game.getPlayerAtIndex(0).getHand().size());
		assertEquals(12, game.getPlayerAtIndex(1).getHand().size());
		assertEquals(12, game.getPlayerAtIndex(2).getHand().size());
		assertEquals(12, game.getPlayerAtIndex(3).getHand().size());
		assertEquals(3, game.getPlayerAtIndex(0).getShields());
		assertEquals(3, game.getPlayerAtIndex(1).getShields());
		assertEquals(2, game.getPlayerAtIndex(2).getShields());
		assertEquals(3, game.getPlayerAtIndex(3).getShields());
		

	}

}
