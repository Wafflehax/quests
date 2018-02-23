package core;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;

import junit.framework.TestCase;

public class Scenario2Test extends TestCase {
	static Logger log = Logger.getLogger(Scenario2Test.class); //log4j logger

	/*
	 * Scenario 2
player1 gets 12 cards including saxons, boar and sword
players 2, 3, and 4 get 12 cards (with some specific ones as seen below)
first story card is Boar Hunt
player 1 declines to sponsor
player 2 sponsors and uses different foes than those of scenario 1 (to cover foes) player 3 does not participate
player 4 eliminated in first stage
player 1 eliminated in 2nd stage
ie no winner
next story card is another quest: defend the queens honor
player 2,3 decline to sponsor
player 4 sponsors quest (1: thieves, 2: saxon knight, 3: evil knight, 4: test of valor)
players 1-3 participate
player 2 has too many cards and plays sir lancelot
for stage 1: player 1,2 dont play any cards, player 3 plays amour
all 3 players pass the stage
stage 2: player 1 plays nothing, player 2 plays dagger, player 3 plays battle ax
all 3 players pass the stage
stage 3: player 1,2 dont play cards, player 3 plays excalibur
player 3 passes the stage
stage 4: test of valor
player 3 tries to bid 1 but since only one player must bid 3
player 3 discards 2 cards (3 - 1 for active amour)
player 3 completes quest and gains 4 shields
player 4 discards 2 cards to go back down to 12
	 */
	
	public void testScenario2() {
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Defend the Queen's Honor");
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
		game.getPlayerAtIndex(0).pickCard("Excalibur", advDeck);
		game.getPlayerAtIndex(0).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(0).pickCard("King Arthur", advDeck);	
		game.getPlayerAtIndex(0).pickCard("Sir Tristan", advDeck);	
		//player2 hand: thieves, dagger, giant, king arthur, lance, sir tristan + 6
		game.getPlayerAtIndex(1).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(1).pickCard("Dagger", advDeck);
		game.getPlayerAtIndex(1).pickCard("Giant", advDeck);
		game.getPlayerAtIndex(1).pickCard("Sword", advDeck);
		game.getPlayerAtIndex(1).pickCard("Dragon", advDeck);	
		game.getPlayerAtIndex(1).pickCard("Sir Lancelot", advDeck);	
		//player3 hand: thieves, horse, excalibur, amour, battle-ax, green knight + 6
		game.getPlayerAtIndex(2).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(2).pickCard("Horse", advDeck);
		game.getPlayerAtIndex(2).pickCard("Sword", advDeck);
		game.getPlayerAtIndex(2).pickCard("Amour", advDeck);
		game.getPlayerAtIndex(2).pickCard("Battle-Ax", advDeck);
		game.getPlayerAtIndex(2).pickCard("Excalibur", advDeck);	
		//player4 hand: thieves, battle-ax, lance, thieves, horse, dagger + 6
		game.getPlayerAtIndex(3).pickCard("Thieves", advDeck); 
		game.getPlayerAtIndex(3).pickCard("Test of Valor", advDeck);
		game.getPlayerAtIndex(3).pickCard("Lance", advDeck);
		game.getPlayerAtIndex(3).pickCard("Saxon Knight", advDeck);
		game.getPlayerAtIndex(3).pickCard("Evil Knight", advDeck);
		game.getPlayerAtIndex(3).pickCard("Queen Iseult", advDeck);
		

		game.getAdvDeck().shuffle();
		for(Player p : game.getPlayers().getPlayers()) {
			for(int i=0; i<4; i++)
				p.drawCard(advDeck);
			assertEquals(10,p.getHand().size());
		}
		assertEquals(125-(4*10), game.getAdvDeck().getDeck().size());

		pres.getModel().beginTurn();
		
		//quest1: boar hunt
		//sponsorship
		pres.userInput(0);
		pres.userInput(1);
		
		//set up
		pres.playCard(74, 0);
		pres.playCard(78, 1);
		pres.userInput(1);
		
		//participation
		pres.userInput(0);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.playCard(108, -1);
		pres.userInput(1);
		pres.playCard(106, -1);
		pres.playCard(131, -1);
		pres.userInput(1);
		
		//stage 1
		pres.playCard(132, -1);
		pres.userInput(1);
		
		//quest2: defend the queens honor
		pres.userInput(0);
		pres.userInput(0);
		pres.userInput(1);
		
		//set up
		pres.playCard(33, 0);
		pres.playCard(30, 1);
		pres.playCard(52, 2);
		pres.playCard(148, 3);
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//player2 play to get to 12 cards
		pres.playCard(136, -1);
		
		//stage 0
		pres.playCard(79, -1);
		pres.userInput(1);
		pres.playCard(90, -1);
		pres.userInput(1);
		pres.playCard(138, -1);
		pres.userInput(1);
		
		//stage 1
		pres.userInput(1);
		pres.playCard(109, -1);
		pres.userInput(1);
		pres.playCard(120, -1);
		pres.userInput(1);
		
		//stage 2
		pres.userInput(1);
		pres.userInput(1);
		pres.playCard(107, -1);
		pres.userInput(1);
		
		//stage 3
		pres.userInput(1);
		pres.userInput(3);
		
		//player 3 discards 2 cards (min bid of 3 - 1 for amour)
		for(int i=0; i<2; i++) {
			pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());
		}
		
		//player 4 discards 2 cards to go back down to 12
		for(int i=0; i<2; i++) {
			pres.discardCard(game.getcurrentTurn().getHand().get(0).getID());
		}
		
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(4, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(18, game.getAdvDeck().getDiscard().size());
		assertEquals("Player 3", game.getcurrentTurn().getName());

	}

}
