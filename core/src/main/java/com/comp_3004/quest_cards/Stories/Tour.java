package com.comp_3004.quest_cards.Stories;

import java.util.ArrayList;
import java.util.LinkedList;
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
import com.comp_3004.quest_cards.player.Player.Rank;
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
	private boolean gameWinMatch = false;
	private boolean complete;
	private boolean playerturn;
	
	public TournamentCard getCurTour() { return tour; }
	
	//getters setters 
	public boolean Complete() { return this.complete; }
	public int getleftToPlayCard() { return leftToPlayCard; }
	public Players getPlayers() { return players; }
	public int getRound() { return round; }
	public boolean isGameWinTour() { return this.gameWinMatch; }
	public int getJoiners() { return this.joiners; }
	public int getLeftAsk() {return this.leftAsk;}
	public ArrayList<Player> getParticipants() { return this.participants; }
	public boolean playerturn() { return this.playerturn; }
	
	//constructor
	/*	input players at the table
	 * 	input Tournament Card that was drawn from storydeck
	 * 	input AdventureDeck 
	 * 	input boolean fina, if this is the game winning tournament due to multiple knights of round table
	 */
	public Tour(Players p, TournamentCard c, AdventureDeck d, boolean fina) {
		this.complete = false;
		players = p;
		tour = c;
		this.d = d;
		this.gameWinMatch = fina;
		if(fina) { //assuming called with more than one champion
			
			//make deep copy of players and store in tempPl if need to return to old turns
			ArrayList<Player> old = new ArrayList<Player>();
			for(Player pl: players.getPlayers()) {
				old.add(pl);
			}
			Players te = new Players(players.getPos(), old.size(), old);
			tempPl = te;
			
			
			
			participants = new ArrayList<Player>();
			for(Player pl: players.getPlayers()) { //reduce players to only knight of table	
				if(pl.getRank() != Rank.KNIGHT_OF_THE_ROUND_TABLE) {
					players.getPlayers().remove(pl);
				}
			}
			leftAsk = players.getNumPlayers();
			joiners = 0;
			//ask knights if they want to play
			for(Player pl : players.getPlayers()) {
				pl.setTour(this);
				pl.setState("tourask");
			}
			if(players.size() == 0) {
				log.info("Error not asking participation due to no players");
			}
			else {
				if(players.current().isAi()) {
					players.current().notifyTurn();
				}
				else {
					log.info(players.current().getName() + " Participate in one final Game Winning Tour " + players.current().getTour().getCurTour().getName() + " ?");
				}
			}
		}
		else if(!fina){
			//set everyone to TourPatricipationState
			for(Player pl : players.getPlayers()) {
				pl.setTour(this);
				pl.setState("tourask");
			}
			leftAsk = p.getNumPlayers();
			participants = new ArrayList<Player>();
			joiners = 0;
			
			if(players.current().isAi()) {
				players.current().notifyTurn();
			}
			else {
				log.info(players.current().getName() + " Participate in Tour " + players.current().getTour().getCurTour().getName() + " ?");
			}
		
		}
			
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
		
		//none left to ask
		if(leftAsk == 0) {
			if(joiners >= 2) {
				this.playerturn = true;
				for(Player ps: participants)
					ps.setState("playtour");
				if(gameWinMatch)
					startGameWinningTour();
				else
					startTour();
			}
			else if(joiners == 1 && gameWinMatch) {
				startGameWinningTour();
			}
			else
				log.info("Can't start Tournament Need atleast 2 players");	
		}
		else {
			Player nextpl = players.peekNext();
			if(nextpl.isAi() && leftAsk > 0) {
				nextpl.notifyTurn(); //do ai work
			}
			else {
				log.info(nextpl.getName() + " Participate in Tour " + nextpl.getTour().getCurTour().getName() + " ?");
			}
		}
			
		
	}	
	
	private void startGameWinningTour() {
		round = 1;
		leftToPlayCard = joiners;
		if(leftToPlayCard == 1) {
			//already have winner
			log.info("Only one player participated in game winning tournament. ");
			determineRoundOutCome();
		}
		
		log.info("Game Winning Tournament Starting............adding card to joiners from adventure deck");
		for(Player ps: participants)
			ps.forceDrawAdventure(d);
		players.setPlayers(participants);
		//players.setPos(0);
		//start first turn of tour
		if(players.current().isAi()) {
			players.current().notifyTurn(); //do ai work
		}
		else {
			log.info(players.current().getName() + " Its your turn press done when finished");
		}
	}
	
	
	private void startTour() {
		round = 1;
		leftToPlayCard = joiners;
		log.info("Tournament Starting............adding card to joiners from adventure deck");
		for(Player ps: participants)
			ps.forceDrawAdventure(d);
		//set players to participants
		tempPl = players; //stored all players
		players.setPlayers(participants);
		//start first turn of tour
		if(players.current().isAi()) {
			players.current().notifyTurn(); //do ai work
		}
		else {
			log.info(players.current().getName() + " Its your turn press done when finished");
		}
		
	}
	
	public class boolPlayers{
		public ArrayList<Player> p;
		public boolean bool;
		private boolPlayers(boolean bool, ArrayList<Player> pl) {
			this.p = pl;
		}
	}
	
	/*
	 * returns true when turn is done
	 * returns players tied, won, check Tour isComplete() whether they are the final winners
	 * not used for participation, only player turns that involve playing cards
	 */
	public boolPlayers doneTurn() {
		boolean done = false;
		ArrayList<Player> res = new ArrayList<Player>();
		boolPlayers re = new boolPlayers(done, res);
		if(players.current().tooManyHandCards()) {
			//if ai player discard until enough
			if(players.current().isAi()) {
				log.info("Ai player too many cards discarding till enough");
				while(players.current().tooManyHandCards()) {
					players.current().setPrevState(players.current().getState());
					players.current().setState("tooManyCards");
				}
			}
			else
				log.info("You have too many cards discard a card, then press done turn");
		}
		else {
			leftToPlayCard--;
			log.info(players.current().getName()+" is done playing cards for round: "+ round);
			if(leftToPlayCard == 0) {
				log.info("No more player turns calculating outcome");
				//players.next();
				res = determineRoundOutCome();			
			}
			else {
				if(players.peekNext().isAi()) {
					//complete ai work
					players.next();
					players.current().notifyTurn(); // ai plays turn
				}
				else {
					log.info(players.current().getName() + " Its your turn press done when finished");
				}
					
			}
			re.bool = true;
			return re;	
		}
		re.bool = false;
		return re;
	}
	
	public ArrayList<Player> determineRoundOutCome() {
		String msg = "Calculating Player battle points and outcome of round " + round;
		log.info(msg);
		ArrayList<Player> winners  = new ArrayList<Player>();
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
			if(pairs.size() > 0) {
				for(int i = 0; i < pairs.size(); i++) {
					winners.add(pairs.get(i).player);
				}
			}
			String out = "";
			if(gameWinMatch == true) {
				if(pairs.size() == 1) {
					pairs.get(0).player.setWon(true);
					log.info(pairs.get(0).player.getName() + " is the sole winner of the Game!");
					log.info("Game Over");
					players = tempPl; //return to full player list
					this.complete = true;
				}
				//more than one winner
				else if(pairs.size() > 1){
					log.info("Here are the final winners of the Game!");
					for(IntPlayerPair p: pairs) {
						p.player.setWon(true);
						log.info(p.player.getName());
					}
					log.info("Game Over");
					players = tempPl; //return to full player list
					this.complete = true;
				}
			}
			else
			{
				if(pairs.size() == 1) {
					// one winner display and tour ends, resets turns to regular sequence before Tour
					out += pairs.get(0).player.getName() + " won the tournament! Gained Sheilds: " + joiners + " + bonus(" + bonus + ") = " + (joiners+bonus);
					log.info(out);
					pairs.get(0).player.addShields(joiners + bonus);
					// discard amours, weapons
					discardAmours();
					discardWeapons();
					log.info("Tournament is OVER.");
					//set turn to regular before tour
					players = tempPl;
					tempPl = null;
					//set states to normal
					setStatePlayers("normal");
					this.complete = true;
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
						
						//notify of next turn
						if(players.current().isAi()) {
							players.current().notifyTurn();
						}
						else {
							log.info(players.current().getName() + " Its your turn press done when finished");	
						}
					}
					else if(round == 2) { //over Tied players win
						round++;
						log.info("Tied again. Tied players win " + (bonus+joiners) + " shields:\n");
						players.setArray(winners);
						for(int i = 0; i < players.size(); i++) {
							String o = players.getPlayerAtIndex(i).getName() + " With Rank ";
							players.getPlayerAtIndex(i).addShields(bonus+joiners);
							o += players.getPlayerAtIndex(i).getRankS();
							o += " and shields " + players.getPlayerAtIndex(i).getShields() + "\n";
							log.info(o);
						}
						//tied but this was the last round discard weapons,amours
						discardAmours();
						discardWeapons();
						//set turn to regular before tour
						players = tempPl;
						tempPl = null;
						//set states to normal
						setStatePlayers("normal");
						this.complete = true;
					}
					else {
						log.info("Error stage greater than 2. Can't play more than two rounds start,tie breaker");
					}
				}
				else {
					log.info("Error calc winners");
				}
			}
		}
		else
			log.info("Error no players to calc points but tour started with players");
		return winners;
	}
	
	public int calcBattlePoints(Player p) {
		int bp = p.getRankBattlePts();
		String out = "";
		out += "------->" + p.getName() + ": has rank: "	+ p.getRankBattlePts() + " battlepoints: ";
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
		//logged, returns battle points
		log.info(out);
		return bp;
	}
	
	public void discardAmours() {
		LinkedList<String> oldState = setStatePlayers("tourcomp");
		if(players.size() > 0) {
			for(int i = 0; i < players.size(); i++) {
				players.getPlayerAtIndex(i).discardAmoursActive(d);
			}
		}
		setStatePlayers(oldState);
	}
	
	public void discardWeapons() {
		LinkedList<String> oldState = setStatePlayers("tourcomp");
		if(players.size() > 0) {
			for(int i = 0; i < players.size(); i++) {
				players.getPlayerAtIndex(i).discardWeaponsActive(d);
			}
		}
		setStatePlayers(oldState);
	}
	
	
	private void setStatePlayers(LinkedList<String> s) {
		if(s.size() == players.size()) {
			int i = 0;
			for(Player pl: players.getPlayers()) {
				pl.setState(s.get(i));
				i++;
			}	
		}
		else
			log.info("Failed setting Player States");
	}
	
	private LinkedList<String> setStatePlayers(String s){
		LinkedList<String> oldState = new LinkedList<String>();
		for(Player pl: players.getPlayers()) {
			oldState.add(pl.getState());
			pl.setState(s);
		}
		return oldState;
	}
	
	
	
	
}
