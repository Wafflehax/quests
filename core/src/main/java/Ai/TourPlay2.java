package Ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Player.Rank;


public class TourPlay2 extends TourPlay{

	private static Logger log = Logger.getLogger(TourPlay2.class); //log4j logger


	public boolean TournamentPlayTurn(Player p) {
		//I play as few cards to get 50 or my best possible Battle points
		if(p != null) {
			LinkedList<AdventureCard> active = p.getActive();
			LinkedList<AdventureCard> hand = p.getHand();
			if(active != null && hand != null && p.getTour() != null) {
				//calculate current battle points from active cards
				int bp = p.getTour().calcBattlePoints(p);
				if(bp < 50) {
					log.info("Playing card untill 50 battlepoints or out of cards");
					Collections.sort(hand, new Comparator<AdventureCard>() {
					     public int compare(AdventureCard o1, AdventureCard o2) {
					         return Integer.compare(o2.getBattlePts(), o1.getBattlePts());
					     }
					});
					LinkedList<AdventureCard> play = new LinkedList<AdventureCard>();
					play.addAll(hand);
					for(AdventureCard c: play) {
						if(bp < 50 && (c instanceof WeaponCard || c instanceof AmourCard || c instanceof AllyCard)) {
							if(p.playCard(c))
								bp += c.getBattlePts();	
						}
					}
					log.info("Stopped playing, now have " + bp + " battle points");
				}
				else
					log.info("played nothing already have 50 battle points");
			}
			else
				log.info("Error player hands null, or tour null");
		}
		else 
			log.info("Error strategy found no player");
		return false;
	}
}
