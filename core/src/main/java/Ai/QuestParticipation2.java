package Ai;

import java.util.ArrayList;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;

public class QuestParticipation2 extends QuestParticipation {

	public boolean doIParticipateInQuest(Player pl) {
		ArrayList<AdventureCard> validCards = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> validFoes = new ArrayList<AdventureCard>();
		for(AdventureCard c : pl.getHand()) {
			if(c instanceof WeaponCard || c instanceof AllyCard || c instanceof AmourCard) {
				if(c.getBattlePts() >= 10)
					validCards.add(c);
			}
			else if(c instanceof FoeCard) {
				if(c.getBattlePts() < 25)
					validFoes.add(c);
			}
		}
		if(validCards.size() >= pl.getQuest().getQuest().getStages() && validFoes.size() >= 2) {
			return true;
		}
		else {
			return false;
		}
	}

}
