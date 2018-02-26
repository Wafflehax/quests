package Ai;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;

public class TourPlay1 extends TourPlay{

	private static Logger log = Logger.getLogger(TourPlay1.class); //log4j logger
	
	public boolean TournamentPlayTurn(Player p) {

		/*
		To decide what to play:
		If another player who can win/evolve by winning this tournament does participate
		OR If I can win/evolve myself
		Then: I play the strongest possible hand(including amour and allies)
		Else: I play only weapons I have two or more instances of
		 */
		
		//check if I can win/evolve
		if(canIRankWin(p)) {
			playStrong(p);
		}
		else{
			// if any other participant can win/evolve then play strong
			boolean play = false;
			for(Player pl: p.getTour().getPlayers().getPlayers()) {
				if(canIRankWin(pl)) {
					play = true;
					break;
				}
			}
			if(play) {
				playStrong(p);				
			}
			else {
				playWeak(p);
			}
		}
		return false;
	}
	
	
	public void playStrong(Player p) {
		//play strongest possible hand, all of the cards you own
		log.info("Playing strongest possible hand, all cards");
		int bp = 0;
		//make copy of hand avoid concurrent modification
		LinkedList<AdventureCard> cards = new LinkedList<AdventureCard>(); 
		cards.addAll(p.getHand());
		for(AdventureCard c: cards) {
			if(c instanceof AllyCard || c instanceof WeaponCard || c instanceof AmourCard) {
				if(p.playCard(c)) {
					bp += c.getBattlePts();
				}
			}
		}
		log.info("Done Playing Cards battle points added " + bp);
	}
	
	public void playWeak(Player p) {
		//Play only weapons I have two or more instances of (in my hand)
		LinkedList<AdventureCard> handcopy = new LinkedList<AdventureCard>();
		LinkedList<AdventureCard> toPlay = new LinkedList<AdventureCard>();
		
		log.info("Getting cards to play");
		handcopy.addAll(p.getHand());
		//remove non weapons, move over p.hand to avoid concurrent modification
		for(AdventureCard c: p.getHand()) {
			if(!(c instanceof WeaponCard) && !(c instanceof AmourCard)) {
				handcopy.remove(c);
			}
		}
		//go through handcopy, and cards which exist more than once save in toplay
		LinkedList<AdventureCard> copy = new LinkedList<AdventureCard>();
		copy.addAll(handcopy);
		for(AdventureCard c: handcopy) {
			
			boolean contained = copy.remove(c);
			if(contained) {
				//remove cards of same type
				if(existsWCard(copy, c.getName())) {
					toPlay.add(c);                          //at least another cards of same type
					while(remove(c.getName(), copy)) {} //keep removing while list contains item
				}		
			}
		}
			
		
		if(!toPlay.isEmpty()) {
			log.info("Playing cards I have two or more instances of");
		}
		//playcards which I have two or more instances of, don't check if one of same type already played
		int bp = 0;
		for(AdventureCard c: toPlay) {
			p.playCard(c);
			bp += c.getBattlePts();
		}
		log.info("Done playing cards added " + bp + " more battle points");
	}

	/*
	 * removes all cards of name s, in handcopy
	 * returns true if item was in contained list, false if list did not contain item
	 */
	private boolean remove(String s, LinkedList<AdventureCard> handcopy) {
		for(AdventureCard w: handcopy) {
			if(w.getName().equalsIgnoreCase(s)) {
				return handcopy.remove(w); //true if list contained it
			}
		}
		return false;
	}
	
	private boolean existsWCard(LinkedList<AdventureCard> handcopy, String s) {
		for(AdventureCard w: handcopy) {
			if(w.getName().equalsIgnoreCase(s)) {
				return true;
			}
		}
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
