package com.comp_3004.quest_cards.Stories;

import com.comp_3004.quest_cards.player.Player;

public abstract class AI{

	 public abstract boolean DoIParticipateInTournament();
	 abstract boolean DoISponsorAQuest();
	 abstract boolean doIParticipateInQuest();
	 abstract boolean nextBid();
	 abstract boolean discardAfterWinningTest();
	
	 
	 public abstract void setPlayer(Player p);
	 public abstract boolean TournamentPlayTurn();
	 
}