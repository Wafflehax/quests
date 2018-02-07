package com.comp_3004.quest_cards.cards;

import java.util.Collections;
import java.util.Stack;

public class AdventureDeck extends Deck {
	
	//attributes
	private Stack<AdventureCard> deck;				//deck of cards
	private Stack<AdventureCard> discard;			//discard pile
	private CardSpawner spawner;						//spawns cards

	//constructors
	public AdventureDeck() {							//default constructor
		this.deck = new Stack<AdventureCard>();
		this.discard = new Stack<AdventureCard>();
		this.spawner = new CardSpawner();
		initFoes();
		initWeapons();
		initAllies();
		initAmour();
		initTests();
	}
	
	public AdventureDeck(Stack<AdventureCard> d) {	//constructs a deck with selected cards
		this.spawner = new CardSpawner();
		this.deck = new Stack<AdventureCard>();
		this.deck = d;
		this.discard = new Stack<AdventureCard>();
	}
	
	public AdventureDeck(String[] d) {	//constructs a deck with selected cards
		this.spawner = new CardSpawner();
		this.deck = new Stack<AdventureCard>();
		for (String name : d)
			this.deck.add(spawner.spawnAdventureCard(name));
		this.discard = new Stack<AdventureCard>();
	}
	
	//getters/setters
	public boolean deckEmpty() { return this.deck.empty(); }
	public boolean discardEmpty() { return this.discard.empty(); }
	public Stack<AdventureCard> getDeck() { return this.deck; }
	public Stack<AdventureCard> getDiscard() { return this.discard; }
	
	//methods
	public void shuffle() {						//shuffles the deck
		Collections.shuffle(deck);
	}
	
	public void shuffleDiscardIntoDeck() {		//shuffles the discard pile into the deck
		while(discard.empty() != true) {
			deck.push(discard.pop());
		}
		shuffle();
	}
	
	public AdventureCard drawCard() {			//draws the top card of the deck
		if(deck.empty()) {
			shuffleDiscardIntoDeck();
			return deck.pop();
		}
		else
			return deck.pop();
	}
	
	public void discardCard(AdventureCard c) {	//moves a card to the discard pile
		discard.push(c);
	}
	
	public void printDeck() {					//prints the cards in the deck
		System.out.printf("Adventure Deck:\n");
		System.out.printf("%-15s%-15s%-20s\n", "Name", "Battle Points", "Type", "ID");
		System.out.printf("==============================================================\n");
		for(AdventureCard a : this.deck) {
			a.printCard();
		}
		System.out.printf("Number of cards: %s\n", this.deck.size());
	}
	
	public void printDiscard() {					//prints the cards in the discard pile
		System.out.printf("Adventure Discard:\n");
		System.out.printf("%-15s%-15s%s\n", "Name", "Battle Points", "Type");
		System.out.printf("==================================\n");
		for(AdventureCard a : this.discard) {
			a.printCard();
		}
		System.out.printf("Number of cards: %s\n", this.discard.size());
	}
	
	//constructor initialization methods
	private void initFoes() {
		for(int i=0; i<8; i++) {
			this.deck.add(spawner.spawnAdventureCard("thieves"));
			this.deck.add(spawner.spawnAdventureCard("saxonKnight"));
		}
		for(int i=0; i<7; i++)
			this.deck.add(spawner.spawnAdventureCard("robberKnight"));
		for(int i=0; i<6; i++)
			this.deck.add(spawner.spawnAdventureCard("evilKnight"));
		for(int i=0; i<5; i++) 
			this.deck.add(spawner.spawnAdventureCard("saxons"));
		for(int i=0; i<4; i++) {
			this.deck.add(spawner.spawnAdventureCard("boar"));
			this.deck.add(spawner.spawnAdventureCard("mordred"));
		}
		for(int i=0; i<3; i++)
			this.deck.add(spawner.spawnAdventureCard("blackKnight"));
		for(int i=0; i<2; i++) {
			this.deck.add(spawner.spawnAdventureCard("giant"));
			this.deck.add(spawner.spawnAdventureCard("greenKnight"));
		}
		this.deck.add(spawner.spawnAdventureCard("dragon"));
	}
	
	private void initWeapons() {
		for(int i=0; i<11; i++)
			this.deck.add(spawner.spawnAdventureCard("horse"));
		for(int i=0; i<16; i++)
			this.deck.add(spawner.spawnAdventureCard("sword"));
		for(int i=0; i<2; i++)
			this.deck.add(spawner.spawnAdventureCard("excalibur"));
		for(int i=0; i<6; i++) {
			this.deck.add(spawner.spawnAdventureCard("lance"));
			this.deck.add(spawner.spawnAdventureCard("dagger"));
		}
		for(int i=0; i<8; i++)
			this.deck.add(spawner.spawnAdventureCard("battleAx"));
	}
	
	private void initAllies() {
		this.deck.add(spawner.spawnAdventureCard("gawain"));
		this.deck.add(spawner.spawnAdventureCard("pellinore"));
		this.deck.add(spawner.spawnAdventureCard("percival"));
		this.deck.add(spawner.spawnAdventureCard("tristan"));
		this.deck.add(spawner.spawnAdventureCard("arthur"));
		this.deck.add(spawner.spawnAdventureCard("guinevere"));
		this.deck.add(spawner.spawnAdventureCard("merlin"));
		this.deck.add(spawner.spawnAdventureCard("iseult"));
		this.deck.add(spawner.spawnAdventureCard("lancelot"));
		this.deck.add(spawner.spawnAdventureCard("galahad"));
	}
	
	private void initTests() {
		for(int i=0; i<2; i++) {
			this.deck.add(spawner.spawnAdventureCard("questingBeast"));
			this.deck.add(spawner.spawnAdventureCard("temptation"));
			this.deck.add(spawner.spawnAdventureCard("valor"));
			this.deck.add(spawner.spawnAdventureCard("morganLeFey"));
		}
	}
	
	private void initAmour() {
		for(int i=0; i<8; i++)
			this.deck.add(spawner.spawnAdventureCard("amour"));
	}
}