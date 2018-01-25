package com.comp_3004.quest_cards.core;

import java.util.Stack;

public class StoryDeck extends Deck {
	private Stack<Story> deck;
	
	//constructor
	public StoryDeck() {
		this.deck = new Stack<Story>();
		initTournaments();
		initEvents();
		initQuests();
	}
	
	protected Story drawCard() {
		return deck.pop();
	}

	
	private void initTournaments() {
		Tournament camelot = new Tournament("Tournament at Camelot", 3);
		this.deck.add(camelot);
		Tournament orkney = new Tournament("Tournament at Orkney", 2);
		this.deck.add(orkney);
		Tournament tintagel = new Tournament("Tournament at Tintagel", 1);
		this.deck.add(tintagel);
		Tournament york = new Tournament("Tournament at York", 0);
		this.deck.add(york);
	}
	
	private void initEvents() {
		for(int i=0; i<2; i++) {
			Event kingsRecognition = new Event("King's Recognition");
			this.deck.add(kingsRecognition);
			Event queensFavor = new Event("Queen's Favor");
			this.deck.add(queensFavor);
			Event courtCalled = new Event("Court Called to Camelot");
			this.deck.add(courtCalled);
		}
		Event pox = new Event("Pox");
		this.deck.add(pox);
		Event plague = new Event("Plague");
		this.deck.add(plague);
		Event chivalrousDeed = new Event("Chivalrous Deed");
		this.deck.add(chivalrousDeed);
		Event prosperity = new Event("Prosperity Throughout the Realm");
		this.deck.add(prosperity);
		Event callToArms = new Event("King's Call to Arms");
		this.deck.add(callToArms);
	}
	
	private void initQuests() {
		Quest holyGrail = new Quest("Search for the Holy Grail");
		this.deck.add(holyGrail);
		Quest greenKnight = new Quest("Test of the Green Knight");
		this.deck.add(greenKnight);
		Quest questingBeast = new Quest("Search for the Questing Beast");
		this.deck.add(questingBeast);
		Quest queensHonor = new Quest("Defend the Queen's Honor");
		this.deck.add(queensHonor);
		Quest fairMaiden = new Quest("Rescue the Fair Maiden");
		this.deck.add(fairMaiden);
		Quest enchantedForest = new Quest("Journey Through the Enchanted Forest");
		this.deck.add(enchantedForest);
		Quest slayTheDragon = new Quest("Slay the Dragon");
		this.deck.add(slayTheDragon);
		for(int i=0; i<2; i++) {
			Quest vanquishEnemies = new Quest("Vanquish King Arthur's Enemies");
			this.deck.add(vanquishEnemies);
			Quest boarHunt = new Quest("Boar Hunt");
			this.deck.add(boarHunt);
			Quest saxonInvaders = new Quest("Repel the Saxon Invaders");
			this.deck.add(saxonInvaders);
		}
	}
	
	public void printDeck() {
		System.out.printf("Story Deck:\n");
		System.out.printf("%-40s%s\n", "Name", "Type");
		System.out.printf("==================================\n");
		for(Story s : this.deck) {
			s.printCard();
		}
		System.out.printf("Number of cards: %s\n", this.deck.size());
	}

}
