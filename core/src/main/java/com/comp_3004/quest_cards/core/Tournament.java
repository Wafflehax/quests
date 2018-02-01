package com.comp_3004.quest_cards.core;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.AllyCard;
import com.comp_3004.quest_cards.cards.AmourCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.cards.WeaponCard;
import com.comp_3004.quest_cards.core.GameModel.gamestates;

public class Tournament {
	private static enum cardModes { PLAY, DISCARD, NONE}; // determine when card was pressed what action it was. ex.going to discard, or activating(playing),none(nothing)
	protected cardModes cardMode;
	private AdventureDeck advDeck;
	private volatile Players players;
	private boolean runGameLoop = true;
	gamestates state = gamestates.START;
	ThreadLock lock;
	Logger log;
	private TournamentCard currTour;

	public Tournament(Players p, AdventureDeck a, TournamentCard c, gamestates s, ThreadLock lk, Logger lg) {
		this.players = p;
		this.advDeck = a;
		this.currTour = c;
		this.cardMode = cardModes.NONE; //what to do when card pressed
		this.state = s;
		this.lock = lk;
		this.log = lg;
	}
	
	public void runTournament() {
		LogicLoopTourTesting();
	}
	

	// separate loop from main to test just tournaments
	private void LogicLoopTourTesting() {
		while(runGameLoop) {
			//all four tournament types
			//TournamentCard camelot = new TournamentCard("Tournament at Camelot", 3);
			//TournamentCard orkney = new TournamentCard("Tournament at Orkney", 2);
			//TournamentCard tintagel = new TournamentCard("Tournament at Tintagel", 1);
			//TournamentCard york = new TournamentCard("Tournament at York", 0);
			//currTour = york;
			
			if(currTour instanceof TournamentCard) {
				playTournament();
			}
			//add other story cards
			else {}		
			runGameLoop = false; // just testing 1 tour currently
		}
	}	
	
	protected void cardPressed(int pos) {
		if(pos < 0 || pos > players.current().playerHandCards.size()-1) {
			log.debug("invalid card, does not match hand");
		}else {
			AdventureCard c = players.current().getHandCard(pos);
			if(cardMode == cardModes.DISCARD) {
				log.debug("Press mode: DISCARD");
				players.current().discardCard(c, advDeck);
			}else if(cardMode == cardModes.PLAY) {
				playCard(c);
				log.debug("Press mode: PLAY");
			}else if(cardMode == cardModes.NONE) {
				log.debug("Press mode: NONE");
			}else {
				log.debug("UNKNOWN Press mode");
			}	
		}
	}
	
	private void playTournament() {
		Players mainTurns = players;
		System.out.println("-----------------\nPlayer: " + players.current().getName() + " has drawn :" 
				+ " "+ currTour.getType() + " => " + currTour.getName());
		//determineParticipants();
		players.players.get(0).participateTournament = true;
		players.players.get(3).participateTournament = true;
		players = players.getTournamentParticipants();
		if(players.isEmpty() || players.size() == 1) {
			log.info("Tournament not held not enough participants: " + players.size());
		}else {
			log.info("Tournament starting");
			for(int i = 0; i < players.size(); i++, players.next()) {
				state = gamestates.PLAYER_TURN;
				cardMode = cardModes.PLAY;
				lock.sleepGame();
				cardMode = cardModes.NONE;
			}
			log.info("Turning over cards and calculating battle points");
			//for now will display results from here
			String out = "";
			Stack<Integer> bPts = new Stack<Integer>();
			Stack<Integer> bPtsPlayerPos = new Stack<Integer>();
			for(int i = 0; i < players.size(); players.next(), i++) {
				int battlePts = players.current().getRankBattlePts();
				for(int w = 0; w < players.current().playerActiveCards.size(); w++) {
					out += "Player : " + players.current().getName() + " has rank:"
							+ players.current().getRankBattlePts() + " ";
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
				bPts.add(battlePts); bPtsPlayerPos.add(i);
				//get winner or tie
			}
			
		}
		players = mainTurns; //return to position of main game before tournament
	}
	
	private void playCard(AdventureCard c) {
		//TODO: TOURNAMENT:can't have two of same weapons, or more than one amour
		if(currTour instanceof TournamentCard) {
			if(c instanceof WeaponCard || c instanceof AmourCard) {
				if(!players.current().exists(c.getName()))
					players.current().playCard(c);
			}
			else if(c instanceof AllyCard)
					players.current().playCard(c);
			else
				log.info("Did not play Ally,Weapon,Amour card: invalid");	
		}
		else
			log.info("Did not play card: invalid");	
	}
	
	private void determineParticipants() {
		cardMode = cardModes.NONE;
		for(int i = 0; i < players.getNumPlayers() && runGameLoop; i++) {
			state = gamestates.ASKING_PARTICIPATION;
			lock.sleepGame();
			if(players.current().participateTournament) {
				players.current().forceDrawAdventure(advDeck);
				while(players.current().tooManyHandCards()) {
					cardMode = cardModes.DISCARD;
					state = gamestates.DISCARD_HAND_CARD;
					log.info(players.current().getName() + " Too many cards in hand.\n");
					lock.sleepGame();
				}
				log.info(players.current().getName() + " has Correct number of cards now.\n");
				cardMode = cardModes.NONE;
			}
			players.next();
		}
		System.out.println("Done gathering participation data");
		cardMode = cardModes.NONE; // disable buttons
	}
	
	protected void setTournamentParticupation(boolean particp) {
		players.current().participateTournament = particp;
		cardMode = cardModes.NONE;
		lock.wake();
	}
	
	public TournamentCard getCurrentTour() { return this.currTour; }
	public void setRunGameLoop(boolean b) { this.runGameLoop = b; }
	public boolean getRunGameLoop() { return this.runGameLoop; }

}
