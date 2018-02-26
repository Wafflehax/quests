package Ai;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;

public class QuestParticipation1 extends QuestParticipation {

	@Override
	/*
	 * I!have!2!weapons/allies!per!stage!!!!AND
	!!!C2:!I!have!at!least!2!foes!of!less!than!20!points
	 */
	public boolean doIParticipateInQuest(Player p) {
		int numFoes = 0;
		int numWeaponsAllies = 0;
		for(AdventureCard c : p.getHand()) {
			if(c instanceof FoeCard && c.getBattlePts() < 20)
				numFoes++;
			else if(c instanceof WeaponCard || c instanceof AllyCard)
				numWeaponsAllies++;
		}
		if((p.getQuest().getQuest().getStages() * 2 < numWeaponsAllies) && numFoes >= 2)
			return true;
		return false;
	}

}
