package core;

import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.EventCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.core.GameModel;

import junit.framework.TestCase;

public class EventTest extends TestCase{

	public void testChivalrousDeed() {
		//set up story deck
		EventCard chivalrousDeed = new EventCard("Chivalrous Deed");
		Stack<StoryCard> sd= new Stack<StoryCard>();
		sd.add(chivalrousDeed);
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		Stack<AdventureCard> ad= new Stack<AdventureCard>();
		AdventureDeck advDeck = new AdventureDeck(ad);
		GameModel game = new GameModel(2,  advDeck, storyDeck);
		
		//assertEquals("Not yet implemented");
	}

}
