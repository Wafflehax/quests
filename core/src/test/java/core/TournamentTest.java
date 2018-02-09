package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.log4j.Logger;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameController;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.Player;
import com.comp_3004.quest_cards.core.Players;
import com.comp_3004.quest_cards.core.states.TourAskParticipation;
import com.comp_3004.quest_cards.core.states.TourInit;
import com.comp_3004.quest_cards.core.states.TourRoundEndEvaluation;
import com.comp_3004.quest_cards.core.states.State;
import com.comp_3004.quest_cards.core.states.TourStartFirstTime;

import junit.framework.TestCase;
import utils.IntPlayerPair;
import utils.utils;

public class TournamentTest extends TestCase{
	public void testTourInit(){
		
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
		
		//check TourStartFirstTime does not proceed does not have required amount of players
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
		//AdventureCard sword2 = new WeaponCard("Sword", 10);
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
		p2.setActiveHand(cardsp2);
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
		LinkedList<AdventureCard> p3Active = new LinkedList<AdventureCard>();
		for(int i = 0; i < 13; i++){
			p3Active.add(new AmourCard());	
		}
		p3.setActiveHand(p3Active);
		assertEquals(false, c.doneTurn());
	}	
}