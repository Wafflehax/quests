package core;

import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Player.Rank;

import Ai.AI;
import Ai.AbstractAI;
import Ai.DoSponsor1;
import Ai.TourParticipate1;
import Ai.TourParticipate2;
import Ai.TourParticipation;
import Ai.TourPlay1;
import Ai.TourPlay2;
import junit.framework.TestCase;

public class AIStrategyTest extends TestCase{

	public void testOne() {
		//testing ai players play during tour tournaments strategy 2
		StoryDeck storyDeck = new StoryDeck();		//set up story deck		
		storyDeck.setTopCard("Tournament at Orkney");
		storyDeck.printDeck();
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		GameModel game;
		int ncards = 0;
		game = new GameModel(0, ncards, advDeck, storyDeck); //start with 1 players, with 10 cards
		
		//p0 ai
		//AbstractAI ai = new Strategy2();
		AbstractAI ai = new AI();
		ai.setTournamentParticipation(new TourParticipate2());
		ai.setTourPlay(new TourPlay2());
		
		
		Player aiplayer = new Player("p0-ai", ai);
		ai.setPlayer(aiplayer);
		
		for(int i = 0; i < ncards; i++) {
			aiplayer.drawCard(game.getAdvDeck());
		}
		
		// p1 second ai player
		AbstractAI ai2 = new AI();
		ai2.setTournamentParticipation(new TourParticipate2());
		ai2.setTourPlay(new TourPlay2());
		
		
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
		
		//set both human players 50 to test
		//test human and ai in tie breaker
		String cards50[] = {"sword", "horse", "excalibur"};
		p2.setHand(cards50);
		p3.setHand(cards50);
		
		//set ais to have 50 bp active
		aiplayer.setActiveHand(cards50);
		aiplayer2.setActiveHand(cards50);
		
		
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
		pres.playCard(AllyConditionsTest.find("sword", p2.getHand()).getID());
		pres.playCard(AllyConditionsTest.find("horse", p2.getHand()).getID());
		pres.playCard(AllyConditionsTest.find("excalibur", p2.getHand()).getID());
		pres.userInput(1); //done turn
		
		//set ai0 to have hand of 50bp 
		String aicards[] = {"lance","excalibur"};
		aiplayer.setHand(aicards);
		aiplayer2.setHand(aicards);
		
		//p3 human
		pres.playCard(AllyConditionsTest.find("sword", p3.getHand()).getID());
		pres.playCard(AllyConditionsTest.find("horse", p3.getHand()).getID());
		pres.playCard(AllyConditionsTest.find("excalibur", p3.getHand()).getID());
		pres.userInput(1); //done turn
		
		//ais play lance and excalibur to get 50 bp
		
		//tied, round 2 
		pres.userInput(1); //done turn p1human
		pres.userInput(1); //done turn p3human
		
		//both ai's won check they Ranked up
		assertEquals(true, aiplayer.getRank() == Rank.KNIGHT);
		assertEquals(true, aiplayer2.getRank() == Rank.KNIGHT);
	}
	
	public void testTwo() {
		//test strategy 2 on tour
		//test Game winning tournament with ai players strategy 2
		AdventureDeck ad = new AdventureDeck();
		StoryDeck d = new StoryDeck();
		int ncards = 0;
		GameModel m = new GameModel(0, ncards, ad, d); // 0 players 0 cards
		GamePresenter pres = new GamePresenter(m);
		
		//test player and ai player
		//p0 ai
		AbstractAI ai = new AI();
		ai.setTournamentParticipation(new TourParticipate2());
		ai.setTourPlay(new TourPlay2());
		
		
		Player aiplayer = new Player("p0-ai", ai);
		ai.setPlayer(aiplayer);
		
		//p1 human
		Player p1 = new Player("p1human");
		//make players Knights of table		
		aiplayer.addShields(22);
		p1.addShields(22);
		
		String cards[] = {"dagger"};
		aiplayer.setHand(cards);
		p1.setHand(cards);
		
		m.addPlayer(aiplayer);
		m.addPlayer(p1);
		m.playGameWinningTour();
		
		//p0 ai participates
		
		//p1 human participates
		pres.userInput(1); //yes
		
		//p1 turn
		pres.playCard(AllyConditionsTest.find("dagger",p1.getHand()).getID());
		pres.userInput(0); //done turn
			
		//check winners
		assertEquals(true, m.getPlayers().getPlayerAtIndex(0).getWon());
		assertEquals(true, m.getPlayers().getPlayerAtIndex(1).getWon());
	}
	
