package core;

import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.EventCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.StoryDeck;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameModel;
import com.comp_3004.quest_cards.core.Player;
import com.comp_3004.quest_cards.core.Player.Rank;

import junit.framework.TestCase;

public class EventTest extends TestCase{

	
	public void testChivalrousDeed() {
		//set up story deck
		Stack<StoryCard> sd= new Stack<StoryCard>();
		EventCard chivalrousDeed = new EventCard("Chivalrous Deed");
		sd.add(chivalrousDeed);
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		Stack<AdventureCard> ad= new Stack<AdventureCard>();
		AdventureDeck advDeck = new AdventureDeck(ad);
		GameModel game;
		
		//test1 - 4 way tie
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.eventTest();
		assertEquals(3, game.getPlayerAtIndex(0).getShields());
		assertEquals(3, game.getPlayerAtIndex(1).getShields());
		assertEquals(3, game.getPlayerAtIndex(2).getShields());
		assertEquals(3, game.getPlayerAtIndex(3).getShields());
		
		//test 2 - 2 way rank tie, 2 way shields tie
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(5);
		game.getPlayerAtIndex(1).addShields(5);
		
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(3, game.getPlayerAtIndex(2).getShields());
		assertEquals(3, game.getPlayerAtIndex(3).getShields());
		
		//test3 - 4 way rank tie, 1 player with lowest shields
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(4);
		game.getPlayerAtIndex(1).addShields(4);
		game.getPlayerAtIndex(2).addShields(4);
		game.getPlayerAtIndex(3).addShields(2);
		
		game.eventTest();
		assertEquals(4, game.getPlayerAtIndex(0).getShields());
		assertEquals(4, game.getPlayerAtIndex(1).getShields());
		assertEquals(4, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		assertEquals(Rank.KNIGHT, game.getPlayerAtIndex(3).getRank());
		
	}
	
	public void testPox() {
		//set up story deck
		Stack<StoryCard> sd= new Stack<StoryCard>();
		EventCard pox = new EventCard("Pox");
		sd.add(pox);
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		Stack<AdventureCard> ad= new Stack<AdventureCard>();
		AdventureDeck advDeck = new AdventureDeck(ad);
		GameModel game;
		
		//test 1 - players unable to lose shields
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(1).addShields(0);
		game.getPlayerAtIndex(2).addShields(5);
		game.getPlayerAtIndex(3).addShields(12);
		
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(1).getShields());
		assertEquals(0, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());
		
		//test 2 - normal case
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(2);
		game.getPlayerAtIndex(1).addShields(2);
		game.getPlayerAtIndex(2).addShields(3);
		game.getPlayerAtIndex(3).addShields(13);
		
		game.eventTest();
		assertEquals(2, game.getPlayerAtIndex(0).getShields());
		assertEquals(1, game.getPlayerAtIndex(1).getShields());
		assertEquals(2, game.getPlayerAtIndex(2).getShields());
		assertEquals(0, game.getPlayerAtIndex(3).getShields());	
	}
	
	public void testPlague() {
		//set up story deck
		Stack<StoryCard> sd= new Stack<StoryCard>();
		EventCard plague = new EventCard("Plague");
		sd.add(plague);
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		Stack<AdventureCard> ad= new Stack<AdventureCard>();
		AdventureDeck advDeck = new AdventureDeck(ad);
		GameModel game;
		
		//test 1 - players unable to lose shields
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(5);
		
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		
		//test 2 - edge case
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(1);
		
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(0).getShields());
		
		//test 2 - normal case
		game = new GameModel(4, 0, advDeck, storyDeck);
		game.getPlayerAtIndex(0).addShields(4);
		
		game.eventTest();
		assertEquals(2, game.getPlayerAtIndex(0).getShields());
	}
	
	public void testCourtCalledToCamelot() {
		//set up story deck
		Stack<StoryCard> sd= new Stack<StoryCard>();
		EventCard courtCalledToCamelot = new EventCard("Court Called to Camelot");
		sd.add(courtCalledToCamelot);
		StoryDeck storyDeck = new StoryDeck(sd);
		
		//set up adventure deck
		Stack<AdventureCard> ad= new Stack<AdventureCard>();
		AdventureDeck advDeck = new AdventureDeck(ad);
		AllyCard merlin = new AllyCard("Merlin", 0, 0);
		ad.push(merlin);
		AllyCard gawain = new AllyCard("Sir Gawain", 10, 0);
		ad.push(gawain);
		AllyCard pellinore = new AllyCard("King Pellinore", 10, 0);
		ad.push(pellinore);
		WeaponCard sword = new WeaponCard("Sword", 10);
		ad.push(sword);
		sword = new WeaponCard("Sword", 10);
		ad.push(sword);
		sword = new WeaponCard("Sword", 10);
		ad.push(sword);
		
		//start game
		GameModel game = new GameModel(2, 3, advDeck, storyDeck);
		game.getPlayerAtIndex(1).playCard(merlin);
		game.getPlayerAtIndex(1).playCard(gawain);
		game.getPlayerAtIndex(1).playCard(pellinore);
		
		//starts turn, draws card from story deck
		game.eventTest();
		assertEquals(0, game.getPlayerAtIndex(0).getActive().size());
		assertEquals(0, game.getPlayerAtIndex(1).getActive().size());
		assertEquals(3, game.getAdvDeck().getDiscard().size());
	}

}
