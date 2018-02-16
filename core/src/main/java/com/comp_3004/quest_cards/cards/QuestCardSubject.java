package com.comp_3004.quest_cards.cards;

import java.util.LinkedList;

public class QuestCardSubject extends QuestCard{
	
	protected boolean played = false;
	LinkedList<AllyObserver> obs;
	
	public QuestCardSubject(String n, int s) {
		super(n, s);
		obs = new LinkedList<AllyObserver>();
	}
	
	public QuestCardSubject(String n, int s, String f) {
		super(n, s, f);
		obs = new LinkedList<AllyObserver>();	
	}
	
	public void register(AllyObserver ob) {
		obs.add(ob);
		ob.subject = this;
	}
	
	private void notifyAllO() {
		for(AllyObserver ob: obs) {
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
