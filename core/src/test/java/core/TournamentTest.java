package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import javax.naming.spi.DirStateFactory.Result;

import com.badlogic.gdx.Game;
import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.Player;
import com.comp_3004.quest_cards.core.Players;
import com.comp_3004.quest_cards.core.Tournament;
import com.comp_3004.quest_cards.core.GameModel.cardModes;

import junit.framework.TestCase;

public class TournamentTest extends TestCase{
	/*
	public void testTournament1() {
		//testing players setting their participation, setTournamentParticupation
		int numPlayers = 4;
		GameModel game = new GameModel();
		Stack<Player> stack = new Stack<Player>();
		Player p0 = new Player("P0");
		Player p1 = new Player("P1");
		Player p2 = new Player("P2");
		Player p3 = new Player("P3");
		stack.add(p0);stack.add(p1);stack.add(p2);stack.add(p3);
		game.addPlayer(p0);
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		Players players = game.getPlayers();
		
		for(int i = 0; i < numPlayers-1; i++, players.next()) {
			Player current = game.getcurrentTurn();
			current.participateTour(true);
			boolean result = current.participantInTournament() && stack.get(i) == current;
			assertEquals(true, result);
			
			current.participateTour(false);
			result = current.participantInTournament();
			assertEquals(true, stack.get(i) == current);
			assertEquals(false, result);
		}
	}
	
	
	public void testTournament2() {
		int time = 100;
		//Test Tournament joiners having too many cards, testing discardCardIfTooMany()
		int numPlayers = 4;
		final GameModel game = new GameModel();
		game.initPlayersStart(numPlayers);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
			game.startGame();
			}
		});
		thread.start();
		
		sleep(time);
		game.setParticipation(false); // p0 no
		sleep(time);
		game.setParticipation(false);//p1 no
		sleep(time);
		Player player = game.getPlayers().current();
		game.setParticipation(true);  //p2 yes  player has to many cards by 1 //game blocked until discarded
		assertEquals(true, player.tooManyHandCards());
		sleep(time);
		game.cardPressed(0);
		assertEquals(false, player.tooManyHandCards()); //discarded correct number now
		game.done(); //done turn	
	}
	*/ 
	public void testTournament3() {
		// testing playCard() Playing a Weapon Card during turn, check was added correctly to right player
		int time = 100;
		//Test Tournament joiners having too many cards, testing discardCardIfTooMany()
		int numPlayers = 3;
		final GameModel game = new GameModel();
		game.initPlayersStart(numPlayers);
		
		Player p3 = new Player("Player 3");
		LinkedList<AdventureCard> cards = new LinkedList<AdventureCard>();
		p3.participateTour(true);
		WeaponCard excalibur = new WeaponCard("Excalibur", 30);
		cards.add(excalibur);
		p3.setHand(cards);
		game.addPlayer(p3);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
			game.startGame();
			}
		});
		thread.start();
		
		sleep(time);
		game.setParticipation(false); // p0 no
		sleep(time);
		game.setParticipation(false);//p1 no
		sleep(time);
		game.setParticipation(true);  //p2 yes  player has to many cards by 1 //game blocked until discarded
		sleep(time);
		game.cardPressed(0);
		game.done(); //done turn
		sleep(time);
		game.setParticipation(true);  //p3 yes good hand <=12
		sleep(time);
		//now player2's turn
		game.cardPressed(0);
		game.done();
		sleep(time);
		Player curr = game.getMatch().getPlayers().current();
		game.cardPressed(0);
		sleep(time);
		LinkedList<AdventureCard> cards2 = curr.getActive();
		boolean r = (AdventureCard)cards2.get(0) == excalibur;
		assertEquals(true, r);		
	}
	
	public void sleep(int milisecs) {
		try {
			Thread.sleep(milisecs); // sleeping to wait for game initialization
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	
}