package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;

import junit.framework.TestCase;

public class DeckTest extends TestCase {

	//Story Deck Tests
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
		ArrayList<String> twos = new ArrayList<String>();
		twos.add("King's Recognition");
		twos.add("Queen's Favor");
		twos.add("Court Called to Camelot");
		twos.add("Vanquish King Arthur's Enemies");
		twos.add("Boar Hunt");
		twos.add("Repel the Saxon Invaders");
		
		//verify correct number of each card in deck
		for(Entry<String, Integer> s : cardCounts.entrySet()) {
		    String key = s.getKey();
		    int value = s.getValue();
		    
		    if(twos.contains(key))
		    		assertEquals(2, value);
		    else
		    		assertEquals(1, value);
		}
	}
	
	public void testStoryDraw() {
		StoryDeck d = new StoryDeck();
		assertEquals(d.getDeck().peek(), d.drawCard());
	}
	
	public void testStoryShuffleDiscardIntoDeck() {
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
	
	//Adventure Deck Tests
	public void testAdvSize() {
		AdventureDeck d = new AdventureDeck();
		assertEquals(125,d.getDeck().size());
	}
	
	public void testAdvComposition() {
		AdventureDeck d = new AdventureDeck();
		
		//populate cardCounts with cards and the number of times they occur in the deck
		HashMap<String, Integer> cardCounts = new HashMap<String, Integer>();
		Iterator<AdventureCard> iter = d.getDeck().iterator();
		while (iter.hasNext()){
			String c = iter.next().getName();
			Integer i = cardCounts.get(c);
			if (i==null)
				cardCounts.put(c, 1);
			else
				cardCounts.put(c, i + 1);
		}
		
		//list of adventure cards and their corresponding occurrences
		HashMap<String, Integer> countVerify = new HashMap<String, Integer>();
		countVerify.put("Sir Lancelot",1);
		countVerify.put("Queen Iseult",1);
		countVerify.put("King Arthur",1);
		countVerify.put("Queen Guinevere",1);
		countVerify.put("Sir Tristan",1);
		countVerify.put("Sir Gawain",1);
		countVerify.put("Sir Percival",1);
		countVerify.put("Sir Galahad",1);
		countVerify.put("King Pellinore",1);
		countVerify.put("Merlin",1);
		countVerify.put("Dragon",1);
		countVerify.put("Green Knight",2);
		countVerify.put("Giant",2);
		countVerify.put("Test of Valor",2);
		countVerify.put("Test of Temptation",2);
		countVerify.put("Excalibur", 2);
		countVerify.put("Test of Morgan Le Fey",2);
		countVerify.put("Test of the Questing Beast",2);
		countVerify.put("Black Knight",3);
		countVerify.put("Boar",4);
		countVerify.put("Mordred",4);
		countVerify.put("Saxons",5);
		countVerify.put("Lance",6);
		countVerify.put("Evil Knight",6);
		countVerify.put("Dagger",6);
		countVerify.put("Robber Knight",7);
		countVerify.put("Amour",8);
		countVerify.put("Saxon Knight",8);
		countVerify.put("Battle-Ax",8);
		countVerify.put("Thieves",8);
		countVerify.put("Horse",11);
		countVerify.put("Sword",16);
		
		
		//verify correct number of each card in deck by comparing card counts
		for(Entry<String, Integer> s : cardCounts.entrySet()) {
		    String key = s.getKey();
		    int value = s.getValue();
		    int verifyValue = countVerify.get(key);
		    assertEquals(verifyValue, value);
		}
		
	}
	
	public void testAdvDraw() {
		AdventureDeck d = new AdventureDeck();
		assertEquals(d.getDeck().peek(), d.drawCard());
	}
	
	public void testAdvShuffleDiscardIntoDeck() {
		AdventureDeck d = new AdventureDeck();
		
		//place entire deck into discard
		while (!d.deckEmpty()) {
			d.discardCard(d.drawCard());
		}
		assertEquals(125,d.getDiscard().size());
		
		//draw, triggering shuffle discard into deck
		boolean cardDrawn = (d.drawCard() != null);
		assert(cardDrawn);
		assertEquals(0,d.getDiscard().size());
		assertEquals(124,d.getDeck().size());
	}
	
	public void testAdvDiscard() {
		AdventureDeck d = new AdventureDeck();
		String card1 = d.getDeck().peek().getName();
		d.discardCard(d.drawCard());
		String card2 = d.getDiscard().peek().getName();
		assertEquals(card1, card2);
	}

}
