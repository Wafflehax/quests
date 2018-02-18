package com.comp_3004.quest_cards.cards;

import utils.Observer;
import utils.Subject;

public class TestObserver extends TestCard implements Observer{
	
	private QuestCardSubject subject;
	private int abids;
	private boolean activated;
	
	public boolean activated() { return this.activated; }

	public TestObserver(String n) {
		super(n);
	}
	
	public TestObserver(String n, int Abids) {
		super(n);
		this.abids = Abids;
	}

	public void update() {
		if(subject.getPlayed())
			activate();
		else if(subject.getPlayed() == false)
			deactivate();
	}
	
	private void activate() {
		if(!activated) {
			switchvars();
			activated = true;
		}
	}
	
	private void deactivate() {
		if(activated) {
			switchvars();
			activated = false;
		}
	}
	
	private void switchvars() {
		int tempBids = this.getMinBid();
		
		this.setMinBid(abids);
		
		this.abids = tempBids;
	}

	public void setSubject(Subject s) {
		if(s instanceof QuestCardSubject)
			this.subject = (QuestCardSubject)s;
	}

	

}
