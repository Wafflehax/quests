package Ai;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.player.Player;

public class TourParticipate1 extends TourParticipation{

	private static Logger log = Logger.getLogger(TourParticipation.class); //log4j logger
	
	public boolean DoIParticipateInTournament(Player p) {
		//Can any player including myself win/evolve by winning this tournament?
		if(p != null && p.getTour() != null) {
			//if any player can rank/win then participate, not everyone could have answered yet
			for(Player pe: p.getTour().getPlayers().getPlayers()) {
				if(canIRankWin(pe)) {
					log.info(pe.getName() + " is Participating since " + pe.getName() + " could win/evolve");
					return true;
				}
			}
		}
		log.info("Not Participating");
		return false;
	}
	
	private boolean canIRankWin(Player p) {
		if(p != null && p.getTour() != null) {
			int shields = p.getShields();
			int needed = p.sheildsToRank();
			if(needed == 0) {
				//highest rank Knight of round table can't rank higher but if in game winning tour participate
				if(p.getTour().isGameWinTour())
					return true;
				return false;
			}
			else {
				int shAvail = p.getTour().getJoiners() + p.getTour().getCurTour().getBonusSh();
				if(shAvail >= needed)
					return true;
			}	
		}
	return false;
	}
}
