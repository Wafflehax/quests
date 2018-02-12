package core;

import java.util.LinkedList;
import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Player.Rank;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.WeaponCard;

import junit.framework.TestCase;

public class PlayerTest extends TestCase{

	public void testPlayerInit() {
		Player p = new Player("player1");
		assertEquals("player1", p.getName());
		assertEquals(Rank.SQUIRE, p.getRank());
		assertEquals(0, p.getShields());
	}
	
	public void testDrawCard() {
		AdventureDeck d = new AdventureDeck();
		Player p = new Player("player1");
		AdventureCard c = d.getDeck().peek();
		assertEquals(State.DECK, c.getState());
		assertEquals(null, c.getOwner());

		p.drawCard(d);
		
		AdventureCard c2 = p.getHand().peek();
		assertEquals(c, c2);
		assertEquals(State.HAND, c.getState());
		assertEquals(p, c.getOwner());
	}
	
	public void testDrawOverHandLimit() {		//currently not allowed, better implementation to come...
		AdventureDeck d = new AdventureDeck();
		Player p = new Player("player1");
		while(p.getHand().size() < 12)
			p.drawCard(d);
		assert(!p.drawCard(d));
	}
	
	public void testPlayCard() {
		AdventureDeck d = new AdventureDeck();
		Player p = new Player("player1");
		p.drawCard(d);
		AdventureCard c = p.getHand().peek();
		assertEquals(State.HAND, c.getState());
		assertEquals(p, c.getOwner());
		
		p.playCard(c);
		
		AdventureCard c2 = p.getActive().peek();
		assertEquals(c, c2);
		assertEquals(State.PLAY, c.getState());
		assertEquals(p, c.getOwner());
		

	}
	
	public void testDiscardFromHand() {
		AdventureDeck d = new AdventureDeck();
		Player p = new Player("player1");
		p.drawCard(d);
		AdventureCard c = p.getHand().peek();
		assertEquals(State.HAND, c.getState());
		assertEquals(p, c.getOwner());
		
		p.discardCard(c, d);
		
		AdventureCard c2 = d.getDiscard().peek();
		assertEquals(c, c2);
		assertEquals(State.DISCARD, c.getState());
		assertEquals(null, c.getOwner());
		
	}
	
	public void testDiscardFromPlay() {
		AdventureDeck d = new AdventureDeck();
		Player p = new Player("player1");
		p.drawCard(d);
		AdventureCard c = p.getHand().peek();
		p.playCard(c);
		assertEquals(State.PLAY, c.getState());
		assertEquals(p, c.getOwner());
		
		p.discardCard(c, d);
		
		AdventureCard c2 = d.getDiscard().peek();
		assertEquals(c, c2);
		assertEquals(State.DISCARD, c.getState());
		assertEquals(null, c.getOwner());
		
	}
	
	public void testIllegalDiscard() {
		AdventureDeck d = new AdventureDeck();
		Player p = new Player("player1");
		AdventureCard c = d.getDeck().peek();
		assertEquals(State.DECK, c.getState());
		assertEquals(null, c.getOwner());
		
		assert(!p.playCard(c));
		
		assertEquals(State.DECK, c.getState());
		assertEquals(null, c.getOwner());
		
	}
	
	public void testAddShields() {
		Player p = new Player("player1");
		p.addShields(4);
		assertEquals(4, p.getShields());
		
		p.addShields(14);
		assertEquals(6, p.getShields());
		assertEquals(Rank.CHAMPION_KNIGHT, p.getRank());
		
		p.addShields(4);
		assertEquals(10, p.getShields());
		assertEquals(Rank.KNIGHT_OF_THE_ROUND_TABLE, p.getRank());
	}
	
	public void testLoseShields() {
		Player p = new Player("player1");
		p.addShields(4);
		assertEquals(4, p.getShields());
		
		p.loseShields(2);
		assertEquals(2, p.getShields());
		
		p.loseShields(4);
		assertEquals(0, p.getShields());
	}

	public void testDiscardWeaponsActive() {
	//test discardWeaponsActive
			Player p0 = new Player("Player 0"); // rank is SQUIRE = 5 bp 
			Stack<AdventureCard> p1c = new Stack<AdventureCard>();   //                   ,Sword,A(King Arthur),Amour,Horse,F(black knight)
			
			AdventureCard c0 = new WeaponCard("Sword", 10); 
			AdventureCard c1 = new AllyCard("King Arthur", 10,2);
			AdventureCard c2 =	new AmourCard();
			AdventureCard c3 = new WeaponCard("Horse", 10);
			AdventureCard c4 = new FoeCard("Black Knight", 25, 35);
			
			p1c.add(c0);
			p1c.add(c1);
			p1c.add(c2);
			p1c.add(c3);
			p1c.add(c4);
			
			AdventureDeck d = new AdventureDeck(p1c);
			p0.drawCard(d);   //takes from front 
			p0.drawCard(d);
			p0.drawCard(d);
			p0.drawCard(d);
			p0.drawCard(d); // order =>Black Knight,Horse,Amour,King Aruthur,Sword
			
			p0.playCard(c0);
			p0.playCard(c1);
			p0.playCard(c2);
			p0.playCard(c3);
			p0.playCard(c4);
			
			p0.discardWeaponsActive(d);
			
			LinkedList<AdventureCard> leftover = p0.getActive();
			assertEquals(3, leftover.size());
			assertEquals(true, leftover.contains(c1));
			assertEquals(true, leftover.contains(c2));
			assertEquals(true, leftover.contains(c4));
	}
	
	public void testDiscardAmourActive() {
		//test discardWeaponsActive
				Player p0 = new Player("Player 0"); // rank is SQUIRE = 5 bp 
				Stack<AdventureCard> p1c = new Stack<AdventureCard>();   //                   ,Sword,A(King Arthur),Amour,Horse,F(black knight)
				
				AdventureCard c0 = new WeaponCard("Sword", 10); 
				AdventureCard c1 = new AllyCard("King Arthur", 10,2);
				AdventureCard c2 =	new AmourCard();
				AdventureCard c3 = new WeaponCard("Horse", 10);
				AdventureCard c4 = new FoeCard("Black Knight", 25, 35);
				
				p1c.add(c0);
				p1c.add(c1);
				p1c.add(c2);
				p1c.add(c3);
				p1c.add(c4);
				
				AdventureDeck d = new AdventureDeck(p1c);
				p0.drawCard(d);   //takes from front 
				p0.drawCard(d);
				p0.drawCard(d);
				p0.drawCard(d);
				p0.drawCard(d); // order =>Black Knight,Horse,Amour,King Aruthur,Sword
				
				p0.playCard(c0);
				p0.playCard(c1);
				p0.playCard(c2);
				p0.playCard(c3);
				p0.playCard(c4);
				
				p0.discardAmoursActive(d);
				
				LinkedList<AdventureCard> leftover = p0.getActive();
				assertEquals(4, leftover.size());
				assertEquals(true, leftover.contains(c0));
				assertEquals(true, leftover.contains(c1));
				assertEquals(true, leftover.contains(c3));
				assertEquals(true, leftover.contains(c4));
		}
}
