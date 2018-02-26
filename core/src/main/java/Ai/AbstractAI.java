package Ai;

import com.comp_3004.quest_cards.player.Player;

public abstract class AbstractAI{

	 public abstract boolean DoIParticipateInTournament();
	 public abstract boolean DoISponsorAQuest();
	 public abstract boolean doIParticipateInQuest();
	 public abstract boolean nextBid();
	 public abstract boolean discardAfterWinningTest();
	
	 public abstract boolean TournamentPlayTurn();
	 
	 
	 //setters
	 public abstract void setPlayer(Player p);
	 public abstract void setTournamentParticipation(TourParticipation t);
	 public abstract void setTourPlay(TourPlay p);
	 public abstract void setSponsor(DoSponsor s);
}