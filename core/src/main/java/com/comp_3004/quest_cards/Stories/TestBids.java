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
	private TestCard test;
	private int currentNumBids;
	
	//constructor
	public TestBids(ArrayList<Player> p, TestCard t) {
		this.participants = p; 
		this.test = t;
	}
	
	

	

}
