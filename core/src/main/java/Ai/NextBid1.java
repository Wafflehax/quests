package Ai;

import java.util.LinkedHashMap;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.player.Player;

public class NextBid1 extends NextBid {

	public boolean nextBid(Player pl) {
			int discards = 0;
			for(AdventureCard c : pl.getHand()) {
				if(c instanceof FoeCard && c.getBattlePts() < 20) {
					discards++;
				}
			}
			if((discards+pl.getFreeBids()) > pl.getQuest().getHighestBid())
				pl.getQuest().placeBid(discards+pl.getFreeBids(), pl);
			else
				pl.getQuest().placeBid(-1, pl);
		return false;
	}

}
