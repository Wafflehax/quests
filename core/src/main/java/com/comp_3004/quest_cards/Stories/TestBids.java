package com.comp_3004.quest_cards.Stories;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.TestCard;
import com.comp_3004.quest_cards.player.Player;
import com.comp_3004.quest_cards.player.Players;

public class TestBids {
	
	//attributes
	static Logger log = Logger.getLogger(TestBids.class); //log4j logger
	private ArrayList<Player> participants;	//players who are participating in the bidding war
	private Players players;					//players in the game
	private Player sponsor;
	private TestCard test;
	private int currentBid;
	
	//constructor
	public TestBids(ArrayList<Player> p, Players pl, TestCard t, Player s) {
		this.participants = p; 
		this.players = pl;
		this.test = t;
		this.sponsor = s;
		this.currentBid = 0;
	}
	
	public void start() {
		System.out.println("In test/bids class");
	}
	
	public boolean placeBid(int bid, Player p) {
		if(bid == -1) {
			participants.remove(p);
			log.info(p.getName()+" declined to bid higher and is no longer participating");
			if(players.peekNext() == sponsor)
				players.next();
			players.next();
			if(participants.size() == 1) {	//last man standing
				log.info(players.current().getName()+" wins the bidding war");
				log.info("Cards to discard: "+currentBid);
			}
			return true;
		}
		if(bid > currentBid) {
			currentBid = bid;
			log.info(p.getName()+" bids "+bid);
			if(players.peekNext() == sponsor)
				players.next();
			players.next();
			return true;
		}
		else {
			log.info("Error: "+p.getName()+" bid lower than current bid");
			return true;
		}
	}
	
	public boolean discardedACard() {
		currentBid--;
		log.info("Discardded a card, "+currentBid+" left to discard");
		if(currentBid == 0)
			return false;
		return true;
		
	}
	
	

	

}
