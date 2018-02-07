package com.comp_3004.quest_cards.cards;

import java.util.Stack;

public abstract class Deck {
	
	//attributes
	protected Stack<Card> deck;							//deck of cards
	protected Stack<Card> discard;						//discard pile
	
	//methods (overridden in child classes)
	public void discardCard(Card c) {					//puts card into the discard pile
		discard.push(c);
	}
	
	//abstract methods
	abstract protected void printDeck();					//prints the cards currently in the deck
	abstract protected void printDiscard();				//prints the cards currently in the discard pile
	abstract protected void shuffle();					//shuffles cards in the deck
	abstract protected void shuffleDiscardIntoDeck();		//shuffles discard pile into deck
	abstract public boolean deckEmpty();					//checks if the deck is empty
	abstract public boolean discardEmpty();				//checks if the discard is empty
}
