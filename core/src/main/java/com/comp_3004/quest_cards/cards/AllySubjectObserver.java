package com.comp_3004.quest_cards.cards;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.Stories.Quest;


public class AllySubjectObserver extends AllyCard {

	static Logger log = Logger.getLogger(Quest.class); //log4j logger
	
	LinkedList<AllySubjectObserver> obs;
	private AllySubjectObserver subject;
	private int abp, abids;
	private boolean activated;
	private String[] activators;
	private boolean inPlay = false;
	
	public boolean isActivated() { return activated; }
	
	public AllySubjectObserver(String n, int bp, int bd) {
		super(n, bp, bd);
	}

	public AllySubjectObserver(String n, int bp, int bd, int Abp, int Abids, String[] ac) {
		super(n, bp, bd);
		this.abids = Abids;
		this.abp = Abp;
		activated = false;
		obs = new LinkedList<AllySubjectObserver>();
		this.activators = ac;
	}

	public void register(AllySubjectObserver c) {
		if(c != this) {
			obs.add(c);
			c.subject = this;
		}
	}
	
	
	@Override
	public void setState(State s) {
		// only notify when inPlay Changes
		boolean inPlay = inPlay(s);
		if(this.inPlay != inPlay) {
			this.inPlay = inPlay;
			this.state = s;
			notifyAllO();
		}
	}
	
	private boolean inPlay(State s) {
		if(s == State.PLAY || s == State.STAGE || s == State.QUEST)
			return true;
		return false;
	}
	
	private void update() {
		log.info("I => " + this.getName() + " Got update from subject => " + subject.getName());
		//Subject are you my activator?
		boolean myActivator = false;
		String sub = subject.getName();
		for(int i = 0; i < activators.length; i++) {
			if(activators[i].equalsIgnoreCase(sub)) {
				myActivator = true;
				break;
			}
		}
		//is activator inPlay && myself? 
		if(myActivator && subject.inPlay(subject.getState()) && this.inPlay(getState()))
			activate();
		else if(myActivator && subject.inPlay(subject.getState()) == false || this.inPlay(getState()) == false)
			deactivate();
		//if not activator do nothing
	}
	
	private void notifyAllO() {
		for(AllySubjectObserver ob : obs) {
			ob.update();
		}
	}
	
	
	private void activate() {
		if(!activated) {
			switchvars();
			activated = true;
			notifyAllO();
		}
	}
	
	private void deactivate() {
		if(activated) {
			switchvars();
			activated = false;
			notifyAllO();
		}
	}
	
	private void switchvars() {
		int tempB = this.battlePts;
		int tempBids = this.bids;
		
		this.battlePts = abp;
		this.bids = abids;
		
		this.abids = tempB;
		this.abp = tempBids;
	}
	
	
	
	
	
	
	
}
