package Ai;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.player.Player;

public class AI  extends AbstractAI{

	Player pl;
	TourParticipation tourp;
	TourPlay tourplay;
	DoSponsor spons;
	QuestParticipation questp;
	PlayInQuest questplay;
	DiscardAfterTest discard;
	
	public AI() {}
	
	public AI(TourParticipation t) {
		this.tourp = t;
	}
	
	//setters
	public void setTournamentParticipation(TourParticipation t) {	this.tourp = t;	}
	public void setTourPlay(TourPlay p) { this.tourplay = p; }
	public void setSponsor(DoSponsor s) { this.spons = s;  }	
	public void setQuestParticipation(QuestParticipation p) { this.questp = p; }
	public void setQuestPlay(PlayInQuest p) { this.questplay = p; }
	public void setDiscardAfterTest(DiscardAfterTest d) { this.discard = d; }
	
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
	
	//test function might remove
	public boolean doSp() {
		if(this.spons == null)
			return false;
		return this.spons.DoISponsorAQuest(this.pl);
	}
	

	public boolean doIParticipateInQuest() {
		if(this.questp == null)
			return false;
		return this.questp.doIParticipateInQuest(this.pl);
	}

	public void playInQuest(AdventureCard stageCard) {
		if(this.questplay != null)
			this.questplay.playInQuest(this.pl, stageCard);
	}

	public boolean discardAfterWinningTest() {
		if(this.discard == null)
			return false;
		return this.discard.discardAfterWinningTest(this.pl);
	}

	public void setPlayer(Player p) {
		this.pl = p;
	}




}
