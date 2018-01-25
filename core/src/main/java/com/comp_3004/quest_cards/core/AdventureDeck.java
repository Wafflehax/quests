package com.comp_3004.quest_cards.core;

import java.util.Stack;

public class AdventureDeck extends Deck {
	private Stack<AdventureCard> deck;
	private Stack<AdventureCard> discard;

	//constructor
	public AdventureDeck() {
		this.deck = new Stack<AdventureCard>();
		this.discard = new Stack<AdventureCard>();
		initFoes();
		initWeapons();
		initAllies();
		initAmour();
		initTests();
	}
	
	protected AdventureCard drawCard() {
		return deck.pop();
	}
	
	private void initFoes() {
		for(int i=0; i<8; i++) {
			FoeCard thieves = new FoeCard("Thieves", 5);
			this.deck.add(thieves);
			FoeCard saxonKnight = new FoeCard("Saxon Knight", 15, 25);
			this.deck.add(saxonKnight);
		}
		for(int i=0; i<7; i++) {
			FoeCard robberKnight = new FoeCard("Robber Knight", 15);
			this.deck.add(robberKnight);
		}
		for(int i=0; i<6; i++) {
			FoeCard evilKnight = new FoeCard("Evil Knight", 20, 30);
			this.deck.add(evilKnight);
		}
		for(int i=0; i<5; i++) {
			FoeCard saxons = new FoeCard("Saxons", 10, 20);
			this.deck.add(saxons);
		}
		for(int i=0; i<4; i++) {
			FoeCard boar = new FoeCard("Boar", 5, 15);
			this.deck.add(boar);
			FoeCard mordred = new FoeCard("Mordred", 30);
			this.deck.add(mordred);
		}
		for(int i=0; i<3; i++) {
			FoeCard blackKnight = new FoeCard("Black Knight", 25, 35);
			this.deck.add(blackKnight);
		}
		for(int i=0; i<2; i++) {
			FoeCard giant = new FoeCard("Giant", 40);
			this.deck.add(giant);
			FoeCard greenKnight = new FoeCard("Green Knight", 25, 40);
			this.deck.add(greenKnight);
		}
		FoeCard dragon = new FoeCard("Dragon", 50, 70);
		this.deck.add(dragon);
	}
	
	private void initWeapons() {
		for(int i=0; i<11; i++) {
			WeaponCard horse = new WeaponCard("Horse", 10);
			this.deck.add(horse);
		}
		for(int i=0; i<16; i++) {
			WeaponCard sword = new WeaponCard("Sword", 10);
			this.deck.add(sword);
		}
		for(int i=0; i<2; i++) {
			WeaponCard excalibur = new WeaponCard("Excalibur", 30);
			this.deck.add(excalibur);
		}
		for(int i=0; i<6; i++) {
			WeaponCard lance = new WeaponCard("Lance", 20);
			this.deck.add(lance);
			WeaponCard dagger = new WeaponCard("Dagger", 5);
			this.deck.add(dagger);
		}
		for(int i=0; i<8; i++) {
			WeaponCard battleAx = new WeaponCard("Battle-Ax", 15);
			this.deck.add(battleAx);
		}
	}
	
	private void initAllies() {
		AllyCard gawain = new AllyCard("Sir Gawain", 10, 0);
		this.deck.add(gawain);
		AllyCard pellinore = new AllyCard("King Pellinore", 10, 0);
		this.deck.add(pellinore);
		AllyCard percival = new AllyCard("Sir Percival", 5, 0);
		this.deck.add(percival);
		AllyCard tristan = new AllyCard("Sir Tristan", 10, 0);
		this.deck.add(tristan);
		AllyCard arthur = new AllyCard("King Arthur", 10,2);
		this.deck.add(arthur);
		AllyCard guinevere = new AllyCard("Queen Guinevere", 0, 3);
		this.deck.add(guinevere);
		AllyCard merlin = new AllyCard("Merlin", 0, 0);
		this.deck.add(merlin);
		AllyCard iseult = new AllyCard("Queen Iseult", 0, 2);
		this.deck.add(iseult);
		AllyCard lancelot = new AllyCard("Sir Lancelot", 15, 0);
		this.deck.add(lancelot);
		AllyCard galahad = new AllyCard("Sir Galahad", 15, 0);
		this.deck.add(galahad);
	}
	
	private void initTests() {
		for(int i=0; i<2; i++) {
			TestCard questingBeast = new TestCard("Test of the Questing Beast");
			this.deck.add(questingBeast);
			TestCard temptation = new TestCard("Test of Temptation");
			this.deck.add(temptation);
			TestCard valor = new TestCard("Test of Valor");
			this.deck.add(valor);
			TestCard morganLeFey = new TestCard("Test of Morgan Le Fey");
			this.deck.add(morganLeFey);
		}
	}
	
	private void initAmour() {
		for(int i=0; i<8; i++) {
			AmourCard a = new AmourCard();
			this.deck.add(a);
		}
	}
	
	public void printDeck() {
		System.out.printf("Adventure Deck:\n");
		System.out.printf("%-15s%-15s%s\n", "Name", "Battle Points", "Type");
		System.out.printf("==================================\n");
		for(AdventureCard a : this.deck) {
			a.printCard();
		}
		System.out.printf("Number of cards: %s\n", this.deck.size());
	}
	

}