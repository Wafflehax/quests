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
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.Player;
import com.comp_3004.quest_cards.core.Players;
import com.comp_3004.quest_cards.core.Tournament;
import com.comp_3004.quest_cards.core.GameModel.cardModes;

import junit.framework.TestCase;
import utils.IntPlayerPair;
import utils.utils;

public class TournamentTest extends TestCase{
	
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
		int numCards = 12;
		final GameModel game = new GameModel();
		game.initPlayersStart(numPlayers, numCards);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
			game.startGameTournamentTest();
			}
		});
		thread.start();
		
		utils.sleep(time);
		game.setParticipation(false); // p0 no
		utils.sleep(time);
		game.setParticipation(false);//p1 no
		utils.sleep(time);
		Player player = game.getPlayers().current();
		game.setParticipation(true);  //p2 yes  player has to many cards by 1 //game blocked until discarded
		assertEquals(true, player.tooManyHandCards());
		utils.sleep(time);
		//game.cardPressed(0);
		game.discardCard(game.getMatch().getPlayers().current().getHand().get(0));
		
		assertEquals(false, player.tooManyHandCards()); //discarded correct number now
		game.done(); //done turn	
	}
	 
	public void testTournament3() {
		// testing playCard() Playing a Weapon Card during turn, check was added correctly to right player
		int time = 100;
		//Test Tournament joiners having too many cards, testing discardCardIfTooMany()
		int numPlayers = 3;
		int numCards = 12;
		final GameModel game = new GameModel();
		game.initPlayersStart(numPlayers, numCards);
		
		Player p3 = new Player("Player 3");
		String[] cards = {"excalibur"};
		p3.participateTour(true);
		p3.setHand(cards);
		game.addPlayer(p3);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
			game.startGameTournamentTest();
			}
		});
		thread.start();
		
		utils.sleep(time);
		game.setParticipation(false); // p0 no
		utils.sleep(time);
		game.setParticipation(false);//p1 no
		utils.sleep(time);
		game.setParticipation(true);  //p2 yes  player has to many cards by 1 //game blocked until discarded
		utils.sleep(time);
		//game.cardPressed(0);
		game.discardCard(game.getMatch().getPlayers().current().getHand().get(0));
		game.done(); //done turn
		utils.sleep(time);
		game.setParticipation(true);  //p3 yes good hand <=12
		utils.sleep(time);
		//now player2's turn
		//game.cardPressed(0);
		game.discardCard(game.getMatch().getPlayers().current().getHand().get(0));
		game.done();
		utils.sleep(time);
		Player curr = game.getMatch().getPlayers().current();
		//game.cardPressed(0);
		game.playCard(curr, game.getMatch().getPlayers().current().getHand().get(0));
		utils.sleep(time);
		LinkedList<AdventureCard> cards2 = curr.getActive();
		boolean r = cards2.get(0).getName() == "Excalibur";
		assertEquals(true, r);		
	}
	
	public void testTournament4() {
		// testing playCard() Playing a card you do not own during your turn, check was not added to hand
		int time = 100;
		//Test Tournament joiners having too many cards, testing discardCardIfTooMany()
		int numPlayers = 3;
		int numCards = 12;
		final GameModel game = new GameModel();
		game.initPlayersStart(numPlayers, numCards);
		
		Player p3 = new Player("Player 3");
		String[] cards = {"saxons"};
		p3.participateTour(true);
		WeaponCard excalibur = new WeaponCard("Excalibur", 30);

		p3.setHand(cards);
		game.addPlayer(p3);
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
			game.startGameTournamentTest();
			}
		});
		thread.start();
		
		utils.sleep(time);
		game.setParticipation(false); // p0 no
		utils.sleep(time);
		game.setParticipation(false);//p1 no
		utils.sleep(time);
		game.setParticipation(true);  //p2 yes  player has to many cards by 1 //game blocked until discarded
		utils.sleep(time);
		//game.cardPressed(0);
		game.discardCard(game.getMatch().getPlayers().current().getHand().get(0));
		game.done(); //done turn
		utils.sleep(time);
		game.setParticipation(true);  //p3 yes good hand <=12
		utils.sleep(time);
		//now player2's turn
		//game.cardPressed(0);
		game.discardCard(game.getMatch().getPlayers().current().getHand().get(0));
		game.done();
		utils.sleep(time);
		Player curr = game.getMatch().getPlayers().current();
		game.getMatch().playCard(excalibur);
		utils.sleep(time);
		LinkedList<AdventureCard> cards2 = curr.getActive();
		assertEquals(0, cards2.size());	
	}
	
	public void testTournament5() {
		//Testing calculation of battle points of a player no special abilities (dependent cards), Weapons,Ally,Amour
		TournamentCard york = new TournamentCard("Tournament at York", 0);
	    Logger log = Logger.getLogger(TournamentTest.class); //log4j logger
		Tournament t = new Tournament(new Players(0, 0, null), null, york, null, log);
		//tested weapons and rank squire
		Player p0 = new Player("Player 0"); // rank is SQUIRE = 5 bp
		String[] cards = {"horse", "sword", "excalibur", "lance", "dagger", "battleAx"}; //90 bp all cards + rank pts
		p0.setActiveHand(cards);
		assertEquals(95, t.calcBattlePoints(p0));
		//tested weapons and rank knight
		p0 = new Player("Player 0");
		p0.addShields(6); //Rank is knight bp = 10
		p0.setActiveHand(cards); // 10 + 90
		int Result = t.calcBattlePoints(p0);
		assertEquals(100,  Result);
		//:TODO test Queen Iseult and Sir Tristian, both have special abilities when in play
		//test ally cards
				
		p0 = new Player("Player 0");
		p0.addShields(6); //Rank is knight bp = 10
		String[] cards2 = {"merlin", "pellinore", "guinevere", "amour"};
		p0.setActiveHand(cards2); // 10 + 90 +10(ally)+ 10(amour) = 120
		int result2 = t.calcBattlePoints(p0);
		assertEquals(120,  result2);
	}
	
	public void testTournament6() {
		//test determinewin() one winner, tied(first time stage = tie), tie again(stage = double_tie), right players won, tied
		TournamentCard york = new TournamentCard("Tournament at York", 0);
	    Logger log = Logger.getLogger(TournamentTest.class); //log4j logger
		Tournament t = new Tournament(new Players(0, 0, null), null, york, null, log);
		
		String[] cards = {"horse", "sword"};
		Player p0 = new Player("Player 0"); // rank is SQUIRE = 5 bp 
		Player p1 = new Player("Player 1"); // rank is SQUIRE = 5 bp
		Player p2 = new Player("Player 2"); // rank is SQUIRE = 5 bp
		Player p3 = new Player("Player 3"); // rank is SQUIRE = 5 bp
		p0.participateTour(true);
		p1.participateTour(true);
		p2.participateTour(true);
		p3.participateTour(true);
		ArrayList<Player> pList = new ArrayList<Player>();
		pList.add(p0); pList.add(p1); pList.add(p2); pList.add(p3);
		Players players = new Players(0, pList.size(), pList);
		
		Players result = t.determineWin(players);
		assertEquals(result.getNumPlayers(), 4);
		assertEquals(true,result.getPlayers().contains(p0));
		assertEquals(true,result.getPlayers().contains(p1));
		assertEquals(true,result.getPlayers().contains(p2));
		assertEquals(true,result.getPlayers().contains(p3));
		
		assertEquals(t.getStage(), Tournament.T_TIE);
		
		Players result2 = t.determineWin(players);
		assertEquals(result2.getNumPlayers(), 4);
		assertEquals(true,result2.getPlayers().contains(p0));
		assertEquals(true,result2.getPlayers().contains(p1));
		assertEquals(true,result2.getPlayers().contains(p2));
		assertEquals(true,result2.getPlayers().contains(p3));
		assertEquals(t.getStage(), Tournament.T_DOUBLE_TIE);
		
		
		//testing single winner
		Tournament t2 = new Tournament(new Players(0, 0, null), null, york, null, log);
		Player p00 = new Player("Player 00"); // rank is SQUIRE = 5 bp 
		Player p01 = new Player("Player 01"); // rank is SQUIRE = 5 bp
		Player p02 = new Player("Player 02"); // rank is SQUIRE = 5 bp
		Player p03 = new Player("Player 03"); // rank is SQUIRE = 5 bp
		p00.setActiveHand(cards); //more battle points than anyone else
		ArrayList<Player> pList2 = new ArrayList<Player>();
		pList2.add(p00); pList2.add(p01); pList2.add(p02); pList2.add(p03);
		Players pl = new Players(0, pList2.size(), pList2);
		Players result3 = t2.determineWin(pl);
		assertEquals(result3.getNumPlayers(), 1);
		assertEquals(true, result3.getPlayers().contains(p00));
	}
}