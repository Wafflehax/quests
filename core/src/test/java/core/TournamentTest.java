package core;

import com.comp_3004.quest_cards.cards.AdventureDeck;

import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Player;
import junit.framework.TestCase;


public class TournamentTest extends TestCase{
	
	public void testTourPart() {
		
		//set up story deck
				String[] sd = {"orkney"};
				StoryDeck storyDeck = new StoryDeck(sd);
				
				//set up adventure deck
				AdventureDeck advDeck = new AdventureDeck();
				advDeck.shuffle();
				
				GameModel game;
				game = new GameModel(4, 0, advDeck, storyDeck);
				
				//set up hands
				String[] hand0 = {"thieves", "dagger", "boar"};
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
				
				//userInput(0) ==> false
				// 1 ==> true
				//asking players if they want to participate
				System.out.println(game.getcurrentTurn().getName() + " Participate in Tour " + game.getcurrentTurn().getTour().getCurTour().getName() + " ?");
				
				pres.userInput(1);
				pres.userInput(1);
				pres.userInput(1); 
				pres.userInput(0); 
				
				pres.userInput(1); //done turn
				pres.userInput(1); //done turn
				pres.userInput(1); //done turn
				
	}
	
	
	/*public void testTourInit(){			
		
		//test TourInit pushes right things on state stack
		GameModel m = new GameModel(4);   // init with 4 players
	    GameController c = new GameController(m);
		TournamentCard t = new TournamentCard("Tournament at Camelot", 3);
		m.setStory(t);
		
		
		//test that TourInit pushed correct AskPartic states 4 Ask P, Start Tour First Time
		m.pushSt(new TourInit(c));
		m.StateMsg();
		String askp = "com.comp_3004.quest_cards.core.states.TourAskParticipation";
		Stack<State> act = m.state;
		String ac = act.pop().getClass().getName(); // ask Tour
		assertEquals(ac, askp); 
		ac = act.pop().getClass().getName(); // ask Tour
		assertEquals(ac, askp); 
		ac = act.pop().getClass().getName(); // ask Tour
		assertEquals(ac, askp); 
		ac = act.pop().getClass().getName(); // ask Tour
		assertEquals(ac, askp); 
		ac = act.pop().getClass().getName(); // ask Tour
		assertEquals(ac, "com.comp_3004.quest_cards.core.states.TourStartFirstTime"); // startTour state
		
		
		//testing Tourjoiners counter right amount of joiners
		m = new GameModel(4);
		c = new GameController(m);
		m.pushSt(new TourInit(c));
		m.setStory(t);
		m.StateMsg();
		assertEquals(c.m.getJoiners(), 0);
		c.yes();  //joins
		assertEquals(c.m.getJoiners(), 1);
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		c.doneTurn(); // p0 partic and done turn
		c.no(); // didn't join
		assertEquals(c.m.getJoiners(), 1); // didnt join
		c.doneTurn();
		c.yes(); //joins
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		assertEquals(c.m.getJoiners(), 2);
		c.doneTurn();
		c.no();
		assertEquals(c.m.getJoiners(), 2);
		
		//check TourStartFirstTime proceeds when have required amount of players
		//have 2 players wanting to play
		ac = m.state.pop().getClass().getName(); // player turn
		assertEquals(ac,"com.comp_3004.quest_cards.core.states.TourPlayerTurn");
		ac = m.state.pop().getClass().getName(); // player turn
		assertEquals(ac,"com.comp_3004.quest_cards.core.states.TourPlayerTurn");
		ac = m.state.pop().getClass().getName(); // player turn
		assertEquals(ac,"com.comp_3004.quest_cards.core.states.TourRoundEndEvaluation");
		
		//check TourStartFirstTime does not proceed, does not have required amount of players
		//have 1 player wanting to play
		m = new GameModel(4);
		c = new GameController(m);
		m.pushSt(new TourInit(c));
		m.setStory(t);
		m.StateMsg();
		c.no();
		c.no();
		c.no();
		c.yes();
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		assertEquals(c.m.getJoiners(), 1);
		c.doneTurn();
		assertEquals(m.state.isEmpty(), true);
		
		
		
		//static cards
		AdventureCard sword1 = new WeaponCard("Sword", 10);
		AdventureCard amour1 = new AmourCard();
		AdventureCard amour2 = new AmourCard();
		AdventureCard Theives1 = new FoeCard("Thieves", 5);
		
		//test handPress(Card c) during PlayerTurn valid hand, invalid
		//test playing Two of same weapons, amours, test playing a card that is not Weapons,Ally,Amour
		m = new GameModel(4);
		c = new GameController(m);
		m.pushSt(new TourInit(c));
		m.setStory(t);
		m.StateMsg();
		c.no();
		c.no();
		c.yes();
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		c.doneTurn();
		c.yes();
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		c.doneTurn();
		assertEquals(c.m.getJoiners(), 2);
		//two players p2,p3
		
		//test playing two of same weapons
		Player p2 = m.getcurrentTurn();
		LinkedList<AdventureCard> cardsp2 = new LinkedList<AdventureCard>();
		sword1.setOwner(p2);
		cardsp2.add(sword1);
		String sw[] = {"sword"};
		p2.setActiveHand(sw);
		assertEquals(false, c.handPress(sword1)); //can't play Sword already have it
		
		//test playing two amours
		LinkedList<AdventureCard> cardsp2g = new LinkedList<AdventureCard>();
		amour1.setOwner(p2);
		amour2.setOwner(p2);
		cardsp2g.remove(sword1);
		cardsp2g.add(amour1);
		cardsp2g.add(amour2);
		p2.setHand(cardsp2g);		
		assertEquals(true, c.handPress(amour1)); //can't play Sword already have it
		assertEquals(false, c.handPress(amour2)); //can't play Sword already have it
		c.doneTurn();	
		
		
		//test playing a Foe card
		Player p3 = c.m.getcurrentTurn();
		LinkedList<AdventureCard> cardsp3 = new LinkedList<AdventureCard>();
		cardsp3.add(Theives1);
		Theives1.setOwner(p3);
		p3.setHand(cardsp3);
		assertEquals(false, c.handPress(Theives1));
		
		
		//Test player doneTurn with a hand over 12
		String ammour[] = {"amour","amour","amour","amour","amour","amour","amour","amour","amour","amour","amour","amour","amour"};
		p3.setActiveHand(ammour);
		assertEquals(false, c.doneTurn());
	}	
	
	public void testTour2() {			
		GameModel m = new GameModel(4);   // init with 4 players
	    GameController c = new GameController(m);
		TournamentCard t = new TournamentCard("Tournament at Camelot", 3);
		m.setStory(t);
		m.pushSt(new TourInit(c));
		m.StateMsg();
		
		
		//test playing card you don't own
		AdventureCard amour1 = new AmourCard();
		
		c.yes();
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		c.doneTurn();
		c.yes();
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		c.doneTurn();
		c.no();
		c.no();
		assertEquals(false, c.handPress(amour1));

		//testing calculation of battle points
		//no special abilities (dependent cards), Weapons,Ally,Amour
		TourRoundEndEvaluation ev = new TourRoundEndEvaluation(c); 
		Player p0 = new Player("Player 0");
		String cards1[] = {"horse","sword","excalibur","lance","dagger","battleAx","pellinore","merlin"};
		p0.setActiveHand(cards1);
	
		assertEquals(105 ,ev.calcBattlePoints(p0));
		//testing calculating battle points	
	}
	
	public void testTour3() {
		//tests both weapons and amours and discarded at the end of a tournament with a single winer at 1 round
		GameModel m = new GameModel(4);   // init with 4 players
	    GameController c = new GameController(m);
		TournamentCard t = new TournamentCard("Tournament at Camelot", 3);
		m.setStory(t);
		m.pushSt(new TourInit(c));
		m.StateMsg();
		
		Player p0 = c.m.getcurrentTurn();  
		WeaponCard horse = new WeaponCard("Horse", 10);
		AmourCard amour = new AmourCard();
		LinkedList<AdventureCard> cardsp0 = new LinkedList<AdventureCard>();
		cardsp0.add(horse);
		cardsp0.add(amour);
		amour.setOwner(p0);
		horse.setOwner(p0);
		p0.setHand(cardsp0);
		
		c.yes();
		c.doneTurn();
		c.yes();
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		c.doneTurn();
		c.no();
		c.no();
		
		//p0,p1 playing Tour
		c.handPress(horse);
		c.doneTurn();
		//p1 plays nothing
		c.doneTurn();
		assertEquals(false, p0.getActive().contains(horse));
		assertEquals(false, p0.getActive().contains(amour));		
	}
	
	public void testTour4() {
		//tests only weapons discarded at a tie during round 1 and not amours
		//tests both weapons,amours discarded at round 2, round 2 is end of tournament no matter tie, single winner
		
		
		//testing tie first stage only weapons discarded not amours
		GameModel m = new GameModel(4);   // init with 4 players
	    GameController c = new GameController(m);
		TournamentCard t = new TournamentCard("Tournament at Camelot", 3);
		m.setStory(t);
		m.pushSt(new TourInit(c));
		m.StateMsg();
		
		Player p0 = c.m.getcurrentTurn();  
		Player p1;
		WeaponCard axe = new WeaponCard("Battle-Ax", 15);
		WeaponCard dagger = new WeaponCard("Dagger", 5);		
		AmourCard amour = new AmourCard();
		WeaponCard lance = new WeaponCard("Lance", 20);
		LinkedList<AdventureCard> cardsp0 = new LinkedList<AdventureCard>();
		LinkedList<AdventureCard> cardsp1 = new LinkedList<AdventureCard>();
		
		dagger.setOwner(p0);
		amour.setOwner(p0);
		lance.setOwner(p0);
		cardsp0.add(dagger);
		cardsp0.add(amour);
		cardsp0.add(lance);
		p0.setHand(cardsp0);
		
		//set p0 to play amour, dagger
		//p1 plays battle-axe , which ties them and check right cards discarded
		
		
		c.yes();
		c.doneTurn();
		
		p1 = m.getcurrentTurn();
		axe.setOwner(p1);
		cardsp1.add(axe);
		p1.setHand(cardsp1);
		
		
		c.yes();
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		c.doneTurn();
		c.no();
		c.no();
		
		//p0,p1 playing Tour
		
		//make tie
		c.handPress(amour);
		c.handPress(dagger);
		c.doneTurn();
		c.handPress(axe);
		c.doneTurn();
		
		//test amour stays and weapons gone
		assertEquals(true, p0.getActive().contains(amour));
		assertEquals(false, p0.getActive().contains(dagger));
		assertEquals(false, p1.getActive().contains(axe));
		
		//p0 plays lance and still has amour
		c.handPress(lance);
		c.doneTurn();
		
		//p1 plays nothing
		c.doneTurn();
		
		
		//test amours and weapons are gone
		assertEquals(false, p0.getActive().contains(amour));
		assertEquals(false, p0.getActive().contains(lance));
	}
	*/
}