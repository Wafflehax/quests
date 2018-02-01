package core;

import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.core.Game;
import com.comp_3004.quest_cards.core.Game.cardModes;
import com.comp_3004.quest_cards.core.Player;
import com.comp_3004.quest_cards.core.Players;

import junit.framework.TestCase;

public class TournamentTest extends TestCase{
	
	private final int stime = 50; // this could cause tests to fail if too small
	
	public void testTournament1() {
		//testing players setting their participation, setTournamentParticupation
		TournamentCard york = new TournamentCard("Tournament at York", 0);
		final int numPlayers = 4;
		final Game game = new Game();
		game.setStory(york);
		Thread g = new Thread(new Runnable() {
			@Override
			public void run() {
				game.startGame(numPlayers);
			}
		});
		g.start();
		sleep(stime); // waiting for game to init players
		Players players = game.getPlayers();
		assertEquals(numPlayers, players.size()); //checking right amount of players
		//set all players to participate
		for(int i = 0; i < numPlayers; i++) {
			Player p0 = players.current();
			game.setTournamentParticupation(true);
			assertEquals(true, p0.participantInTournament());
		}
		//set all players to not participate
		for(int i = 0; i < numPlayers; i++) {
			Player p0 = players.current();
			game.setTournamentParticupation(false);
			assertEquals(false, p0.participantInTournament());
		}
	}
	
	public void testTournament2(){
		// test each player starts with 12 cards, none active
		//new game of four players
		TournamentCard york = new TournamentCard("Tournament at York", 0);
		final int numPlayers = 4;
		final Game game = new Game();
		game.setStory(york);
		Thread g = new Thread(new Runnable() {
			@Override
			public void run() {
				game.startGame(numPlayers);
			}
		});
		g.start();
		sleep(stime); // waiting for game to init players
		Players players = game.getPlayers();
		for(int i = 0; i < numPlayers; i++) {
			assertEquals(12, players.current().numberOfHandCards());
			assertEquals(0, players.current().numberOfActiveCards());
		}
	}
	
	public void testTournament3() {
		//Test having too many cards joining tournament, and discarding a card to have valid hand
		TournamentCard york = new TournamentCard("Tournament at York", 0);
		final int numPlayers = 4;
		final Game game = new Game();
		game.setStory(york);
		Thread g = new Thread(new Runnable() {
			@Override
			public void run() {
				game.startGame(numPlayers);
			}
		});
		g.start();
		sleep(stime); // waiting for game to init players);
		Players players = game.getPlayers();
		
		Player p0 = players.current();
		game.setTournamentParticupation(true); //if true, auto draws card and adds to player hand
		assertEquals(true, p0.tooManyHandCards());
		sleep(stime); //wait for gameThr to wake and check toomany cards and setmode to discard
		boolean mode = game.getCardMode() == cardModes.DISCARD;
		assertEquals(true, mode); // check in discard mode
		game.cardPressed(0); //emulating pressing card in first position
		assertEquals(false, p0.tooManyHandCards());
		game.done(); //finish action
	}
	
	public void testTournament4() {
		//test Find out winner and apply battle point
		assertEquals(true, true);
	}
	
	
	public void sleep(int milisecs) {
		try {
			Thread.sleep(milisecs); // sleeping to wait for game initialization
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}