	public void testThree() {
		//testing strategy 1 on tournaments
		
		//If another player who can win/evolve by winning this tournament does participate
		//OR If I can win/evolve myself
		//Then: I play the strongest possible hand(including amour and allies)
		//----->testting this->Else: I play only weapons I have two or more instances of
		 
		
		//testing I play only weapons I have two or more instances of
		AdventureDeck ad = new AdventureDeck();
		String scards[] = {"tintagel"};
		StoryDeck d = new StoryDeck(scards);
		int ncards = 0;
		GameModel m = new GameModel(0, ncards, ad, d); // 0 players 0 cards
		GamePresenter pres = new GamePresenter(m);
		
		
		//p0 human
		Player p0 = new Player("p0human");
		//p1 ai
		AbstractAI ai = new AI();
		ai.setTournamentParticipation( new TourParticipate1());
		ai.setTourPlay(new TourPlay1());
		
		
		Player p1 = new Player("p1-ai", ai);
		ai.setPlayer(p1);
		//p2 human
		Player p2 = new Player("p2human");
		
		p0.addShields(4); //-> p0 is squire and sheilds = 4
		
		
		String cards[] = {"amour","amour","amour","sword","sword","sword","dagger","dagger","lance", "horse"};
		p1.setHand(cards);
		
		m.addPlayer(p0);
		m.addPlayer(p1);
		m.addPlayer(p2);
		m.beginTurn();
		
		//p0 does NOT participate
		pres.userInput(0);
		
		//p1 ai participates since p0 could win/evolve
		
		//p2 participates 
		pres.userInput(1);
		
		//Player turns playing cards
		
		//p1 ai plays -> 1amour, 1 sword,1dagger , since adventure deck not shuffled ai got test morgan de-fay for participating which was not played
		
		//p2 done
		pres.userInput(1);
		p1.printHand();
		assertEquals(true, p1.getHand().size() == 7 + 1); //plus one for Morgan de fay
	}

	public void testFour() {
		//testing strategy 1 on tournaments
		
		//If another player who can win/evolve by winning this tournament does participate
		//OR If I can win/evolve myself
		//Then: I play the strongest possible hand(including amour and allies)
		 
		
		AdventureDeck ad = new AdventureDeck();
		String scards[] = {"tintagel"};
		StoryDeck d = new StoryDeck(scards);
		int ncards = 0;
		GameModel m = new GameModel(0, ncards, ad, d); // 0 players 0 cards
		GamePresenter pres = new GamePresenter(m);
		
		Player p0 = new Player("p0");
		p0.addShields(3);
		
		AbstractAI ai = new AI();
		ai.setTournamentParticipation( new TourParticipate1());
		ai.setTourPlay(new TourPlay1());
		
		
		
		Player p1 = new Player("p1-ai", ai);
		ai.setPlayer(p1);
		String cards[] = {"amour","amour","amour","sword","sword","sword","dagger","dagger","lance", "horse"};
		p1.setHand(cards);
		
		m.addPlayer(p0);
		m.addPlayer(p1);
		
		//test If another player who can win/evolve by winning this tournament does participate
		//Then: I play the strongest possible hand(including amour and allies)
		
		m.beginTurn();
		
		//p0 participates
		p0.userInput(1);
		
		//p0 turn
		pres.userInput(1);//done turn
		
		//strong play
		assertEquals(6, p1.getHand().size()); //didn't play added card test of valor from joining tournament, deck was not shuffled
		p1.printHand();
		//other play would have played 3 cards. 8 left over
		
		
		// test If I can win/evolve myself
		//Then: I play the strongest possible hand(including amour and allies)
		
		//new game
		ad = new AdventureDeck();
		d = new StoryDeck(scards);
		m = new GameModel(0, ncards, ad, d);
		pres = new GamePresenter(m);
		
		p0 = new Player("p0");
		ai = new AI();
		ai.setTournamentParticipation( new TourParticipate1());
		ai.setTourPlay(new TourPlay1());
		
		
		p1 = new Player("p1-ai", ai);
		ai.setPlayer(p1);
		m.addPlayer(p0);
		m.addPlayer(p1);
		
		p1.setHand(cards);
		m.beginTurn();
		p1.addShields(3);
		
		//p0 participates
		pres.userInput(1);
		
		//p0 turn
		pres.userInput(1);//done turn
		
		//strong play
		assertEquals(6, p1.getHand().size()); //didn't play added card test of valor from joining tournament, deck was not shuffled
		p1.printHand();
		
		
	} 
	
