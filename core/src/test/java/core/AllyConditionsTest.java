package core;
import java.util.LinkedList;
import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureCard.State;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyObserver;
import com.comp_3004.quest_cards.cards.AllySubjectObserver;
import com.comp_3004.quest_cards.cards.CardSpawner;
import com.comp_3004.quest_cards.cards.QuestCardSubject;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.player.Players;

import junit.framework.TestCase;

public class AllyConditionsTest extends TestCase {

	/*
	public void testOne() {
		GameModel m = new GameModel(4);
		AdventureDeck ad = m.getAdvDeck();
		ad.printDeck();
		AllySubjectObserver qu = (AllySubjectObserver)findCard(107, m.getPlayers(), ad);
		AllySubjectObserver tris = (AllySubjectObserver)findCard(103, m.getPlayers(), ad);
		
		qu.register(tris);
		tris.register(qu);
		
		//someone plays one of the cards
		qu.setState(State.PLAY);
		tris.setState(State.PLAY);
		
		assertEquals(true, qu.isActivated());
		assertEquals(true, tris.isActivated());
		
		tris.setState(State.DISCARD);
		
		assertEquals(false, qu.isActivated());
		assertEquals(false, tris.isActivated());
		
		tris.setState(State.QUEST);
		assertEquals(true, qu.isActivated());
		assertEquals(true, tris.isActivated());
		
		qu.setState(State.HAND);
		tris.setState(State.HAND);
		
		qu.setState(State.PLAY);
		tris.setState(State.PLAY);
		assertEquals(true, qu.isActivated());
		assertEquals(true, tris.isActivated());
		
		//removes tristian as an observer on the queen, queen still observing tristian
		qu.deregister(tris);
		// queen no longer getting tristian updates
		tris.deregister(qu);
	}
	*/
	
	public void testTwo() {
		
		Stack<StoryCard> d = new Stack<StoryCard>();
		CardSpawner s = new CardSpawner();
		d.add(s.spawnStoryCard("testOfTheGreenKnight"));
		
		StoryDeck sdeck = new StoryDeck(d);
		AdventureDeck ad = new AdventureDeck();
		GameModel m = new GameModel(4, 5, ad, sdeck); //everyone starting with 5 cards
		
		QuestCardSubject test = (QuestCardSubject)sdeck.getDeck().get(0);
		AllyObserver gaw = (AllyObserver)findCard(101, m.getPlayers(), ad);
		
		test.register(gaw);
		
		ad.printDeck();
		sdeck.drawCard();
		sdeck.discardCard(test);
		
		
		
	}
	
	
	
	
	public AdventureCard findCard(int id, Players p, AdventureDeck d) {
		
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
	
	private AdventureCard find(int id, AdventureDeck d) {
		for(AdventureCard c: d.getDeck()) {
			if(c.getID() == id)
				return c;	
		}
		return null;
	}
	
	private AdventureCard find(int id, LinkedList<AdventureCard> d) {
		for(AdventureCard c: d) {
			if(c.getID() == id)
				return c;	
		}
		return null;
	}
	
}
