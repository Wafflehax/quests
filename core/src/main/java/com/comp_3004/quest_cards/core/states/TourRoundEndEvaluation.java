package com.comp_3004.quest_cards.core.states;

import java.util.ArrayList;
import java.util.Stack;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.Card;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameController;
import com.comp_3004.quest_cards.player.Player;

import utils.IntPlayerPair;
import utils.utils;

public class TourRoundEndEvaluation extends State{

	private int tourStage = 1;
	private int joiners;
	private int bonus;
	
	public TourRoundEndEvaluation(GameController c) {
		super(c);
		TournamentCard tour = (TournamentCard)this.c.m.getStory();
		bonus = tour.getBonusSh();
		
	}

	@Override
	public void msg() {
		String msg = "Calculating Player battle points and outcome of stage " + tourStage;
		joiners = this.c.m.getJoiners();
		log.info(msg);
		//calculate battle points
		int pl = this.c.m.getNumPlayers();
		if(pl > 0) {
			Stack<IntPlayerPair> pairs = new Stack<IntPlayerPair>();
			for(int i = 0 ; i < pl; i++, this.c.m.nextPlayer()) {
				int pp = calcBattlePoints(this.c.m.getcurrentTurn());
				IntPlayerPair calcedPoints = new IntPlayerPair(pp, this.c.m.getcurrentTurn()); // <pts, player>
				pairs.add(calcedPoints);
			}
			pairs = utils.getMaxIntPairs(pairs);
			ArrayList<Player> winners = new ArrayList<Player>();
			if(pairs.size() > 0) {
				for(int i = 0; i < pairs.size(); i++) {
					winners.add(pairs.get(i).player);
				}
			}
			String out = "";
			if(pairs.size() == 1) {
				// one winner display and tour ends, resets turns to regular sequence before Tour
				out += pairs.get(0).player.getName() + " won the tournament! Gained Sheilds: " + joiners + " + bonus(" + bonus + ") = " + (joiners+bonus);
				this.c.m.getcurrentTurn().addShields(joiners + bonus);
				log.info(out);
				// discard amours, weapons
				this.c.m.discardAmours();
				this.c.m.discardWeapons();
				//set turn to regular before tour
				this.c.m.setPlayers(this.c.m.playersTemp);
				this.c.m.playersTemp = null;
				//pop curr state go to next
				this.c.m.sPop(); //pop
				this.c.m.StateMsg(); //move on
			}
			else if(pairs.size() == 2) {
				if(tourStage == 1) {
					out += "Tied. Here are the tied players going to the Tie Breaker:\n";
					//discard of everyone's weapons
					this.c.m.discardWeapons();
					//set players to those tied					
					this.c.m.SetPlayerArrayResetPos(winners);
					for(int i = 0; i < this.c.m.getPlayers().size(); i++) {
						out += "Player : " + this.c.m.getPlayers().getPlayerAtIndex(i).getName() + "\n";
					}
					log.info(out);
					// push more player turns and do not pop self
					for(int i = 0; i < this.c.m.getPlayers().size(); i++) {
						this.c.m.pushSt(new TourPlayerTurn(this.c));
					}
					this.c.m.StateMsg(); // move to next state					
					tourStage++;
				}
				else if(tourStage == 2) { //over Tied players win
					out += "Tied again. Tied players win " + (bonus+joiners) + " battle points:\n";
					//tied but this was the last round discard weapons,amours
					this.c.m.discardAmours();
					this.c.m.discardWeapons();
					for(int i = 0; i < this.c.m.getPlayers().size(); i++) {
						out += "Player : " + this.c.m.getPlayers().getPlayerAtIndex(i).getName() + " With Rank ";
						this.c.m.getPlayers().getPlayerAtIndex(i).addShields(bonus+joiners);
						out += this.c.m.getPlayers().getPlayerAtIndex(i).getRankS();
						out += " and shields " + this.c.m.getPlayers().getPlayerAtIndex(i).getShields() + "\n";
					}
					tourStage++;
					log.info(out);
					//set players back to regular turn before tour
					this.c.m.setPlayers(this.c.m.playersTemp);
					this.c.m.playersTemp = null;
					//over pop self and move on
					this.c.m.sPop();
					this.c.m.StateMsg(); //moving to next state
				}
				else {
					log.info("Error stage greater than 2. Can't play more than two rounds start,tie breaker");
				}
			}
			else {
				log.info("Error calc winners");
			}
		}
		else
			log.info("Error no players to calc points but tour started with players");
	}

	public int calcBattlePoints(Player p) {
		int bp = p.getRankBattlePts();
		String out = "";
		out += "Player : " + p.getName() + " has rank: "	+ p.getRankBattlePts() + "\nbattlepoints: ";
		for(int w = 0; w < p.getActive().size(); w++) {
			AdventureCard c = p.getActive().get(w);
			if(c instanceof WeaponCard) {
				 WeaponCard weapon = (WeaponCard)c; bp += weapon.getBattlePts();
				 out += weapon.getName() + " : "  + weapon.getBattlePts() + " ";
			}else if(c instanceof AllyCard) {
				 AllyCard ally = (AllyCard)c; bp += ally.getBattlePts();
				 out += ally.getName() + " : "  + ally.getBattlePts() + " ";
			}else if(c instanceof AmourCard) {
				AmourCard amour = (AmourCard)c; bp += amour.getBattlePts();
				out += amour.getName() + " : " + amour.getBattlePts() + " ";
			}
		}
		out += " ==>total : " + bp;
		log.info(out);
		return bp;
	}
	
	
	@Override
	public void no() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void yes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handPress(Card c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disCardPress(Card c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doneTurn() {
		// TODO Auto-generated method stub
		return false;
	}
	
}