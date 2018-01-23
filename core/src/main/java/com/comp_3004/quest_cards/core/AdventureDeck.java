package com.comp_3004.quest_cards.core;

import java.util.Stack;

public class AdventureDeck extends Deck {
	private Stack<Adventure> deck;

	//constructor
	public AdventureDeck() {
		this.deck = new Stack<Adventure>();
		initFoes();
		initWeapons();
		initAllies();
		initAmour();
		initTests();
	}
	
	protected Adventure drawCard() {
		return deck.pop();
	}
	
	private void initFoes() {
		for(int i=0; i<8; i++) {
			Foe thieves = new Foe("Thieves", 5);
			this.deck.add(thieves);
			Foe saxonKnight = new Foe("Saxon Knight", 15, 25);
			this.deck.add(saxonKnight);
		}
		for(int i=0; i<7; i++) {
			Foe robberKnight = new Foe("Robber Knight", 15);
			this.deck.add(robberKnight);
		}
		for(int i=0; i<6; i++) {
			Foe evilKnight = new Foe("Evil Knight", 20, 30);
			this.deck.add(evilKnight);
		}
		for(int i=0; i<5; i++) {
			Foe saxons = new Foe("Saxons", 10, 20);
			this.deck.add(saxons);
		}
		for(int i=0; i<4; i++) {
			Foe boar = new Foe("Boar", 5, 15);
			this.deck.add(boar);
			Foe mordred = new Foe("Mordred", 30);
			this.deck.add(mordred);
		}
		for(int i=0; i<3; i++) {
			Foe blackKnight = new Foe("Black Knight", 25, 35);
			this.deck.add(blackKnight);
		}
		for(int i=0; i<2; i++) {
			Foe giant = new Foe("Giant", 40);
			this.deck.add(giant);
			Foe greenKnight = new Foe("Green Knight", 25, 40);
			this.deck.add(greenKnight);
		}
		Foe dragon = new Foe("Dragon", 50, 70);
		this.deck.add(dragon);
	}
	
	private void initWeapons() {
		for(int i=0; i<11; i++) {
			Weapon horse = new Weapon("Horse", 10);
			this.deck.add(horse);
		}
		for(int i=0; i<16; i++) {
			Weapon sword = new Weapon("Sword", 10);
			this.deck.add(sword);
		}
		for(int i=0; i<2; i++) {
			Weapon excalibur = new Weapon("Excalibur", 30);
			this.deck.add(excalibur);
		}
		for(int i=0; i<6; i++) {
			Weapon lance = new Weapon("Lance", 20);
			this.deck.add(lance);
			Weapon dagger = new Weapon("Dagger", 5);
			this.deck.add(dagger);
		}
		for(int i=0; i<8; i++) {
			Weapon battleAx = new Weapon("Battle-Ax", 15);
			this.deck.add(battleAx);
		}
	}
	
	private void initAllies() {
		Ally gawain = new Ally("Sir Gawain", 10, 0);
		this.deck.add(gawain);
		Ally pellinore = new Ally("King Pellinore", 10, 0);
		this.deck.add(pellinore);
		Ally percival = new Ally("Sir Percival", 5, 0);
		this.deck.add(percival);
		Ally tristan = new Ally("Sir Tristan", 10, 0);
		this.deck.add(tristan);
		Ally arthur = new Ally("King Arthur", 10,2);
		this.deck.add(arthur);
		Ally guinevere = new Ally("Queen Guinevere", 0, 3);
		this.deck.add(guinevere);
		Ally merlin = new Ally("Merlin", 0, 0);
		this.deck.add(merlin);
		Ally iseult = new Ally("Queen Iseult", 0, 2);
		this.deck.add(iseult);
		Ally lancelot = new Ally("Sir Lancelot", 15, 0);
		this.deck.add(lancelot);
		Ally galahad = new Ally("Sir Galahad", 15, 0);
		this.deck.add(galahad);
	}
	
	private void initTests() {
		for(int i=0; i<2; i++) {
			Test questingBeast = new Test("Test of the Questing Beast");
			this.deck.add(questingBeast);
			Test temptation = new Test("Test of Temptation");
			this.deck.add(temptation);
			Test valor = new Test("Test of Valor");
			this.deck.add(valor);
			Test morganLeFey = new Test("Test of Morgan Le Fey");
			this.deck.add(morganLeFey);
		}
	}
	
	private void initAmour() {
		for(int i=0; i<8; i++) {
			Amour a = new Amour();
			this.deck.add(a);
		}
	}
	
	public void printDeck() {
		System.out.printf("Adventure Deck:\n");
		System.out.printf("%-15s%-15s%s\n", "Name", "Battle Points", "Type");
		System.out.printf("==================================\n");
		for(Adventure a : this.deck) {
			a.printCard();
		}
		System.out.printf("Number of cards: %s\n", this.deck.size());
	}
	

}