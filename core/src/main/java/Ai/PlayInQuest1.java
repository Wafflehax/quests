package Ai;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;

public class PlayInQuest1 extends PlayInQuest {
	
	@Override
	public void playInQuest(Player pl) {
		pl.setState("playQuest");
		ArrayList<AdventureCard> cardsToPlay = new ArrayList<AdventureCard>();
		//if last stage, play strongest combo
		if(pl.getQuest().getCurrentStageNum() == pl.getQuest().getQuest().getStages() - 1) {
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
		
		else {
			cardsToPlay.clear();
			int cardsPlayed = 0;
			boolean amourPlayed = false;
			//play amour/allies first
			for(AdventureCard c : pl.getHand()) {
				if(c instanceof AmourCard && !amourPlayed) {
					cardsToPlay.add(c);
					cardsPlayed++;
					if(cardsPlayed == 2)
						break;
				}
				if(c instanceof AllyCard) {
					cardsToPlay.add(c);
					cardsPlayed++;
					if(cardsPlayed == 2)
						break;
				}
			}
			if(cardsPlayed < 2) {
				LinkedHashMap<Integer, String> weapons = new LinkedHashMap<Integer, String>();
				for(AdventureCard c : pl.getHand()) {
					if(c instanceof WeaponCard) {
						weapons.put(c.getBattlePts(), c.getName());
					}
				}
				while(cardsPlayed < 2) {
					String lowestWeapon = "";
					for(int i : weapons.keySet()) {
						lowestWeapon = weapons.get(i);
						weapons.remove(i);
						break;
					}
					for(AdventureCard c : pl.getHand()) {
						if(c.getName() == lowestWeapon) {
							cardsToPlay.add(c);
							cardsPlayed++;
							break;
						}
					}
				}
			}
			for(AdventureCard c : cardsToPlay) {
				System.out.println(c.getName());
				pl.playCard(c, -1);
			}
			pl.getQuest().doneAddingCards();
		}
	}

}
