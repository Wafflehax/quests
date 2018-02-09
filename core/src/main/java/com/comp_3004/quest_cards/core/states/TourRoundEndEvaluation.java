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
import com.comp_3004.quest_cards.core.Player;

import utils.IntPlayerPair;
import utils.utils;

public class TourRoundEndEvaluation extends State{

	public TourRoundEndEvaluation(GameController c) {
		super(c);
	}

	@Override
	public void msg() {
		String msg = "Calculating Player battle points and outcome";
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
				TournamentCard tour = (TournamentCard)this.c.m.getStory();
				int joiners = this.c.m.getJoiners();
				int bonus = tour.getBonusSh();
				out += pairs.get(0).player.getName() + " won the tournament! Gained Sheilds: " + joiners + " + bonus(" + bonus + ") = " + (joiners+bonus);
				this.c.m.getcurrentTurn().addShields(joiners + bonus);
				log.info(out);
				
				this.c.m.setPlayers(this.c.m.playersTemp);
				this.c.m.playersTemp = null;
				this.c.m.sPop(); //pop
				this.c.m.StateMsg(); //move on
			}
			else if(pairs.size() > 1) {
				this.c.m.SetPlayerArrayResetPos(winners); //players = winners //used if tied and need to play another round
				log.info("Ties not coded yet");
				
				
			}
			else {
				log.info("Error calc winners");
			}
			
			
		}
		else
			log.info("Error no players to calc points but tour started with players");
			
	}

	private int calcBattlePoints(Player p) {
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