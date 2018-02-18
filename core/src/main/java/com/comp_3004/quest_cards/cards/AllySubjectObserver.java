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
	
	//removes observer from List and their point to this instance
	public void deregister(AllySubjectObserver c) {
		c.subject = null;
		this.deactivate();
		obs.remove(c);
	}
	
	public void deregisterBoth(AllySubjectObserver c) {
		if(this.activated) {
			this.switchvars();
			this.activated = false;
		}
		if(c.activated) {
			c.activated = false;
			c.switchvars();	
		}
		obs.remove(c);
		c.obs.remove(this);
		c.subject = null;
		subject = null;
	}
	
	
	@Override
	public void setState(State s) {
		// only notify when inPlay Changes
		boolean inPlay = inPlay(s);
		if(this.inPlay(getState()) != inPlay) {
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
		if(subject == null) {
			//deregistered so special ability disabled
			log.info(this.getName() + " no longer observing a subject");
			this.activated = false;
			this.switchvars();
		}
		else {
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
			if(myActivator && subject.inPlay(subject.getState()) && this.inPlay(getState()) && subject.subject != null)
				activate();
			else if(myActivator && subject.inPlay(subject.getState()) == false || this.inPlay(getState()) == false || subject.subject == null)
				deactivate();
			//if not activator do nothing
		}
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
		
		this.abids = tempBids;
		this.abp = tempB;
	}
	
	
	
	
	
	
	
}
