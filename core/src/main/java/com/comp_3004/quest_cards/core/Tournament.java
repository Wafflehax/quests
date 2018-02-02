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
import com.comp_3004.quest_cards.core.match.GameMatch;

import utils.IntPair;

public class Tournament extends GameMatch {
	private AdventureDeck advDeck;
	private volatile Players players;
	private boolean runGameLoop = true;
	ThreadLock lock;
	Logger log;
	private static enum cardModes { PLAY, DISCARD, NONE}; // determine when card was pressed what action it was. ex.going to discard, or activating(playing),none(nothing)
	protected cardModes cardMode;
	private TournamentCard currTour;

	public Tournament(Players p, AdventureDeck a, TournamentCard c, ThreadLock lk, Logger lg) {
		this.players = p;
		this.advDeck = a;
		this.currTour = c;
		this.cardMode = cardModes.NONE; //what to do when card pressed
		this.lock = lk;
		this.log = lg;
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
			log.info("Tournament starting");//player plays their cards face down			
			for(int i = 0; i < players.size(); i++, players.next()) {
				//state = gamestates.PLAYER_TURN;
				cardMode = cardModes.PLAY;
				lock.sleepGame();
				cardMode = cardModes.NONE;
			}
			log.info("Turning over cards and calculating battle points");
			determineWin();
		}
		players = mainTurns; //return to position of main game before tournament, and go to next player
		players.next();
	}
	
	private Stack<IntPair> determineWin() {
		Stack<IntPair> p = new Stack<IntPair>();
		//for now will display results from here
		String out = "";
		for(int i = 0; i < players.size(); players.next(), i++) {
			int battlePts = players.current().getRankBattlePts();
			for(int w = 0; w < players.current().playerActiveCards.size(); w++) {
				out += "Player : " + players.current().getName() + " has rank:"	+ players.current().getRankBattlePts() + " ";
				AdventureCard c = players.current().playerActiveCards.get(w);
				if(c instanceof WeaponCard) {
					 WeaponCard weapon = (WeaponCard)c; battlePts += weapon.getBattlePts();
					 out += weapon.getName() + " : "  + weapon.getBattlePts() + " ";
				}else if(c instanceof AllyCard) {
					 AllyCard ally = (AllyCard)c; battlePts += ally.getBattlePts();
					 out += ally.getName() + " : "  + ally.getBattlePts() + " ";
				}else if(c instanceof AmourCard) {
					AmourCard amour = (AmourCard)c; battlePts += amour.getBattlePts();
					out += amour.getName() + " : " + amour.getBattlePts() + " ";
				}
			}
			out += " \ntotal : " + battlePts;
			log.info(out);
			IntPair calcedPoints = new IntPair(battlePts, i); // <pts, pos>
			p.add(calcedPoints);
			//get winner or tie
			

		}
		return p;
	}
	
	
	public void playCard(AdventureCard c) {
		//TODO: TOURNAMENT:can't have two of same weapons, or more than one amour
		if(currTour instanceof TournamentCard) {
			if(c instanceof WeaponCard || c instanceof AmourCard) {
				if(!players.current().exists(c.getName()))
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
		for(int i = 0; i < players.getNumPlayers() && runGameLoop; i++) {
			//state = gamestates.ASKING_PARTICIPATION;
			lock.sleepGame();
			if(players.current().participateTournament)
				discardCardIfTooMany();
		}
		cardMode = cardModes.NONE; // disable buttons
	}
	
	public void setTournamentParticupation(boolean particp) {
		players.current().participateTournament = particp;
		cardMode = cardModes.NONE; //disable card click from doing anything
		if(particp)
			players.current().forceDrawAdventure(advDeck); //force draw for player
		lock.wake();
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
	
	public TournamentCard getCurrentTour() { return this.currTour; }
	public void setRunGameLoop(boolean b) { this.runGameLoop = b; }
	public boolean getRunGameLoop() { return this.runGameLoop; }

}
