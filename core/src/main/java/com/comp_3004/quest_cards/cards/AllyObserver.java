package com.comp_3004.quest_cards.cards;

public class AllyObserver extends AllyCard {

	protected QuestCardSubject subject;
	private int abp, abids;
	private boolean activated;
	
	
	public AllyObserver(String n, int bp, int bd) {
		super(n, bp, bd);
	}

	public AllyObserver(String n, int bp, int bd, int Abp, int Abids) {
		super(n, bp, bd);
		this.abp = Abp;
		this.abids = Abids;
	}

	public boolean activated() { return this.activated; }
	
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
		int tempB = this.battlePts;
		int tempBids = this.bids;
		
		this.battlePts = abp;
		this.bids = abids;
		
		this.abids = tempBids;
		this.abp = tempB;
	}
	
	
	
}
