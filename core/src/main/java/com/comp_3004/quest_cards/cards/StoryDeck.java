package com.comp_3004.quest_cards.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class StoryDeck extends Deck {
	
	//attributes
	private Stack<StoryCard> deck;				//deck of cards
	private Stack<StoryCard> discard;			//discard pile
	private CardSpawner spawner;					//spawns cards
	
	//constructors
	public StoryDeck() {							//default constructor
		this.deck = new Stack<StoryCard>();
		this.discard = new Stack<StoryCard>();
		this.spawner = new CardSpawner();
		initTournaments();
		initEvents();
		initQuests();
	}
	public StoryDeck(String cardType) {			//constructs deck consisting of only one type of card	
		this.deck = new Stack<StoryCard>();
		this.discard = new Stack<StoryCard>();
		
		if(cardType == "Tournaments")
			initTournaments();
		else if(cardType == "Events")
			initEvents();
		else if(cardType == "Quests")
			initQuests();
	}
	public StoryDeck(Stack<StoryCard> d) {		//constructs deck containing selected cards
		this.spawner = new CardSpawner();
		this.deck = d;
		this.discard = new Stack<StoryCard>();
	}
	public StoryDeck(String[] d) {				//constructs deck containing selected cards
		this.spawner = new CardSpawner();
		this.deck = new Stack<StoryCard>();
		for (String name : d)
			this.deck.add(spawner.spawnStoryCard(name));
		this.discard = new Stack<StoryCard>();
	}
	
	//getters/setters
		public boolean deckEmpty() { return this.deck.empty(); }
		public boolean discardEmpty() { return this.discard.empty(); }
		public Stack<StoryCard> getDeck() { return this.deck; }
		public Stack<StoryCard> getDiscard() { return this.discard; }
	
	//methods
	public void shuffle() {							//shuffles the deck
		Collections.shuffle(deck);
	}
	
	
	
	protected void shuffleDiscardIntoDeck() {		//shuffles the discard pile into the deck
		while(discard.empty() != true) {
			deck.push(discard.pop());
		}
		shuffle();
	}
	
	public StoryCard drawCard() {					//draws the top card of the story deck
		if(deck.empty()) {
			shuffleDiscardIntoDeck();
			return deck.pop();
		}
		else
			return deck.pop();
	}
	
	public void discardCard(StoryCard c) {			//moves card to decks discard pile
		discard.push(c);
	}
	
	public void printDeck() {						//prints cards in the deck
		System.out.printf("Story Deck:\n");
		System.out.printf("%-40s%-20s%s\n", "Name", "Type", "ID");
		System.out.printf("================================================================\n");
		for(StoryCard s : deck) {
			s.printCard();
		}
		System.out.printf("Number of cards: %s\n", deck.size());
	}
	
	public void printDiscard() {						//prints cards in the discard
		System.out.printf("Story Discard:\n");
		System.out.printf("%-40s%s\n", "Name", "Type");
		System.out.printf("==================================\n");
		for(StoryCard s : discard) {
			s.printCard();
		}
		System.out.printf("Number of cards: %s\n", discard.size());
		
	}
	
	//constructor initialization methods
	private void initTournaments() {
		this.deck.add(spawner.spawnStoryCard("camelot"));
		this.deck.add(spawner.spawnStoryCard("orkney"));
		this.deck.add(spawner.spawnStoryCard("tintagel"));
		this.deck.add(spawner.spawnStoryCard("york"));
	}
	
	private void initEvents() {
		for(int i=0; i<2; i++) {
			this.deck.add(spawner.spawnStoryCard("kingsRecognition"));
			this.deck.add(spawner.spawnStoryCard("queensFavor"));
			this.deck.add(spawner.spawnStoryCard("courtCalledToCamelot"));
		}
		this.deck.add(spawner.spawnStoryCard("pox"));
		this.deck.add(spawner.spawnStoryCard("plauge"));
		this.deck.add(spawner.spawnStoryCard("chivalrousDeed"));
		this.deck.add(spawner.spawnStoryCard("prosperityThroughoutTheRealms"));
		this.deck.add(spawner.spawnStoryCard("kingsCallToArms"));
	}
	
	private void initQuests() {
		this.deck.add(spawner.spawnStoryCard("searchForTheHolyGrail"));
		this.deck.add(spawner.spawnStoryCard("testOfTheGreenKnight"));
		this.deck.add(spawner.spawnStoryCard("searchForTheQuestingBeast"));
		this.deck.add(spawner.spawnStoryCard("defendTheQueensHonor"));
		this.deck.add(spawner.spawnStoryCard("rescueTheFairMaiden"));
		this.deck.add(spawner.spawnStoryCard("journeyThroughTheEnchantedForest"));
		this.deck.add(spawner.spawnStoryCard("slayTheDragon"));
		for(int i=0; i<2; i++) {
			this.deck.add(spawner.spawnStoryCard("vanquishKingArthursEnemies"));
			this.deck.add(spawner.spawnStoryCard("boarHunt"));
			this.deck.add(spawner.spawnStoryCard("repelTheSaxonInvaders"));
		}
	}

}
