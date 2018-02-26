package Ai;

import com.comp_3004.quest_cards.player.Player;

public class AI  extends AbstractAI{

	Player pl;
	TourParticipation tourp;
	TourPlay tourplay;
	DoSponsor spons;
	
	public AI() {
		
	}
	
	public AI(TourParticipation t) {
		this.tourp = t;
	}
	
	//setters
	public void setTournamentParticipation(TourParticipation t) {	this.tourp = t;	}
	public void setTourPlay(TourPlay p) { this.tourplay = p; }
	public void setSponsor(DoSponsor s) { this.spons = s;  }	
	
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
		if(this.spons == null)
			return false;
		return this.spons.DoISponsorAQuest(this.pl);
	}

	public boolean doIParticipateInQuest() {
		return false;
	}

	public boolean nextBid() {
		return false;
	}

	public boolean discardAfterWinningTest() {
		return false;
	}

	public void setPlayer(Player p) {
		this.pl = p;
	}


	

	
}
