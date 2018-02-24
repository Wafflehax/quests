package core;

import com.comp_3004.quest_cards.Stories.AI;
import com.comp_3004.quest_cards.Stories.Strategy2;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;

import junit.framework.TestCase;

public class AIStrategyTest extends TestCase{

	public void testOne() {
		//testing functionality
		StoryDeck storyDeck = new StoryDeck();		//set up story deck		
		storyDeck.setTopCard("Tournament at Orkney");
		storyDeck.printDeck();
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		GameModel game;
		int ncards = 10;
		game = new GameModel(0, ncards, advDeck, storyDeck); //start with 1 players, with 10 cards
		
		
		//p0 ai
		AI ai = new Strategy2();
		Player aiplayer = new Player("p0-ai", ai);
		ai.setPlayer(aiplayer);
		
		for(int i = 0; i < ncards; i++) {
			aiplayer.drawCard(game.getAdvDeck());
		}
		
		
		// p1 second ai player
		AI ai2 = new Strategy2();
		Player aiplayer2 = new Player("p2-ai", ai2);
		ai2.setPlayer(aiplayer2);
		
		for(int i = 0; i < ncards; i++) {
			aiplayer2.drawCard(game.getAdvDeck());
		}
		
		//p2 human
		Player p2 = new Player("p1human");
		String cards[] = {"sword"};
		p2.setHand(cards);
		WeaponCard swo = (WeaponCard) AllyConditionsTest.find("sword", p2.getHand());
		for(int i = 0; i < ncards; i++) {
			p2.drawCard(game.getAdvDeck());
		}
		
		//p2 human
				Player p3 = new Player("p3human");
				String cards3[] = {"sword"};
				p3.setHand(cards3);
				WeaponCard swo3 = (WeaponCard) AllyConditionsTest.find("sword", p3.getHand());
				for(int i = 0; i < ncards; i++) {
					p3.drawCard(game.getAdvDeck());
				}
		
		game.addPlayer(aiplayer);
		game.addPlayer(p2);
		game.addPlayer(aiplayer2);
		game.addPlayer(p3);
		
		//The game does not assign strategies to AI players. It the initial player setting up the game that chooses
		//so atleast one player is not an ai
		
		
		
		GamePresenter pres = new GamePresenter(game);
		//what if first player is an ai player?
		pres.getModel().beginTurn();
		
		//p0,p1,p2 all participate
		pres.userInput(1); // p2 yes
		
		
		//p3 participates
		pres.userInput(1); 
		
		//p1 human turn
		pres.userInput(1); //done turn
		
		//p3 human
		pres.userInput(1); //done turn
		
		
		
		
		
		
	}
	
}
