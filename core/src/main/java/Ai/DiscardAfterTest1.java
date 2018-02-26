package Ai;

import java.util.ArrayList;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.player.Player;

public class DiscardAfterTest1 extends DiscardAfterTest {

	@Override
	public boolean discardAfterWinningTest(Player pl) {
		ArrayList<AdventureCard> discards = new ArrayList<AdventureCard>();
		for(AdventureCard c : pl.getHand()) {
			if(c instanceof FoeCard && c.getBattlePts() < 20) {
				discards.add(c);
			}
		}
		for(AdventureCard c : discards)
			pl.discardCard(c, pl.getQuest().getAdvDeck());
		return false;
	}
}
