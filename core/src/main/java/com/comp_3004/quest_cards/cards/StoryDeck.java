package com.comp_3004.quest_cards.cards;

import java.util.Stack;

public class StoryDeck extends Deck {
	private Stack<StoryCard> deck;
	
	//constructor
	public StoryDeck() {
		this.deck = new Stack<StoryCard>();
		initTournaments();
		initEvents();
		initQuests();
	}
	
	protected StoryCard drawCard() {
		return deck.pop();
	}

	
	private void initTournaments() {
		TournamentCard camelot = new TournamentCard("Tournament at Camelot", 3);
		this.deck.add(camelot);
		TournamentCard orkney = new TournamentCard("Tournament at Orkney", 2);
		this.deck.add(orkney);
		TournamentCard tintagel = new TournamentCard("Tournament at Tintagel", 1);
		this.deck.add(tintagel);
		TournamentCard york = new TournamentCard("Tournament at York", 0);
		this.deck.add(york);
	}
	
	private void initEvents() {
		for(int i=0; i<2; i++) {
			EventCard kingsRecognition = new EventCard("King's Recognition");
			this.deck.add(kingsRecognition);
			EventCard queensFavor = new EventCard("Queen's Favor");
			this.deck.add(queensFavor);
			EventCard courtCalled = new EventCard("Court Called to Camelot");
			this.deck.add(courtCalled);
		}
		EventCard pox = new EventCard("Pox");
		this.deck.add(pox);
		EventCard plague = new EventCard("Plague");
		this.deck.add(plague);
		EventCard chivalrousDeed = new EventCard("Chivalrous Deed");
		this.deck.add(chivalrousDeed);
		EventCard prosperity = new EventCard("Prosperity Throughout the Realm");
		this.deck.add(prosperity);
		EventCard callToArms = new EventCard("King's Call to Arms");
		this.deck.add(callToArms);
	}
	
	private void initQuests() {
		QuestCard holyGrail = new QuestCard("Search for the Holy Grail");
		this.deck.add(holyGrail);
		QuestCard greenKnight = new QuestCard("Test of the Green Knight");
		this.deck.add(greenKnight);
		QuestCard questingBeast = new QuestCard("Search for the Questing Beast");
		this.deck.add(questingBeast);
		QuestCard queensHonor = new QuestCard("Defend the Queen's Honor");
		this.deck.add(queensHonor);
		QuestCard fairMaiden = new QuestCard("Rescue the Fair Maiden");
		this.deck.add(fairMaiden);
		QuestCard enchantedForest = new QuestCard("Journey Through the Enchanted Forest");
		this.deck.add(enchantedForest);
		QuestCard slayTheDragon = new QuestCard("Slay the Dragon");
		this.deck.add(slayTheDragon);
		for(int i=0; i<2; i++) {
			QuestCard vanquishEnemies = new QuestCard("Vanquish King Arthur's Enemies");
			this.deck.add(vanquishEnemies);
			QuestCard boarHunt = new QuestCard("Boar Hunt");
			this.deck.add(boarHunt);
			QuestCard saxonInvaders = new QuestCard("Repel the Saxon Invaders");
			this.deck.add(saxonInvaders);
		}
	}
	
	public void printDeck() {
		System.out.printf("Story Deck:\n");
		System.out.printf("%-40s%s\n", "Name", "Type");
		System.out.printf("==================================\n");
		for(StoryCard s : this.deck) {
			s.printCard();
		}
		System.out.printf("Number of cards: %s\n", this.deck.size());
	}

}
