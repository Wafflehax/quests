package com.comp_3004.quest_cards.Stories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Player.Rank;

public class Strategy2 extends AbstractAI{
	
	private static Logger log = Logger.getLogger(Strategy2.class); //log4j logger
	
	private Player pl;
	private int bidRound = 1;
	
	public Strategy2() {}
	
	public void setPlayer(Player p) {
		this.pl = p;
	}
	
	@Override
	public boolean DoIParticipateInTournament() {
		//always participate
		log.info("participating");
		return true;
	}

	@Override
	public boolean DoISponsorAQuest() {
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
		pl.getQuest().sponsorS2();
		return true;
	}

	@Override
	public boolean doIParticipateInQuest() {
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
			bidRound = 1;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void playInQuest(AdventureCard stageCard) {
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
			nextBid();
		}
	}

	@Override
	public boolean nextBid() {
		if(bidRound == 1) {
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
		}
		return false;
	}

	@Override
	public boolean discardAfterWinningTest() {
		ArrayList<AdventureCard> discards = new ArrayList<AdventureCard>();
		for(AdventureCard c : pl.getHand()) {
			if(c instanceof FoeCard && c.getBattlePts() < 25) {
				discards.add(c);
			}
		}
		System.out.println(pl.getState());
		for(AdventureCard c : discards) {
			pl.discardCard(c, pl.getQuest().getAdvDeck());
			pl.getQuest().discardedACard();
		}
		return false;
	}

	@Override
	public boolean TournamentPlayTurn() {
		//I play as few cards to get 50 or my best possible Battle points
		
		if(this.pl != null) {
			LinkedList<AdventureCard> active = this.pl.getActive();
			LinkedList<AdventureCard> hand = this.pl.getHand();
			if(active != null && hand != null && this.pl.getTour() != null) {
				//calculate current battle points from active cards
				int bp = this.pl.getTour().calcBattlePoints(this.pl);
				if(bp < 50) {
					log.info("Playing card untill 50 battlepoints or out of cards");
					Collections.sort(hand, new Comparator<AdventureCard>() {
					     public int compare(AdventureCard o1, AdventureCard o2) {
					         return Integer.compare(o2.getBattlePts(), o1.getBattlePts());
					     }
					});
					LinkedList<AdventureCard> play = new LinkedList<AdventureCard>();
					play.addAll(hand);
					for(AdventureCard c: play) {
						if(bp < 50 && (c instanceof WeaponCard || c instanceof AmourCard || c instanceof AllyCard)) {
							if(this.pl.playCard(c))
								bp += c.getBattlePts();	
						}
					}
					log.info("Stopped playing, now have " + bp + " battle points");
				}
				else
					log.info("played nothing already have 50 battle points");
			}
			else
				log.info("Error player hands null, or tour null");
		}
		else 
			log.info("Error strategy found no player");
		return false;
	}
	
}