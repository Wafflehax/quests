package com.comp_3004.quest_cards.cards;

import java.util.Collections;
import java.util.Stack;
//welcome to the start of the new card-deck implementation development branch!!!
public abstract class Deck {
	
	protected Stack<Card> deck;
	protected Stack<Card> discard;
	
	public void discardCard(Card c) {		//puts card in decks discard pile
		discard.push(c);
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public void shuffleDiscardIntoDeck() {
		while(discard.empty() != true) {
			deck.push(discard.pop());
		}
		this.shuffle();
	}
	
	//abstract methods
	abstract protected void printDeck();			//prints the cards currently in the deck
	abstract protected void printDiscard();		//prints the cards currently in the discard pile

}
