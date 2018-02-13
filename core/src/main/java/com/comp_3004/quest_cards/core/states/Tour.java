package com.comp_3004.quest_cards.core.states;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import com.comp_3004.quest_cards.Stories.Quest;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.cards.TournamentCard;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Players;

public class Tour {

	static Logger log = Logger.getLogger(Tour.class); //log4j logger
	private TournamentCard tour;					//current tournament
	private Players players;
	private AdventureDeck d;
	private int joiners;
	private ArrayList<Player> participants;
	private int round;
	
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
		participants = new ArrayList<Player>();
		joiners = 0;
		round = 1;
	}
	
	public void tourParticipate(Player p) {
		participants.add(p);
		log.info(p.getName() + " is participating in the tournament");
		joiners++;
		for(Player pl : participants)
			pl.setState("playTour");
		players.next();
	}	
	
	
	
}
