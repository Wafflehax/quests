package Ai;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.player.Player;

public class AI  extends AbstractAI{

	Player pl;
	TourParticipation tourp;
	TourPlay tourplay;
	DoSponsor spons;
	Quests2 quests;
	
	public AI() {}
	
	public AI(TourParticipation t) {
		this.tourp = t;
	}
	
	//setters
	public void setTournamentParticipation(TourParticipation t) {	this.tourp = t;	}
	public void setTourPlay(TourPlay p) { this.tourplay = p; }
	public void setSponsor(DoSponsor s) { this.spons = s;  }	
	public void setQuest(Quests2 q) {
		this.quests = q;
		this.quests.setPlayer(pl);
	}
	
	public boolean DoIParticipateInTournament() {
		if(this.tourp == null)
			return false;
		return this.tourp.DoIParticipateInTournament(this.pl);
	}

	public boolean TournamentPlayTurn() {
		if(this.tourplay == null)
			return false;
		return this.tourplay.TournamentPlayTurn(this.pl);
	}
	
	public boolean DoISponsorAQuest() {
		if(this.quests == null)
			return false;
		return this.quests.DoISponsorAQuest();
	}
	
	//test function might remove
	public boolean doSp() {
		if(this.spons == null)
			return false;
		return this.spons.DoISponsorAQuest(this.pl);
	}
	

	public boolean doIParticipateInQuest() {
		if(this.quests == null)
			return false;
		return this.quests.doIParticipateInQuest();
	}

	public boolean nextBid() {
		if(this.quests == null)
			return false;
		return this.quests.nextBid();
	}

	public boolean discardAfterWinningTest() {
		if(this.quests == null)
			return false;
		return this.quests.discardAfterWinningTest();
	}

	public void setPlayer(Player p) {
		this.pl = p;
	}

	public void playInQuest(AdventureCard stageCard) {
		if(this.quests != null)
			this.quests.playInQuest(stageCard);
	}


}
