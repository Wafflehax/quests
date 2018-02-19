package core;

import com.comp_3004.quest_cards.Stories.Tour;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;
import junit.framework.TestCase;


public class TournamentTest extends TestCase{
	
	
	public void testTourOne() {
		//test participation gathering of tournament and check joiners get a card

		StoryDeck storyDeck = new StoryDeck();		//set up story deck		
		storyDeck.setTopCard("Tournament at Orkney");
		storyDeck.printDeck();
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		for(Player p : game.getPlayers().getPlayers()) {
			System.out.println(p.getName());
			p.printHand();
		}
		
		//asking players if they want to participate
		Player p0p = pres.getModel().getcurrentTurn();
		int p0 = pres.getModel().getcurrentTurn().getHand().size();
		pres.userInput(1); //yes
		
		Player p1p = pres.getModel().getcurrentTurn();
		int p1 = pres.getModel().getcurrentTurn().getHand().size();
		pres.userInput(1); //yes
		
		Player p2p = pres.getModel().getcurrentTurn();
		int p2 = pres.getModel().getcurrentTurn().getHand().size();
		pres.userInput(1); //yes
		
		Player p3p = pres.getModel().getcurrentTurn();
		int p3 = pres.getModel().getcurrentTurn().getHand().size();
		pres.userInput(0); //no
		
		//check input registered correctly
		assertEquals(true, game.getTour().getPlayers().getPlayers().contains(game.getPlayerAtIndex(0)));
		assertEquals(true, game.getTour().getPlayers().getPlayers().contains(game.getPlayerAtIndex(1)));
		assertEquals(true, game.getTour().getPlayers().getPlayers().contains(game.getPlayerAtIndex(2)));
		assertEquals(3, game.getTour().getPlayers().size());
		
		//check everyone has one more card than before
		assertEquals(p0+1, p0p.getHand().size());
		assertEquals(p1+1, p1p.getHand().size());
		assertEquals(p2+1, p2p.getHand().size());
		assertEquals(p3, p3p.getHand().size()); //didn't play didn't get card
	}
	
	public void testTourTwo() {
		
		//Test joiners having too many cards when trying to leave their player turn, block moving on
		StoryDeck storyDeck = new StoryDeck();		//set up story deck		
		storyDeck.setTopCard("Tournament at Orkney");
		storyDeck.printDeck();
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(2, 12, advDeck, storyDeck); //start with 2 players, both with 12 cards
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		for(Player p : game.getPlayers().getPlayers()) {
			System.out.println(p.getName());
			p.printHand();
		}
		
		//asking players if they want to participate
		
		//Player 0
		Player p0p = pres.getModel().getcurrentTurn();
		int p0 = pres.getModel().getcurrentTurn().getHand().size();
		pres.userInput(1); //yes
		
		//Player 1
		Player p1p = pres.getModel().getcurrentTurn();
		int p1 = pres.getModel().getcurrentTurn().getHand().size();
		pres.userInput(1); //yes
		
		//check input registered correctly
		assertEquals(true, game.getTour().getPlayers().getPlayers().contains(game.getPlayerAtIndex(0)));
		assertEquals(true, game.getTour().getPlayers().getPlayers().contains(game.getPlayerAtIndex(1)));
		assertEquals(2, game.getTour().getPlayers().size());
		
		//check everyone has one more card than before
		assertEquals(p0+1, p0p.getHand().size());
		assertEquals(p1+1, p1p.getHand().size());
		
		//Player 0 turn
		pres.userInput(1); //done turn
		//check player didn't move on
		assertEquals(game.getcurrentTurn(), p0p); //check still player 0
		
		
		pres.discardCard(p0p.getHand().get(0).getID());
		pres.userInput(1); // try to finish turn now
		assertEquals(game.getcurrentTurn(), p1p); //check moved on to next player
	}
	public void testThree() {
		//Test player trying to discard a card they played, block it
		StoryDeck storyDeck = new StoryDeck();		//set up story deck		
		storyDeck.setTopCard("Tournament at Orkney");
		storyDeck.printDeck();
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(2, 10, advDeck, storyDeck); //start with 2 players, both with 12 cards
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		for(Player p : game.getPlayers().getPlayers()) {
			System.out.println(p.getName());
			p.printHand();
		}
		
		//asking players if they want to participate
		
		//Player 0
		Player p0p = pres.getModel().getcurrentTurn();
		int p0 = pres.getModel().getcurrentTurn().getHand().size();
		pres.userInput(1); //yes
		
		//Player 1
		Player p1p = pres.getModel().getcurrentTurn();
		int p1 = pres.getModel().getcurrentTurn().getHand().size();
		pres.userInput(1); //yes
		
		//check input registered correctly
		assertEquals(true, game.getTour().getPlayers().getPlayers().contains(game.getPlayerAtIndex(0)));
		assertEquals(true, game.getTour().getPlayers().getPlayers().contains(game.getPlayerAtIndex(1)));
		assertEquals(2, game.getTour().getPlayers().size());
		
		//check everyone has one more card than before
		assertEquals(p0+1, p0p.getHand().size());
		assertEquals(p1+1, p1p.getHand().size());
		
		//Player 0 turn
		int played = game.getcurrentTurn().getHand().get(0).getID();
		pres.playCard(played); //played card
		int nact = p0p.getActive().size(); //number active
		
		//attempt to discard a played card
		pres.discardCard(played);
		assertEquals(nact, p0p.getActive().size()); // number of active cards same as before, discard failed
	}
	
	
	public void testFour() {
		//Test basic calculation of battle points for single player, ally,weapons,amour//calcBattlePoints
		AdventureDeck ad = new AdventureDeck();
		String story[] = {"camelot"};
		StoryDeck d = new StoryDeck(story);
		GameModel m = new GameModel(2, 4, ad, d); // 2 player 4 cards each
		//did not init special allies
		m.beginTurn();
		
		CardSpawner sp = new CardSpawner();
		Player p = new Player("Player 0");
		//try weapons, ally, amour
		int bp = 10 + 5 + 10 + 0 + 10;
		String[] cards = {"horse","dagger", "gawain", 
				"iseult", "amour"};
		p.setActiveHand(cards);
		
		//player is squire
		bp += 5;
		assertEquals(p.getRank(), Player.Rank.SQUIRE);
		assertEquals(bp, m.getTour().calcBattlePoints(p));		
	}
	
}