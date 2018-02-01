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

<<<<<<< HEAD:core/src/main/java/com/comp_3004/quest_cards/core/Game.java
import utils.IntPair;


public class Game{
	
	static Logger log = Logger.getLogger(Game.class); //log4j logger
	public static final byte MAX_HAND_SIZE = 12;
	
	public static enum gamestates{ WAITING, ASKING_PARTICIPATION, PLAYER_TURN, START,
		DISCARD_HAND_CARD }; // used for communicating with view
	public static enum cardModes { PLAY, DISCARD, NONE}; // determine when card was pressed what action it was. ex.going to discard, or activating(playing),none(nothing)
	protected String message; //displaying messg on view, ex. battle points of everyone at end of tour
	
	protected AdventureDeck advDeck;
	protected StoryDeck storyDeck;
	
	private int numPlayers;
	protected boolean runGameLoop;
	private volatile Players players;
	private volatile Object lockObj = new Object(); // lock for current thread
	protected volatile ThreadLock lock = new ThreadLock(lockObj);
	protected gamestates state = gamestates.START;
	protected cardModes cardMode = cardModes.NONE; //what to do when card pressed
	
	protected StoryCard currStory;
	
	//getters setters
	protected Player getcurrentTurn() { return players.current();}
	public void setStory(StoryCard c) { currStory = c; }
	public Players getPlayers() { return players; }
	public cardModes getCardMode() { return cardMode; }
	
	// constructor
	public Game() {	}
	
	public void startGame(int numPlayers) {
		this.numPlayers = numPlayers;
		advDeck = new AdventureDeck();
		advDeck.shuffle();
		storyDeck = new StoryDeck();
		storyDeck.shuffle();
		initPlayersStart();
		runGameLoop = true;
		MainGameLoop();
	}
	
	private void MainGameLoop() {
		while(runGameLoop) {
			if(currStory instanceof TournamentCard) {
=======
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
>>>>>>> MVC:core/src/main/java/com/comp_3004/quest_cards/core/Tournament.java
				playTournament();
			}
			runGameLoop = false; // just testing 1 tour currently
		}
	}
	
	// separate loop from main to test just tournaments
	private void LogicLoopTourTesting() {
		
	}	
	
	public void cardPressed(int pos) {
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
<<<<<<< HEAD:core/src/main/java/com/comp_3004/quest_cards/core/Game.java
				+ " "+ currStory.getType() + " => " + currStory.getName());
		determineParticipants();
=======
				+ " "+ currTour.getType() + " => " + currTour.getName());
		//determineParticipants();
		players.players.get(0).participateTournament = true;
		players.players.get(3).participateTournament = true;
>>>>>>> MVC:core/src/main/java/com/comp_3004/quest_cards/core/Tournament.java
		players = players.getTournamentParticipants();
		if(players.isEmpty() || players.size() == 1) {
			log.info("Tournament not held not enough participants: " + players.size());
		}else {
			log.info("Tournament starting");//player plays their cards face down			
			for(int i = 0; i < players.size(); i++, players.next()) {
				state = gamestates.PLAYER_TURN;
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
	
	
	private void playCard(AdventureCard c) {
<<<<<<< HEAD:core/src/main/java/com/comp_3004/quest_cards/core/Game.java
		//TOURNAMENT:can't have two of same weapons, or more than one amour
		if(currStory instanceof TournamentCard) {
=======
		//TODO: TOURNAMENT:can't have two of same weapons, or more than one amour
		if(currTour instanceof TournamentCard) {
>>>>>>> MVC:core/src/main/java/com/comp_3004/quest_cards/core/Tournament.java
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
<<<<<<< HEAD:core/src/main/java/com/comp_3004/quest_cards/core/Game.java
		for(int i = 0; i < numPlayers && runGameLoop; i++, players.next()) {
=======
		for(int i = 0; i < players.getNumPlayers() && runGameLoop; i++) {
>>>>>>> MVC:core/src/main/java/com/comp_3004/quest_cards/core/Tournament.java
			state = gamestates.ASKING_PARTICIPATION;
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
	
<<<<<<< HEAD:core/src/main/java/com/comp_3004/quest_cards/core/Game.java
	//Checks current players hand,if too many disposes card and loops till player acceptable amount
	public void discardCardIfTooMany() {
		while(players.current().tooManyHandCards()) {
			cardMode = cardModes.DISCARD;
			state = gamestates.DISCARD_HAND_CARD;
			log.info(players.current().getName() + " Too many cards in hand.\n");
			lock.sleepGame();
		}
		cardMode = cardModes.NONE; 
		log.info(players.current().getName() + " has Correct number of cards now.\n");
	}
	
	public void done() { lock.wake(); }
}
=======
	public TournamentCard getCurrentTour() { return this.currTour; }
	public void setRunGameLoop(boolean b) { this.runGameLoop = b; }
	public boolean getRunGameLoop() { return this.runGameLoop; }

}
>>>>>>> MVC:core/src/main/java/com/comp_3004/quest_cards/core/Tournament.java