	public void testParticipation() {
		//test Participation strat 1
		AdventureDeck ad = new AdventureDeck();
		String scards[] = {"tintagel"};
		StoryDeck d = new StoryDeck(scards);
		int ncards = 0;
		GameModel m = new GameModel(0, ncards, ad, d); // 0 players 0 cards
		GamePresenter pres = new GamePresenter(m);
		
		
		Player p0 = new Player("p0");
		
		TourParticipation t = new TourParticipate1();
		AbstractAI ai = new AI();
		ai.setTournamentParticipation(t);
		
		Player p1 = new Player("p1-ai", ai);
		ai.setPlayer(p1);
		
		m.addPlayer(p0);
		m.addPlayer(p1);
		m.beginTurn();
		
		//someone else can Rank
		pres.userInput(1); //p0 participates
		p0.addShields(4);
		
		assertEquals(true, p1.getAI().DoIParticipateInTournament());
		
		//I can rank
		
		//new game
		ad = new AdventureDeck();
		d = new StoryDeck(scards);
		m = new GameModel(0, ncards, ad, d);
		pres = new GamePresenter(m);
		
		p0 = new Player("p0");
		//ai = new Strategy1();
		ai = new AI();
		ai.setTournamentParticipation( new TourParticipate1());
		ai.setTourPlay(new TourPlay1());
		
		p1 = new Player("p1-ai", ai);
		ai.setPlayer(p1);
		m.addPlayer(p0);
		m.addPlayer(p1);
		
		//ai has 3 shields, avail = 1 joiner + 1 bonus
		p1.addShields(3);
		
		m.beginTurn();
		pres.userInput(1); //p0 participates
		
		assertEquals(true, p1.getAI().DoIParticipateInTournament());
		
		
		//No on can rank no participation
		System.out.println("-------------------------------------------------");
		ad = new AdventureDeck();
		d = new StoryDeck(scards);
		m = new GameModel(0, ncards, ad, d);
		pres = new GamePresenter(m);
		
		p0 = new Player("p0");
		ai = new AI();
		ai.setTournamentParticipation( new TourParticipate1());
		ai.setTourPlay(new TourPlay1());
		
		p1 = new Player("p1-ai", ai);
		ai.setPlayer(p1);
		m.addPlayer(p0);
		m.addPlayer(p1);
		
		m.beginTurn();
		pres.userInput(1); //p0 participates
		
		assertEquals(false, p1.getAI().DoIParticipateInTournament());
	}
	
	public void testDoSponsor() {
		//Do sponsor yes/no ?
		
		//set up story deck
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(0, 0, advDeck, storyDeck);
		
		//this would be AbstractAI but two implementations until resolved
		AI ai = new AI();
		Player p0 = new Player("p0-ai", ai);
		ai.setSponsor(new DoSponsor1());
		ai.setPlayer(p0);
		String cards[] = {"thieves", "boar", "saxonKnight", "robberKnight","blackKnight"};  //5, 15/5, 15/25, 15, 25
		p0.setHand(cards);
		
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		Player p3 = new Player("p3");
		
		GamePresenter pres = new GamePresenter(game);
		game.addPlayer(p0);
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		pres.getModel().beginTurn();
		
		assertEquals(true, ai.doSp());
		
		//see setup works two foes and test
		
		storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		game = new GameModel(0, 0, advDeck, storyDeck);
		ai = new AI();
		p0 = new Player("p0-ai", ai);
		ai.setSponsor(new DoSponsor1());
		ai.setPlayer(p0);
		String cards1[] = {"thieves", "boar", "temptation"};  //5, 15/5, 
		p0.setHand(cards1);
		
		p1 = new Player("p1");
		p2 = new Player("p2");
		p3 = new Player("p3");
		
		pres = new GamePresenter(game);
		game.addPlayer(p0);
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		pres.getModel().beginTurn();
		
		assertEquals(true, ai.doSp());
		
		//no one can rank, and I can not setup valid cards
		
		storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		game = new GameModel(0, 0, advDeck, storyDeck);
		ai = new AI();
		p0 = new Player("p0-ai", ai);
		ai.setSponsor(new DoSponsor1());
		ai.setPlayer(p0);
		String cards11[] = {"thieves", "boar",};  //5, 15/5, 
		p0.setHand(cards11);
		
		p1 = new Player("p1");
		p2 = new Player("p2");
		p3 = new Player("p3");
		
		pres = new GamePresenter(game);
		game.addPlayer(p0);
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		pres.getModel().beginTurn();
		
		assertEquals(false, ai.doSp());
	}	
}
