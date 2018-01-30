package core;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.core.Player;
import com.comp_3004.quest_cards.core.Player.Rank;

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

}
