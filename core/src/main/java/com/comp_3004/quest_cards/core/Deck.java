package com.comp_3004.quest_cards.core;

import java.util.Stack;
//welcome to the start of the new card-deck implementation development branch!!!
public abstract class Deck {
	
	protected Stack<Card> deck;
	protected Stack<Card> discard;
	
	protected void discardCard(StoryCard card) {
		discard.push(card);
	};
	abstract protected void printDeck();

}
