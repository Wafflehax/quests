package Ai;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.player.Player;

public abstract class AbstractAI{

	 public abstract boolean DoIParticipateInTournament();
	 public abstract boolean DoISponsorAQuest();
	 public abstract boolean doIParticipateInQuest();
	 public abstract void playInQuest();
	 public abstract boolean nextBid();
	 public abstract boolean discardAfterWinningTest();
	 public abstract boolean TournamentPlayTurn();
	 
	 
	 //setters
	 public abstract void setPlayer(Player p);
	 public abstract void setTournamentParticipation(TourParticipation t);
	 public abstract void setTourPlay(TourPlay p);
	 public abstract void setSponsor(DoSponsor s);
	 public abstract void setQuestParticipation(QuestParticipation p);
	 public abstract void setQuestPlay(PlayInQuest p);
	 public abstract void setNextBid(NextBid n);
	 public abstract void setDiscardAfterTest(DiscardAfterTest d);
	 
}