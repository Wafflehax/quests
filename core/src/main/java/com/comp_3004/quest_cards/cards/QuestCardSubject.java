package com.comp_3004.quest_cards.cards;

import java.util.LinkedList;

import utils.Observer;
import utils.Subject;

public class QuestCardSubject extends QuestCard implements Subject{
	
	protected boolean played = false;
	LinkedList<Observer> obs;
	
	public QuestCardSubject(String n, int s) {
		super(n, s);
		obs = new LinkedList<Observer>();
	}
	
	public QuestCardSubject(String n, int s, String f) {
		super(n, s, f);
		obs = new LinkedList<Observer>();	
	}
	
	public void register(Observer ob) {
		obs.add(ob);
		ob.setSubject(this);
	}
	
	public void notifyAllO() {
		for(Observer ob: obs) {
			ob.update();
		}
	}
	
	//getter setters
	public boolean getPlayed() { return this.played; }
	public void setPlayed(boolean b) { 
		if(this.played != b) {
			this.played = b; 
			notifyAllO();
		}
	}	
	
}
