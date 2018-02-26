package Ai;

import java.util.ArrayList;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;

public class PlayInQuest2 extends PlayInQuest {

	public void playInQuest(Player pl, AdventureCard stageCard) {
		pl.setState("playQuest");
		if(stageCard instanceof FoeCard) {
			//if last stage, play strongest combo
			if(pl.getQuest().getCurrentStageNum() == pl.getQuest().getQuest().getStages() - 1) {
				ArrayList<AdventureCard> cardsToPlay = new ArrayList<AdventureCard>();
				for(AdventureCard c : pl.getHand()) {
					if(c instanceof AllyCard)
						cardsToPlay.add(c);
					if(c instanceof WeaponCard) {
						boolean weaponInList = false;
						for(AdventureCard w : cardsToPlay) {
							if(c.getName() == w.getName()) {
								weaponInList = true;
								break;
							}
						}
						if(!weaponInList)
							cardsToPlay.add(c);
					}
				}
				for(AdventureCard c : cardsToPlay)
					pl.playCard(c, -1);
				pl.getQuest().doneAddingCards();
			}
			//play amour first, then ally, then weapon. always increase by at least 10
			else {
				AdventureCard targetCard = null;
				boolean playedAmour = false;
				for(AdventureCard c : pl.getActive()) {
					if(c instanceof AmourCard)
						playedAmour = true;
				}
				for(AdventureCard c : pl.getHand()) {
					if(c instanceof AmourCard && !playedAmour) {
						targetCard = c;
						break;
					}
				}
				if(targetCard != null) {
					pl.playCard(targetCard, -1);
					pl.getQuest().doneAddingCards();
					return;
				}
				for(AdventureCard c : pl.getHand()) {
					if(c instanceof AllyCard && c.getBattlePts() >= 10) {
						targetCard = c;
						break;
					}
				}
				if(targetCard != null) {
					pl.playCard(targetCard, -1);
					pl.getQuest().doneAddingCards();
					return;
				}
				for(AdventureCard c : pl.getHand()) {
					if(c instanceof WeaponCard && c.getBattlePts() >= 10) {
						targetCard = c;
						break;
					}
				}
				if(targetCard != null) {
					pl.playCard(targetCard, -1);
					pl.getQuest().doneAddingCards();
				}
			}
		}
		else if(stageCard instanceof TestCard) {
			nextBid(pl);
		}
	}


	public boolean nextBid(Player pl) {
		//if(bidRound == 1) {
			int discards = 0;
			for(AdventureCard c : pl.getHand()) {
				if(c instanceof FoeCard && c.getBattlePts() < 25) {
					discards++;
				}
			}
			System.out.println(discards);
			System.out.println(pl.getFreeBids());
			if((discards+pl.getFreeBids()) > pl.getQuest().getHighestBid())
				pl.getQuest().placeBid(discards+pl.getFreeBids(), pl);
			else
				pl.getQuest().placeBid(-1, pl);
		//}
		return false;
	}
}
