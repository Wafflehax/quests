package Ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.player.Player;

public class DoSponsor1 extends DoSponsor{

	public boolean DoISponsorAQuest(Player p) {
	//if someone else could win/evolve by winning this quest
	  //then do not sponsor
		
		boolean play = true;
		for(Player pl: p.getQuest().getPlayers()) {
			if(pl != p && canIRankWin(pl)) {
				play = false;
				break;
			}
		}
		//ELSE IF I have a sufficient number of valid foes (ie with increasing battle points)
		//taking into consideration having a test reduces this number by 1.
			//THEN SPONSOR AND SETUP1
		if(play) {
			//if I have sufficent number of foes to setup
			if(canSetUp(p)) {
				return true;
			}
			else {
				//decline
				return false;
			}
		}
		return play;
	}

	private class FoeBPPair{
		FoeCard c;
		int bp;
		private FoeBPPair(FoeCard c, int bp) {
			this.c = c;
			this.bp = bp;
		}
	}
	
	
	public boolean canSetUp(Player p) {
		//I have a sufficient number of valid foes (ie with increasing battle points)
		//taking into consideration having a test reduces this number by 1.
		
		
		int foesNeeded;
		//can have a max of one test in a quest
		TestCard test = getTest(p);
		if(test == null) {
			foesNeeded = 3;
		}
		else {
			foesNeeded = 2;
		}
		//get foe cards in increasing other of battle points
		QuestCard qu = p.getQuest().getQuest();
		LinkedList<FoeCard> foes = getFoes(p);
		if(foes.size() >= foesNeeded) {
			LinkedList<FoeBPPair> f = new LinkedList<FoeBPPair>();
			for(FoeCard c: foes) {
				f.add(new FoeBPPair(c, getBPFoe(c, qu)));
			}
			//sort increasing order
			Collections.sort(f, new Comparator<FoeBPPair>() {
			     public int compare(FoeBPPair o1, FoeBPPair o2) {
			         return Integer.compare(o1.bp, o2.bp);
			     }
			});
			if(test == null) {
				//get all foes
				LinkedList<FoeBPPair> setup = new LinkedList<FoeBPPair>();
				FoeBPPair item = f.getFirst();
				setup.add(item);
				f.remove(item);
				
				//collect Foes in increasing order of bp
				int i = 1;
				while(!f.isEmpty() && i < foesNeeded) {
					FoeBPPair toadd = f.getFirst();
					if(toadd.bp > setup.getLast().bp) {
						setup.add(toadd);
						i++;
					}
					f.removeFirst();	
				}
				if(i < foesNeeded)
					return false;
				else 
					return true;
			}
			else {
				//get all foes and test at end
				LinkedList<FoeBPPair> setup = new LinkedList<FoeBPPair>();
				FoeBPPair item = f.getFirst();
				setup.add(item);
				f.remove(item);
				
				//collect Foes in increasing order of bp
				int i = 1;
				while(!f.isEmpty() && i < foesNeeded) {
					FoeBPPair toadd = f.getFirst();
					if(toadd.bp > setup.getLast().bp) {
						setup.add(toadd);
						i++;
					}
					f.removeFirst();	
				}
				if(i < foesNeeded)
					return false;
				else 
					return true;
			}
		}
		return false;
	}
	
	private LinkedList<FoeCard> getFoes(Player p){
		LinkedList<FoeCard> cards = new LinkedList<FoeCard>();
		for(AdventureCard c: p.getHand()) {
			if(c instanceof FoeCard)
				cards.add((FoeCard)c);
		}
		return cards;
	}
	
	private int getBPFoe(FoeCard card, QuestCard quest) {
		int bp = 0;
		//code from Quests
		if(card instanceof FoeCard) {
			if(quest.getNamedFoe() == card.getName())
				bp += ((FoeCard) card).getAltBattlePts();
			else if(quest.getNamedFoe() == "allSaxons") {
				if(card.getName() == "Saxons" || card.getName() == "Saxon Knight")
					bp += ((FoeCard) card).getAltBattlePts();
				else
					bp += ((FoeCard) card).getBattlePts();
			}
			else if(quest.getNamedFoe() == "all") {
				if(((FoeCard) card).getAltBattlePts() != 0)
					bp += ((FoeCard) card).getAltBattlePts();
				else
					bp += ((FoeCard) card).getBattlePts();
			}
			else
				bp += ((FoeCard) card).getBattlePts();
		}
		return bp;
	}
	
	private TestCard getTest(Player p) {
		for(AdventureCard c: p.getHand()) {
			if(c instanceof TestCard) {
				return (TestCard)c;
			}
		}
		return null;
	}
	
	private boolean canIRankWin(Player p) {
		if(p != null && p.getQuest() != null) {
			int shields = p.getShields();
			int needed = p.sheildsToRank();
			if(needed == 0) { //if knight of table can't Rank higher
				return false;
			}
			else {
				//Each quest has three stages. So three shields could be won always
				int shAvail = 3;
				if(shAvail >= needed)
					return true;
			}	
		}
	return false;
	}
	
	
}
