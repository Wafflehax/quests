package core;
import java.util.LinkedList;
import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AllyObserver;
import com.comp_3004.quest_cards.cards.AllySubjectObserver;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.QuestCardSubject;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.TestObserver;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.GamePresenter;
import com.comp_3004.quest_cards.player.Players;

import junit.framework.TestCase;

public class AllyConditionsTest extends TestCase {
	
	/*
	public void testOne() {
		//Test behavour of Sir Tristian, Queen Iseult special ability activation
		
		GameModel m = new GameModel(4);
		AdventureDeck ad = m.getAdvDeck();
		ad.printDeck();
		AllySubjectObserver qu = (AllySubjectObserver)findCard(107, m.getPlayers(), ad);
		AllySubjectObserver tris = (AllySubjectObserver)findCard(103, m.getPlayers(), ad);
		//register cards as observers
		qu.register(tris);
		tris.register(qu);
		//both cards are in play
		qu.setState(State.PLAY);
		tris.setState(State.PLAY);
		assertEquals(true, qu.isActivated());
		assertEquals(true, tris.isActivated());
		//one cards is not in play anymore
		tris.setState(State.DISCARD);
		//both cards abilities deactivated
		assertEquals(false, qu.isActivated());
		assertEquals(false, tris.isActivated());
		//card reactivated
		tris.setState(State.QUEST);
		//both card activated
		assertEquals(true, qu.isActivated());
		assertEquals(true, tris.isActivated());
		
		//switching to other inplay state
		qu.setState(State.HAND);
		tris.setState(State.HAND);
		
		qu.setState(State.PLAY);
		tris.setState(State.PLAY);
		//both cards should be active
		assertEquals(true, qu.isActivated());
		assertEquals(true, tris.isActivated());
		
		//removes tristian as an observer on the queen, queen still observing tristian
		qu.deregister(tris);
		//once deregistered both deactivated
		assertEquals(false, qu.isActivated());
		assertEquals(false, tris.isActivated());
		// queen no longer getting tristian updates
		tris.deregister(qu);
		assertEquals(false, qu.isActivated());
		assertEquals(false, tris.isActivated());
		
	}
	
	public void testTwo() {
		//testing QuestCard and Ally special ability activation. Quest is subject, Ally observer
		Stack<StoryCard> d = new Stack<StoryCard>();
		CardSpawner s = new CardSpawner();
		d.add(s.spawnStoryCard("testOfTheGreenKnight"));
		
		StoryDeck sdeck = new StoryDeck();
		AdventureDeck ad = new AdventureDeck();
		GameModel m = new GameModel(4, 0, ad, sdeck); //everyone starting with 0 cards
		
		QuestCardSubject testgknight = (QuestCardSubject)find("Test of the Green Knight", sdeck);
		AllyObserver gawain = (AllyObserver) find("Sir Gawain", ad);	
		
		//Quest was played
		testgknight.setPlayed(true);
		//gawain was activated not matter if it was played or not
		assertEquals(true, gawain.activated());
		//gawain discarded still active while quest card is active
		gawain.setState(State.DISCARD);
		assertEquals(true, gawain.activated());
		//Quest card discarded to story deck
		testgknight.setPlayed(false);
		assertEquals(false, gawain.activated());
	}
	
	public void testThree() {
		//tests allies have correct ability points, bids and battle points
		Stack<StoryCard> d = new Stack<StoryCard>();
		CardSpawner s = new CardSpawner();
		d.add(s.spawnStoryCard("testOfTheGreenKnight"));
		
		StoryDeck sdeck = new StoryDeck();
		AdventureDeck ad = new AdventureDeck();
		GameModel m = new GameModel(4, 0, ad, sdeck); //everyone starting with 0 cards
		
		AllySubjectObserver qu = (AllySubjectObserver) find("Queen Iseult", ad);
		AllySubjectObserver tris = (AllySubjectObserver) find("Sir Tristan", ad);	
		
		QuestCardSubject testgknight = (QuestCardSubject)find("Test of the Green Knight", sdeck);
		AllyObserver gawain = (AllyObserver) find("Sir Gawain", ad);	
		
		QuestCardSubject dqueenhonor = (QuestCardSubject)find("Defend the Queen's Honor", sdeck);
		AllyObserver lancelot = (AllyObserver) find("Sir Lancelot", ad);	
		
		QuestCardSubject grailho = (QuestCardSubject)find("Search for the Holy Grail", sdeck);
		AllyObserver perciv = (AllyObserver) find("Sir Percival", ad);	
		
		QuestCardSubject questbeat = (QuestCardSubject)find("Search for the Questing Beast", sdeck);
		AllyObserver pellin = (AllyObserver) find("King Pellinore", ad);	
		
		
		//None are activated Make sure they have correct battle points, bids
		assertEquals(true, (qu.getBattlePts() == 0 && qu.getBids() == 2));
		assertEquals(true, (tris.getBattlePts() == 10 && tris.getBids() == 0));
		
		qu.setState(State.PLAY);
		tris.setState(State.PLAY);
		//Check activated battle points, bids
		assertEquals(true, (qu.getBattlePts() == 0 && qu.getBids() == 4));
		assertEquals(true, (tris.getBattlePts() == 20 && tris.getBids() == 0));
		
		//Not Active state
		testgknight.setPlayed(false);
		assertEquals(true, (gawain.getBattlePts() == 10 && gawain.getBids() == 0));
		//Active state
		testgknight.setPlayed(true);
		assertEquals(true, (gawain.getBattlePts() == 20 && gawain.getBids() == 0));
		
		dqueenhonor.setPlayed(false);
		assertEquals(true, (lancelot.getBattlePts() == 15 && lancelot.getBids() == 0));
		dqueenhonor.setPlayed(true);
		assertEquals(true, (lancelot.getBattlePts() == 25 && lancelot.getBids() == 0));
		
		grailho.setPlayed(false);
		assertEquals(true, (perciv.getBattlePts() == 5 && perciv.getBids() == 0));
		grailho.setPlayed(true);
		assertEquals(true, (perciv.getBattlePts() == 20 && perciv.getBids() == 0));
		
		questbeat.setPlayed(false);
		assertEquals(true, (pellin.getBattlePts() == 10 && pellin.getBids() == 0));
		questbeat.setPlayed(true);
		assertEquals(true, (pellin.getBattlePts() == 10 && pellin.getBids() == 4));	
	}
	
	public void testFour() {
		//tests  Test of the Questing Beast conditional bids
		//Test of the Questing Beast has minimum 4 bids on Questing Beast Quest
		
		Stack<StoryCard> d = new Stack<StoryCard>();
		CardSpawner s = new CardSpawner();
		d.add(s.spawnStoryCard("testOfTheGreenKnight"));
		
		StoryDeck sdeck = new StoryDeck();
		AdventureDeck ad = new AdventureDeck();
		GameModel m = new GameModel(4, 0, ad, sdeck); //everyone starting with 0 cards
		QuestCardSubject questbeat = (QuestCardSubject)find("Search for the Questing Beast", sdeck);
		TestObserver tesbeast = (TestObserver) find("Test of the Questing Beast", ad);	
		AllyObserver pellin = (AllyObserver) find("King Pellinore", ad);	//already registered
		
		//test activated 
		questbeat.setPlayed(true);
		assertEquals(true, tesbeast.activated());
		assertEquals(true, pellin.activated());
		assertEquals(true, (tesbeast.getBattlePts() == 0 && tesbeast.getMinBid() == 4));
		
		
		//test deactivated 
		questbeat.setPlayed(false);
		assertEquals(false, tesbeast.activated());
		assertEquals(false, pellin.activated());
		assertEquals(true, (tesbeast.getBattlePts() == 0 && tesbeast.getMinBid() == 0));
		
	}
	*/
	
