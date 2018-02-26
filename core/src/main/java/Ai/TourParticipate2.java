package Ai;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.player.Player;

public class TourParticipate2 extends TourParticipation{

	private static Logger log = Logger.getLogger(TourParticipation.class); //log4j logger
	
	
	@Override
	boolean DoIParticipateInTournament(Player p) {
		//always participate
		log.info("participating");
		return true;
	}

}
