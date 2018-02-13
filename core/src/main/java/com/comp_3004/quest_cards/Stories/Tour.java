package com.comp_3004.quest_cards.Stories;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Players;

import utils.IntPlayerPair;
import utils.utils;

public class Tour {

	static Logger log = Logger.getLogger(Tour.class); //log4j logger
	private TournamentCard tour;					//current tournament
	private Players players;
	private Players tempPl;
	private AdventureDeck d;
	private int joiners;
	private ArrayList<Player> participants;
	private int round;
	private int leftAsk;
	private int leftToPlayCard;
	
	public TournamentCard getCurTour() { return tour; }
	
	
	public Tour(Players p, TournamentCard c, AdventureDeck d) {
		players = p;
		tour = c;
		this.d = d;
		//set everyone to TourPatricipationState
		for(Player pl : players.getPlayers()) {
			pl.setTour(this);
			pl.setState("tourask");
		}
		leftAsk = p.getNumPlayers();
		participants = new ArrayList<Player>();
		joiners = 0;
	}
	
	
	
	public void tourParticipate(Player p, boolean b) {
		leftAsk--;
		if(b) {
			participants.add(p);
			log.info(p.getName() + " is participating in the tournament");
			joiners++;	
		}
		else
			log.info(p.getName() + " is NOT participating in the tournament"); //left on ask
		players.next();
		
		//none left to ask
		if(leftAsk == 0) {
			if(joiners >= 2) {
				for(Player ps: participants)
					ps.setState("playtour");
				startTour();
			}
			else
				log.info("Can't start Tournament Need atleast 2 players");	
		}
	}	
	
	public void startTour() {
		round = 1;
		leftToPlayCard = joiners;
		log.info("Tournament Starting............adding card to joiners from adventure deck");
		for(Player ps: participants)
			ps.forceDrawAdventure(d);
		//set players to participants
		tempPl = players; //stored all players
		players.setPlayers(participants);
		log.info(players.current().getName() + " Its your turn press done when finished");
	}
	
	public boolean doneTurn() {
		if(players.current().tooManyHandCards()) {
			log.info("You have too many cards discard a card, then press done turn");
		}
		else {
			leftToPlayCard--;
			log.info(players.current().getName()+" is done playing cards for round: "+ round);
			if(leftToPlayCard == 0) {
				log.info("No more player turns calculating outcome");
				players.next();
				determineRoundOutCome();			
			}
			else {
				players.next();
				log.info(players.current().getName() + " Its your turn press done when finished");
			}
			return true;	
		}
		return false;
	}
	
	public void determineRoundOutCome() {
		String msg = "Calculating Player battle points and outcome of stage " + round;
		log.info(msg);
		//calculate battle points
		int pl = players.getNumPlayers();
		if(pl > 0) {
			int bonus = tour.getBonusSh();
			Stack<IntPlayerPair> pairs = new Stack<IntPlayerPair>();
			for(int i = 0 ; i < pl; i++, players.next()) {
				int pp = calcBattlePoints(players.current());
				IntPlayerPair calcedPoints = new IntPlayerPair(pp, players.current()); // <pts, player>
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
				pairs.get(0).player.addShields(joiners + bonus);
				log.info(out);
				// discard amours, weapons
				discardAmours();
				discardWeapons();
				//set turn to regular before tour
				players = tempPl;
				tempPl = null;
			}
			else if(pairs.size() >= 2) {
				if(round == 1) {
					round++;
					out += "Tied. Here are the tied players going to the Tie Breaker(Round " + round +  ") :\n";
					//discard of everyone's weapons
					discardWeapons();
					//set players to those tied					
					players.setArray(winners);
					for(int i = 0; i < players.size(); i++) {
						out += "Player : " + players.getPlayerAtIndex(i).getName() + "\n";
					}
					log.info(out);
					leftToPlayCard = pairs.size();
					log.info(players.current().getName() + " Its your turn press done when finished");					
				}
				else if(round == 2) { //over Tied players win
					round++;
					out += "Tied again. Tied players win " + (bonus+joiners) + " battle points:\n";
					//tied but this was the last round discard weapons,amours
					discardAmours();
					discardWeapons();
					for(int i = 0; i < players.size(); i++) {
						out += "Player : " + players.getPlayerAtIndex(i).getName() + " With Rank ";
						players.getPlayerAtIndex(i).addShields(bonus+joiners);
						out += players.getPlayerAtIndex(i).getRankS();
						out += " and shields " + players.getPlayerAtIndex(i).getShields() + "\n";
					}
					log.info(out);
					//set turn to regular before tour
					players = tempPl;
					tempPl = null;
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
	
	public void discardAmours() {
		if(players.size() > 0) {
			for(int i = 0; i < players.size(); i++) {
				players.getPlayerAtIndex(i).discardAmoursActive(d);
			}
		}
	}
	
	public void discardWeapons() {
		if(players.size() > 0) {
			for(int i = 0; i < players.size(); i++) {
				players.getPlayerAtIndex(i).discardWeaponsActive(d);
			}
		}
	}
	
	
	
	
}
