package com.comp_3004.quest_cards.Stories;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.player.Player;

public class Strategy1 extends AbstractAI{

	private static Logger log = Logger.getLogger(Strategy1.class); //log4j logger
	
	private Player pl;
	
	public Strategy1() {}
	
	public void setPlayer(Player p) {
		this.pl = p;
	}
	
	@Override
	public boolean DoIParticipateInTournament() {
		//Can any player including myself win/evolve by winning this tournament?
		if(this.pl != null && this.pl.getTour() != null) {
			//if any player can rank/win then participate, not everyone could have answered yet
			for(Player p: this.pl.getTour().getParticipants()) {
				if(canIRankWin(p)) {
					log.info("Participating since " + p.getName() + " could win/evolve");
					return true;
				}
			}
		}
		log.info("Not Participating");
		return false;
	}
	
	private boolean canIRankWin(Player p) {
		if(this.pl != null && this.pl.getTour() != null) {
			int shields = this.pl.getShields();
			int needed = this.pl.sheildsToRank();
			if(needed == 0) {
				//highest rank Knight of round table can't rank higher but if in game winning tour participate
				if(this.pl.getTour().isGameWinTour())
					return true;
				return false;
			}
			else {
				int shAvail = this.pl.getTour().getJoiners() + this.pl.getTour().getCurTour().getBonusSh();
				if(shAvail >= needed)
					return true;
			}	
		}
	return false;
	}

	@Override
	boolean DoISponsorAQuest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean doIParticipateInQuest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean nextBid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean discardAfterWinningTest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean TournamentPlayTurn() {
		/*
		To decide what to play:
		If anyone participating can win/Evolve
		Then: I play the strongest possible hand!(including amour and allies)
		Else: I play only weapons I have two or more instances of
		 */
		
		
		return false;
	}

}