	//testing tristan/iseult in an actual game
	public void testFive() {	
		StoryDeck storyDeck = new StoryDeck();
		storyDeck.setTopCard("Boar Hunt");
		storyDeck.setTopCard("Boar Hunt");
		
		//set up adventure deck
		AdventureDeck advDeck = new AdventureDeck();
		advDeck.shuffle();
		
		GameModel game;
		game = new GameModel(4, 0, advDeck, storyDeck);
		AllySubjectObserver qu = (AllySubjectObserver) find("Queen Iseult", advDeck);
		AllySubjectObserver tris = (AllySubjectObserver) find("Sir Tristan", advDeck);
		advDeck.printDeck();
		
		//set up hands
		String[] hand1 = {"blackKnight", "temptation"};
		String[] hand2 = {"iseult", "excalibur", "lance"};
		game.getPlayerAtIndex(0).pickCard("Sir Tristan", advDeck);
		game.getPlayerAtIndex(1).setHand(hand1);
		game.getPlayerAtIndex(2).setHand(hand2);
		game.getPlayerAtIndex(2).pickCard("Queen Iseult", advDeck);
		
		GamePresenter pres = new GamePresenter(game);
		pres.getModel().beginTurn();
		
		//sponsorship
		pres.userInput(0);
		pres.userInput(1); //player 1 sponsors
		
		//set up
		pres.playCard(154, 0);	//black knight
		pres.playCard(155, 1);	//test of temptation
		pres.userInput(1);
		
		//participation
		pres.userInput(1);
		pres.userInput(1);
		pres.userInput(1);
		
		//stage 0
		pres.playCard(qu.getID(), -1); 	//player 2 plays iseult
		pres.playCard(618, -1);	//player 2 plays excalibur
		pres.userInput(1);
		pres.userInput(1);	//player 3 plays nothing
		game.getcurrentTurn().printHand();
		pres.playCard(tris.getID(), -1);	//player 0 plays tristan
		pres.userInput(1);
		//Conditional battle points kick in when both cards are put in play state
		
		/* player 2 and player 0 should move on to next stage, but the conditional battlepoints
		 * for tristan do not kick in, so only player 2 is moving on to the next stage, and Queen
		 * Iseults bids should be at 4 since Tristan is in play
		 */
		assertEquals(4, ((AllyCard)game.getcurrentTurn().getActive().get(0)).getBids());
		assertEquals(2, game.getQuest().getParticipants().size());
		
	}
	
