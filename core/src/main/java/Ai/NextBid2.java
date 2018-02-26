package Ai;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.player.Player;

public class NextBid2 extends NextBid {
	private int bidRound = 1;
	@Override
	public boolean nextBid(Player pl) {
		if(bidRound == 1) {
			int discards = 0;
			for(AdventureCard c : pl.getHand()) {
				if(c instanceof FoeCard && c.getBattlePts() < 25) {
					discards++;
				}
			}
			if((discards+pl.getFreeBids()) > pl.getQuest().getHighestBid()) {
				pl.getQuest().placeBid(discards+pl.getFreeBids(), pl);
				bidRound++;
			}
			else {
				pl.getQuest().placeBid(-1, pl);
				bidRound = 1;
			}
		}
		else {
			int discards = 0;
			for(AdventureCard c : pl.getHand()) {
				if(c instanceof FoeCard && c.getBattlePts() < 25) {
					discards++;
				}
			}
			//get duplicates
			LinkedHashMap<String, Integer> dups = new LinkedHashMap<String, Integer>();
			for(AdventureCard c : pl.getHand()) {
				if(dups.containsKey(c.getName()))
					dups.put(c.getName(), dups.get(c.getName())+1);
				else
					dups.put(c.getName(), 1);
			}
			for(String s : dups.keySet()) {
				if(dups.get(s) > 1)
					discards += (dups.get(s) - 1);
			}
			if((discards+pl.getFreeBids()) > pl.getQuest().getHighestBid()) {
				pl.getQuest().placeBid(discards+pl.getFreeBids(), pl);
				bidRound++;
			}
			else {
				pl.getQuest().placeBid(-1, pl);
				bidRound = 1;
			}
		}
		return false;
	}
}
