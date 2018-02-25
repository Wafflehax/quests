package com.comp_3004.quest_cards.Stories;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.player.Player;

public abstract class AbstractAI{

	 public abstract boolean DoIParticipateInTournament();
	 public abstract boolean DoISponsorAQuest();
	 public abstract boolean doIParticipateInQuest();
	 public abstract void playInQuest(AdventureCard stageCard);
	 public abstract boolean nextBid();
	 public abstract boolean discardAfterWinningTest();
	
	 
	 public abstract void setPlayer(Player p);
	 public abstract boolean TournamentPlayTurn();
	 
}