	private AdventureCard find(String n, AdventureDeck d) {
		for(AdventureCard c: d.getDeck()) {
			if(c.getName().equalsIgnoreCase(n))
				return c;	
		}
		return null;
	}
	
	
	private StoryCard find(String n, StoryDeck d) {
		for(StoryCard c: d.getDeck()) {
			if(c.getName().equalsIgnoreCase(n))
				return c;	
		}
		return null;
	}
	
	
	public static AdventureCard findCard(int id, Players p, AdventureDeck d) {
		
		if(p.size() > 0) {
			AdventureCard cc = find(id, d);
			if(cc == null) {
				for(int i = 0; i < p.getPlayers().size(); i++) {
					if(cc == null)
						cc = find(id, p.getPlayers().get(i).getHand());
					if(cc == null)
						cc = find(id, p.getPlayers().get(i).getActive());
					if(cc != null)
						return cc;
				}	
			}
			return cc;
		}
		
		return null;
	}
	
	public static AdventureCard find(int id, AdventureDeck d) {
		for(AdventureCard c: d.getDeck()) {
			if(c.getID() == id)
				return c;	
		}
		return null;
	}
	
	public static AdventureCard find(int id, LinkedList<AdventureCard> d) {
		for(AdventureCard c: d) {
			if(c.getID() == id)
				return c;	
		}
		return null;
	}
	
}
