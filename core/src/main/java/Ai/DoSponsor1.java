package Ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Player.Rank;

public class DoSponsor1 extends DoSponsor{
	private static Logger log = Logger.getLogger(DoSponsor1.class); //log4j logger
	public boolean DoISponsorAQuest(Player pl) {
		//check if any players would "evolve/win" if they complete quest
		int shieldsWon = pl.getQuest().getQuest().getStages();
		for(Player p : pl.getQuest().getPlayers()) {
			if(p.getRank() == Rank.SQUIRE) {
				if(shieldsWon + p.getShields() >= 5) {
					log.info(pl.getName()+" declines to sponsor: "+p.getName()+" will rank up if they complete quest");
					return false;
				}
			}
			else if(p.getRank() == Rank.KNIGHT) {
				if(shieldsWon + p.getShields() >= 7) {
					log.info(pl.getName()+" declines to sponsor: "+p.getName()+" will rank up if they complete quest");
					return false;
				}
			}
			else if(p.getRank() == Rank.CHAMPION_KNIGHT) {
				if(shieldsWon + p.getShields() >= 10) {
					log.info(pl.getName()+" declines to sponsor: "+p.getName()+" will win if they complete quest");
					return false;
				}
			}
		}
		
		//determine if player has valid cards to sponsor
		pl.getQuest().sponsorS1();
		return true;
	}
}
