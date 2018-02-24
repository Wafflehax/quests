package com.comp_3004.quest_cards.Stories;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;

public class Strategy2 extends AI{
	
	private static Logger log = Logger.getLogger(Strategy2.class); //log4j logger
	
	private Player pl;
	
	public Strategy2() {}
	
	public void setPlayer(Player p) {
		this.pl = p;
	}
	
	@Override
	public boolean DoIParticipateInTournament() {
		//always participate
		log.info("participating");
		return true;
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
		//I play as few cards to get 50 or my best possible Battle points
		
		if(this.pl != null) {
			LinkedList<AdventureCard> active = this.pl.getActive();
			LinkedList<AdventureCard> hand = this.pl.getHand();
			if(active != null && hand != null && this.pl.getTour() != null) {
				int bp = this.pl.getTour().calcBattlePoints(this.pl);
				
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
							if(this.pl.playCard(c))
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
