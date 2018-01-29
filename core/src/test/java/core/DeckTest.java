package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;

import junit.framework.*;

public class DeckTest extends TestCase{

	public void testStorySize() {
		StoryDeck d = new StoryDeck();
		assertEquals(28,d.getDeck().size());
	}
	
	public void testStoryComposition() {
		StoryDeck d = new StoryDeck();
		
		//populate cardCounts with cards and the number of times they occur in the deck
		HashMap<String, Integer> cardCounts = new HashMap<String, Integer>();
		Iterator<StoryCard> iter = d.getDeck().iterator();
		while (iter.hasNext()){
			String c = iter.next().getName();
			Integer i = cardCounts.get(c);
			if (i==null)
				cardCounts.put(c, 1);
			else
				cardCounts.put(c, i + 1);
		}
		
		//list of cards that occur twice
		ArrayList<String> twice = new ArrayList<String>();
		twice.add("King's Recognition");
		twice.add("Queen's Favor");
		twice.add("Court Called to Camelot");
		twice.add("Vanquish King Arthur's Enemies");
		twice.add("Boar Hunt");
		twice.add("Repel the Saxon Invaders");
		
		//verify correct number of each card in deck
		for(Entry<String, Integer> s : cardCounts.entrySet()) {
		    String key = s.getKey();
		    int value = s.getValue();
		    
		    if(twice.contains(key))
		    		assertEquals(2, value);
		    else
		    		assertEquals(1, value);
		}
	}
	
	public void testStoryDraw() {
		StoryDeck d = new StoryDeck();
		assertEquals(d.getDeck().peek(), d.drawCard());
	}
	
	public void testShuffleDiscardIntoDeck() {
		StoryDeck d = new StoryDeck();
		
		//place entire deck into discard
		while (!d.deckEmpty()) {
			d.discardCard(d.drawCard());
		}
		assertEquals(28,d.getDiscard().size());
		
		//draw, triggering shuffle discard into deck
		boolean cardDrawn = (d.drawCard() != null);
		assert(cardDrawn);
		assertEquals(0,d.getDiscard().size());
		assertEquals(27,d.getDeck().size());
	}
	
	public void testStoryDiscard() {
		StoryDeck d = new StoryDeck();
		String card1 = d.getDeck().peek().getName();
		d.discardCard(d.drawCard());
		String card2 = d.getDiscard().peek().getName();
		assertEquals(card1, card2);
	}
	

}
