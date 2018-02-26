package Ai;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.player.Player;

public class DiscardAfterTest2 extends DiscardAfterTest {

	@Override
	public boolean discardAfterWinningTest(Player pl) {
		ArrayList<AdventureCard> discards = new ArrayList<AdventureCard>();
		for(AdventureCard c : pl.getHand()) {
			if(c instanceof FoeCard && c.getBattlePts() < 25) {
				discards.add(c);
			}
		}
		for(AdventureCard c : discards)
			pl.discardCard(c, pl.getQuest().getAdvDeck());
		
		discards.clear();
		if(pl.getQuest().getBids().get(pl) > 0) {
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
					for(int i=0; i<(dups.get(s)-1); i++) {
						for(AdventureCard c : pl.getHand()) {
							if(s == c.getName() && !discards.contains(c)) {
								discards.add(c);
								break;
							}
						}
					}
			}
			
			System.out.println(discards.size());
			for(AdventureCard c : discards)
				System.out.println(c.getName());
			for(AdventureCard c : discards)
				pl.discardCard(c, pl.getQuest().getAdvDeck());
		}
		return false;
	}

}
