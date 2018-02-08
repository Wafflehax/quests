package com.comp_3004.quest_cards.core;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.comp_3004.quest_cards.cards.AdventureCard;
import com.comp_3004.quest_cards.cards.AdventureDeck;
import com.comp_3004.quest_cards.cards.FoeCard;
import com.comp_3004.quest_cards.cards.QuestCard;
import com.comp_3004.quest_cards.cards.StoryCard;
import com.comp_3004.quest_cards.cards.TestCard;

public class Quest {
	
	//attributes
	static Logger log = Logger.getLogger(Event.class); //log4j logger
	private QuestCard quest;
	private Players players;
	private AdventureDeck advDeck;

	//constructor
	public Quest(QuestCard q, Players p, AdventureDeck d) {
		this.quest = q;
		this.players = p;
		this.advDeck = d;
	}
	
	//abstract this method into submethods that are called such as questSponsorship(), questSetUp(), questPlay()
	public void runQuest() {
		log.info(players.current().getName() + " drew quest " + quest.getName());
		Scanner sc = new Scanner(System.in);
		//game announces the quest drawn (show quest card on screen)
		System.out.printf("Starting %s\n", quest.getName());
		
		//players starting with current player accept/decline sponsoring quest
		int choice;
		boolean questSponsored = false;
		for(Player p : players.getPlayers()) {
			System.out.printf("%s, do you want to Sponsor this Quest? (1: yes 0: no)\n", p.getName());
			choice = sc.nextInt();
			if(choice == 1) {
				//check if player has foe/test cards at least equal to the number of stages in the quest
				int numFoeTest = 0;
				boolean testCardCounted = false;
				for(AdventureCard card : p.getHand()) {
					if(card instanceof FoeCard)
						numFoeTest++;
					if(card instanceof TestCard) {
						if(!testCardCounted) {
							numFoeTest++;
							testCardCounted = true;
						}
					}	
				}
				if(numFoeTest >= quest.getStages()) {
					questSponsored = true;
					System.out.printf("%s sponsored the quest!", p.getName());
					break;
				}
				else
					System.out.printf("%s, you do not have the required cards to sponsor the quest. "
							+ "(Quest requires %s foe+test cards, you have %s\n", p.getName(), quest.getStages(), numFoeTest);
			}
		}
		if(!questSponsored)
			System.out.println("No one sponsored quest, turn over");
		
		//sponsor sets up quest
			//each stage must have a foe or a test
				//only one test card per quest
				//if a stage has a foe it can have weapons added as well
					//no two weapons with the same name
			//each stage must increase in battlepoints (ignore tests)
		//quest set up complete
		
		//remaining players decide if they want to participate in the quest
		
		//playing a quest
			//each participating player draws a card from the adventure deck
			//TODO: finish steps in playing a quest
		
		
	}

}
