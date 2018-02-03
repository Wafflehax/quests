package com.comp_3004.quest_cards.core;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameModel.cardModes;

import utils.IntPlayerPair;
import utils.utils;

public class Tournament extends GameMatch {
	private AdventureDeck advDeck;
	private volatile Players players;
	private boolean runGameLoop = true;
	ThreadLock lock;
	Logger log;
	private TournamentCard currTour;
	public static final int T_NO_TIE = 0;
	public static int T_TIE = 1;
	public static int T_DOUBLE_TIE = 2;
	private int stage = T_NO_TIE;
	
	public Tournament(Players p, AdventureDeck a, TournamentCard c, ThreadLock lk, Logger lg) {
		this.players = p;
		this.advDeck = a;
		this.currTour = c;
		this.cardMode = cardModes.NONE; //what to do when card pressed
		this.lock = lk;
		this.log = lg;
	}	
	
	//getters / setters
	public Players getPlayers() { return players; }
	public TournamentCard getCurrentTour() { return this.currTour; }
	public void setRunGameLoop(boolean b) { this.runGameLoop = b; }
	public boolean getRunGameLoop() { return this.runGameLoop; }
	public void setParticipation(boolean b) {
		players.current().participateTournament = b;
		cardMode = cardModes.NONE; //disable card click from doing anything
		if(b)
			players.current().forceDrawAdventure(advDeck); //force draw for player
		lock.wake();
	}
	public int getStage() {	return stage; }
	
	public void setTournamentParticupation(boolean particp) {
	}
	
	public void run() {
		Players mainTurns = players;
		System.out.println("-----------------\nPlayer: " + players.current().getName() + " has drawn :" 
				+ " "+ currTour.getType() + " => " + currTour.getName());
		determineParticipants();
		players = players.getTournamentParticipants();
		if(players.isEmpty() || players.size() == 1) {
			log.info("Tournament not held not enough participants: " + players.size());
		}else {
			log.info("Tournament starting , # players: " + players.size());//player plays their cards face down			
			for(int i = 0; i < players.size(); i++) {
				//state = gamestates.PLAYER_TURN;
				cardMode = cardModes.PLAY;
				log.info("Player => " + players.current().getName() + " Its your turn play your hand");
				lock.sleepGame();
				log.info("Turn done");
				players.next();
				cardMode = cardModes.NONE;
			}
			log.info("Turning over cards and calculating battle points");
			//Players winners = determineWin(players);
			//get stage decide if another game
			lock.sleepGame(); 
			
		}
		players = mainTurns; //return to position of main game before tournament, and go to next player
		players.next();
	}
	
	//Assumes input players are participating
	public Players determineWin(Players p) {
		Stack<IntPlayerPair> pairs = new Stack<IntPlayerPair>();
		Players result;
		String out = "";
		for(int i = 0; i < p.size(); p.next(), i++) {
			int battlePts = calcBattlePoints(p.current());
			IntPlayerPair calcedPoints = new IntPlayerPair(battlePts, p.players.get(i)); // <pts, player>
			pairs.add(calcedPoints);
		}
		pairs = utils.getMaxIntPairs(pairs);
		if(p.size() == 1) { // single winner
			Player win = players.players.get(0);
			log.info("Player => " + win.getName() + " won " + pairs.get(0).value + " battlepoints");
			ArrayList<Player> list = new ArrayList<Player>();
			list.add(pairs.get(0).player);
			result = new Players(0, 1, list);
		}
		else {
			stage++;
			ArrayList<Player> list = new ArrayList<Player>();
			int sizeP = 0;
			for(int i = 0; i < pairs.size(); i++) {
				list.add(pairs.get(i).player);
				sizeP++;
			}
			result = new Players(0, sizeP, list);
			log.info("Tie, not coded yet");
		}
		return result; // return winner, tied players
		
	}
	
	
	public int calcBattlePoints(Player p) {
		int bp = p.getRankBattlePts();
		String out = "";
		out += "Player : " + p.getName() + " has rank: "	+ p.getRankBattlePts() + " battlepoints\n";
		for(int w = 0; w < p.playerActiveCards.size(); w++) {
			AdventureCard c = p.playerActiveCards.get(w);
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
	
	public void playCard(AdventureCard c) {
		//TODO: TOURNAMENT:can't have two of same weapons, or more than one amour
		if(currTour instanceof TournamentCard && cardMode == cardModes.PLAY) {
			if(c instanceof WeaponCard || c instanceof AmourCard) {
				if(!players.current().existsActive(c.getName()))
					players.current().playCard(c);
			}
			else if(c instanceof AllyCard)
					players.current().playCard(c);
			else
				log.info("Did not play Ally, Weapon,Amour card: invalid");	
		}
		else
			log.info("Did not play card: invalid");	
	}
	
	private void determineParticipants() {
		cardMode = cardModes.NONE;
		for(int i = 0; i < players.getNumPlayers() && runGameLoop; i++, players.next()) {
			//state = gamestates.ASKING_PARTICIPATION;
			log.info("Player=> " + players.current().getName() + "Participate?");
			lock.sleepGame();
			log.info("WOKe from getting participation");
			if(players.current().participateTournament)
				discardCardIfTooMany();
		}
		cardMode = cardModes.NONE; // disable buttons
	}
	
	//Checks current players hand,if too many disposes card and loops till player acceptable amount
	public void discardCardIfTooMany() {
		while(players.current().tooManyHandCards()) {
			cardMode = cardModes.DISCARD;
			//state = gamestates.DISCARD_HAND_CARD;
			log.info(players.current().getName() + " Too many cards in hand.\n");
			lock.sleepGame();
		}
		cardMode = cardModes.NONE; 
		log.info(players.current().getName() + " has Correct number of cards now.\n");
	}
	
	public void done() { lock.wake(); }

}
