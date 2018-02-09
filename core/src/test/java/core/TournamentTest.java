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
import com.comp_3004.quest_cards.core.states.State;
import com.comp_3004.quest_cards.core.states.TourStartFirstTime;

import junit.framework.TestCase;
import utils.IntPlayerPair;
import utils.utils;

public class TournamentTest extends TestCase{
	
	public void testunfinished() {

		GameModel m = new GameModel(4);   
	    GameController c = new GameController(m);
		
		TournamentCard t = new TournamentCard("Tournament at Camelot", 3);
		m.setStory(t);
		//assuming players where intialized and drew a Story card that was a tournament
		
		m.pushSt(new TourInit(c));
		m.StateMsg();
		
		c.yes(); // player 0 has too many cards 13 > 12
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		c.doneTurn();
		c.yes();
		c.disCardPress(m.getPlayers().current().getHand().getFirst());
		c.doneTurn();
		c.no();
		c.no();
		//Tour Started
		//Player one turn
		m.getcurrentTurn().addShields(7);// advance rank so they win in calc pts after
		c.doneTurn(); //p0 plays nothing okay
		c.doneTurn(); //p1 nothing
	}
	
	
	/* Not finished testing
	public void testTourStartFirstTime() {
		Test if Tour started
		GameModel m = new GameModel();   
	    GameController c = new GameController(m);
		
		m.initPlayersStart(4, 12);
		TournamentCard t = new TournamentCard("Tournament at Camelot", 3);
		c.m.setStory(t);
		c.m.pushSt(new TourStartFirstTime(c)); 
		
		int np = m.getNumPlayers();
		for(int i = 0; i < np; i++) {
			State st = new Ask_T_Part(c);
			m.pushSt(st);
		}
		//Pushed player ask staes followed by TourStartFirst Time
		//test tour started
		m.prevPlayer(); // state = ask-tour advances next player at start
		m.getState().msg(); // Starts the state stack
		
	}
	*/
	
}